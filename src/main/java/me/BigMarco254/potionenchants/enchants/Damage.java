package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.PotionEnchants;
import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.ListenerEnchant;
import me.BigMarco254.potionenchants.utils.Pair;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Damage extends ListenerEnchant {

    private Map<Integer, Pair<Integer, Double>> levelDamageChance;

    public Damage() {
        super(EnchantmentTarget.WEAPON, "Damage", 115, 2, EnchantCategory.SWORD, "damage");
        ConfigurationSection sec = PotionEnchants.getInstance().getConfig().getConfigurationSection("enchants.damage");
        if(sec == null){
            System.out.println("PotionEnchants: Error in Damage config section, 'enchants.damage' does not exist.");
            return;
        }
        Set<String> keys = sec.getKeys(false);
        levelDamageChance = new HashMap<>(keys.size());
        for (String k : keys) {
            try {
                levelDamageChance.put(Integer.parseInt(k), new Pair<>(
                        sec.getInt(k + ".chance"),
                        sec.getDouble(k + ".amount-of-hearts") * 2
                ));
            } catch (Exception e) {
                System.out.println("PotionEnchants: Error in Damage config section, invalid number: "  + k);
            }
        }
    }

    @Override
    public void onPlayerAttack(EntityDamageByEntityEvent e, int level) {
        Pair<Integer, Double> damageChance = levelDamageChance.getOrDefault(level, null);
        if (damageChance != null && new Random().nextInt(100) < damageChance.getKey()) {
            e.getEntity();
            e.setDamage(e.getDamage() + damageChance.getValue());
        }
    }
}
