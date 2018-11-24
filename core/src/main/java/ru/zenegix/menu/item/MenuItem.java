package ru.zenegix.menu.item;

import ru.zenegix.menu.Permissible;
import ru.zenegix.menu.icon.MenuIcon;
import ru.zenegix.menu.session.MenuSession;

import java.util.function.Consumer;

public interface MenuItem extends Permissible {

    MenuIcon getIcon(MenuSession menuSession);

    Consumer<MenuItemClick> getClickHandler();

}
