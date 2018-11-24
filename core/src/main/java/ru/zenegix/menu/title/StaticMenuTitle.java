package ru.zenegix.menu.title;

import ru.zenegix.menu.session.MenuSession;

public class StaticMenuTitle implements MenuTitle {

    private String title;

    public StaticMenuTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle(MenuSession menuSession) {
        return this.title;
    }

}
