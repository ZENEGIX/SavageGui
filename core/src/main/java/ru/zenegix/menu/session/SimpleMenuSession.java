package ru.zenegix.menu.session;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ru.zenegix.menu.item.MenuItem;
import ru.zenegix.menu.processor.MenuOpenProcessor;
import ru.zenegix.menu.processor.OpenProcessorResponse;
import ru.zenegix.menu.template.MenuTemplate;
import ru.zenegix.menu.window.MenuWindow;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SimpleMenuSession implements MenuSession {

    private final Player owner;

    private final MenuOpenProcessor openProcessor;

    private MenuTemplate activeTemplate;

    private MenuWindow activeWindow;

    private Inventory inventory;

    private Map<Integer, MenuItem> items;

    public SimpleMenuSession(Player owner, MenuOpenProcessor openProcessor) {
        this.owner = owner;
        this.openProcessor = openProcessor;
    }

    @Override
    public String getTitle() {
        return this.getActiveWindow() == null
                ? null
                : this.getActiveWindow().getTitle().getTitle(this);
    }

    @Override
    public Map<Integer, MenuItem> getItems() {
        return Collections.unmodifiableMap(this.items);
    }

    @Override
    public Optional<MenuItem> getItemByIndex(int index) {
        return Optional.ofNullable(this.items.get(index));
    }

    @Override
    public MenuTemplate getActiveMenuTemplate() {
        return this.activeTemplate;
    }

    @Override
    public MenuWindow getActiveWindow() {
        return this.activeWindow;
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public boolean open(MenuTemplate menuTemplate, String windowId) {
        Optional<MenuWindow> optionalMenuWindow = menuTemplate.findWindow(windowId);

        if (!optionalMenuWindow.isPresent()) {
            return false;
        }

        this.activeTemplate = menuTemplate;
        this.activeWindow = optionalMenuWindow.get();

        OpenProcessorResponse response = this.openProcessor.open(this);

        this.inventory = response.getInventory();
        this.items = response.getItems();

        return true;
    }

    @Override
    public void close() {
        if (this.inventory == null) {
            return;
        }

        this.inventory = null;
        this.activeWindow = null;
        this.activeTemplate = null;
        this.items = null;

        this.owner.closeInventory();
    }

    @Override
    public boolean update(int index) {
        if (!this.items.containsKey(index)) {
            return false;
        }

        this.openProcessor.updateItem(this, index);

        return true;
    }

    @Override
    public boolean update() {
        if (this.inventory == null) {
            return false;
        }

        OpenProcessorResponse response = this.openProcessor.update(this);
        this.items = response.getItems();

        return true;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SimpleMenuSession that = (SimpleMenuSession) o;

        return Objects.equals(this.owner, that.owner) &&
                Objects.equals(this.openProcessor, that.openProcessor) &&
                Objects.equals(this.activeTemplate, that.activeTemplate) &&
                Objects.equals(this.activeWindow, that.activeWindow) &&
                Objects.equals(this.inventory, that.inventory) &&
                Objects.equals(this.items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.owner,
                this.openProcessor,
                this.activeTemplate,
                this.activeWindow,
                this.inventory,
                this.items
        );
    }
}
