package ru.zenegix.menu.title;

import ru.zenegix.menu.session.MenuSession;

public interface MenuTitle {

    String DEFAULT_TITLE = "SavageMenu";

    String getTitle(MenuSession menuSession);

}
