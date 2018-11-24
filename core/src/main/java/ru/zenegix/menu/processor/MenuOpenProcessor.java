package ru.zenegix.menu.processor;

import ru.zenegix.menu.session.MenuSession;

public interface MenuOpenProcessor {

    OpenProcessorResponse open(MenuSession menuSession);

    OpenProcessorResponse update(MenuSession menuSession);

    OpenProcessorResponse updateItem(MenuSession menuSession, int index);

}
