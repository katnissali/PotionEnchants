package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.PotionEnchants;
import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.ListenerEnchant;
import me.BigMarco254.potionenchants.utils.Pair;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Freeze extends ListenerEnchant {

    private Random random;
    private Map<Integer, Pair<Integer, Integer>> freezeChancesTime;

    public Freeze() {
        super(EnchantmentTarget.WEAPON, "Freeze", 118, 2, EnchantCategory.SWORD, "freeze");
        this.random = new Random();
        ConfigurationSection sec = PotionEnchants.getInstance().getConfig().getConfigurationSection("enchants.freeze");
        Set<String> keys = sec.getKeys(false);
        freezeChancesTime = new HashMap<>(keys.size());
        for (String k : keys) {
            try {
                freezeChancesTime.put(Integer.parseInt(k), new Pair<>(
                        sec.getInt(k + ".chance"),
                        sec.getInt(k + ".duration-in-secs")
                ));
            }catch (Exception e) {
                System.out.println("PotionEnchants: Error in Freeze config section, invalid number: "  + k);
            }
        }
    }

    @Override
    public void onPlayerAttack(EntityDamageByEntityEvent e, int level) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Pair<Integer, Integer> freezeChance = freezeChancesTime.getOrDefault(level, null);
        if (freezeChance == null) return;

        int randomNum = this.random.nextInt(100);

        if (randomNum < freezeChance.getKey()) {
            Player p = (Player) e.getEntity();
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, freezeChance.getValue() * 20, 100));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, freezeChance.getValue(), 100));
        }
    }
}
