package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.PotionEnchants;
import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.ListenerEnchant;
import me.BigMarco254.potionenchants.utils.Pair;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Experience extends ListenerEnchant {

    private Random random;
    private Map<Integer, Pair<Integer, Double>> levelExpMultipliers;

    public Experience() {
        super(EnchantmentTarget.TOOL, "Experience", 112, 4, EnchantCategory.TOOLS, "experience");
        this.random = new Random();
        ConfigurationSection sec = PotionEnchants.getInstance().getConfig().getConfigurationSection("enchants.experience");
        Set<String> keys = sec.getKeys(false);
        levelExpMultipliers = new HashMap<>(keys.size());
        for (String k : keys) {
            try {
                levelExpMultipliers.put(Integer.parseInt(k), new Pair<>(
                        sec.getInt(k + ".chance"),
                        sec.getDouble(k + ".multiplier")
                ));
            } catch (Exception e) {
                System.out.println("PotionEnchants: Error in Experience config section, invalid number: "  + k);
            }
        }
    }

    @Override
    public void onBlockBreak(BlockBreakEvent e, int level) {
        Pair<Integer, Double> multiplierChance = levelExpMultipliers.getOrDefault(level, null);
        if (multiplierChance == null) return;

        int randomNum = this.random.nextInt(100);

        if (randomNum < multiplierChance.getKey()) {
            e.setExpToDrop((int)(e.getExpToDrop() * multiplierChance.getValue()));
        }

    }
}
