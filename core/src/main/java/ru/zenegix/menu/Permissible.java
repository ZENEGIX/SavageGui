package ru.zenegix.menu;

import ru.zenegix.menu.session.MenuSession;

public interface Permissible {

    boolean canView(MenuSession menuSession);

}
