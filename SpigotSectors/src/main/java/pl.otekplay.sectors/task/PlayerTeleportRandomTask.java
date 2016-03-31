package pl.otekplay.sectors.task;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.utils.LocationUtil;

import java.util.UUID;
@AllArgsConstructor
public class PlayerTeleportRandomTask extends BukkitRunnable {
    private final UUID uuid;
    private final Location loc;
    private int count;


    @Override
    public void run() {
        System.out.println(count);
        Player p = Bukkit.getPlayer(uuid);
        if (p != null) {
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
    public static void start(UUID uuid){
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            Location loc1 = LocationUtil.getValidRandomLocation();
            System.out.println(loc1.toString());
            new PlayerTeleportRandomTask(uuid, loc1,0).runTaskTimer(Main.getInstance(),1,1);
        });
    }
}