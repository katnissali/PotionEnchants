package me.BigMarco254.potionenchants.menus.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class MenuItem {
    private Menu menu;
    private int slot;

    void addToMenu(Menu menu) {
        this.menu = menu;
    }

    void removeFromMenu(Menu menu) {
        if (this.menu == menu) {
            this.menu = null;
        }
    }

    public Menu getMenu() {
        return this.menu;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public abstract void onClick(Player p0, InventoryClickType p1);

    public void update(Player p) {}

    public abstract ItemStack getItemStack();

    public abstract static class UnclickableMenuItem extends MenuItem {
        public void onClick() {
        }
    }
}

