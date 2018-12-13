package ru.zenegix.menu.processor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class UnsupportSeamlessMenuOpenProcessor extends AbstractSeamlessMenuOpenProcessor {

    @Override
    public void openSeamless(Player bukkitPlayer, Inventory bukkitInventory) {
        bukkitPlayer.openInventory(bukkitInventory);
    }

}
