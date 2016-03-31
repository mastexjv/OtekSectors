package pl.otekplay.sectors.listeners;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import pl.otekplay.sectors.storage.Settings;

public class ProxyPingListener implements Listener {


    @EventHandler
    public void onProxyPingEvent(ProxyPingEvent e) {
        e.getResponse().setDescription(Settings.MOTD);
        e.getResponse().getPlayers().setOnline(BungeeCord.getInstance().getOnlineCount() * Settings.MULTIPLER_ONLINE);
        e.getResponse().getPlayers().setMax(e.getResponse().getPlayers().getOnline() + 1);

    }
}
