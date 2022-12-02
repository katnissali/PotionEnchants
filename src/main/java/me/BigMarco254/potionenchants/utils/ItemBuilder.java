package me.BigMarco254.potionenchants.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.List;

public class ItemBuilder {

    private ItemStack item;

    public ItemBuilder(Material material, int data) {
//        this.item = new ItemStack(material, 1, ((byte)data));
        this.item = new ItemStack(material, 1);
        Damageable meta = (Damageable) item.getItemMeta();
        meta.setDamage(data);
        item.setItemMeta(meta);

    }

    public ItemBuilder setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = this.item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(Utils.colorize(name));
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = this.item.getItemMeta();
        assert meta != null;
        meta.setLore(Utils.colorize(lore));
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(String ... lore) {
        ItemMeta meta = this.item.getItemMeta();
        assert meta != null;
        meta.setLore(Utils.colorize(lore));
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment ench, int level) {
        this.item.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder setColor(Color color) {
        if (!this.item.getType().toString().contains("LEATHER")) {
            throw new IllegalArgumentException("setColor can only be used on leather armour!");
        }
        LeatherArmorMeta meta = (LeatherArmorMeta)this.item.getItemMeta();
        assert meta != null;
        meta.setColor(color);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setOwner(String name) {
        SkullMeta meta = (SkullMeta)this.item.getItemMeta();
        meta.setOwner(name);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setDurability(int durability) {
        if (durability >= -32768 && durability <= 32767) {

            Damageable meta = (Damageable) item.getItemMeta();
            assert meta != null;
            meta.setDamage((short)durability);
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setData(MaterialData data) {
        ItemMeta meta = this.item.getItemMeta();
        this.item.setData(data);
        this.item.setItemMeta(meta);
        return this;
    }
    public ItemBuilder setItem(ItemStack item){
        this.item = item;
        return this;
    }
    public ItemStack getStack() {
        return this.item;
    }
}