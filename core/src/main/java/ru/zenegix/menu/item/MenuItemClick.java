package ru.zenegix.menu.item;

import ru.zenegix.menu.session.MenuSession;

public class MenuItemClick {

    private final MenuSession menuSession;

    private final MenuItem clicked;

    public MenuItemClick(MenuSession menuSession, MenuItem clicked) {
        this.menuSession = menuSession;
        this.clicked = clicked;
    }

    public MenuSession getMenuSession() {
        return this.menuSession;
    }

    public MenuItem getClicked() {
        return this.clicked;
    }
}
