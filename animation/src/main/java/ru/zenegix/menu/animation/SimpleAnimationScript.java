package ru.zenegix.menu.animation;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import ru.zenegix.menu.session.MenuSession;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;

public class SimpleAnimationScript implements AnimationScript {

    private final Plugin plugin;

    private final String name;

    private final File script;

    private final Collection<MenuSession> watchingAnimation = new ArrayList<>();

    private final ClassLoader pluginClassLoader;

    private ScriptEngine scriptEngine;

    public SimpleAnimationScript(Plugin plugin, String name, File script) {
        this.plugin = plugin;
        this.name = name;
        this.script = script;
        this.pluginClassLoader = this.plugin.getClass().getClassLoader();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void load() {
        try {
            this.scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");

            this.scriptEngine.put("plugin", this.plugin);
            this.scriptEngine.eval(new FileReader(this.script));
        } catch (Exception e) {
            throw new RuntimeException("Could not load animation script", e);
        }
    }

    @Override
    public void invoke(MenuSession menuSession, Object... args) {
        if (this.watchingAnimation.contains(menuSession)) {
            return;
        }

        Invocable invocable = (Invocable) this.scriptEngine;

        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            try {
                Thread.currentThread().setContextClassLoader(this.pluginClassLoader);
                this.watchingAnimation.add(menuSession);

                invocable.invokeFunction("animation", menuSession, args);
            } catch (Exception e) {
                throw new RuntimeException("An error occurred during the execution of the script", e);
            } finally {
                Thread.currentThread().setContextClassLoader(classLoader);
                this.watchingAnimation.remove(menuSession);
            }
        });
    }

}
