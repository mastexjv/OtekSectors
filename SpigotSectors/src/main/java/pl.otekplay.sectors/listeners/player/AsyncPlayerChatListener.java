package pl.otekplay.sectors.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.packets.impl.player.PlayerSendMessagePacket;

public class AsyncPlayerChatListener implements Listener {


    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        Main.getInstance().getClient().sendPacket(new PlayerSendMessagePacket(p.getUniqueId().toString(), e.getMessage()));
        e.setCancelled(true);
    }
}
