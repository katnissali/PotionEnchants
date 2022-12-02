package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.PotionEnchant;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Regeneration extends PotionEnchant {

    public Regeneration() {
        super(EnchantmentTarget.ARMOR_TORSO, "Regeneration", 104, 2, "regeneration", EnchantCategory.ARMOR, "regeneration");
    }

    @Override
    public void applyEnchant(Player p, int level) {
        p.addPotionEffect(PotionEffectType.REGENERATION.createEffect(Integer.MAX_VALUE, getAmplifier(level)));
    }

    @Override
    public void removeEnchant(Player p) {
        p.removePotionEffect(PotionEffectType.REGENERATION);
    }
}
