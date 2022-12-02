package me.BigMarco254.potionenchants.objects;

import me.BigMarco254.potionenchants.PotionEnchants;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class PotionEnchant extends PEnchant {

    private Map<Integer, Integer> amplifiers  = new HashMap<Integer, Integer>();
    public final Integer defaultAmplifier;

    public PotionEnchant(EnchantmentTarget enchantTarget, String name, int id, int maxLevel, String potionLevelKey, EnchantCategory category, String configKey) {
        super(enchantTarget, name, id, maxLevel, category, configKey);
        ConfigurationSection sec = PotionEnchants.getInstance().getConfig().getConfigurationSection("enchants.potion-levels." + potionLevelKey);

        int defAmp = Integer.MIN_VALUE;

        if(sec == null){
            System.out.println("Error: 'enchants.potion-levels." + potionLevelKey + "' is null. Skipping keys. defAmp defaulted to Integer.MIN_VALUE.");
            this.defaultAmplifier = Integer.MIN_VALUE;
        } else {
            Set<String> keys = sec.getKeys(false);

            for (String k : keys) {
                try {
                    amplifiers.put(Integer.parseInt(k), sec.getInt(k) - 1);
                    if (defAmp == Integer.MIN_VALUE) {
                        defAmp = sec.getInt(k) - 1;
                    }
                } catch (Exception e) {
                    System.out.println("PotionEnchants Error: Invalid number: " + k);
                }
            }
            this.defaultAmplifier = defAmp;
        }
    }

    public abstract void applyEnchant(Player p, int level);

    public abstract void removeEnchant(Player p);

    @Override
    public boolean isPotionEnchant() {
        return true;
    }

    public Integer getAmplifier(int level) {
        return amplifiers.getOrDefault(level, 1);
    }
}
