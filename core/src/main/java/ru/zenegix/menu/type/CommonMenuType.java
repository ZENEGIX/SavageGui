package ru.zenegix.menu.type;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import ru.zenegix.menu.session.MenuSession;

public class CommonMenuType implements MenuType {

    private int size;

    CommonMenuType(int size) {
        this.size = size;
    }

    @Override
    public Inventory createInventory(MenuSession menuSession, String title) {
        return Bukkit.createInventory(menuSession, this.size, title);
    }

}
