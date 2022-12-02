package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.PotionEnchant;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Speed extends PotionEnchant {

    public Speed() {
        super(EnchantmentTarget.ARMOR_FEET, "Speed", 108, 2, "speed", EnchantCategory.ARMOR, "speed");
    }

    @Override
    public void applyEnchant(Player p, int level) {
        p.addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, getAmplifier(level)));
    }

    @Override
    public void removeEnchant(Player p) {
        p.removePotionEffect(PotionEffectType.SPEED);
    }
}
