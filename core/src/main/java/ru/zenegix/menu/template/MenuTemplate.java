package ru.zenegix.menu.template;

import ru.zenegix.menu.session.MenuSession;
import ru.zenegix.menu.window.MenuWindow;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface MenuTemplate extends MenuWindow {

    String MAIN_WINDOW_ID = "root_window";

    Map<String, MenuWindow> getAllWindows();

    Optional<MenuWindow> findWindow(String id);

    default Map<String, MenuWindow> getWindowsPerSession(MenuSession session) {
        return this.getAllWindows().entrySet().stream()
                .filter(entry -> entry.getValue().canView(session))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
