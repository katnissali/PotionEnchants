package me.BigMarco254.potionenchants.utils;

import me.BigMarco254.potionenchants.PotionEnchants;
import me.BigMarco254.potionenchants.objects.EnchantCategory;
import me.BigMarco254.potionenchants.objects.PEnchant;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
//import net.minecraft.server.v1_8_R3.Item;
//import net.minecraft.server.v1_8_R3.NBTTagCompound;
//import net.minecraft.server.v1_8_R3.NBTTagList;
//import net.minecraft.server.v1_8_R3.NBTTagString;
import net.minecraft.world.item.Items;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
//import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
//import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    //    private static String getKey(NBTTagCompound tag, String key){
//        return getAllKeys(tag).stream().filter(value -> value.equals(key)).findFirst().orElse(null);
//    }
    private static String getString(NBTTagList tag, int i){
        return tag.j(i);
    }
    private static NBTTagCompound getCompound(NBTTagCompound tag, String str){
        return tag.p(str);
    }
    private static void setInt(NBTTagCompound tag, String arg0, int arg1){
        tag.a(arg0, arg1);
    }
    private static void setIntArray(NBTTagCompound tag, String arg0, int[] arg1){
        tag.a(arg0, arg1);
    }
    private static short getShort(NBTTagCompound tag, String str){
        return tag.g(str); //    MAY NOT WORK
    }
    private static void setString(NBTTagCompound tag, String arg0, String arg1){
        tag.a(arg0, arg1);
    }
    private static void set(NBTTagCompound tag, String str, NBTBase base){
        tag.a(str, base);
    }
    private static Set<String> getAllKeys(NBTTagCompound tag){
        return tag.d();
    }
    private static void setShort(NBTTagCompound tag, String arg0, short arg1){
        tag.a(arg0, arg1);
    }
    private static boolean hasKey(NBTTagCompound tag, String key){
//        return tag.b(key);

        return tag.d().contains(key);
    }
    private static NBTTagList getList(NBTTagCompound tag, String str, int num){
        return tag.c(str, num); //  GOOD
    }
    private static NBTTagString formatNBTTagString(String str){
        return NBTTagString.a(str);
    }
    private static boolean hasTag(net.minecraft.world.item.ItemStack nmsItem){
        return nmsItem.t();
    }
    private static NBTTagCompound getTag(net.minecraft.world.item.ItemStack nmsItem){
        return nmsItem.u();
    }
    private static void setTag(net.minecraft.world.item.ItemStack nmsItem, NBTTagCompound tag){
        nmsItem.c(tag);
    }

    public static ItemStack applyCustomEnchant(ItemStack item, PEnchant enchant, int level) {
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
//        NBTTagCompound tag = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
        NBTTagCompound tag = hasTag(nmsItem) ? getTag(nmsItem) : new NBTTagCompound();
        //  todo not sure if below works
        NBTTagCompound displayTag = hasKey(tag, "display") ? getCompound(tag, "display") : new NBTTagCompound();
//        NBTTagCompound displayTag = tag.hasKey("display") ? tag.getCompound("display") : new NBTTagCompound();
//        NBTTagList loreList = displayTag.hasKey("Lore") ? displayTag.getList("Lore", 8) : new NBTTagList();
        NBTTagList loreList = hasKey(displayTag, "Lore") ? getList(displayTag, "Lore", 8) : new NBTTagList();

        NBTTagString nbtTagString = formatNBTTagString(colorize(format(PotionEnchants.getInstance().getConfig().getString("enchants.lore-format"), new Pair<>("\\{name}", enchant.getName()), new Pair<>("\\{level}", PotionEnchants.getRomanNumeral(level)))));
        loreList.add(nbtTagString);

//        loreList.add(new NBTTagString(colorize(format(PotionEnchants.getInstance().getConfig().getString("enchants.lore-format"), new Pair<>("\\{name}", enchant.getName()), new Pair<>("\\{level}", PotionEnchants.getRomanNumeral(level))))));
//        displayTag.set("Lore", loreList);
//        tag.set("display", displayTag);
        set(displayTag, "Lore", loreList);
        set(tag, "display", displayTag);
//        NBTTagList customEnchantsList = tag.hasKey("PotionEnchants") ? tag.getList("PotionEnchants", 10) : new NBTTagList();
        NBTTagList customEnchantsList = hasKey(tag, "PotionEnchant") ? getList(tag, "PotionEnchant", 10) : new NBTTagList();
        NBTTagCompound addedEnchTag = new NBTTagCompound();
//        addedEnchTag.setShort("id", (short)enchant.getId());
//        addedEnchTag.setShort("level", (short)level);
        setShort(addedEnchTag, "id", (short)enchant.getId());
        setShort(addedEnchTag, "level", (short)level);

        customEnchantsList.add(addedEnchTag);
//        tag.set("PotionEnchants", customEnchantsList);
        set(tag, "PotionEnchant", customEnchantsList);
//        if (!tag.hasKey("ench")) {
        if (!hasKey(tag, "ench")) {
//            tag.set("ench", new NBTTagList());
            set(tag, "ench", new NBTTagList());
        }
        setTag(nmsItem, tag);
//        nmsItem.setTag(tag);

        ItemStack newItem = CraftItemStack.asBukkitCopy(nmsItem);
        ItemMeta meta = newItem.getItemMeta();
//        meta.setDisplayName(getCompound(tag, "display").toString());
        assert meta != null;
        List<String> lore = meta.getLore();
        lore.add(colorize(format(PotionEnchants.getInstance().getConfig().getString("enchants.lore-format"), new Pair<>("\\{name}", enchant.getName()), new Pair<>("\\{level}", PotionEnchants.getRomanNumeral(level)))));
        meta.setLore(lore);
        newItem.setItemMeta(meta);

        return newItem;
    }

