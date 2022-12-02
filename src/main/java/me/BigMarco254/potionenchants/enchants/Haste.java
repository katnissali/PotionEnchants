package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.PotionEnchant;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Haste extends PotionEnchant {

    public Haste() {
        super(EnchantmentTarget.TOOL, "Haste", 110, 3, "haste", EnchantCategory.TOOLS, "haste");
    }

    @Override
    public void applyEnchant(Player p, int level) {
        p.addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(Integer.MAX_VALUE, getAmplifier(level)));
    }

    @Override
    public void removeEnchant(Player p) {
        p.removePotionEffect(PotionEffectType.FAST_DIGGING);
    }
}
