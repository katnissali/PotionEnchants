package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.PotionEnchant;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class FireImmunity extends PotionEnchant {


    public FireImmunity() {
        super(EnchantmentTarget.ARMOR_LEGS, "Fire Immunity", 105, 1, "fire-immunity", EnchantCategory.ARMOR, "fire-immunity");
    }

    @Override
    public void applyEnchant(Player p, int level) {
        p.addPotionEffect(PotionEffectType.FIRE_RESISTANCE.createEffect(Integer.MAX_VALUE, this.defaultAmplifier));
    }

    @Override
    public void removeEnchant(Player p) {
        p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
    }
}
