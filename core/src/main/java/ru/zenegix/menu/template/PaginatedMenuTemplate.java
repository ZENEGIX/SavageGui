package ru.zenegix.menu.template;

import ru.zenegix.menu.item.MenuItem;
import ru.zenegix.menu.session.MenuSession;
import ru.zenegix.menu.title.MenuTitle;
import ru.zenegix.menu.type.MenuType;
import ru.zenegix.menu.window.MenuWindow;

import java.util.*;

public class PaginatedMenuTemplate implements MenuTemplate {

    private final MenuWindow mainWindow;

    private final Map<String, MenuWindow> windows;

    public PaginatedMenuTemplate(MenuWindow mainWindow) {
        this(mainWindow, new HashMap<>());
    }

    public PaginatedMenuTemplate(MenuWindow mainWindow, Map<String, MenuWindow> windows) {
        this.mainWindow = mainWindow;
        this.windows = windows;

        this.windows.put(MenuTemplate.MAIN_WINDOW_ID, this.mainWindow);
    }

    @Override
    public Map<String, MenuWindow> getAllWindows() {
        return Collections.unmodifiableMap(this.windows);
    }

    @Override
    public Optional<MenuWindow> findWindow(String id) {
        return Optional.ofNullable(this.windows.get(id));
    }

    @Override
    public MenuType getMenuType() {
        return this.getMainWindow().getMenuType();
    }

    @Override
    public MenuTitle getTitle() {
        return this.getMainWindow().getTitle();
    }

    @Override
    public List<MenuItem> getItems() {
        return this.getMainWindow().getItems();
    }

    @Override
    public boolean canView(MenuSession menuSession) {
        return this.getMainWindow().canView(menuSession);
    }

    @Override
    public boolean update() {
        return this.getMainWindow().update();
    }

    public MenuWindow getMainWindow() {
        return this.mainWindow;
    }

    public static Builder builder(MenuWindow mainWindow) {
        return new Builder(mainWindow);
    }

    public static class Builder {

        private final MenuWindow mainWindow;

        private final Map<String, MenuWindow> windows = new HashMap<>();

        public Builder(MenuWindow mainWindow) {
            this.mainWindow = mainWindow;
        }

        public Builder registerWindow(String id, MenuWindow window) {
            this.windows.put(id.toLowerCase(), window);

            return this;
        }

        public PaginatedMenuTemplate build() {
            return new PaginatedMenuTemplate(this.mainWindow, this.windows);
        }

    }

}
