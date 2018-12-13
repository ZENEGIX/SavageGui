package ru.zenegix.menu.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryHolder;
import ru.zenegix.menu.MenuManager;
import ru.zenegix.menu.item.MenuItemClick;
import ru.zenegix.menu.predicate.NotNeedCloseMenuPredicate;
import ru.zenegix.menu.session.MenuSession;
import ru.zenegix.menu.session.MenuSessionResolver;

public class EventListener implements Listener {

    private final MenuSessionResolver sessionResolver;

    private final NotNeedCloseMenuPredicate notNeedCloseMenuPredicate;

    public EventListener(MenuSessionResolver sessionResolver, NotNeedCloseMenuPredicate notNeedCloseMenuPredicate) {
        this.sessionResolver = sessionResolver;
        this.notNeedCloseMenuPredicate = notNeedCloseMenuPredicate;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }

        // Проверяем, открыто ли меню. Если да, то отменяем ивент
        if (event.getWhoClicked().getOpenInventory().getTopInventory().getHolder() instanceof MenuSession) {
            event.setCancelled(true);
        }

        InventoryHolder holder = event.getClickedInventory().getHolder();

        if (!(holder instanceof MenuSession)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        MenuSession menuSession = this.sessionResolver.getSessionByPlayer(player);

        if (menuSession == null) {
            return;
        }

        menuSession.getItemByIndex(event.getRawSlot()).ifPresent(menuItem -> {
            if (menuItem.canView(menuSession)) {
                menuItem.getClickHandler().accept(new MenuItemClick(menuSession, menuItem, event.getRawSlot()));
            }
        });
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        this.sessionResolver.inactiveSession(event.getPlayer());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (!this.notNeedCloseMenuPredicate.isNotNeedCloseMenu(player)) {
            this.sessionResolver.getSessionByPlayer(player).close();
        }
    }

}
