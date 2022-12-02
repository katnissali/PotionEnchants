package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.PotionEnchants;
import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.ListenerEnchant;
import me.BigMarco254.potionenchants.utils.Pair;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Inquisitive extends ListenerEnchant {

    private Random random;
    private Map<Integer, Pair<Integer, Integer>> levelExpAdd;

    public Inquisitive() {
        super(EnchantmentTarget.WEAPON, "Inquisitive", 117, 3, EnchantCategory.SWORD, "inquisitive");
        this.random = new Random();
        ConfigurationSection sec = PotionEnchants.getInstance().getConfig().getConfigurationSection("enchants.inquisitive");
        Set<String> keys = sec.getKeys(false);
        levelExpAdd = new HashMap<>(keys.size());
        for (String k : keys) {
            try {
                levelExpAdd.put(Integer.parseInt(k), new Pair<>(
                        sec.getInt(k + ".chance"),
                        sec.getInt(k + ".exp-to-add")
                ));
            }catch (Exception e) {
                System.out.println("PotionEnchants: Error in Inquisitive config section, invalid number: "  + k);
            }
        }
    }

    @Override
    public void onPlayerKillEntity(EntityDeathEvent e, int level) {
        Pair<Integer, Integer> addChance = levelExpAdd.getOrDefault(level, null);
        if (addChance == null) return;

        int randomNum = this.random.nextInt(100);

        if (randomNum < addChance.getKey()) {

            e.setDroppedExp(e.getDroppedExp() + addChance.getValue());
        }
    }

}
