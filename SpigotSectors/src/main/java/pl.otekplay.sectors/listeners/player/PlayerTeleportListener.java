package pl.otekplay.sectors.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener implements Listener {



    @EventHandler
    public void onPlayerTeleportEvent(PlayerTeleportEvent e){
        Player p = e.getPlayer();

    }
}
