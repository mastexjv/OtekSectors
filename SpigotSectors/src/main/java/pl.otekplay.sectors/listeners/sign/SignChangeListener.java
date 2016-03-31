package pl.otekplay.sectors.listeners.sign;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.utils.ChatUtil;

/**
 * Created by Oskar on 16.02.2016.
 */
public class SignChangeListener implements Listener {


    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        User user = UserManager.getUser(e.getPlayer().getUniqueId());
        for (int i = 0; i < 4; i++) {
            if (e.getLine(i).matches("^[a-zA-Z0-9_]*$")) {
                if (e.getLine(i).length() > 20) {
                    e.setCancelled(true);
                    return;
                }
            } else if (e.getLine(i).length() > 50) {
                e.setCancelled(true);
                return;
            }
            if (user.can(GroupType.HELPER)) {
                e.setLine(i, ChatUtil.fixColors(e.getLine(i)));
            }
        }
    }
}