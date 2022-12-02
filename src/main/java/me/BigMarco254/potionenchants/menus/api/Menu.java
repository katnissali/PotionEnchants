package me.BigMarco254.potionenchants.menus.api;

import com.google.common.collect.Maps;
import me.BigMarco254.potionenchants.PotionEnchants;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class Menu implements InventoryHolder {
    private ConcurrentMap<Integer, MenuItem> items;
    private String title;
    private int rows;
    private boolean exitOnClickOutside;
    private MenuAPI.MenuCloseBehaviour menuCloseBehaviour;
    private boolean bypassMenuCloseBehaviour;
    private Menu parentMenu;
    private Inventory inventory;

    public Menu(String title, int rows) {
        this(title, rows, null);
    }

    private Menu(String title, int rows, Menu parentMenu) {
        this.items = Maps.newConcurrentMap();
        this.exitOnClickOutside = false;
        this.bypassMenuCloseBehaviour = false;
        this.title = title;
        this.rows = rows;
        this.parentMenu = parentMenu;
    }

    public MenuAPI.MenuCloseBehaviour getMenuCloseBehaviour() {
        return this.menuCloseBehaviour;
    }

    public void setMenuCloseBehaviour(MenuAPI.MenuCloseBehaviour menuCloseBehaviour) {
        this.menuCloseBehaviour = menuCloseBehaviour;
    }

    public void setBypassMenuCloseBehaviour(boolean bypassMenuCloseBehaviour) {
        this.bypassMenuCloseBehaviour = bypassMenuCloseBehaviour;
    }

    public boolean bypassMenuCloseBehaviour() {
        return this.bypassMenuCloseBehaviour;
    }

    private void setExitOnClickOutside(boolean exit) {
        this.exitOnClickOutside = exit;
    }

    public Map<Integer, MenuItem> getMenuItems() {
        return this.items;
    }

    public boolean addMenuItem(MenuItem item, int x, int y) {
        return this.addMenuItem(item, y * 9 + x);
    }

    public MenuItem getMenuItem(int index) {
        return this.items.get(index);
    }

    public boolean addMenuItem(MenuItem item, int index) {
        ItemStack slot = this.getInventory().getItem(index);
        if (slot != null && slot.getType() != Material.AIR) {
            this.removeMenuItem(index);
        }
        item.setSlot(index);
        this.getInventory().setItem(index, item.getItemStack());
        this.items.put(index, item);
        item.addToMenu(this);
        return true;
    }

    public boolean removeMenuItem(int x, int y) {
        return this.removeMenuItem(y * 9 + x);
    }

    public boolean removeMenuItem(int index) {
        ItemStack slot = this.getInventory().getItem(index);
        if (slot == null || slot.getType().equals(Material.AIR)) {
            return false;
        }
        this.getInventory().clear(index);
        this.items.remove(index).removeFromMenu(this);
        return true;
    }

    void selectMenuItem(Player player, int index, InventoryClickType clickType) {
        if (this.items.containsKey(index)) {
            MenuItem item = this.items.get(index);
            item.onClick(player, clickType);
        }
    }

    public void openMenu(Player player) {
        if (!this.getInventory().getViewers().contains(player)) {
            player.openInventory(this.getInventory());
        }
    }

    public void closeMenu(Player player) {
        if (this.getInventory().getViewers().contains(player)) {
            this.getInventory().getViewers().remove(player);
            player.closeInventory();
        }
    }

    public void scheduleUpdateTask(final Player player, int ticks) {
        new BukkitRunnable() {
            public void run() {
                if (player == null || Bukkit.getPlayer(player.getName()) == null) {
                    this.cancel();
                    return;
                }
                if (player.getOpenInventory() == null || player.getOpenInventory().getTopInventory() == null || player.getOpenInventory().getTopInventory().getHolder() == null) {
                    this.cancel();
                    return;
                }
                if (!(player.getOpenInventory().getTopInventory().getHolder() instanceof Menu)) {
                    this.cancel();
                    return;
                }
                Menu menu = (Menu) player.getOpenInventory().getTopInventory().getHolder();
                if (!menu.inventory.equals(Menu.this.inventory)) {
                    this.cancel();
                    return;
                }
                for (Map.Entry<Integer, MenuItem> entry : menu.items.entrySet()) {
                    Menu.this.getInventory().setItem(entry.getKey(), entry.getValue().getItemStack());
                }
            }
        }.runTaskTimer(PotionEnchants.getInstance(), (long) ticks, (long) ticks);
    }

    public Menu getParent() {
        return this.parentMenu;
    }

    public void setParent(Menu menu) {
        this.parentMenu = menu;
    }

    public Inventory getInventory() {
        if (this.inventory == null) {
            this.inventory = Bukkit.createInventory(this, this.rows * 9, this.title);
        }
        return this.inventory;
    }

    public boolean exitOnClickOutside() {
        return this.exitOnClickOutside;
    }

    @Override
    protected Menu clone() throws CloneNotSupportedException {
        Menu menu = (Menu) super.clone();
        Menu clone = new Menu(this.title, this.rows);
        clone.setExitOnClickOutside(this.exitOnClickOutside);
        clone.setMenuCloseBehaviour(this.menuCloseBehaviour);
        for (Map.Entry<Integer, MenuItem> entry : this.items.entrySet()) {
            clone.addMenuItem(entry.getValue(), entry.getKey());
        }
        return clone;
    }

    public void updateMenu() {
        for (HumanEntity entity : this.getInventory().getViewers()) {
            ((Player) entity).updateInventory();
        }
    }

    public void updateV2Menu(Player player) {
        if (player == null || Bukkit.getPlayer(player.getName()) == null) {
            return;
        }
        if (player.getOpenInventory() == null || player.getOpenInventory().getTopInventory() == null || player.getOpenInventory().getTopInventory().getHolder() == null) {
            return;
        }
        if (!(player.getOpenInventory().getTopInventory().getHolder() instanceof Menu)) {
            return;
        }
        Menu menu = (Menu) player.getOpenInventory().getTopInventory().getHolder();
        if (!menu.inventory.equals(Menu.this.inventory)) {
            return;
        }
        for (Map.Entry<Integer, MenuItem> entry : menu.items.entrySet()) {
            entry.getValue().update(player);
            Menu.this.getInventory().setItem(entry.getKey(), entry.getValue().getItemStack());
        }
    }
}
