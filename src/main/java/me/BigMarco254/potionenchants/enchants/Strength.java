package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.PotionEnchant;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Strength extends PotionEnchant {

    public Strength() {
        super(EnchantmentTarget.WEAPON, "Strength", 114, 1, "strength", EnchantCategory.SWORD, "strength");
    }

    @Override
    public void applyEnchant(Player p, int level) {
        p.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(Integer.MAX_VALUE, defaultAmplifier));
    }

    @Override
    public void removeEnchant(Player p) {
        p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
    }

    @Override
    public boolean isItemInHandEnchant() {
        return true;
    }
}
