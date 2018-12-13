package ru.zenegix.menu.predicate;

import org.bukkit.entity.Player;

public interface NotNeedCloseMenuPredicate {

    boolean isNotNeedCloseMenu(Player player);

    void setNotNeedCloseMenu(Player player);

    void removeNotNeedCloseMenu(Player player);

}
