package ru.zenegix.menu.animation;

import ru.zenegix.menu.window.MenuWindow;

public interface AnimateMenuStrategy {

    MenuWindow makeAnimatable(MenuWindow menuWindow);

}
