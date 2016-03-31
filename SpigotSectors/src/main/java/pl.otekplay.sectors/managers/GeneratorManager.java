package pl.otekplay.sectors.managers;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.builders.ItemBuilder;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;

import java.util.Arrays;

/**
 * Created by Administrator on 2/6/2016.
 */
public class GeneratorManager {
        @Getter
    private static final ItemStack
                generator = new ItemBuilder(Material.ENDER_STONE).setName(ChatUtil.fixColors("&6Generator")).setLore(Arrays.asList(ChatColor.RED + "&cxHC.PL")).toItemStack(),
                boyfarmer = new ItemBuilder(Material.ENDER_PORTAL_FRAME).setName(ChatUtil.fixColors("&6Boyfarmer")).setLore(Arrays.asList(ChatColor.RED + "&cxHC.PL", ChatColor.GREEN + "Generuje sciane obsydianu")).toItemStack(),
                digger = new ItemBuilder(Material.ENDER_PORTAL_FRAME).setName(ChatUtil.fixColors("&6Digger")).setLore(Arrays.asList(ChatColor.RED + "&cxHC.PL", ChatColor.GREEN + "Kopie dziure do bedrocka")).toItemStack(),
                sandfarmer = new ItemBuilder(Material.ENDER_PORTAL_FRAME).setName(ChatUtil.fixColors("&6Sandfarmer")).setLore(Arrays.asList(ChatColor.RED + "&cxHC.PL", ChatColor.GREEN + "Generuje sciane piasku")).toItemStack();

    public static void checkGenerator (BlockBreakEvent e) {
        if (!e.getBlock().getType().equals(Material.ENDER_STONE)) {
            return;
        }
        final Block blok = e.getBlock();
        Location loc = blok.getLocation();
        final Location loc1 = loc.clone().subtract(0, 1, 0);
        if ((blok.getType() == Material.STONE) && (loc1.getBlock().getType() == Material.ENDER_STONE)) {
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                if (loc1.getBlock().getType().equals(Material.ENDER_STONE)) {
                    blok.setType(Material.STONE);
                    return;
                }
            }, 35L);
            return;
        }
        if ((blok.getType().equals(Material.OBSIDIAN) && (loc1.getBlock().getType().equals(Material.ENDER_STONE)))) {
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                if (loc1.getBlock().getType().equals(Material.ENDER_STONE)) {
                    blok.setType(Material.OBSIDIAN);
                    return;
                }
            }, 35L);
        }
        return;
    }

    public static void checkFarmer (BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand() == null) {
            return;
        }
        if (p.getItemInHand().getItemMeta() == null) {
            return;
        }

        if (p.getItemInHand().getItemMeta().getDisplayName() == null) {
            return;
        }
        if (p.getItemInHand().getItemMeta().getDisplayName().equals("&6Boyfarmer"))  {
            p.sendMessage(Settings.GENERATOR_BOYFARMER_PLACE);
            e.getBlockPlaced().setType(Material.OBSIDIAN);
            for (int hight = e.getBlockPlaced().getY() - 1; hight > 0; hight--) {
                Block block = e.getBlock().getWorld().getBlockAt(e.getBlockPlaced().getX(), hight, e.getBlockPlaced().getZ());
                if (block.getType() == Material.BEDROCK) {
                    return;
                }
                block.setType(Material.OBSIDIAN);
            }
            return;
        }
        if (p.getItemInHand().getItemMeta().getDisplayName().equals("&6Generator")) {
            p.sendMessage(Settings.GENERATOR_PLACE);
            return;
        }

        if (p.getItemInHand().getItemMeta().getDisplayName().equals("&6Digger")) {
            p.sendMessage(Settings.GENERATOR_DIGGER_PLACE);
            e.getBlockPlaced().setType(Material.AIR);
            for (int hight = e.getBlockPlaced().getY() - 1; hight > 0; hight--) {
                Block block = e.getBlock().getWorld().getBlockAt(e.getBlockPlaced().getX(), hight, e.getBlockPlaced().getZ());
                if (block.getType() == Material.BEDROCK) {
                    return;
                }
                block.setType(Material.AIR);
            }
            return;
        }
        if (p.getItemInHand().getItemMeta().getDisplayName().equals("&6Sandfarmer")) {
            p.sendMessage(Settings.GENERATOR_SANDFARMER_PLACE);
            e.getBlockPlaced().setType(Material.SAND);
            for (int hight = e.getBlockPlaced().getY() - 1; hight > 0; hight--) {
                Block block = e.getBlock().getWorld().getBlockAt(e.getBlockPlaced().getX(), hight, e.getBlockPlaced().getZ());
                if (block.getType() == Material.BEDROCK) {
                    return;
                }
                block.setType(Material.SAND);
            }
            return;
        }
    }

}
