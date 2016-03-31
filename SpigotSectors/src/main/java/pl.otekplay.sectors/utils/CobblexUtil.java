package pl.otekplay.sectors.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import pl.otekplay.sectors.builders.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oskar on 16.02.2016.
 */
public class CobblexUtil {

    private static ItemStack item;
    private static List<Location> placedCobblex;

    public static void init() {
        placedCobblex = new ArrayList<>();
        item = new ItemBuilder(Material.MOSSY_COBBLESTONE).setName(ChatUtil.fixColors("&e&lCobblex")).setLore(ChatUtil.fixColors("&ePostaw, a nastepnie rozwal aby otrzymac itemy!")).toItemStack();
        Bukkit.addRecipe(new ShapedRecipe(item).shape("ccc", "ccc", "ccc").setIngredient('c', Material.COBBLESTONE));
    }


    public static ItemStack getCobblex() {
        return item;
    }

    public static List<Location> getPlacedCobblex() {
        return placedCobblex;
    }
}
