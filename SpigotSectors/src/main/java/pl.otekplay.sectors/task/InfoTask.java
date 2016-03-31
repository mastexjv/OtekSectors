package pl.otekplay.sectors.task;

import me.confuser.barapi.BarAPI;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.LocationUtil;

public class InfoTask implements Runnable {
    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            User user= UserManager.getUser(p.getUniqueId());
            if(user.hasCombat()){
                if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() != 47) {
                    BarAPI.setMessage(p, ChatUtil.fixColors("&7[&cANTYRELOG&7]"));
                } else {
                    ChatUtil.sendActionBar(p, ChatUtil.fixColors("&7[&cANTYRELOG&7]"));
                }
                continue;
            }
            int distance = LocationUtil.getDistanceFromBorder(p.getLocation());
            if (distance < 50) {
                if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() != 47) {
                    BarAPI.setMessage(p, ChatUtil.fixColors("&7GRANICA SEKTORA -- > &c" + distance), (float) (2 * distance));
                } else {
                    ChatUtil.sendActionBar(p, ChatUtil.fixColors("&7GRANICA SEKTORA -- > &c" + distance));
                }

            } else {
                if (BarAPI.hasBar(p)) {
                    if(UserManager.getUser(p.getUniqueId()).hasCombat()){
                        continue;
                    }
                    BarAPI.removeBar(p);
                }
            }
        }
    }

    public static void start() {
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new InfoTask(), 5, 5);
    }
}
