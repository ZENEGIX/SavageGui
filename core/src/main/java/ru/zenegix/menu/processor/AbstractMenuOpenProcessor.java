package ru.zenegix.menu.processor;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.zenegix.menu.icon.MenuIcon;
import ru.zenegix.menu.item.MenuItem;
import ru.zenegix.menu.session.MenuSession;
import ru.zenegix.menu.window.MenuWindow;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractMenuOpenProcessor implements MenuOpenProcessor {

    @Override
    public OpenProcessorResponse open(MenuSession menuSession) {
        Inventory toOpen;

        if (menuSession.getInventory() != null && menuSession.getInventory().getTitle().equals(menuSession.getTitle())) {
            toOpen = menuSession.getInventory();
        } else {
            toOpen = menuSession.getActiveWindow().getMenuType().createInventory(menuSession, menuSession.getTitle());
        }

        OpenProcessorResponse response = this.fill(menuSession, toOpen);
        menuSession.getOwner().openInventory(response.getInventory());

        return response;
    }

    @Override
    public OpenProcessorResponse update(MenuSession menuSession) {
        Inventory inventory = menuSession.getInventory();
        inventory.clear();

        OpenProcessorResponse response = this.fill(menuSession, inventory);

        menuSession.getOwner().updateInventory();

        return response;
    }

    public OpenProcessorResponse updateItem(MenuSession menuSession, int index) {
        Inventory inventory = menuSession.getInventory();
        Map<Integer, MenuItem> indexes = new HashMap<>();
        Optional<MenuItem> item = menuSession.getItemByIndex(index);

        item.ifPresent((menuItem) -> {
            if (menuItem.canView(menuSession)) {
                MenuIcon icon = menuItem.getIcon(menuSession);
                int iconIndex = icon.getIndex();

                if (iconIndex >= 0 && iconIndex < inventory.getSize()) {
                    inventory.setItem(icon.getIndex(), icon.getItemStack());
                    indexes.put(icon.getIndex(), menuItem);
                }
            }
        });

        return new OpenProcessorResponse(inventory, indexes);
    }

    protected OpenProcessorResponse fill(MenuSession menuSession, Inventory inventory) {
        Map<Integer, MenuItem> items = new HashMap<>();
        MenuWindow menuWindow = menuSession.getActiveWindow();
        int size = inventory.getSize();

        for (MenuItem item : menuWindow.getItems()) {
            if (!item.canView(menuSession)) {
                continue;
            }

            MenuIcon icon = item.getIcon(menuSession);
            int index = icon.getIndex();
            ItemStack itemStack = icon.getItemStack();

            if (index < 0 || index > size) {
                continue;
            }

            inventory.setItem(icon.getIndex(), itemStack);
            items.put(index, item);
        }

        return new OpenProcessorResponse(inventory, items);
    }

}
