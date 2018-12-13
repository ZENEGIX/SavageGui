package ru.zenegix.menu.animation;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import ru.zenegix.menu.session.MenuSession;
import ru.zenegix.menu.utils.ItemStackBuilder;
import ru.zenegix.menu.utils.ThrowingConsumer;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public class SimpleAnimationScript implements AnimationScript {

    private final Plugin plugin;

    private final String name;

    private final File script;

    private final Multimap<MenuSession, Integer> watchingAnimation = ArrayListMultimap.create();

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
            this.loadConstants();
            this.scriptEngine.eval(new FileReader(this.script));
        } catch (Exception e) {
            throw new RuntimeException("Could not load animation script", e);
        }
    }

    @Override
    public void invoke(MenuSession menuSession, Map<String, Object> args) {
        if (this.watchingAnimation.containsKey(menuSession)) {
            return;
        }

        Invocable invocable = (Invocable) this.scriptEngine;
        int slot = ((Number) args.getOrDefault("slot", -1)).intValue();

        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            try {
                Thread.currentThread().setContextClassLoader(this.pluginClassLoader);
                this.watchingAnimation.put(menuSession, slot);

                invocable.invokeFunction("animation", menuSession, args);
            } catch (Exception e) {
                throw new RuntimeException("An error occurred during the execution of the script", e);
            } finally {
                Thread.currentThread().setContextClassLoader(classLoader);
                this.watchingAnimation.remove(menuSession, slot);
            }
        });
    }

    @Override
    public boolean isWatching(MenuSession menuSession, Integer slot) {
        return slot == null
                ? this.watchingAnimation.containsKey(menuSession)
                : this.watchingAnimation.containsEntry(menuSession, slot);
    }

    private void loadConstants() {
        this.scriptEngine.put("plugin", this.plugin);
        this.scriptEngine.put("sleep", (ThrowingConsumer<Integer>) Thread::sleep);
        this.scriptEngine.put("sleepLong", (ThrowingConsumer<Long>) Thread::sleep);
        this.scriptEngine.put("itemStackBuilder", (Supplier<ItemStackBuilder>) ItemStackBuilder::create);
    }

    public static AnimationScript load(Plugin plugin, String scriptName) {
        String fileName = scriptName + ".js";
        File file = new File(plugin.getDataFolder(), fileName);

        if (!file.exists()) {
            try {
                Files.copy(plugin.getResource(fileName), file.toPath());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return new SimpleAnimationScript(plugin, scriptName, file);
    }

}
