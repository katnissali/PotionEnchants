package me.BigMarco254.potionenchants.objects;

//import me.tox.PvPingMobCoins.api.listener.MobDeathEvent;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class ListenerEnchant extends PEnchant {

    public ListenerEnchant(EnchantmentTarget enchantTarget, String name, int id, int maxLevel, EnchantCategory category, String configKey) {
        super(enchantTarget, name, id, maxLevel, category, configKey);
    }

    public void onBlockBreak(BlockBreakEvent e, int level) {}

    public void onPlayerAttack(EntityDamageByEntityEvent e, int level) {}

//    public void onMobCoin(MobDeathEvent e, int level) {}

    public void onPlayerInteract(PlayerInteractEvent e, int level) {}

    public void onPlayerKillEntity(EntityDeathEvent e, int level) {}

    @Override
    public boolean isListenerEnchant() {
        return true;
    }
}
