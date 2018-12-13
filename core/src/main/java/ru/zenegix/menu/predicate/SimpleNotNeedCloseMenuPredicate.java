package ru.zenegix.menu.predicate;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class SimpleNotNeedCloseMenuPredicate implements NotNeedCloseMenuPredicate {

    private final Plugin plugin;

    private final MetadataValue metadataValue;

    private final String metadataKey;

    private static final String DEFAULT_METADATA_KEY = "savage-menu.not_need_close_menu";

    public SimpleNotNeedCloseMenuPredicate(Plugin plugin) {
        this(plugin, DEFAULT_METADATA_KEY);
    }

    public SimpleNotNeedCloseMenuPredicate(Plugin plugin, String metadataKey) {
        this.metadataValue = new FixedMetadataValue(
                this.plugin = plugin,
                true
        );
        this.metadataKey = metadataKey;
    }

    @Override
    public boolean isNotNeedCloseMenu(Player player) {
        return player.hasMetadata(this.metadataKey);
    }

    @Override
    public void setNotNeedCloseMenu(Player player) {
        player.setMetadata(this.metadataKey, this.metadataValue);
    }

    @Override
    public void removeNotNeedCloseMenu(Player player) {
        player.removeMetadata(this.metadataKey, this.plugin);
    }
}
