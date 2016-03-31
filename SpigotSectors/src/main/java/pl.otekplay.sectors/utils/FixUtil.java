package pl.otekplay.sectors.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.otekplay.sectors.Main;

public class FixUtil {
    public static void fixClonePlayers(final Player p) {
        if(true){
            return;
        }
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (p.equals(online)) {
                    continue;
                }
                p.hidePlayer(online);
                online.hidePlayer(p);
                p.showPlayer(online);
                online.showPlayer(p);
            }
        });

    }
}
