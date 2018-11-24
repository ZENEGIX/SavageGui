package ru.zenegix.menu.animation;

import ru.zenegix.menu.item.MenuItem;
import ru.zenegix.menu.session.MenuSession;

import java.util.Collection;

public interface AnimatedMenuItem extends MenuItem {

    void onFrame(Collection<MenuSession> sessions);

}
