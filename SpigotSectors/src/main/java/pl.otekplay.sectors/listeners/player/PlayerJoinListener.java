package pl.otekplay.sectors.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.packets.impl.player.PlayerTeleportLocationPacket;
import pl.otekplay.sectors.packets.impl.sector.SectorChangePacket;
import pl.otekplay.sectors.utils.LocationUtil;
import pl.otekplay.sectors.utils.TagUtil;

public class PlayerJoinListener implements Listener {


    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        TagUtil.createBoard(p);
        TagUtil.updateBoard(p);
        User user = UserManager.getUser(p.getUniqueId());
        if(user.isVanish()){
            user.hidePlayer();
        }else{
            user.showPlayer();
        }
        p.setFlying(user.isFly());
        for(Player online:Bukkit.getOnlinePlayers()){
            User userOnline = UserManager.getUser(online.getUniqueId());
            if(userOnline.isVanish()){
                p.hidePlayer(online.getPlayer());
            }
        }
        e.setJoinMessage(null);
        if(p.isDead()){
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                p.spigot().respawn();
                Main.getInstance().getClient().sendPacket(new SectorChangePacket("Sector:0:0", p.getUniqueId().toString()));
                Main.getInstance().getClient().sendPacket(new PlayerTeleportLocationPacket(p.getUniqueId().toString(), LocationUtil.locToString(p.getWorld().getSpawnLocation())));
            }, 1);
        }
    }
}