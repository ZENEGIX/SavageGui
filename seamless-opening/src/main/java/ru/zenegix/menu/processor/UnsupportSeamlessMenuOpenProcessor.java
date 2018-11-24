package ru.zenegix.menu.processor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class UnsupportSeamlessMenuOpenProcessor extends AbstractSeamlessMenuOpenProcessor {

    public UnsupportSeamlessMenuOpenProcessor(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void openSeamless(Player bukkitPlayer, Inventory bukkitInventory) {
        bukkitPlayer.openInventory(bukkitInventory);
    }

}
