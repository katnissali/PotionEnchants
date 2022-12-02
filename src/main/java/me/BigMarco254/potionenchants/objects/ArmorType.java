package me.BigMarco254.potionenchants.objects;

import org.bukkit.inventory.ItemStack;

/**
 * @author Arnah
 * @since Jul 30, 2015
 */
public enum ArmorType{
    HELMET(5), CHESTPLATE(6), LEGGINGS(7), BOOTS(8);

    private final int slot;

    ArmorType(int slot){
        this.slot = slot;
    }

    /**
     * Attempts to match the ArmorType for the specified ItemStack.
     *
     * @param itemStack The ItemStack to parse the type of.
     * @return The parsed ArmorType. (null if none were found.)
     */
    public static ArmorType matchType(final ItemStack itemStack){
        if(itemStack != null) {
            String type = itemStack.getType().name();
            if (type.endsWith("HELMET") || type.endsWith("SKULL")) return HELMET;
            if (type.endsWith("CHESTPLATE")) return CHESTPLATE;
            if (type.endsWith("LEGGINGS")) return LEGGINGS;
            if (type.endsWith("BOOTS")) return BOOTS;
        }
        return null;
    }

    public int getSlot(){ return slot; }
}