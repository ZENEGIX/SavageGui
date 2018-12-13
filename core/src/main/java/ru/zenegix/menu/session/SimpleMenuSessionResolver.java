package ru.zenegix.menu.session;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.zenegix.menu.predicate.NotNeedCloseMenuPredicate;
import ru.zenegix.menu.processor.MenuOpenProcessor;

import java.util.*;

public class SimpleMenuSessionResolver extends AbstractSessionResolver {

    private final NotNeedCloseMenuPredicate notNeedCloseMenuPredicate;

    private final Map<Player, MenuSession> sessions = new HashMap<>();

    private final MenuOpenProcessor menuOpenProcessor;

    public SimpleMenuSessionResolver(NotNeedCloseMenuPredicate notNeedCloseMenuPredicate, MenuOpenProcessor menuOpenProcessor) {
        this.notNeedCloseMenuPredicate = notNeedCloseMenuPredicate;
        this.menuOpenProcessor = menuOpenProcessor;
    }

    @Override
    public MenuSession getSessionByPlayer(Player player) {
        return this.sessions.computeIfAbsent(player, (key) -> new SimpleMenuSession(this.notNeedCloseMenuPredicate, key, this.menuOpenProcessor));
    }

    @Override
    public Collection<MenuSession> getSessions() {
        return Collections.unmodifiableCollection(this.sessions.values());
    }

    @Override
    public void inactiveSession(Player player) {
        this.sessions.remove(player);
    }

}
