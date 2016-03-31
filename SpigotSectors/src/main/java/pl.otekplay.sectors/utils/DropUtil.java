package pl.otekplay.sectors.utils;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import pl.otekplay.sectors.data.Drop;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;

/**
 * Created by Administrator on 3/8/2016.
 */
public class DropUtil {

    public static boolean isBlocked(Drop drop, Player p) {
        User user = UserManager.getUser(p.getUniqueId());
        if (user.getDisabledDrops() == null) {
            return false;
        }
        if (user.getDisabledDrops().contains(drop)) {
            return true;
        }
        return false;
    }

    public static String isFortuneString(boolean fortune) {
        if (fortune) {
            return "&2tak";
        } else {
            return "&cnie";
        }
    }

    public static String isBlockedString(Drop drop, Player p){
        User user = UserManager.getUser(p.getUniqueId());
        if (user.getDisabledDrops() == null) {
            return "&cERROR";
        }
        if (user.getDisabledDrops().contains(drop)) {
            return "&cwylaczony";
        } else {
            return "&2wlaczony";
        }
    }

    public static String checkChanceOfDrop(Player p, double chance) {
        double c = chance;
        User user = UserManager.getUser(p.getUniqueId());
        if(user.can(GroupType.VIP)) {
            c = c * (1+0.3);
        }
        return String.format("%.2f", c);
    }

    public static void checkLevel(Player p) {
        User user = UserManager.getUser(p.getUniqueId());
        if (user.getExp() > (int)(((user.getLevel()+1) * 1.5) * 1000)) {
            user.setExp(0);
            user.setLevel(user.getLevel()+1);
            p.sendMessage(Settings.USER_DROP_LVL_UP.replace("{LEVEL}", user.getLevel()+""));
            Firework fw = (Firework) p.getWorld().spawnEntity(p.getEyeLocation(), EntityType.FIREWORK);
        }
    }


}
