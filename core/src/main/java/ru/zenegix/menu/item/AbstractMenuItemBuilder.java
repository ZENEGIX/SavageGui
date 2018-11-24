package ru.zenegix.menu.item;

import ru.zenegix.menu.session.MenuSession;

import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class AbstractMenuItemBuilder<B extends AbstractMenuItemBuilder<B, I>, I extends MenuItem> {

    protected Consumer<MenuItemClick> clickHandler = (click) -> {};

    protected Predicate<MenuSession> viewCheck = (menuSession -> true);

    public B withClickHandler(Consumer<MenuItemClick> clickHandler) {
        this.clickHandler = this.clickHandler.andThen(clickHandler);

        return (B) this;
    }

    public B withViewCheck(Predicate<MenuSession> viewCheck) {
        this.viewCheck = viewCheck.and(viewCheck);

        return (B) this;
    }

    public abstract I build();

}
