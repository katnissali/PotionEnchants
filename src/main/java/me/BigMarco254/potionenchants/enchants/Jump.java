package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.PotionEnchant;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Jump extends PotionEnchant {

    public Jump() {
        super(EnchantmentTarget.ARMOR_FEET, "Jump", 107, 2, "jump", EnchantCategory.ARMOR, "jump");
    }

    @Override
    public void applyEnchant(Player p, int level) {
        p.addPotionEffect(PotionEffectType.JUMP.createEffect(Integer.MAX_VALUE, getAmplifier(level)));
    }

    @Override
    public void removeEnchant(Player p) {
        p.removePotionEffect(PotionEffectType.JUMP);
    }
}
