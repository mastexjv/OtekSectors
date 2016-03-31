package pl.otekplay.sectors.listeners;

import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.utils.AnimationUtil;

/**
 * Created by Oskar on 24.02.2016.
 */
public class PostLoginListener implements Listener {
    @EventHandler
    public void onPostLoginEvent(PostLoginEvent e){
        User user = UserManager.getUser(e.getPlayer().getUniqueId());
        AnimationUtil.registerPacketManager(user);
    }
}
