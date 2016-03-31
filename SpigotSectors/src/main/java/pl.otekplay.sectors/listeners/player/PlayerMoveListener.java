package pl.otekplay.sectors.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.utils.BorderUtil;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        User user = UserManager.getUser(p.getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!user.isServerChange()) {
                    try {
                        BorderUtil.checkBorder(p);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }.runTaskAsynchronously(Main.getInstance());

    }

}
