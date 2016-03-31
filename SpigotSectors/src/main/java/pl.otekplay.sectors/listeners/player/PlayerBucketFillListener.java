package pl.otekplay.sectors.listeners.player;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;
import pl.otekplay.sectors.managers.GuildManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.LocationUtil;

public class PlayerBucketFillListener implements Listener {




    @EventHandler
    public void onPlayerBucketFillEvent(PlayerBucketFillEvent e){
        Player p = e.getPlayer();
        Block b = e.getBlockClicked();
        if(!GuildManager.canTouch(p,b.getLocation())){
            e.setCancelled(true);
            return;
        }
        if(LocationUtil.getDistanceFromBorder(b.getLocation()) < 50){
            p.sendMessage(Settings.SECTOR_ERROR_BUILD);
            e.setCancelled(true);
        }
    }
}
