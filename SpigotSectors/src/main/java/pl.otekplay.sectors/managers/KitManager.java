package pl.otekplay.sectors.managers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.otekplay.sectors.builders.ItemBuilder;
import pl.otekplay.sectors.data.Kit;
import pl.otekplay.sectors.enums.KitType;
import pl.otekplay.sectors.utils.ChatUtil;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Oskar on 16.02.2016.
 */
public class KitManager {
    private static ConcurrentHashMap<Integer, Kit> kits = new ConcurrentHashMap<>();

    public static void registerKit(Kit kit) {
        kits.put(kit.getID(), kit);
    }
    public static Kit getKit(int id){
        return kits.get(id);
    }

    public static void loadKits() {
        registerKit(new Kit(KitType.STARTER.getID(), "Starter", ChatUtil.fixColors("&2Startowe itemy xHC.pl"), new String[]{
                ChatUtil.fixColors("&6Dziekujemy ze wybrales nasz serwer!")
        }, new ItemStack[]{
                new ItemBuilder(Material.IRON_PICKAXE).setName(ChatUtil.fixColors("&fx&4HC.PL")).toItemStack(),
                new ItemBuilder(Material.COOKED_BEEF, 64).setName(ChatUtil.fixColors("&fx&4HC.PL")).toItemStack(),
                new ItemBuilder(Material.ENDER_CHEST, 1).setName(ChatUtil.fixColors("&fx&4HC.PL")).toItemStack(),
                new ItemBuilder(Material.BOAT, 1).setName(ChatUtil.fixColors("&fx&4HC.PL")).toItemStack()
        }));
     //   registerKit(new Kit(KitType.VIP.getID(), "Vip", ChatUtil.fixColors("&2Startowe itemy xHC.pl"), new String[]{
             //   ChatUtil.fixColors("&6Dziekujemy ze wybrales nasz serwer!")
       // }, new ItemStack[]{
                /*new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 3).toItemStack(),
                new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 3).toItemStack(),
                new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 3).toItemStack(),
                new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 3).toItemStack(),
                new ItemBuilder(Material.DIAMOND_PICKAXE).addEnchant(org.bukkit.enchantments.Enchantment.DIG_SPEED, 5).addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 3).addEnchant(org.bukkit.enchantments.Enchantment.LUCK, 3).toItemStack(),
                new ItemBuilder(Material.BOW).addEnchant(org.bukkit.enchantments.Enchantment.ARROW_KNOCKBACK, 2).addEnchant(org.bukkit.enchantments.Enchantment.ARROW_INFINITE, 1).toItemStack(),
                new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 5).addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 3).addEnchant(org.bukkit.enchantments.Enchantment.FIRE_ASPECT, 2).toItemStack(),
                new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(org.bukkit.enchantments.Enchantment.KNOCKBACK, 2).addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 3).toItemStack()
                new ItemStack(Material.ENDER_PEARL, 5),
                new ItemStack(Material.GOLDEN_APPLE, 10),
                new ItemStack(Material.GOLDEN_APPLE, 3, (short)1)*/
        //}));
    }
}
