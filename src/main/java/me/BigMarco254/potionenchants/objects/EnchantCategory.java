package me.BigMarco254.potionenchants.objects;

public enum EnchantCategory {

    TOOLS,
    ARMOR,
    SWORD;

    public String getConfigKey() {
        return name().toLowerCase();
    }

}
