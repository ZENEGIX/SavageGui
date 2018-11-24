package ru.zenegix.menu.item;

import ru.zenegix.menu.icon.MenuIcon;
import ru.zenegix.menu.session.MenuSession;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class PerPlayerMenuItem extends AbstractMenuItem {

    private final Function<MenuSession, MenuIcon> iconRequest;

    public PerPlayerMenuItem(Function<MenuSession, MenuIcon> iconRequest, Predicate<MenuSession> viewPredication, Consumer<MenuItemClick> clickHandler) {
        super(viewPredication, clickHandler);

        this.iconRequest = iconRequest;
    }

    @Override
    public MenuIcon getIcon(MenuSession menuSession) {
        return this.iconRequest.apply(menuSession);
    }

    public static PerPlayerMenuItem.Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractMenuItemBuilder<Builder, PerPlayerMenuItem> {

        private Function<MenuSession, MenuIcon> iconRequest;

        @Override
        public PerPlayerMenuItem build() {
            return new PerPlayerMenuItem(this.iconRequest, this.viewCheck, this.clickHandler);
        }

        public Builder setIconRequest(Function<MenuSession, MenuIcon> iconRequest) {
            this.iconRequest = iconRequest;

            return this;
        }

    }

}
