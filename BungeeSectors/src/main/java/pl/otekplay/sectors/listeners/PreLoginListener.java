package pl.otekplay.sectors.listeners;

import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import pl.otekplay.sectors.data.Ban;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.BanManager;
import pl.otekplay.sectors.managers.SectorManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;

public class PreLoginListener implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLoginEvent(PreLoginEvent e) {
        System.out.println(e.getConnection().getUniqueId()+" connecting...");
        System.out.println(e.getConnection().getUniqueId());
        if(e.isCancelled()){
            return;
        }
        String name = e.getConnection().getName();
        if(!ChatUtil.isAlphaNumeric(name)){
            e.setCancelReason(Settings.LOGIN_NICK_BAD);
            e.setCancelled(true);
            return;
        }
        if(Settings.WHITELISTMODE){
            if(!Settings.whitelist.contains(name)){
                e.setCancelReason(Settings.WHITELIST_MESSAGE);
                e.setCancelled(true);
                return;
            }
        }
        if(SectorManager.getSectors().size() != 9){
            e.setCancelReason(Settings.SECTORS_STILL_LOAD);
            e.setCancelled(true);
            return;
        }
        boolean exist = UserManager.isUser(e.getConnection().getUniqueId());
        if (BanManager.isBanned(e.getConnection().getUniqueId())) {
            Ban ban = BanManager.getBan(e.getConnection().getUniqueId());
            e.setCancelReason(Settings.BAN_PLAYER_MSG.replace("%time%",ban.getStringDate()).replace("%reason%",ban.getReason()).replace("%admin%",ban.getBanner()));
            e.setCancelled(true);
            return;
        }
        User user;
        if (!UserManager.isUser(e.getConnection().getUniqueId())) {
            user = UserManager.createUser(e.getConnection().getUniqueId(), e.getConnection().getName(),  e.getConnection().getAddress().getHostString(), e.getConnection().getVirtualHost().toString().toLowerCase().split(":")[0]);
        } else {
            user = UserManager.getUser(e.getConnection().getUniqueId());
            if(!user.getDomain().equalsIgnoreCase(e.getConnection().getVirtualHost().toString().toLowerCase().split(":")[0])){
                user.setDomain(e.getConnection().getVirtualHost().toString().toLowerCase().split(":")[0]);
            }
            if(!user.getIP().equalsIgnoreCase(e.getConnection().getAddress().getHostString())){
                user.setIP(e.getConnection().getAddress().getHostString());
            }
        }
        if (!user.getName().equals(e.getConnection().getName())) {
            e.setCancelReason(Settings.LOGIN_NICK_IS_EXIST);
            e.setCancelled(true);
            return;
        }
    }
}
