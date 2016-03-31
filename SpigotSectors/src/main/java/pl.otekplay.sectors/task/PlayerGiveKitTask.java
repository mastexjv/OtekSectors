package pl.otekplay.sectors.task;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.data.Kit;
import pl.otekplay.sectors.managers.KitManager;
import pl.otekplay.sectors.utils.LocationUtil;

import java.util.UUID;

/**
 * Created by Oskar on 16.02.2016.
 */
@AllArgsConstructor
public class PlayerGiveKitTask extends BukkitRunnable {

    private final UUID uuid;
    private final Kit kit;
    private int count;
    @Override
    public void run() {
        Player p = Bukkit.getPlayer(uuid);
        if (p != null) {
            kit.equip(p);
            cancel();
            return;
        }
        if (count == 100) {
            cancel();
            return;
        }
        count++;

    }



    public static void start(UUID uuid,int id){
        new PlayerGiveKitTask(uuid, KitManager.getKit(id),0).runTaskTimer(Main.getInstance(),0,1);

    }
}
