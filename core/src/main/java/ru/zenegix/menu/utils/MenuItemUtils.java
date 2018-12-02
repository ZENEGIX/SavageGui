package ru.zenegix.menu.utils;

import ru.zenegix.menu.item.MenuItemClick;
import ru.zenegix.menu.template.MenuTemplate;

import java.util.function.Consumer;

public class MenuItemUtils {

    public static Consumer<MenuItemClick> moveToPage(int page) {
        return moveToPage(page == 0 ? MenuTemplate.MAIN_WINDOW_ID : String.valueOf(page));
    }

    public static Consumer<MenuItemClick> moveToPage(String windowId) {
        return (click) -> {
            MenuTemplate menuTemplate = click.getMenuSession().getActiveMenuTemplate();

            click.getMenuSession().open(menuTemplate, windowId);
        };
    }

}
