package ru.zenegix.menu.select;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.zenegix.menu.MenuManager;
import ru.zenegix.menu.icon.SimpleMenuIcon;
import ru.zenegix.menu.item.MenuItem;
import ru.zenegix.menu.item.StaticMenuItem;
import ru.zenegix.menu.template.PaginatedMenuTemplate;
import ru.zenegix.menu.title.MenuTitle;
import ru.zenegix.menu.window.MenuWindow;
import ru.zenegix.menu.window.MenuWindowBuilder;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

@SuppressWarnings("unused")
public class SelectMenuBuilder<K> {

    private final MenuManager menuManager;

    private final List<K> keys;

    private final int maxPages;

    private BiFunction<Player, K, ItemStack> converter;

    private TitleFunction titleFunction = (player, page, maxPages) -> MenuTitle.DEFAULT_TITLE;

    private Predicate<Integer> indexPredicate = (index) -> true;

    private ItemsFunction items = (player, page, maxPages) -> Collections.emptyList();

    private BiConsumer<Player, K> clickHandler = (player, key) -> {};

    private BiPredicate<Player, K> viewPredicate = (player, key) -> true;

    public SelectMenuBuilder(MenuManager menuManager, List<K> keys, int maxPages) {
        this.menuManager = menuManager;
        this.keys = keys;
        this.maxPages = maxPages;
    }

    public SelectMenuBuilder(MenuManager menuManager, List<K> keys) {
        this(menuManager, keys, 64);
    }

    public SelectMenuBuilder<K> withConverter(BiFunction<Player, K, ItemStack> converter) {
        this.converter = converter;

        return this;
    }

    public SelectMenuBuilder<K> withTitleFunction(TitleFunction titleFunction) {
        this.titleFunction = titleFunction;

        return this;
    }

    public SelectMenuBuilder<K> withIndexPredicate(Predicate<Integer> indexPredicate) {
        this.indexPredicate = indexPredicate;

        return this;
    }

    public SelectMenuBuilder<K> withItems(ItemsFunction items) {
        this.items = items;

        return this;
    }

    public SelectMenuBuilder<K> withClickHandler(BiConsumer<Player, K> clickHandler) {
        this.clickHandler = clickHandler;

        return this;
    }

    public SelectMenuBuilder<K> withViewPredicate(BiPredicate<Player, K> viewPredicate) {
        this.viewPredicate = viewPredicate;

        return this;
    }

    public PaginatedMenuTemplate build(Player player) {
        int maxPages = this.calculateMaxPages(player);
        AtomicInteger lastIndex = new AtomicInteger(0);

        List<MenuWindow> list = new ArrayList<>();
        MenuWindow temp;
        int page = 0;

        while ((temp = this.createWindow(player, lastIndex, page, maxPages)) != null) {
            list.add(temp);

            if (++page > this.maxPages) {
                throw new RuntimeException("a large number of pages (" + page + ")");
            }
        }

        Map<String, MenuWindow> windows = new HashMap<>();

        for (int i = 1; i < list.size(); i++) {
            windows.put(String.valueOf(i), list.get(i));
        }

        return new PaginatedMenuTemplate(list.get(0), windows);
    }

    private MenuWindow createWindow(Player player, AtomicInteger lastIndexAsAtomic, int page, int maxPages) {
        int lastIndex = lastIndexAsAtomic.get();
        int remainder = this.keys.size() - lastIndex;

        if (remainder < 1) {
            return null;
        }

        MenuWindowBuilder builder = this.menuManager.createWindowBuilder()
                .setRows(6)
                .setTitle(this.titleFunction.apply(player, page, maxPages));

        for (int i = 0; i < 54 && lastIndex < this.keys.size(); i++) {
            if (!this.indexPredicate.test(i)) {
                continue;
            }

            K key = this.keys.get(lastIndex++);

            if (!this.viewPredicate.test(player, key)) {
                continue;
            }

            builder.addItems(StaticMenuItem.builder()
                    .setMenuIcon(new SimpleMenuIcon(i, this.converter.apply(player, key)))
                    .withClickHandler(click -> this.clickHandler.accept(player, key))
                    .build());
        }

        builder.addItems(this.items.apply(player, page, maxPages));
        lastIndexAsAtomic.set(lastIndex);

        return builder.build();
    }

    private int calculateMaxPages(Player player) {
        long maxItemsInPage = IntStream.range(0, 54).filter(this.indexPredicate::test).count();
        long itemsForPlayer = this.keys.stream().filter(key -> this.viewPredicate.test(player, key)).count();
        double pages = ((double) itemsForPlayer) / ((double) maxItemsInPage);

        return (int) Math.ceil(pages);
    }

    @FunctionalInterface
    public interface ItemsFunction {

        /**
         * Создать предметы для страницы
         *
         * @param player игрок
         * @param page индекс страницы, для которой надо создать предметы
         * @param maxPages количество страниц
         * @return список предметов
         */
        List<MenuItem> apply(Player player, int page, int maxPages);

    }

    @FunctionalInterface
    public interface TitleFunction {

        /**
         * Создать тайтл
         *
         * @param player игрок
         * @param page индекс текущей страницы
         * @param maxPages количество страниц
         * @return тайтл
         */
        String apply(Player player, int page, int maxPages);

    }

}
