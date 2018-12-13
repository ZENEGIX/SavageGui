package ru.zenegix.menu.animation;

import ru.zenegix.menu.session.MenuSession;

import java.util.Map;

public interface AnimationScript {

    String getName();

    void load();

    void invoke(MenuSession menuSession, Map<String, Object> args);

    boolean isWatching(MenuSession menuSession, Integer slot);

}
