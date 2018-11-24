package ru.zenegix.menu.template;

import ru.zenegix.menu.item.MenuItem;
import ru.zenegix.menu.session.MenuSession;
import ru.zenegix.menu.title.MenuTitle;
import ru.zenegix.menu.type.MenuType;
import ru.zenegix.menu.window.MenuWindow;

import java.util.List;
import java.util.Optional;

public abstract class AbstractMenuTemplate implements MenuTemplate {

    @Override
    public Optional<MenuWindow> findWindow(String id) {
        return Optional.ofNullable(this.getAllWindows().get(id));
    }

    @Override
    public MenuType getMenuType() {
        return this.getRootWindow().getMenuType();
    }

    @Override
    public MenuTitle getTitle() {
        return this.getRootWindow().getTitle();
    }

    @Override
    public List<MenuItem> getItems() {
        return this.getRootWindow().getItems();
    }

    @Override
    public boolean canView(MenuSession menuSession) {
        return this.getRootWindow().canView(menuSession);
    }

    @Override
    public boolean update() {
        return this.getRootWindow().update();
    }

    public abstract MenuWindow getRootWindow();

}
