package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.PotionEnchant;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Lumination extends PotionEnchant {

    public Lumination() {
        super(EnchantmentTarget.ARMOR_HEAD, "Lumination", 101, 1, "lumination", EnchantCategory.ARMOR, "lumination");
    }

    @Override
    public void applyEnchant(Player p, int level) {
        p.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(Integer.MAX_VALUE, defaultAmplifier));
    }

    @Override
    public void removeEnchant(Player p) {
        p.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }
}
