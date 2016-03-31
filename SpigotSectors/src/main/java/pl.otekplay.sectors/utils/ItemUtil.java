package pl.otekplay.sectors.utils;

import org.bukkit.inventory.ItemStack;

public class ItemUtil {

    public static boolean isItemNamed(ItemStack is) {
        return (is != null) && (is.hasItemMeta()) && is.getItemMeta().hasDisplayName();
    }
}
