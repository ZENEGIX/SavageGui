package ru.zenegix.menu.animation;

import org.bukkit.plugin.Plugin;
import ru.zenegix.menu.MenuManager;
import ru.zenegix.menu.item.MenuItemClick;
import ru.zenegix.menu.processor.MenuOpenProcessor;
import ru.zenegix.menu.session.MenuSession;
import ru.zenegix.menu.session.MenuSessionResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class AnimationMenuManager extends MenuManager {

    private final Collection<AnimationScript> scripts = new ArrayList<>();

    public AnimationMenuManager(Plugin plugin) {
        super(plugin);
    }

    public AnimationMenuManager(Plugin plugin, MenuOpenProcessor menuOpenProcessor) {
        super(plugin, menuOpenProcessor);
    }

    public AnimationMenuManager(Plugin plugin, MenuSessionResolver sessionResolver) {
        super(plugin, sessionResolver);
    }

    public void registerScript(AnimationScript script) {
        if (this.scripts.contains(script)) {
            return;
        }

        script.load();
        this.scripts.add(script);
    }

    public void animate(MenuSession menuSession, AnimationScript animationScript) {
        this.animate(menuSession, null, animationScript);
    }

    public void animate(MenuSession menuSession, Integer itemIndex, AnimationScript animationScript) {
        animationScript.invoke(menuSession, itemIndex);
    }

    public void animate(MenuItemClick click, AnimationScript animationScript) {
        this.animate(click.getMenuSession(), click.getSlot(), animationScript);
    }

    public Optional<AnimationScript> findScriptByName(String name) {
        return this.scripts.stream().filter(script -> script.getName().equals(name)).findFirst();
    }

}
