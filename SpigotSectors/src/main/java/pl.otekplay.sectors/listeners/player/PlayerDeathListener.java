package pl.otekplay.sectors.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.packets.impl.player.PlayerDeathPacket;
import pl.otekplay.sectors.packets.impl.player.PlayerTeleportLocationPacket;
import pl.otekplay.sectors.packets.impl.sector.SectorChangePacket;
import pl.otekplay.sectors.utils.ItemsUtil;
import pl.otekplay.sectors.utils.LocationUtil;

public class PlayerDeathListener implements Listener {


    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Main.getInstance().getClient().sendPacket(new PlayerDeathPacket(p.getUniqueId().toString()));
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            p.spigot().respawn();
            Main.getInstance().getClient().sendPacket(new SectorChangePacket("Sector:0:0", p.getUniqueId().toString()));
            Main.getInstance().getClient().sendPacket(new PlayerTeleportLocationPacket(p.getUniqueId().toString(), LocationUtil.locToString(p.getWorld().getSpawnLocation())));
        }, 1);
        e.setDeathMessage(null);
        e.getDrops().clear();
        User user = UserManager.getUser(p.getUniqueId());
        if(p.getKiller() != null){
            Player killer = p.getKiller();
            if(killer.isOnline()) {
                ItemsUtil.addItemsToPlayer(killer, e.getDrops(), killer.getLocation().getBlock());
                e.getDrops().clear();
            }
        }

        if(user.hasCombat()){
            user.setLastAttack(0);
        }
    }
}
