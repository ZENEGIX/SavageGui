package ru.zenegix.menu.example;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ru.zenegix.menu.MenuManager;
import ru.zenegix.menu.animation.AnimateMenuStrategy;
import ru.zenegix.menu.animation.SimpleAnimatedMenuItem;
import ru.zenegix.menu.icon.SimpleMenuIcon;
import ru.zenegix.menu.item.ClickActions;
import ru.zenegix.menu.item.MenuItem;
import ru.zenegix.menu.item.PerPlayerMenuItem;
import ru.zenegix.menu.item.StaticMenuItem;
import ru.zenegix.menu.processor.SeamlessMenuOpenProcessorFactory;
import ru.zenegix.menu.template.MenuTemplate;
import ru.zenegix.menu.template.PaginatedMenuTemplate;
import ru.zenegix.menu.template.SingleMenuTemplate;
import ru.zenegix.menu.utils.ItemStackBuilder;
import ru.zenegix.menu.window.MenuWindow;

import java.util.concurrent.atomic.AtomicBoolean;

public class ExamplePlugin extends JavaPlugin {

    private MenuManager menuManager;

    private MenuTemplate menuTemplate;

    private PaginatedMenuTemplate paginatedMenuTemplate;

    @Override
    public void onEnable() {
        this.menuManager = new MenuManager(this, SeamlessMenuOpenProcessorFactory.get(this));
        AnimateMenuStrategy animateMenuStrategy = this.menuManager.createAnimateMenuStrategy(5);
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);

        MenuItem animated = SimpleAnimatedMenuItem.builder().onFrame(((menuSession, previousFrame) -> {
            ItemStack item = previousFrame == null
                    ? new ItemStack(Material.STONE)
                    : previousFrame.getItemStack();

            int index;

            if (previousFrame == null) {
                index = 0;
            } else {
                index = previousFrame.getIndex();

                if (index == 8) {
                    atomicBoolean.set(false);
                } else if (index == 0) {
                    atomicBoolean.set(true);
                }

                if (atomicBoolean.get()) {
                    index++;
                } else {
                    index--;
                }
            }

            return new SimpleMenuIcon(index, item);
        })).build();

        MenuWindow menuWindow = this.menuManager.createWindowBuilder()
                .setTitle(session -> "Привет, " + session.getOwner().getName())
                .setSize(18)
                .addItems(animated)
                .build();
        animateMenuStrategy.makeAnimatable(menuWindow);
        this.menuTemplate = new SingleMenuTemplate(menuWindow);

        {
            MenuWindow mainWindow = this.menuManager.createWindowBuilder()
                    .setTitle("Типа главное окно, пон да?")
                    .addItems(StaticMenuItem.builder().setMenuIcon(new SimpleMenuIcon(0, new ItemStack(Material.STONE)))
                            .withClickHandler(ClickActions.navigateAction("xz"))
                            .build()
                    ).build();
            MenuWindow playersInfoWindow = this.menuManager.createWindowBuilder()
                    .setTitle(menuSession -> "Привет, " + menuSession.getOwner().getName())
                    .addItems(PerPlayerMenuItem.builder().setIconRequest(menuSession -> {
                        return new SimpleMenuIcon(3, ItemStackBuilder.create()
                                .setMaterial(Material.STONE)
                                .withItemMeta().setName("Твой ник: " + menuSession.getOwner()
                                        .getName())
                                .and().build()
                        );
                    }).build())
                    .build();

            this.paginatedMenuTemplate = PaginatedMenuTemplate.builder(mainWindow)
                    .registerWindow("xz", playersInfoWindow)
                    .build();
        }

        this.getCommand("menu").setExecutor((sender, command, label, args) -> {
            this.menuManager.open((Player) sender, this.paginatedMenuTemplate);

            return true;
        });
    }

}