//    public static boolean isEnchantCompatibleWith(ItemStack item, PEnchant enchant) {
//        if (!enchant.getItemTarget().includes(item.getType())) {
//            return false;
//        }
//        Map<PEnchant, Integer> currentEnchants = getCustomEnchants(item);
//        System.out.println("currentEnchant keys: " + currentEnchants.keySet());
//        System.out.println("ench key: " + enchant);
//        return !currentEnchants.containsKey(enchant);
////        if (currentEnchants.containsKey(enchant)) {
////            System.out.println("contains key");
////            return false;
////        }
////        return true;
////        return enchant.getItemTarget().includes(item.getType()) && getCustomEnchants(item).containsKey(enchant);
//
//    }
    public static boolean isEnchantCompatibleWith(ItemStack item, PEnchant enchant) {
        return enchant.getItemTarget().includes(item.getType()) && !getCustomEnchants(item).containsKey(enchant);
    }

    public static Map<PEnchant, Integer> getCustomEnchants(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return Collections.emptyMap();
        }
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
//        if (!nmsItem.hasTag() || !nmsItem.getTag().hasKey("PotionEnchants")) {
        if(!hasTag(nmsItem) || !hasKey(getTag(nmsItem), "PotionEnchant")){
            return Collections.emptyMap();
        }
