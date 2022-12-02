package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.PotionEnchants;
import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.ListenerEnchant;
import me.BigMarco254.potionenchants.utils.Pair;
import me.tox.PvPingMobCoins.api.listener.MobDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.EnchantmentTarget;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MobcoinLuck extends ListenerEnchant {

    private Random random;
    private Map<Integer, Pair<Integer, Integer>> levelMobCoinsAdd;

    public MobcoinLuck() {
        super(EnchantmentTarget.TOOL, "Mobcoin Luck", 109, 4, EnchantCategory.TOOLS, "mobcoin-luck");
        this.random = new Random();
        ConfigurationSection sec = PotionEnchants.getInstance().getConfig().getConfigurationSection("enchants.mobcoin-luck");
        Set<String> keys = sec.getKeys(false);
        levelMobCoinsAdd = new HashMap<>(keys.size());
        for (String k : keys) {
            try {
                levelMobCoinsAdd.put(Integer.parseInt(k), new Pair<>(
                        sec.getInt(k + ".chance"),
                        sec.getInt(k + ".amt-of-mobcoins")
                ));
            }catch (Exception e) {
                System.out.println("PotionEnchants: Error in Mob-coin config section, invalid number: "  + k);
            }
        }
    }

    @Override
    public void onMobCoin(MobDeathEvent e, int level) {
        //give extra mob coin based on chance
        Pair<Integer, Integer> addChance = levelMobCoinsAdd.getOrDefault(level, null);
        if (addChance == null) return;

        int randomNum = this.random.nextInt(100);

        if (randomNum < addChance.getKey()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mobcoins give " + e.getPlayer().getName() + " " + addChance.getValue());
            //PvPingMobCoinsAPI.getInstance().addCoins(e.getPlayer(), addChance.getValue());
        }
    }
}
