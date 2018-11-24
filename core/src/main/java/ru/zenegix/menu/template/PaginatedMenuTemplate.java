package ru.zenegix.menu.template;

import ru.zenegix.menu.window.MenuWindow;

import java.util.*;

public class PaginatedMenuTemplate extends AbstractMenuTemplate {

    private final MenuWindow rootWindow;

    private final Map<String, MenuWindow> windows;

    public PaginatedMenuTemplate(MenuWindow rootWindow) {
        this(rootWindow, new HashMap<>());
    }

    public PaginatedMenuTemplate(MenuWindow rootWindow, Map<String, MenuWindow> windows) {
        this.rootWindow = rootWindow;
        this.windows = windows;

        this.windows.put(MenuTemplate.MAIN_WINDOW_ID, this.rootWindow);
    }

    @Override
    public Map<String, MenuWindow> getAllWindows() {
        return Collections.unmodifiableMap(this.windows);
    }

    @Override
    public MenuWindow getRootWindow() {
        return this.rootWindow;
    }

    public static Builder builder(MenuWindow rootWindow) {
        return new Builder(rootWindow);
    }

    public static class Builder {

        private final MenuWindow rootWindow;

        private final Map<String, MenuWindow> windows = new HashMap<>();

        public Builder(MenuWindow rootWindow) {
            this.rootWindow = rootWindow;
        }

        public Builder registerWindow(String id, MenuWindow window) {
            this.windows.put(id.toLowerCase(), window);

            return this;
        }

        public PaginatedMenuTemplate build() {
            return new PaginatedMenuTemplate(this.rootWindow, this.windows);
        }

    }

}
