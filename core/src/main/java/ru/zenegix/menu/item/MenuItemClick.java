package ru.zenegix.menu.item;

import ru.zenegix.menu.session.MenuSession;

public class MenuItemClick {

    private final MenuSession menuSession;

    private final MenuItem clicked;

    private final int slot;

    public MenuItemClick(MenuSession menuSession, MenuItem clicked, int slot) {
        this.menuSession = menuSession;
        this.clicked = clicked;
        this.slot = slot;
    }

    public MenuSession getMenuSession() {
        return this.menuSession;
    }

    public MenuItem getClicked() {
        return this.clicked;
    }

    public int getSlot() {
        return slot;
    }

}
