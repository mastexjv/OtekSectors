package pl.otekplay.sectors.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

/**
 * Created by Oskar on 19.02.2016.
 */
public class PlayerSpawnLocationListener implements Listener {


    @EventHandler
    public void onPlayerSpawnLocationEvent(PlayerSpawnLocationEvent e){
        e.setSpawnLocation(e.getPlayer().getLocation());
    }
}
