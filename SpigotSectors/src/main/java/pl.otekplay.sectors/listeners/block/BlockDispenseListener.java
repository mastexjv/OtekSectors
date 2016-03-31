package pl.otekplay.sectors.listeners.block;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Oskar on 16.02.2016.
 */
public class BlockDispenseListener implements Listener {


    @EventHandler
    public void onBlockDispenseEvent(BlockDispenseEvent e) {
        ItemStack is = e.getItem();
        if ((is.getDurability() != 0) && (is.getType().equals(Material.POTION))) {
            Potion pt = Potion.fromItemStack(is);
            PotionEffectType pet = pt.getType().getEffectType();
            if (pet.equals(PotionEffectType.INCREASE_DAMAGE)) {
                if (pt.getLevel() > 1) {
                    e.setCancelled(true);
                }
            }
        }
    }
}

