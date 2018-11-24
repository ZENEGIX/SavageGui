package ru.zenegix.menu.icon;

import org.bukkit.inventory.ItemStack;

public class CoordinatableMenuIcon extends SimpleMenuIcon {

    public CoordinatableMenuIcon(int y, int x, ItemStack itemStack) {
        super((y * 9) + x, itemStack);
    }

}
