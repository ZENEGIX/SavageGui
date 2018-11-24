package ru.zenegix.menu.title;

import ru.zenegix.menu.session.MenuSession;

import java.util.function.Function;

public class PerPlayerMenuTitle implements MenuTitle {

    private Function<MenuSession, String> titleFunction;

    public PerPlayerMenuTitle(Function<MenuSession, String> titleFunction) {
        this.titleFunction = titleFunction;
    }

    @Override
    public String getTitle(MenuSession menuSession) {
        return this.titleFunction.apply(menuSession);
    }

}
