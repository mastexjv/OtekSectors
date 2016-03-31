package pl.otekplay.sectors.listeners.entity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.UserManager;

/**
 * Created by Administrator on 2/18/2016.
 */
public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamageEvent (EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player)e.getEntity();
        User user = UserManager.getUser(p.getUniqueId());
        if (user.isGod()) {
            e.setCancelled(true); // Zapytac Otka :V
        }
    }

}