//        NBTTagList customEnchs = nmsItem.getTag().getList("PotionEnchants", 10);
        NBTTagList customEnchs = getList(getTag(nmsItem), "PotionEnchant", 10);
        Map<PEnchant, Integer> result = new HashMap<>(customEnchs.size());
        for (NBTBase customEnch : customEnchs) {
//            int id = 0xffff & customEnchs.get(i).getShort("id");
//            int level = 0xffff & customEnchs.get(i).getShort("level");
            int id = 0xffff & getShort((NBTTagCompound) customEnch, "id");
            int level = 0xffff & getShort((NBTTagCompound) customEnch, "level");
            result.put(PotionEnchants.getEnchantById(id), level);
        }
        return result;
    }


    public static ItemStack getRandomEnchantScroll(EnchantCategory category) {
        Random random = new Random();
        int randomNum = random.nextInt(100);
        PEnchant enchant = null;
        double current = 0;
        for (PEnchant ench : PotionEnchants.getEnchantsByCategory(category)) {
            current += ench.getChanceForEnchant();
            if (randomNum < current) {
                enchant = ench;
                break;
            }
        }
        if (enchant == null) {
            return null;
        }
        int level = enchant.getRandomLevel();
        return getEnchantScroll(enchant, level);
    }

    public static ItemStack getEnchantScroll(PEnchant enchant, int level) {
        net.minecraft.world.item.ItemStack nmsItem = new net.minecraft.world.item.ItemStack(Items.rW); // Item.getById -> Item.a || Items.rW is firecharge
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagCompound display = new NBTTagCompound();
//        display.setString("Name", Utils.colorize(
        setString(display, "Name", Utils.colorize(
                Utils.format(
                        PotionEnchants.getInstance().getConfig().getString("enchant-scrolls." + enchant.getCategory().getConfigKey() + ".name"),
                        new Pair<>("\\{name}", enchant.getName()),
                        new Pair<>("\\{level}", PotionEnchants.getRomanNumeral(level))
                )
        ));
//        display.set("Lore", createStringList(Utils.colorize(PotionEnchants.getInstance().getConfig().getStringList("enchant-scrolls." + enchant.getCategory().getConfigKey() + ".lore"))));
//        tag.set("display", display);
        set(display, "Lore", createStringList(Utils.colorize(PotionEnchants.getInstance().getConfig().getStringList("enchant-scrolls." + enchant.getCategory().getConfigKey() + ".lore"))));
        set(tag, "display", display);
//        tag.setInt("HideFlags", 34);
        setInt(tag, "HideFlags", 34);

//        tag.setString("AntI-StACk", UUID.randomUUID().toString());
        setString(tag, "AntI-StACk", UUID.randomUUID().toString());
        NBTTagCompound potEnchTag = new NBTTagCompound();
//        potEnchTag.setShort("id", (short)enchant.getId());
//        potEnchTag.setShort("level", (short)level);
        setShort(potEnchTag, "id", (short)enchant.getId());
        setShort(potEnchTag, "level", (short)level);
        set(tag, "PotionEnchant", potEnchTag);
        NBTTagCompound explosion = new NBTTagCompound();
        int[] colorArray;
        try {
            colorArray = new int[]{((Color)Color.class.getDeclaredField(PotionEnchants.getInstance().getConfig().getString("enchant-scrolls." + enchant.getCategory().getConfigKey() + ".color").toUpperCase()).get(null)).asRGB()};
        }catch (Exception e) {
            colorArray = new int[]{Color.GRAY.asRGB()};
        }
//        explosion.setIntArray("Colors", colorArray);
        setIntArray(explosion, "Colors", colorArray);
//        tag.set("Explosion", explosion);
        set(tag, "Explosion", explosion);
//        nmsItem.setTag(tag);
        setTag(nmsItem, tag);
        ItemStack craftItem = CraftItemStack.asBukkitCopy(nmsItem);
        ItemMeta meta = craftItem.getItemMeta();
        meta.setDisplayName(Utils.colorize(
                Utils.format(
                        PotionEnchants.getInstance().getConfig().getString("enchant-scrolls." + enchant.getCategory().getConfigKey() + ".name"),
                        new Pair<>("\\{name}", enchant.getName()),
                        new Pair<>("\\{level}", PotionEnchants.getRomanNumeral(level))
                )));
        meta.setLore(Utils.colorize(PotionEnchants.getInstance().getConfig().getStringList("enchant-scrolls." + enchant.getCategory().getConfigKey() + ".lore")));
        craftItem.setItemMeta(meta);
        return craftItem;
    }

    public static Pair<PEnchant, Integer> getScrollEnchant(ItemStack itemStack) {
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
//        if (!hasTag(nmsItem) || !getTag(nmsItem).hasKey("PotionEnchant")) {
        if (!hasTag(nmsItem) || !hasKey(getTag(nmsItem), "PotionEnchant")) {
            return null;
        }

//        NBTTagCompound enchTag = getTag(nmsItem).getCompound("PotionEnchant");
        NBTTagCompound enchTag = getCompound(getTag(nmsItem), "PotionEnchant");

//        int enchId = 0xffff & enchTag.getShort("id");
//        int level = 0xffff & enchTag.getShort("level");
        int enchId = 0xffff & getShort(enchTag, "id");
        int level = 0xffff & getShort(enchTag, "level");
        PEnchant enchant = PotionEnchants.getEnchantById(enchId);
        if (enchant == null) {
            return null;
        }
        return new Pair<>(enchant, level);
    }

    private static NBTTagList createStringList(List<String> list) {
        if (list != null && !list.isEmpty()) {
            NBTTagList tagList = new NBTTagList();
            list.forEach((str) -> tagList.add(formatNBTTagString(str)));
            return tagList;
        } else {
            return null;
        }
    }

    public static String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> colorize(List<String> sL) {
        return sL.stream().map(Utils::colorize).collect(Collectors.toList());
    }

    public static List<String> colorize(String ... sL) {
        return Arrays.stream(sL).map(Utils::colorize).collect(Collectors.toList());
    }

    public static String format(String s, Pair<String, String> ... pairs) {
        for (Pair<String, String> pair : pairs) {
            s = s.replaceAll(pair.getKey(), pair.getValue());
        }
        return s;
    }

    public static List<String> format(List<String> sL, Pair<String, String> ... pairs) {
        return sL.stream().map((s) -> format(s, pairs)).collect(Collectors.toList());
    }

    public static List<String> formatAndColorize(List<String> sL, Pair<String, String> ... pairs) {
        return sL.stream().map(Utils::colorize).map((s) -> format(s, pairs)).collect(Collectors.toList());
    }

    public static NBTTagList format(NBTTagList sL, Pair<String, String> ... pairs) {
        for (int i = 0; i < sL.size(); i++) {
//            sL.a(i, new NBTTagString(format(sL.getString(i), pairs)));
            sL.a(i, formatNBTTagString(format(getString(sL, i), pairs)));
        }
        return sL;
    }

    public static int getTotalExperience(Player player) {
        int ver = Integer.parseInt(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].split("_")[1]);
        int efl = ver >= 8 ? Utils.getLevelExpNew(player.getLevel()) : Utils.getLevelExpOld(player.getLevel());
        int exp = Math.round((float)efl * player.getExp());
        int currentLevel = player.getLevel();
        while (currentLevel > 0) {
            exp += Utils.getLevelExpNew(--currentLevel);
        }
        if (exp < 0) {
            exp = 0;
        }
        return exp;
    }

    public static int getLevelExpOld(int level) {
        return level >= 30 ? 62 + (level - 30) * 7 : (level >= 15 ? 17 + (level - 15) * 3 : 17);
    }

    public static int getLevelExpNew(int level) {
        return level < 16 ? level * 2 + 7 : (level < 31 ? level * 5 - 38 : level * 9 - 158);
    }


}
