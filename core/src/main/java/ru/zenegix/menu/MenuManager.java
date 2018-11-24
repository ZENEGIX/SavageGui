package ru.zenegix.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.zenegix.menu.animation.AnimateMenuStrategy;
import ru.zenegix.menu.animation.SimpleAnimateMenuStrategy;
import ru.zenegix.menu.listener.EventListener;
import ru.zenegix.menu.processor.DefaultMenuOpenProcessor;
import ru.zenegix.menu.processor.MenuOpenProcessor;
import ru.zenegix.menu.session.MenuSessionResolver;
import ru.zenegix.menu.session.SimpleMenuSessionResolver;
import ru.zenegix.menu.template.MenuTemplate;
import ru.zenegix.menu.window.MenuWindowBuilder;

public class MenuManager {

    private final Plugin plugin;

    private final MenuSessionResolver sessionResolver;

    public MenuManager(Plugin plugin) {
        this(plugin, DefaultMenuOpenProcessor.INSTANCE);
    }

    public MenuManager(Plugin plugin, MenuOpenProcessor menuOpenProcessor) {
        this(plugin, new SimpleMenuSessionResolver(menuOpenProcessor));
    }

    public MenuManager(Plugin plugin, MenuSessionResolver sessionResolver) {
        Bukkit.getPluginManager().registerEvents(
                new EventListener(this.sessionResolver = sessionResolver),
                this.plugin = plugin
        );
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public MenuSessionResolver getSessionResolver() {
        return this.sessionResolver;
    }

    public MenuWindowBuilder createWindowBuilder() {
        return new MenuWindowBuilder(this.sessionResolver);
    }

    public void open(Player player, MenuTemplate menuTemplate) {
        this.sessionResolver.getSessionByPlayer(player).open(menuTemplate);
    }

    public void open(Player player, MenuTemplate menuTemplate, String windowId) {
        this.sessionResolver.getSessionByPlayer(player).open(menuTemplate, windowId);
    }

    public AnimateMenuStrategy createAnimateMenuStrategy(int period) {
        return new SimpleAnimateMenuStrategy(this, period);
    }

}
