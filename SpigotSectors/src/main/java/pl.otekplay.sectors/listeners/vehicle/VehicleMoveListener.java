package pl.otekplay.sectors.listeners.vehicle;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.utils.BorderUtil;

/**
 * Created by Oskar on 16.02.2016.
 */
public class VehicleMoveListener implements Listener {

    @EventHandler
    public void onVehicleMoveEvent(VehicleMoveEvent e){
        if(e.getVehicle().getPassenger() instanceof Player) {
            Player p = (Player) e.getVehicle().getPassenger();
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
}
