package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.PotionEnchant;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Implants extends PotionEnchant {

    public Implants() {
        super(EnchantmentTarget.ARMOR_HEAD, "Implants", 102, 2, "implants", EnchantCategory.ARMOR, "implants");
    }

    @Override
    public void applyEnchant(Player p, int level) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, getAmplifier(level)));
    }

    @Override
    public void removeEnchant(Player p) {
        p.removePotionEffect(PotionEffectType.SATURATION);
    }
}
