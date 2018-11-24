package ru.zenegix.menu.item;

import ru.zenegix.menu.session.MenuSession;

import java.util.function.Consumer;

public class ClickActions {

    public static Consumer<MenuItemClick> navigateAction(String windowId) {
        return navigateAction(windowId, click -> {});
    }

    public static Consumer<MenuItemClick> navigateAction(String windowId, Consumer<MenuItemClick> postHandler) {
        return click -> {
            MenuSession menuSession = click.getMenuSession();
            menuSession.open(menuSession.getActiveMenuTemplate(), windowId);

            postHandler.accept(click);
        };
    }

}
