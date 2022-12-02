package me.BigMarco254.potionenchants.objects;

public enum EnchantCategory {
    TOOLS("tools"),
    ARMOR("armor"),
    SWORD("sword");

    final String configKey;

    EnchantCategory(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigKey() {
        return configKey;
    }
}
