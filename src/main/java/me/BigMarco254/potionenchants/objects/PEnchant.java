package me.BigMarco254.potionenchants.objects;

import me.BigMarco254.potionenchants.PotionEnchants;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class PEnchant {

    final EnchantmentTarget enchantTarget;
    final String name;
    final int id;
    final int maxLevel;
    final EnchantCategory category;
    final double chanceForEnchant;
    final Map<Integer, Double> chanceForLevel = new HashMap<Integer, Double>();


    public PEnchant(EnchantmentTarget enchantTarget, String name, int id, int maxLevel, EnchantCategory category, String configChanceKey) {
        this.enchantTarget = enchantTarget;
        this.name = name;
        this.id = id;
        this.maxLevel = maxLevel;
        this.category = category;
        this.chanceForEnchant = PotionEnchants.getInstance().getConfig().getDouble("enchant-chances." + category.getConfigKey() + "." + configChanceKey);
        ConfigurationSection levelChancesSec = PotionEnchants.getInstance().getConfig().getConfigurationSection("enchant-level-chances." + configChanceKey);
        if(levelChancesSec == null){
            System.out.println("Error: 'enchant-level-chances." + configChanceKey + "' is null in config. Skipping keys.");
        } else {
            for (String k : levelChancesSec.getKeys(false)) {
                try {
                    chanceForLevel.put(Integer.parseInt(k), levelChancesSec.getDouble(k));
                } catch (Exception e) {
                    System.out.println("PotionEnchants Error: Invalid level number: " + k + " for level chance for enchant: " + name);
                }
            }
        }
    }

    public int getRandomLevel() {
        if (chanceForLevel.size() == 1) {
            return 1;
        }
        Random random = new Random();
        int randomNum = random.nextInt(100);
        double current = 0.0;
        for (Map.Entry<Integer, Double> entry : chanceForLevel.entrySet()) {
            current += entry.getValue();
            if (randomNum < current) {
                return entry.getKey();
            }
        }
        return -1;
    }

    public double getChanceForEnchant() {
        return chanceForEnchant;
    }

    public boolean isListenerEnchant() {
        return false;
    }

    public boolean isPotionEnchant() {
        return false;
    }

    public boolean isItemInHandEnchant() { return false; }


    public int getMaxLevel() {
        return this.maxLevel;
    }

    public EnchantmentTarget getItemTarget() {
        return this.enchantTarget;
    }

    public boolean canEnchantItem(ItemStack item) {
        return this.enchantTarget.includes(item);
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public EnchantCategory getCategory() {
        return category;
    }
}
