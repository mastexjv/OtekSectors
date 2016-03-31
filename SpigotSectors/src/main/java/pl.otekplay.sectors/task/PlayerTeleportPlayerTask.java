package pl.otekplay.sectors.task;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.otekplay.sectors.Main;

import java.util.UUID;

@AllArgsConstructor
public class PlayerTeleportPlayerTask extends BukkitRunnable {
    private final UUID uuid;
    private final UUID uuidTeleportPlayer;
    private int count;

    @Override
    public void run() {
        Player p = Bukkit.getPlayer(uuid);
        System.out.println("Gracz "+p);
        if (p != null) {
            Player teleport = Bukkit.getPlayer(uuidTeleportPlayer);
            if (teleport != null) {
                p.teleport(teleport.getLocation());
                p.hidePlayer(teleport);
                p.showPlayer(teleport);
                teleport.hidePlayer(p);
                teleport.showPlayer(p);
                cancel();
                return;
            }else{
                p.sendMessage("Gracz do ktorego miales sie przeteleportowac wylogowal sie.");
                cancel();
                return;
            }
        }
        if (count == 100) {
            cancel();
            return;
        }
        count++;
    }

    public static void start(UUID uuid, UUID player) {
        new PlayerTeleportPlayerTask(uuid, player, 0).runTaskTimer(Main.getInstance(), 1, 1);
    }
}