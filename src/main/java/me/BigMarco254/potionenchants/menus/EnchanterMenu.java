package me.BigMarco254.potionenchants.menus;

import me.BigMarco254.potionenchants.PotionEnchants;
import me.BigMarco254.potionenchants.menus.api.InventoryClickType;
import me.BigMarco254.potionenchants.menus.api.Menu;
import me.BigMarco254.potionenchants.menus.api.MenuAPI;
import me.BigMarco254.potionenchants.menus.api.MenuItem;
import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.utils.ItemBuilder;
import me.BigMarco254.potionenchants.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnchanterMenu {

    public EnchanterMenu(Player p) {
        final Menu menu = MenuAPI.getInstance().createMenu(Utils.colorize(PotionEnchants.getInstance().getConfig().getString("enchanter-menu.menu-title")), 1);
        final int currentExp = Utils.getTotalExperience(p);
        final double currentMoney = PotionEnchants.getInstance().getEconomy().getBalance(p);
        for (int i = 0; i < 9; ++i) {
            menu.addMenuItem(new MenuItem(){

                @Override
                public void onClick(Player p, InventoryClickType type) {
                }

                @Override
                public ItemStack getItemStack() {
                    return new ItemBuilder(Material.GLASS_PANE, 15).setName("&r").getStack();
                }
            }, i);
        }

        // 0 1 0 0 1 0 0 1 0
        // 0 0 1 0 1 0 1 0 0
        // 2, 4, 6
        menu.addMenuItem(new EnchanterMenuItem(EnchantCategory.TOOLS, p, "tools"), 2);
        menu.addMenuItem(new EnchanterMenuItem(EnchantCategory.ARMOR, p, "armor"), 4);
        menu.addMenuItem(new EnchanterMenuItem(EnchantCategory.SWORD, p, "sword"), 6);

        menu.openMenu(p);
    }

}
