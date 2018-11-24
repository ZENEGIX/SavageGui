package ru.zenegix.menu.item;

import ru.zenegix.menu.session.MenuSession;

import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class AbstractMenuItem implements MenuItem {

    private final Predicate<MenuSession> viewPredication;

    private final Consumer<MenuItemClick> clickHandler;

    public AbstractMenuItem(Predicate<MenuSession> viewPredication, Consumer<MenuItemClick> clickHandler) {
        this.viewPredication = viewPredication;
        this.clickHandler = clickHandler;
    }

    @Override
    public boolean canView(MenuSession menuSession) {
        return this.viewPredication.test(menuSession);
    }

    @Override
    public Consumer<MenuItemClick> getClickHandler() {
        return this.clickHandler;
    }

}
