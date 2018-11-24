package ru.zenegix.menu.processor;

import org.bukkit.inventory.Inventory;
import ru.zenegix.menu.item.MenuItem;

import java.util.Map;

public class OpenProcessorResponse {

    private Inventory inventory;

    private Map<Integer, MenuItem> items;

    public OpenProcessorResponse(Inventory inventory, Map<Integer, MenuItem> items) {
        this.inventory = inventory;
        this.items = items;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Map<Integer, MenuItem> getItems() {
        return items;
    }

}
