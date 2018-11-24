package ru.zenegix.menu.processor.v1_8;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftContainer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import ru.zenegix.menu.processor.AbstractSeamlessMenuOpenProcessor;

// Спасибо Дмитрию Манчинскому (https://vk.com/xtrafrancyz) за кусочек кода, который позволяет бесшовно открывать инвентари
@SuppressWarnings("Duplicates")
public class SeamlessMenuOpenProcessor extends AbstractSeamlessMenuOpenProcessor {

    public SeamlessMenuOpenProcessor(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void openSeamless(Player bukkitPlayer, Inventory bukkitInventory) {
        IInventory iinventory = ((CraftInventory) bukkitInventory).getInventory();
        EntityPlayer player = ((CraftPlayer) bukkitPlayer).getHandle();
        Container container = CraftEventFactory.callInventoryOpenEvent(player, new ContainerChest(player.inventory, iinventory, player));

        if (container != null) {
            if (player.activeContainer != null) {

                CraftEventFactory.handleInventoryCloseEvent(player);
                player.activeContainer.b(player);
            }

            int containerCounter = player.nextContainerCounter();

            player.playerConnection.sendPacket(new PacketPlayOutOpenWindow(
                    containerCounter,
                    CraftContainer.getNotchInventoryType(bukkitInventory.getType()),
                    new ChatComponentText(bukkitInventory.getTitle()),
                    bukkitInventory.getSize()
            ));
            player.activeContainer = container;
            player.activeContainer.windowId = containerCounter;
            player.activeContainer.addSlotListener(player);
        }
    }

}
