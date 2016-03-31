package pl.otekplay.sectors.task;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.packets.impl.player.PlayerTeleportAnswerPacket;
import pl.otekplay.sectors.utils.LocationUtil;

import java.util.UUID;

@AllArgsConstructor
public class RequestTeleportTask extends BukkitRunnable {
    private final UUID uuid;
    private final int time;
    private final int action;
    private final String teleport;
    private int count;

    @Override
    public void run() {
        Player p = Bukkit.getPlayer(uuid);
        Location loc = p.getLocation();
        if (p != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (p != null) {
                        if (LocationUtil.isSameLocation(loc, p.getLocation())) {
                            Main.getInstance().getClient().sendPacket(new PlayerTeleportAnswerPacket(uuid.toString(),0,action,teleport));
                        } else {
                            Main.getInstance().getClient().sendPacket(new PlayerTeleportAnswerPacket(uuid.toString(),1,action,teleport));
                        }
                    }
                }
            }.runTaskLater(Main.getInstance(), 20 * 5);
            cancel();
            return;
        }
        if (count == 100) {
            cancel();
            return;
        }
        count++;
    }

    public static void start(UUID uuid, int time, int action, String teleport) {
        new RequestTeleportTask(uuid, time, action, teleport, 0).runTaskTimer(Main.getInstance(), 0, 1);
    }
}