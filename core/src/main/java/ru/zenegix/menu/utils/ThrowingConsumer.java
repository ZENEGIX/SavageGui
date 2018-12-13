package ru.zenegix.menu.utils;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingConsumer<T> extends Consumer<T> {

    @Override
    default void accept(T t) {
        try {
            this.consume(t);
        } catch (Throwable throwable) {
            throw new RuntimeException("could not accept a consumer", throwable);
        }
    }

    void consume(T t) throws Throwable;

}
