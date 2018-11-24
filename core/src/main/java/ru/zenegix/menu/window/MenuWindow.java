package ru.zenegix.menu.window;

import ru.zenegix.menu.Updatable;
import ru.zenegix.menu.type.MenuType;
import ru.zenegix.menu.Permissible;
import ru.zenegix.menu.item.MenuItem;
import ru.zenegix.menu.title.MenuTitle;

import java.util.List;

public interface MenuWindow extends Permissible, Updatable {

    MenuType getMenuType();

    MenuTitle getTitle();

    List<MenuItem> getItems();

}
