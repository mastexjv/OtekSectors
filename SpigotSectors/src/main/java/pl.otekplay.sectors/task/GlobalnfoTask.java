package pl.otekplay.sectors.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.LocationUtil;

public class GlobalnfoTask implements Runnable {
    public static void start() {
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new GlobalnfoTask(), 10, 10);
    }

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            User user= UserManager.getUser(p.getUniqueId());
            if(user.hasCombat()){
                ChatUtil.sendActionBar(p, ChatUtil.fixColors("&7[&cANTYRELOG&7]"));
                continue;
            }
            int distance = LocationUtil.getDistanceFromBorder(p.getLocation());
            if (distance < 50) {
                ChatUtil.sendActionBar(p, ChatUtil.fixColors("&7GRANICA SEKTORA -- > &c" + distance));
            }
        }
    }
}
