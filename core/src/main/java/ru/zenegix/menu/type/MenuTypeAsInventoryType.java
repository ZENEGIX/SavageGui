package ru.zenegix.menu.type;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import ru.zenegix.menu.session.MenuSession;

public class MenuTypeAsInventoryType implements MenuType {

    private InventoryType inventoryType;

    MenuTypeAsInventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }

    @Override
    public Inventory createInventory(MenuSession menuSession, String title) {
        return Bukkit.createInventory(menuSession, this.inventoryType, title);
    }

}
