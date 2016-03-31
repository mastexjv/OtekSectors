package pl.otekplay.sectors.listeners.entity;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import pl.otekplay.sectors.data.Guild;
import pl.otekplay.sectors.managers.GuildManager;
import pl.otekplay.sectors.utils.*;

import java.util.List;

public class EntityExplodeListener implements Listener {

    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent e) {
        if (!TimeUtil.isNight()) {
            e.setCancelled(true);
            e.blockList().clear();
            return;
        }
        Location loc = e.getLocation();
        List<Location> sphere = SpaceUtil.sphere(loc, 4, 4, false, true, 0);
        for (Location l : sphere) {
            //BUILD BLOCK
            if (GuildManager.getGuildByLocation(l) != null) {
                Guild guild = GuildManager.getGuildByLocation(l);
                if (System.currentTimeMillis() > guild.getLastTNT()) {
                   guild.getLeader().sendMessage("Na twoim terenie wybuchlo tnt, nie mozecie budowac przez 120 sekund");
                }
                guild.setLastTNT(System.currentTimeMillis() + (120*1000));
            }

            //OBSIDIAN ETC.
            Material material = l.getBlock().getType();
            if (material == Material.DRAGON_EGG) {
                e.setCancelled(true);
                return;
            }
            if (material == Material.WATER || material == Material.STATIONARY_WATER || material == Material.LAVA || material == Material.STATIONARY_LAVA) {
                if (RandomUtil.getChance(25.0)) {
                    l.getBlock().setType(Material.COBBLESTONE);
                }
            }
            if (material == Material.ENDER_CHEST || material == Material.OBSIDIAN || material == Material.ANVIL || material == Material.ENCHANTMENT_TABLE) {
                if (RandomUtil.getChance(10.0)) {
                    l.getBlock().setType(Material.AIR);
                }
            }
            if (material == Material.BEACON) {
                l.getBlock().setType(Material.AIR);
                l.getBlock().getWorld().dropItemNaturally(l, new ItemStack(Material.BEACON));
                l.getBlock().getWorld().playEffect(l, Effect.EXPLOSION_LARGE, 3);
            }
        }
    }
}