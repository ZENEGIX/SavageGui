package ru.zenegix.menu.animation;

import org.bukkit.Bukkit;
import ru.zenegix.menu.MenuManager;
import ru.zenegix.menu.session.MenuSession;
import ru.zenegix.menu.window.MenuWindow;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleAnimateMenuStrategy implements AnimateMenuStrategy {

    private Map<MenuWindow, List<AnimatedMenuItem>> windows = new HashMap<>();

    public SimpleAnimateMenuStrategy(MenuManager menuManager, int period) {
        Bukkit.getScheduler().runTaskTimer(menuManager.getPlugin(), () -> {
            this.windows.forEach((window, items) -> {
                Collection<MenuSession> sessions = menuManager.getSessionResolver().findSessionsByWindow(window);

                items.forEach(item -> item.onFrame(sessions));
            });
        }, period, period);
    }

    @Override
    public MenuWindow makeAnimatable(MenuWindow menuWindow) {
        this.windows.put(
                menuWindow,
                menuWindow.getItems().stream()
                        .filter(item -> item instanceof AnimatedMenuItem)
                        .map(item -> (AnimatedMenuItem) item)
                        .collect(Collectors.toList())
        );

        return menuWindow;
    }
}
