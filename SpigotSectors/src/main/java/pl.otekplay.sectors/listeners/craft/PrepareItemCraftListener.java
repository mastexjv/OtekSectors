package pl.otekplay.sectors.listeners.craft;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import pl.otekplay.sectors.utils.CobblexUtil;


/**
 * Created by rarlo on 20.11.2015.
 */
public class PrepareItemCraftListener implements Listener {


    @EventHandler
    public void onPrepareItemCraftEvent(PrepareItemCraftEvent e) {
        if (e.getInventory().getResult().equals(CobblexUtil.getCobblex())) {
            boolean c = true;
            for (int i = 1; i < 10; i++) {
                if (!(e.getInventory().getItem(i).getType() != null && e.getInventory().getItem(i).getType().equals(Material.COBBLESTONE) && e.getInventory().getItem(i).getAmount() == 64)) {
                    c = false;
                }
            }
            if (!c) {
                e.getInventory().setResult(new ItemStack(Material.AIR, 1));
            }
        }

    }
}
