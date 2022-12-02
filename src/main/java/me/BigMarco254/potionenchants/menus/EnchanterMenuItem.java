package me.BigMarco254.potionenchants.menus;

import me.BigMarco254.potionenchants.PotionEnchants;
import me.BigMarco254.potionenchants.menus.api.InventoryClickType;
import me.BigMarco254.potionenchants.menus.api.MenuItem;
import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.utils.Pair;
import me.BigMarco254.potionenchants.utils.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchanterMenuItem extends MenuItem {

    final String itemKey;
    final EnchantCategory category;
    final int expCost;
    final double dollarCost;
    int currentExp;
    double currentMoney;


    public EnchanterMenuItem(EnchantCategory category, Player p, String itemKey) {
        this.category = category;
        this.itemKey = itemKey;
        ConfigurationSection sec = PotionEnchants.getInstance().getConfig().getConfigurationSection("enchanter-menu." + itemKey);
        if(sec != null) {
            this.expCost = sec.getInt("exp-price");
            this.dollarCost = sec.getDouble("dollar-price");
        } else {
            System.out.println("Error: ConfigurationSection 'enchanter-menu." + itemKey + "' does not exist so there is no xp and dollar cost. Defaulting to 0.");
            this.expCost = 0;
            this.dollarCost = 0;
        }

        this.update(p);
    }

    @Override
    public void onClick(Player p, InventoryClickType clickType) {
        if (expCost != -1) {
            int currentExp = Utils.getTotalExperience(p);
            if (currentExp < expCost) {
                p.sendMessage(Utils.colorize(PotionEnchants.getInstance().getConfig().getString("messages.not-enough-exp")));
                this.getMenu().closeMenu(p);
                return;
            }
            p.setTotalExperience(0);
            p.setLevel(0);
            p.setExp(0);
            p.giveExp(currentExp - expCost);
        }
        if (dollarCost != -1) {
            double currentBal = PotionEnchants.getInstance().getEconomy().getBalance(p);
            if (currentBal < dollarCost) {
                p.sendMessage(Utils.colorize(PotionEnchants.getInstance().getConfig().getString("messages.not-enough-money")));
                this.getMenu().closeMenu(p);
                return;
            }
            PotionEnchants.getInstance().getEconomy().withdrawPlayer(p, dollarCost);
        }
        p.getInventory().addItem(Utils.getRandomEnchantScroll(this.category));
        this.getMenu().updateV2Menu(p);
    }

    public void update(Player p) {
        this.currentExp = Utils.getTotalExperience(p);
        this.currentMoney = PotionEnchants.getInstance().getEconomy().getBalance(p);
    }

    @Override
    public ItemStack getItemStack() {
        boolean canAffordExp = expCost == -1 || currentExp >= expCost;
        boolean canAffordMoney = dollarCost == -1 || currentMoney >= dollarCost;
        ConfigurationSection sec = PotionEnchants.getInstance().getConfig().getConfigurationSection("enchanter-menu." + itemKey);
        if(sec == null){
            System.out.println("Error: ConfigurationSection 'enchanter-menu." + itemKey + "' does not exist. Returning null ItemStack.");
            return null;
        }

        String materialStr = sec.getString("item.material");
        Material material;
        if(materialStr == null){
            System.out.println("Error: null material type at 'enchanter-menu." + itemKey + "' in config. Defaulting to air.");
            material = Material.AIR;
        } else {
            material = Material.getMaterial(materialStr);
            if(material == null) material = Material.AIR;
        }

        ItemStack item = new ItemStack(material, 1, (short)sec.getInt("item.data", 0));
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.colorize(sec.getString("item.name")));
        itemMeta.setLore(Utils.formatAndColorize(
                sec.getStringList("item.lore"),
                new Pair<>("\\{currentExp}", currentExp + ""),
                new Pair<>("\\{currentMoney}", String.format("%.2f", currentMoney)),
                new Pair<>("\\{hasEnoughExpColor}", Utils.colorize(PotionEnchants.getInstance().getConfig().getString("enchanter-menu." + (canAffordExp ? "canAffordColor" : "cannotAffordColor")))),
                new Pair<>("\\{hasEnoughMoneyColor}", Utils.colorize(PotionEnchants.getInstance().getConfig().getString("enchanter-menu." + (canAffordMoney ? "canAffordColor" : "cannotAffordColor"))))
        ));
        item.setItemMeta(itemMeta);
        return item;
    }
}
