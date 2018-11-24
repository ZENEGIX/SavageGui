package ru.zenegix.menu.window;

import ru.zenegix.menu.item.MenuItem;
import ru.zenegix.menu.session.MenuSession;
import ru.zenegix.menu.session.MenuSessionResolver;
import ru.zenegix.menu.title.MenuTitle;
import ru.zenegix.menu.type.MenuType;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class SimpleMenuWindow implements MenuWindow {

    private final MenuSessionResolver menuSessionResolver;

    private final MenuTitle title;

    private final MenuType menuType;

    private final Predicate<MenuSession> canViewChecker;

    private final List<MenuItem> items;

    protected SimpleMenuWindow(MenuSessionResolver menuSessionResolver, MenuTitle title, MenuType menuType,
                               Predicate<MenuSession> canViewChecker, List<MenuItem> items) {
        this.menuSessionResolver = menuSessionResolver;
        this.title = title;
        this.menuType = menuType;
        this.canViewChecker = canViewChecker;
        this.items = items;
    }

    @Override
    public MenuType getMenuType() {
        return this.menuType;
    }

    @Override
    public MenuTitle getTitle() {
        return this.title;
    }

    @Override
    public List<MenuItem> getItems() {
        return this.items;
    }

    @Override
    public boolean canView(MenuSession menuSession) {
        return this.canViewChecker.test(menuSession);
    }

    @Override
    public boolean update() {
        Collection<MenuSession> sessions = this.menuSessionResolver.findSessionsByWindow(this);

        sessions.forEach(MenuSession::update);

        return !sessions.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SimpleMenuWindow that = (SimpleMenuWindow) o;

        return Objects.equals(this.menuSessionResolver, that.menuSessionResolver) &&
                Objects.equals(this.title, that.title) &&
                Objects.equals(this.menuType, that.menuType) &&
                Objects.equals(this.canViewChecker, that.canViewChecker) &&
                Objects.equals(this.items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.menuSessionResolver,
                this.title,
                this.menuType,
                this.canViewChecker,
                this.items
        );
    }

}
