package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.ListenerEnchant;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class ObsidianDestroyer extends ListenerEnchant {
    public ObsidianDestroyer() {
        super(EnchantmentTarget.TOOL, "Obsidian Destroyer", 113, 1, EnchantCategory.TOOLS, "obsidian-destroyer");
    }

    public void onPlayerInteract(PlayerInteractEvent e, int level) {
        if (e.getAction() != Action.LEFT_CLICK_BLOCK || e.getClickedBlock() == null || e.getClickedBlock().getType() != Material.OBSIDIAN) return;

        e.setCancelled(true);
        BlockBreakEvent blockBreakEvent = new BlockBreakEvent(e.getClickedBlock(), e.getPlayer());
        Bukkit.getPluginManager().callEvent(blockBreakEvent);

        if(!blockBreakEvent.isCancelled()) {

            Collection<ItemStack> drops = e.getClickedBlock().getDrops(e.getItem());
            e.getClickedBlock().setType(Material.AIR);
            drops.forEach(d -> e.getClickedBlock().getWorld().dropItemNaturally(e.getClickedBlock().getLocation(), d));

        }
    }
}
