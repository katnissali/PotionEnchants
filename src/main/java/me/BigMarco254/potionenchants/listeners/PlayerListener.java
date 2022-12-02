package me.BigMarco254.potionenchants.listeners;

import me.BigMarco254.potionenchants.PotionEnchants;
import me.BigMarco254.potionenchants.events.ArmorEquipEvent;
import me.BigMarco254.potionenchants.objects.ListenerEnchant;
import me.BigMarco254.potionenchants.objects.PEnchant;
import me.BigMarco254.potionenchants.objects.PotionEnchant;
import me.BigMarco254.potionenchants.utils.Pair;
import me.BigMarco254.potionenchants.utils.Utils;
import me.tox.PvPingMobCoins.api.listener.MobDeathEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Map;

public class PlayerListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.isCancelled()) return;
        if(e.getAction() == InventoryAction.NOTHING) return;// Why does this get called if nothing happens??
        if(e.getClick().equals(ClickType.SHIFT_LEFT)
                || e.getClick().equals(ClickType.SHIFT_RIGHT)
                || e.getClick().equals(ClickType.NUMBER_KEY)
                || !(e.getWhoClicked() instanceof Player)){
            return;
        }
        if(e.getSlotType() != InventoryType.SlotType.ARMOR && e.getSlotType() != InventoryType.SlotType.QUICKBAR && e.getSlotType() != InventoryType.SlotType.CONTAINER) return;
        if(e.getClickedInventory() != null && !e.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;
        if (!e.getInventory().getType().equals(InventoryType.CRAFTING) && !e.getInventory().getType().equals(InventoryType.PLAYER)) return;

        ItemStack scrollItem = e.getCursor(); //item that should be scroll
        ItemStack oldItem = e.getCurrentItem(); //item that was previously in slot

        if (scrollItem == null
                || scrollItem.getType() == Material.AIR
                || oldItem == null
                || oldItem.getType() == Material.AIR
                || scrollItem.getType() != Material.FIRE_CHARGE) return;

        Pair<PEnchant, Integer> enchLev = Utils.getScrollEnchant(scrollItem);

        if (enchLev == null) return;

        e.setCancelled(true);

        if (!Utils.isEnchantCompatibleWith(oldItem, enchLev.getKey())) {
            e.getWhoClicked().sendMessage(Utils.colorize(PotionEnchants.getInstance().getConfig().getString("messages.enchant-not-compatible")));
            return;
        }

        e.setCursor(null);
        e.setCurrentItem(Utils.applyCustomEnchant(oldItem, enchLev.getKey(), enchLev.getValue()));
    }

    @EventHandler
    public void onArmorChange(ArmorEquipEvent e) {
        if (e.getOldArmorPiece() != null && e.getOldArmorPiece().getType() != Material.AIR) {
            Map<PEnchant, Integer> oldItemEnchs = Utils.getCustomEnchants(e.getOldArmorPiece());
            oldItemEnchs.forEach((ench, level) -> {
                if (ench.isPotionEnchant() && !ench.isItemInHandEnchant()) {
                    ((PotionEnchant)ench).removeEnchant(e.getPlayer());
                }
            });
        }
        if (e.getNewArmorPiece() != null && e.getNewArmorPiece().getType() != Material.AIR) {
            Map<PEnchant, Integer> newItemEnchs = Utils.getCustomEnchants(e.getNewArmorPiece());
            newItemEnchs.forEach((ench, level) -> {
                if (ench.isPotionEnchant() && !ench.isItemInHandEnchant()) {
                    ((PotionEnchant)ench).applyEnchant(e.getPlayer(), level);
                }
            });
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (isAir(e.getItem())) {
            return;
        }
        Map<PEnchant, Integer> enchants = Utils.getCustomEnchants(e.getItem());
        enchants.forEach((ench, level) -> {
            if (ench.isListenerEnchant()) {
                ((ListenerEnchant)ench).onPlayerInteract(e, level);
            }
        });
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        Player damager = (Player)e.getDamager();
        if (isAir(damager)) return;
        Map<PEnchant, Integer> enchants = Utils.getCustomEnchants(damager.getInventory().getItemInMainHand());
        enchants.forEach((ench, level) -> {
            if (ench.isListenerEnchant()) {
                ((ListenerEnchant)ench).onPlayerAttack(e, level);
            }
        });
    }

    @EventHandler
    public void onPlayerKill(EntityDeathEvent e) {
        Player p = e.getEntity().getKiller();
        if (p == null || isAir(p)) return;

        Map<PEnchant, Integer> enchants = Utils.getCustomEnchants(p.getInventory().getItemInMainHand());
        enchants.forEach((ench, level) -> {
            if (ench.isListenerEnchant()) {
                ((ListenerEnchant)ench).onPlayerKillEntity(e, level);
            }
        });
    }

    @EventHandler
    public void onPlayerMobCoin(MobDeathEvent e) {
        if (isAir(e.getPlayer())) return;

        Map<PEnchant, Integer> enchants = Utils.getCustomEnchants(e.getPlayer().getInventory().getItemInMainHand());
        enchants.forEach((ench, level) -> {
            if (ench.isListenerEnchant()) {
                ((ListenerEnchant)ench).onMobCoin(e, level);
            }
        });
    }

    @EventHandler
    public void onChangeItemInHand(PlayerItemHeldEvent e) {
        ItemStack oldItem = e.getPlayer().getInventory().getItem(e.getPreviousSlot());
        if (isAir(oldItem)) {
            Map<PEnchant, Integer> oldItemEnchs = Utils.getCustomEnchants(oldItem);
            oldItemEnchs.forEach((ench, level) -> {
                if (ench.isPotionEnchant() && ench.isItemInHandEnchant()) {
                    ((PotionEnchant)ench).removeEnchant(e.getPlayer());
                }
            });
        }

        ItemStack newItem = e.getPlayer().getInventory().getItem(e.getNewSlot());
        if (isAir(newItem)) {
            Map<PEnchant, Integer> newItemEnchs = Utils.getCustomEnchants(newItem);
            newItemEnchs.forEach((ench, level) -> {
                if (ench.isPotionEnchant() && ench.isItemInHandEnchant()) {
                    ((PotionEnchant)ench).applyEnchant(e.getPlayer(), level);
                }
            });
        }
    }

    private final boolean isAir(@Nullable ItemStack item){
        return item != null && item.getType() == Material.AIR;
    }
    private final boolean isAir(Player player){
        return isAir(player.getInventory().getItemInMainHand());
    }

}
