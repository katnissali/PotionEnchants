package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.PotionEnchant;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Ninja extends PotionEnchant {

    public Ninja() {
        super(EnchantmentTarget.ARMOR_LEGS, "Ninja", 106, 1, "ninja", EnchantCategory.ARMOR, "ninja");
    }

    @Override
    public void applyEnchant(Player p, int level) {
        p.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(Integer.MAX_VALUE, defaultAmplifier));
    }

    @Override
    public void removeEnchant(Player p) {
        p.removePotionEffect(PotionEffectType.INVISIBILITY);
    }
}
