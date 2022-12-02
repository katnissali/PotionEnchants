package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.PotionEnchant;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class HealthBoost extends PotionEnchant {

    private Map<Integer, Integer> amplifiers;

    public HealthBoost() {
        super(EnchantmentTarget.ARMOR_TORSO, "Health Boost", 103, 2, "health-boost", EnchantCategory.ARMOR, "health-boost");
    }

    @Override
    public void applyEnchant(Player p, int level) {
        p.addPotionEffect(PotionEffectType.ABSORPTION.createEffect(Integer.MAX_VALUE, getAmplifier(level)));
    }

    @Override
    public void removeEnchant(Player p) {
        p.removePotionEffect(PotionEffectType.ABSORPTION);
    }
}
