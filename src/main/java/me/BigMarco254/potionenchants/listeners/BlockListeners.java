package me.BigMarco254.potionenchants.listeners;

import me.BigMarco254.potionenchants.objects.ListenerEnchant;
import me.BigMarco254.potionenchants.objects.PEnchant;
import me.BigMarco254.potionenchants.utils.Utils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class BlockListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent e) {
        ItemStack itemInHand = e.getPlayer().getInventory().getItemInMainHand();

        if (!e.isCancelled() && itemInHand.getType() != Material.AIR) {
            Map<PEnchant, Integer> itemEnchants = Utils.getCustomEnchants(itemInHand);
            itemEnchants.forEach((ench, level) -> {
                if (ench.isListenerEnchant()) {
                    ((ListenerEnchant)ench).onBlockBreak(e, level);
                }
            });
        }

    }

}
