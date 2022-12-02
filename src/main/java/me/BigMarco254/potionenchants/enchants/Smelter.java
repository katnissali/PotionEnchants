package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.PotionEnchants;
import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.ListenerEnchant;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Smelter extends ListenerEnchant {

    Map<Material, Material> itemConverstions;

    public Smelter() {
        super(EnchantmentTarget.TOOL, "Smelter", 111, 1, EnchantCategory.TOOLS, "smelter");
        ConfigurationSection section = PotionEnchants.getInstance().getConfig().getConfigurationSection("enchants.smelter");
        Set<String> keys = section.getKeys(false);
        itemConverstions = new HashMap<>(keys.size());
        for (String k : keys) {
            try {
                itemConverstions.put(Material.getMaterial(k), Material.getMaterial(section.getString(k)));
            }catch (Exception e) {
                System.out.println("PotionEnchants: Error in Smelter config section, unknown material: "  + k);
            }
        }
    }

    @Override
    public void onBlockBreak(BlockBreakEvent e, int level) {
        Collection<ItemStack> oldDrops = e.getBlock().getDrops(e.getPlayer().getItemInHand());
        e.setCancelled(true);
        e.getBlock().setType(Material.AIR);
        for (ItemStack old : oldDrops) {
            if (itemConverstions.containsKey(old.getType())) {
                ItemStack newItem = new ItemStack(itemConverstions.get(old.getType()), old.getAmount());
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), newItem);
            }else {
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), old);
            }
        }
    }
}
