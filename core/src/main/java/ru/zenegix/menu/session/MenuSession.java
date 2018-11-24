package ru.zenegix.menu.session;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import ru.zenegix.menu.Updatable;
import ru.zenegix.menu.item.MenuItem;
import ru.zenegix.menu.template.MenuTemplate;
import ru.zenegix.menu.window.MenuWindow;

import java.util.Map;
import java.util.Optional;

public interface MenuSession extends InventoryHolder, Updatable {

    String getTitle();

    Map<Integer, MenuItem> getItems();

    Optional<MenuItem> getItemByIndex(int index);

    MenuTemplate getActiveMenuTemplate();

    MenuWindow getActiveWindow();

    Player getOwner();

    boolean open(MenuTemplate menuTemplate, String windowId);

    default boolean open(MenuTemplate menuTemplate) {
        return this.open(menuTemplate, MenuTemplate.MAIN_WINDOW_ID);
    }

    void close();

    boolean update(int index);

}
