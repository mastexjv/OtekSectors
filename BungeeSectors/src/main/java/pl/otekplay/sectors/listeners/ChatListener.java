package pl.otekplay.sectors.listeners;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.CommandUtil;

public class ChatListener implements Listener {


    @EventHandler
    public void onChatEvent(ChatEvent e) {
        if (!e.isCommand()) {
            return;
        }
        String[] messages = e.getMessage().split(" ");
        String message = messages[0].replace("/", "");
        if (!CommandUtil.isCommandExsist(message)) {
            User user = UserManager.getUserByIP(e.getSender().getAddress().getHostString());
            if (user != null) {
                if (user.can(GroupType.MODERATOR)) {
                    return;
                }
                if (user.getLastCommandTime() > System.currentTimeMillis()) {
                    user.sendMessage(Settings.PREFIX+ChatColor.RED + "Komend mozesz uzywac co 10 sekund!");
                    e.setCancelled(true);
                    return;
                }
                user.sendMessage(Settings.PREFIX+ChatColor.GRAY+"Komendy " + ChatColor.RED + message + ChatColor.GRAY + " nie ma na naszym serwerze, jesli potrzebujesz pomocy uzyj komendy "+ChatColor.RED+"/pomoc");
                e.setCancelled(true);
                user.setLastCommandTime(System.currentTimeMillis() + 10000);
            }
        }
    }
}
