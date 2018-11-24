package ru.zenegix.menu.icon;

import org.bukkit.inventory.ItemStack;
import ru.zenegix.menu.item.StaticMenuItem;

public class SimpleMenuIcon implements MenuIcon {

    private int index;

    private ItemStack itemStack;

    public SimpleMenuIcon(int index, ItemStack itemStack) {
        this.index = index;
        this.itemStack = itemStack;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public StaticMenuItem.Builder toMenuItemBuilder() {
        return StaticMenuItem.builder().setMenuIcon(this);
    }

}
