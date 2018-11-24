package ru.zenegix.menu.session;

import ru.zenegix.menu.template.MenuTemplate;
import ru.zenegix.menu.window.MenuWindow;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractSessionResolver implements MenuSessionResolver {

    @Override
    public Collection<MenuSession> findSessionsByTemplate(MenuTemplate menuTemplate, Collection<MenuSession> exclude) {
        return this.excludeAndCollect(session -> menuTemplate.equals(session.getActiveMenuTemplate()), exclude);
    }

    @Override
    public Collection<MenuSession> findSessionsByWindow(MenuWindow menuWindow, Collection<MenuSession> exclude) {
        return this.excludeAndCollect(session -> menuWindow.equals(session.getActiveWindow()), exclude);
    }

    private Collection<MenuSession> excludeAndCollect(Predicate<MenuSession> predicate, Collection<MenuSession> exclude) {
        return this.excludeAndCollect(this.getSessions().stream().filter(predicate), exclude);
    }

    private Collection<MenuSession> excludeAndCollect(Stream<MenuSession> stream, Collection<MenuSession> exclude) {
        if (!exclude.isEmpty()) {
            stream = stream.filter(session -> !exclude.contains(session));
        }

        return stream.collect(Collectors.toList());
    }

}
