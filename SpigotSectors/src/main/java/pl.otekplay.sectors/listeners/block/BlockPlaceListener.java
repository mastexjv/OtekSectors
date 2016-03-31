package pl.otekplay.sectors.listeners.block;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.otekplay.sectors.managers.GeneratorManager;
import pl.otekplay.sectors.managers.GuildManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.CobblexUtil;
import pl.otekplay.sectors.utils.LocationUtil;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e){
        Player p = e.getPlayer();
        Block b = e.getBlock();
        if(!GuildManager.canTouch(p,b.getLocation())){
            e.setCancelled(true);
            return;
        }
        if(LocationUtil.getDistanceFromBorder(b.getLocation()) < 50){
            p.sendMessage(Settings.SECTOR_ERROR_BUILD);
            e.setCancelled(true);
            return;
        }

        GeneratorManager.checkFarmer(e);
        if(p.getItemInHand().getItemMeta().equals(CobblexUtil.getCobblex().getItemMeta())){
            CobblexUtil.getPlacedCobblex().add(e.getBlock().getLocation());
        }
    }
}
