package ru.zenegix.menu.item;

import ru.zenegix.menu.icon.MenuIcon;
import ru.zenegix.menu.session.MenuSession;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class StaticMenuItem extends AbstractMenuItem {

    private final MenuIcon menuIcon;

    public StaticMenuItem(MenuIcon menuIcon, Consumer<MenuItemClick> clickHandler) {
        this(menuIcon, (menuSession -> true), clickHandler);
    }

    public StaticMenuItem(MenuIcon menuIcon, Predicate<MenuSession> viewPredication, Consumer<MenuItemClick> clickHandler) {
        super(viewPredication, clickHandler);

        this.menuIcon = menuIcon;
    }

    @Override
    public MenuIcon getIcon(MenuSession menuSession) {
        return this.menuIcon;
    }

    public static StaticMenuItem.Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractMenuItemBuilder<Builder, StaticMenuItem> {

        private MenuIcon menuIcon;

        @Override
        public StaticMenuItem build() {
            return new StaticMenuItem(this.menuIcon, this.viewCheck, this.clickHandler);
        }

        public Builder setMenuIcon(MenuIcon menuIcon) {
            this.menuIcon = menuIcon;

            return this;
        }

    }

}
