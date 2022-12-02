package me.BigMarco254.potionenchants.menus.api;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class MenuAPI implements Listener {

    private static MenuAPI instance;

    public static MenuAPI getInstance() {
        if (MenuAPI.instance == null) {
            synchronized (MenuAPI.class) {
                if (MenuAPI.instance == null) {
                    MenuAPI.instance = new MenuAPI();
                }
            }
        }
        return MenuAPI.instance;
    }

    public Menu createMenu(String title, int rows) {
        return new Menu(title, rows);
    }

    public Menu cloneMenu(Menu menu) throws CloneNotSupportedException {
        return menu.clone();
    }

    public void removeMenu(Menu menu) {
        for (HumanEntity viewer : menu.getInventory().getViewers()) {
            if (viewer instanceof Player) {
                menu.closeMenu((Player) viewer);
            } else {
                viewer.closeInventory();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMenuItemClicked(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        int index = event.getRawSlot();

        if (inventory.getHolder() instanceof Menu) {

            event.setCancelled(true);
            player.updateInventory();
            Menu menu = (Menu) inventory.getHolder();


            if(event.getSlotType() != InventoryType.SlotType.OUTSIDE && index < inventory.getSize() && event.getAction() != InventoryAction.NOTHING)
                menu.selectMenuItem(player, index, InventoryClickType.fromInventoryAction(event.getAction()));
            else
                menu.closeMenu(player);


//            if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) {
//                if (menu.exitOnClickOutside()) {
//                    menu.closeMenu(player);
//                }
//            } else {
//                if (index < inventory.getSize() && event.getAction() != InventoryAction.NOTHING) {
//                    menu.selectMenuItem(player, index, InventoryClickType.fromInventoryAction(event.getAction()));
//                } else if (menu.exitOnClickOutside()) {
//                    menu.closeMenu(player);
//                }
//            }

        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMenuClosed(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        if (event.getPlayer() instanceof Player && inventory.getHolder() instanceof Menu) {
            Menu menu = (Menu) inventory.getHolder();
            MenuCloseBehaviour menuCloseBehaviour = menu.getMenuCloseBehaviour();
            if (menuCloseBehaviour != null)
                menuCloseBehaviour.onClose((Player) event.getPlayer(), menu, menu.bypassMenuCloseBehaviour());

        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerLogoutCloseMenu(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.getOpenInventory() == null || !(player.getOpenInventory().getTopInventory().getHolder() instanceof Menu)) {
            return;
        }
        Menu menu = (Menu) player.getOpenInventory().getTopInventory().getHolder();
        menu.setBypassMenuCloseBehaviour(true);
        menu.setMenuCloseBehaviour(null);
        player.closeInventory();
    }

    public interface MenuCloseBehaviour {
        void onClose(Player p0, Menu p1, boolean p2);
    }
}

