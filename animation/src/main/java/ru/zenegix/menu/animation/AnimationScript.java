package ru.zenegix.menu.animation;

import ru.zenegix.menu.session.MenuSession;

public interface AnimationScript {

    String getName();

    void load();

    void invoke(MenuSession menuSession, Object... args);

}
