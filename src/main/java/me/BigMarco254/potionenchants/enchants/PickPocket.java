package me.BigMarco254.potionenchants.enchants;

import me.BigMarco254.potionenchants.PotionEnchants;
import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.ListenerEnchant;
import me.BigMarco254.potionenchants.utils.Pair;
import me.BigMarco254.potionenchants.utils.Utils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class PickPocket extends ListenerEnchant {

    private Map<Integer, Pair<Integer, Double>> levelStealChance;
    private Random random;

    public PickPocket() {
        super(EnchantmentTarget.WEAPON, "Pick Pocket", 116, 2, EnchantCategory.SWORD, "pick-pocket");
        this.random = new Random();
        ConfigurationSection sec = PotionEnchants.getInstance().getConfig().getConfigurationSection("enchants.pick-pocket");
        Set<String> keys = sec.getKeys(false);
        levelStealChance = new HashMap<>(keys.size());
        for (String k : keys) {
            try {
                levelStealChance.put(Integer.parseInt(k), new Pair<>(
                        sec.getInt(k + ".chance"),
                        sec.getDouble(k + ".percent-to-steal")
                ));
            }catch (Exception e) {
                System.out.println("PotionEnchants: Error in Pick Pocket config section, invalid number: "  + k);
            }
        }
    }

    @Override
    public void onPlayerAttack(EntityDamageByEntityEvent e, int level) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Pair<Integer, Double> stealChance = levelStealChance.getOrDefault(level, null);
        if (stealChance == null) return;
        double realPercent = stealChance.getValue() / 100.00;

        int randomNum = this.random.nextInt(100);

        if (randomNum < stealChance.getKey()) {
            Player target = (Player) e.getEntity();
            double amountStollen = PotionEnchants.getInstance().getEconomy().getBalance(target) * realPercent;
            if (amountStollen <= 0) {
                return;
            }
            double roundedStollen = Math.round(amountStollen * 100.0) / 100.0;
            PotionEnchants.getInstance().getEconomy().withdrawPlayer(target, roundedStollen);
            PotionEnchants.getInstance().getEconomy().depositPlayer((Player)e.getDamager(), roundedStollen);
            target.sendMessage(Utils.colorize(Utils.format(
                    PotionEnchants.getInstance().getConfig().getString("messages.pickpocket-money-was-stolen"),
                    new Pair<>("\\{player}", e.getDamager().getName()),
                    new Pair<>("\\{amount}", String.format("%.2f", roundedStollen))
            )));
            e.getDamager().sendMessage(Utils.colorize(Utils.format(
                    PotionEnchants.getInstance().getConfig().getString("messages.pickpocket-stole-money"),
                    new Pair<>("\\{player}", target.getName()),
                    new Pair<>("\\{amount}", String.format("%.2f", roundedStollen))
            )));
        }
    }

}
