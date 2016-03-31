package pl.otekplay.sectors.listeners;

import net.md_5.bungee.api.event.PreLoginEvent;
import pl.kamcio96.uuidfetcher.DataCallback;
import pl.kamcio96.uuidfetcher.PlayerUUIDData;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.data.Ban;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.BanManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;

public class CustomLoginListener implements DataCallback {

    private final PreLoginEvent event;
    private final boolean fetcher;

    public CustomLoginListener(PreLoginEvent e, boolean usedFetcher) {
        this.event = e;
        this.fetcher = usedFetcher;
    }

    @Override
    public void result(PlayerUUIDData data) {
        System.out.println(data.toString());
        if (BanManager.isBanned(data.getUniqueId())) {
            Ban ban = BanManager.getBan(data.getUniqueId());
            event.setCancelReason(Settings.BAN_PLAYER_MSG);
            event.setCancelled(true);
            if (fetcher) {
                event.completeIntent(Main.getInstance());
            }
            return;
        }
        User user;
        if (!UserManager.isUser(data.getUniqueId())) {
            user = UserManager.createUser(data.getUniqueId(), data.getName(), data.isPremium(), event.getConnection().getAddress().getHostString(), event.getConnection().getVirtualHost().toString().toLowerCase().split(":")[0]);
        } else {
            user = UserManager.getUser(data.getUniqueId());
            if(!user.getDomain().equalsIgnoreCase(event.getConnection().getVirtualHost().toString().toLowerCase().split(":")[0])){
                user.setDomain(event.getConnection().getVirtualHost().toString().toLowerCase().split(":")[0]);
            }
            if(!user.getIP().equalsIgnoreCase(event.getConnection().getAddress().getHostString())){
                user.setIP(event.getConnection().getAddress().getHostString());
            }
        }
        if (!user.getName().equals(event.getConnection().getName())) {
            event.setCancelReason(Settings.LOGIN_NICK_IS_EXIST);
            event.setCancelled(true);
            if (fetcher) {
                event.completeIntent(Main.getInstance());
            }
            return;
        }
        event.getConnection().setOnlineMode(user.isPremium());
        if (fetcher) {
            event.completeIntent(Main.getInstance());
        }
    }

    @Override
    public void error() {
        event.setCancelReason(Settings.LOGIN_SERVER_ERROR);
        event.setCancelled(true);
        event.completeIntent(Main.getInstance());
    }
}
