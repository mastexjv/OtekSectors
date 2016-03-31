package pl.otekplay.sectors.listeners;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import pl.otekplay.sectors.data.Sector;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.KitType;
import pl.otekplay.sectors.managers.SectorManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.packets.impl.player.PlayerTeleportRandomPacket;
import pl.otekplay.sectors.packets.impl.user.UserGiveKitPacket;

public class ServerConnectListener implements Listener {


    @EventHandler
    public void onServerConnectEvent(ServerConnectEvent e) {
        ProxiedPlayer pp = e.getPlayer();
        if (!UserManager.isUser(pp.getUniqueId())) {
            pp.disconnect("Przeloguj sie.");
            return;
        }
        User user = UserManager.getUser(pp.getUniqueId());
        System.out.println(e.getTarget().toString());
        System.out.println(user.getSector());
        if (user.getSector() == null) {
            System.out.println(1);
            Sector sector = SectorManager.getRandomSector();
            user.setSector(sector);
            e.setTarget(sector.getServer());
            sector.getClient().sendPacket(new PlayerTeleportRandomPacket(user.getUuid().toString()));
            sector.getClient().sendPacket(new UserGiveKitPacket(user.getUuid().toString(), KitType.STARTER.getID()));
        } else {
            System.out.println(2);
            if (SectorManager.isSector(e.getTarget().getName())) {
                Sector sector = SectorManager.getSector(e.getTarget().getName());
                user.setSector(sector);
                e.setTarget(sector.getServer());
            }
        }
    }
}
