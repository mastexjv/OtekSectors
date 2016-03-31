package pl.otekplay.sectors.listeners;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.utils.AnimationUtil;

/**
 * Created by Oskar on 24.02.2016.
 */
public class DisconnectListener implements Listener {


    @EventHandler
    public void onDisconnectEvent(PlayerDisconnectEvent e){
        ProxiedPlayer p = e.getPlayer();
        User user = UserManager.getUser(p.getUniqueId());
        AnimationUtil.unregisterPacketManager(user);
    }
}
