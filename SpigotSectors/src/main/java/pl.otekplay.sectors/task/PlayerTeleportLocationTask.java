package pl.otekplay.sectors.task;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.otekplay.sectors.Main;

import java.util.UUID;

@AllArgsConstructor
public class PlayerTeleportLocationTask extends BukkitRunnable {
    private final UUID uuid;
    private final Location loc;
    private int count;

    @Override
    public void run() {
        Player p = Bukkit.getPlayer(uuid);
        if (p != null) {
            if(loc.getY() == 0){
                loc.setY(loc.getWorld().getHighestBlockYAt(loc.getBlockX(),loc.getBlockZ()));
            }
            p.teleport(loc);
            cancel();
            return;
        }
        if (count == 100) {
            cancel();
            return;
        }
        count++;
    }
    public static void start(UUID uuid,Location loc){
        new PlayerTeleportLocationTask(uuid,loc,0).runTaskTimer(Main.getInstance(),1,1);
    }
}
