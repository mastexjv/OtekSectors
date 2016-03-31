package pl.otekplay.sectors.listeners.craft;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import pl.otekplay.sectors.utils.CobblexUtil;
import pl.otekplay.sectors.utils.RandomUtil;


public class CraftItemListener implements Listener {


    @EventHandler
    public void onCraftItemEvent(CraftItemEvent e) {
        if (e.getInventory().getResult() != null) {
            if (e.getInventory().getResult().equals(CobblexUtil.getCobblex())) {
                for (int i = 1; i < 10; i++) {
                    e.getInventory().setItem(i, new ItemStack(Material.AIR, 1));
                }
                e.setCurrentItem(new ItemStack(Material.AIR));
                ItemStack cobble = CobblexUtil.getCobblex().clone();
                cobble.setAmount(RandomUtil.getRandInt(1, 3));
                e.getWhoClicked().getInventory().addItem(cobble);
                e.setCancelled(true);
            }
        }
    }
}
