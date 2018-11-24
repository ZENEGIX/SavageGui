package ru.zenegix.menu.template;

import ru.zenegix.menu.item.MenuItem;
import ru.zenegix.menu.session.MenuSession;
import ru.zenegix.menu.title.MenuTitle;
import ru.zenegix.menu.type.MenuType;
import ru.zenegix.menu.window.MenuWindow;

import java.util.*;

public class SingleMenuTemplate implements MenuTemplate {

    private final MenuWindow mainWindow;

    private final Map<String, MenuWindow> mainWindowAsMap;

    public SingleMenuTemplate(MenuWindow mainWindow) {
        this.mainWindowAsMap = Collections.singletonMap(
                MenuTemplate.MAIN_WINDOW_ID,
                this.mainWindow = mainWindow
        );
    }

    @Override
    public MenuType getMenuType() {
        return this.mainWindow.getMenuType();
    }

    @Override
    public MenuTitle getTitle() {
        return this.mainWindow.getTitle();
    }

    @Override
    public List<MenuItem> getItems() {
        return this.mainWindow.getItems();
    }

    @Override
    public boolean canView(MenuSession menuSession) {
        return this.mainWindow.canView(menuSession);
    }

    @Override
    public boolean update() {
        return this.mainWindow.update();
    }

    @Override
    public Map<String, MenuWindow> getAllWindows() {
        return this.mainWindowAsMap;
    }

    @Override
    public Optional<MenuWindow> findWindow(String id) {
        return id.equalsIgnoreCase(MenuTemplate.MAIN_WINDOW_ID)
                ? Optional.of(this.mainWindow)
                : Optional.empty();
    }

    @Override
    public Map<String, MenuWindow> getWindowsPerSession(MenuSession session) {
        return this.canView(session)
                ? this.mainWindowAsMap
                : Collections.emptyMap();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SingleMenuTemplate that = (SingleMenuTemplate) o;

        return Objects.equals(this.mainWindow, that.mainWindow) &&
                Objects.equals(this.mainWindowAsMap, that.mainWindowAsMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.mainWindow,
                this.mainWindowAsMap
        );
    }
}
