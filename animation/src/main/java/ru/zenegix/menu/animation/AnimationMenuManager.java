package ru.zenegix.menu.animation;

import ru.zenegix.menu.item.MenuItemClick;
import ru.zenegix.menu.session.MenuSession;

import java.util.*;
import java.util.function.Consumer;

public class AnimationMenuManager {

    private final Collection<AnimationScript> scripts = new ArrayList<>();

    public void registerScript(AnimationScript script) {
        if (this.scripts.contains(script)) {
            return;
        }

        script.load();
        this.scripts.add(script);
    }

    public void animate(MenuSession menuSession, AnimationScript animationScript) {
        this.animate(menuSession, new HashMap<>(), animationScript);
    }

    public void animate(MenuSession menuSession, Map<String, Object> args, AnimationScript animationScript) {
        if (animationScript != null) {
            animationScript.invoke(menuSession, args);
        }
    }

    public void animate(MenuItemClick click, AnimationScript animationScript) {
        this.animate(click, new HashMap<>(), animationScript);
    }

    public void animate(MenuItemClick click, Map<String, Object> args, AnimationScript animationScript) {
        args.put("slot", click.getSlot());
        this.animate(click.getMenuSession(), args, animationScript);
    }

    public Consumer<MenuItemClick> startAnimationAfterClick(String scriptName) {
        return this.startAnimationAfterClick(Collections.emptyMap(), scriptName);
    }

    public Consumer<MenuItemClick> startAnimationAfterClick(Map<String, Object> data, String scriptName) {
        return this.startAnimationAfterClick(
                data,
                this.findScriptByName(scriptName).orElse(null)
        );
    }

    public Consumer<MenuItemClick> startAnimationAfterClick(AnimationScript script) {
        return this.startAnimationAfterClick(Collections.emptyMap(), script);
    }

    public Consumer<MenuItemClick> startAnimationAfterClick(Map<String, Object> data, AnimationScript script) {
        return ((click) -> this.animate(click, data, script));
    }

    public Optional<AnimationScript> findScriptByName(String name) {
        return this.scripts.stream().filter(script -> script.getName().equals(name)).findFirst();
    }

}
