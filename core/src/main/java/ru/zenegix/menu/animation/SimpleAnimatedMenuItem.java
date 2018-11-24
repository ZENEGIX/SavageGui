package ru.zenegix.menu.animation;

import org.bukkit.inventory.Inventory;
import ru.zenegix.menu.icon.MenuIcon;
import ru.zenegix.menu.item.AbstractMenuItem;
import ru.zenegix.menu.item.AbstractMenuItemBuilder;
import ru.zenegix.menu.item.MenuItemClick;
import ru.zenegix.menu.session.MenuSession;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SimpleAnimatedMenuItem extends AbstractMenuItem implements AnimatedMenuItem {

    private final BiFunction<MenuSession, MenuIcon, MenuIcon> onFrameFunction;

    private final Map<MenuSession, MenuIcon> cache = new WeakHashMap<>();

    public SimpleAnimatedMenuItem(BiFunction<MenuSession, MenuIcon, MenuIcon> onFrameFunction,
                                  Predicate<MenuSession> viewPredication,
                                  Consumer<MenuItemClick> clickHandler) {
        super(viewPredication, clickHandler);

        this.onFrameFunction = onFrameFunction;
    }

    @Override
    public void onFrame(Collection<MenuSession> sessions) {
        this.cache.entrySet().removeIf(entry -> !sessions.contains(entry.getKey()));

        for (MenuSession menuSession : sessions) {
            if (!this.canView(menuSession)) {
                continue;
            }

            MenuIcon previous = this.cache.get(menuSession);
            MenuIcon icon = this.getIcon(menuSession);

            if (previous != null && previous.getIndex() != icon.getIndex()) {
                Inventory inventory = menuSession.getInventory();

                inventory.clear(previous.getIndex());
                inventory.setItem(icon.getIndex(), icon.getItemStack());

                menuSession.getOwner().updateInventory();
            } else {
                menuSession.getInventory().setItem(icon.getIndex(), icon.getItemStack());
            }
        }
    }

    @Override
    public MenuIcon getIcon(MenuSession menuSession) {
        MenuIcon icon = this.onFrameFunction.apply(menuSession, this.cache.get(menuSession));

        this.cache.put(menuSession, icon);

        return icon;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractMenuItemBuilder<Builder, AnimatedMenuItem> {

        private BiFunction<MenuSession, MenuIcon, MenuIcon> onFrameFunction;

        public Builder onFrame(BiFunction<MenuSession, MenuIcon, MenuIcon> onFrameFunction) {
            this.onFrameFunction = onFrameFunction;

            return this;
        }

        @Override
        public AnimatedMenuItem build() {
            return new SimpleAnimatedMenuItem(this.onFrameFunction, this.viewCheck, this.clickHandler);
        }

    }

}
