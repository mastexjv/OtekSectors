package pl.otekplay.sectors.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Oskar on 18.02.2016.
 */
public class ItemsUtil {
    public static void addItemsToPlayer(final Player player, final List<ItemStack> items, final Block b) {
        final PlayerInventory inv = player.getInventory();
        final HashMap<Integer, ItemStack> notStored = inv.addItem((ItemStack[])items.toArray(new ItemStack[items.size()]));
        for (final Map.Entry<Integer, ItemStack> en : notStored.entrySet()) {
            b.getWorld().dropItemNaturally(b.getLocation(), en.getValue());
        }
    }

    public static void addItemsToInv(final Inventory inv, final List<ItemStack> items, final Block b) {
        final HashMap<Integer, ItemStack> notStored = inv.addItem((ItemStack[])items.toArray(new ItemStack[items.size()]));
        for (final Map.Entry<Integer, ItemStack> en : notStored.entrySet()) {
            b.getWorld().dropItemNaturally(b.getLocation(), en.getValue());
        }
    }

    public static void addItemsToInv(final Inventory inv, final ItemStack items, final Block b) {
        final HashMap<Integer, ItemStack> notStored = inv.addItem(items);
        for (final Map.Entry<Integer, ItemStack> en : notStored.entrySet()) {
            b.getWorld().dropItemNaturally(b.getLocation(), en.getValue());
        }
    }

    public static void addItemsToPlayer(final Player player, final ItemStack items, final Block b) {
        final PlayerInventory inv = player.getInventory();
        final HashMap<Integer, ItemStack> notStored = inv.addItem(items);
        for (final Map.Entry<Integer, ItemStack> en : notStored.entrySet()) {
            b.getWorld().dropItemNaturally(b.getLocation(), en.getValue());
        }
    }
}
