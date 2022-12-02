package me.BigMarco254.potionenchants;

import me.BigMarco254.potionenchants.commands.PEnchantsCommand;
import me.BigMarco254.potionenchants.enchants.*;
import me.BigMarco254.potionenchants.listeners.ArmorListener;
import me.BigMarco254.potionenchants.listeners.BlockListeners;
import me.BigMarco254.potionenchants.listeners.PlayerListener;
import me.BigMarco254.potionenchants.menus.api.MenuAPI;
import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.PEnchant;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public class PotionEnchants extends JavaPlugin {

    private Economy econ;
    private static Map<String, PEnchant> ENCHANTS_BY_NAME;
    private static Map<Integer, PEnchant> ENCHANTS_BY_ID;
    private static Map<EnchantCategory, List<PEnchant>> ENCHANTS_BY_CATEGORY;
    private static Map<Integer, String> ROMAN_NUMERALS;
    private static PotionEnchants instance;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.setupRomanNumerals();
        this.setupEnchants();
        this.setupEconomy();
        PluginCommand cmd = this.getCommand("potionenchants");
        if(cmd != null) cmd.setExecutor(new PEnchantsCommand());
        this.registerListeners();
        System.out.println("PotionEnchants has been enabled!");
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;

        this.econ = (Economy)rsp.getProvider();
        return this.econ != null;
    }

    public Economy getEconomy() {
        return econ;
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        List<Material> clickableBlocks = this.getConfig().getStringList("right-clickable-blocks-dont-armor-mount").stream().map(Material::getMaterial).collect(Collectors.toList());
        pm.registerEvents(new ArmorListener(clickableBlocks), this);
        pm.registerEvents(new PlayerListener(), this);
        pm.registerEvents(new BlockListeners(), this);
        pm.registerEvents(new MenuAPI(), this);
    }

    private void setupRomanNumerals() {
        ConfigurationSection sec = this.getConfig().getConfigurationSection("enchants.levels");
        Set<String> keys = sec.getKeys(false);
        ROMAN_NUMERALS = new HashMap<>(keys.size());
        for (String k : keys) {
            try {
                ROMAN_NUMERALS.put(Integer.parseInt(k), sec.getString(k));
            }catch (Exception e) {
                System.out.println("PotionEnchants Error: Invalid number for enchants.levels");
            }
        }
    }

    private void setupEnchants() {
        List<PEnchant> enchants = Arrays.asList(
                new Lumination(),
                new Implants(),
                new HealthBoost(),
                new Regeneration(),
                new FireImmunity(),
                new Ninja(),
                new Jump(),
                new Speed(),
                new MobcoinLuck(),
                new Haste(),
                new Smelter(),
                new Experience(),
                new ObsidianDestroyer(),
                new Strength(),
                new Damage(),
                new PickPocket(),
                new Inquisitive(),
                new Freeze()
        );
        ENCHANTS_BY_NAME = new HashMap<>(enchants.size());
        ENCHANTS_BY_ID = new HashMap<>(enchants.size());
        ENCHANTS_BY_CATEGORY = new HashMap<>(EnchantCategory.values().length);
        for (PEnchant ench : enchants) {
            ENCHANTS_BY_NAME.put(ench.getName(), ench);
            ENCHANTS_BY_ID.put(ench.getId(), ench);
            if (ENCHANTS_BY_CATEGORY.containsKey(ench.getCategory())) {
                ENCHANTS_BY_CATEGORY.get(ench.getCategory()).add(ench);
            }else {
                List<PEnchant> newList = new ArrayList<>();
                newList.add(ench);
                ENCHANTS_BY_CATEGORY.put(ench.getCategory(), newList);
            }
        }
    }
    public static PEnchant getEnchantById(int id) {
        return ENCHANTS_BY_ID.getOrDefault(id, null);
    }

    public static List<PEnchant> getEnchantsByCategory(EnchantCategory category) {
        return ENCHANTS_BY_CATEGORY.getOrDefault(category, Collections.emptyList());
    }

    public static String getRomanNumeral(int num) {
        return ROMAN_NUMERALS.getOrDefault(num, "{INVALID NUMBER}");
    }

    public static PotionEnchants getInstance() {
        return instance;
    }
}
