package ru.zenegix.menu.processor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import ru.zenegix.menu.session.MenuSession;

public abstract class AbstractSeamlessMenuOpenProcessor extends AbstractMenuOpenProcessor {

    private final Plugin plugin;

    public AbstractSeamlessMenuOpenProcessor(Plugin plugin) {
        this.plugin = plugin;
    }

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
            Player player = menuSession.getOwner();

            player.setMetadata("seamless-opening", new FixedMetadataValue(this.plugin, true));

            this.openSeamless(menuSession.getOwner(), toOpen);
        } else {
            menuSession.getOwner().openInventory(response.getInventory());
        }

        return response;
    }

    public abstract void openSeamless(Player bukkitPlayer, Inventory bukkitInventory);

}
