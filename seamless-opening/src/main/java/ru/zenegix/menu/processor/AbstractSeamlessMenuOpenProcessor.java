package ru.zenegix.menu.processor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ru.zenegix.menu.session.MenuSession;

public abstract class AbstractSeamlessMenuOpenProcessor extends AbstractMenuOpenProcessor {

    @Override
    public OpenProcessorResponse open(MenuSession menuSession) {
        Inventory toOpen;
        boolean needSeamless = false;

        if (menuSession.getInventory() != null) {
            needSeamless = true;
        }

        if (needSeamless && menuSession.getInventory().getTitle().equals(menuSession.getTitle())) {
            toOpen = menuSession.getInventory();
        } else {
            toOpen = menuSession.getActiveWindow().getMenuType().createInventory(menuSession, menuSession.getTitle());
        }

        OpenProcessorResponse response = this.fill(menuSession, toOpen);

        if (needSeamless) {
            this.openSeamless(menuSession.getOwner(), toOpen);
        } else {
            menuSession.getOwner().openInventory(response.getInventory());
        }

        return response;
    }

    public abstract void openSeamless(Player bukkitPlayer, Inventory bukkitInventory);

}
