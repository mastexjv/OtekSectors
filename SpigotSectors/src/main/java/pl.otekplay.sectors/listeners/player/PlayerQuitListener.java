package pl.otekplay.sectors.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.netty.SectorClient;
import pl.otekplay.sectors.packets.impl.player.PlayerInventoryUpdatePacket;
import pl.otekplay.sectors.packets.impl.player.PlayerStatsUpdatePacket;
import pl.otekplay.sectors.utils.SerializationUtil;
import pl.otekplay.sectors.utils.TagUtil;

public class PlayerQuitListener implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Player p = e.getPlayer();
        User user = UserManager.getUser(p.getUniqueId());
        if (user.hasCombat()) {
            p.damage(20.0);
        }
        TagUtil.removeBoard(p);
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            if (user.isServerChange()) {
                user.setServerChange(false);
            }
            SectorClient client = Main.getInstance().getClient();
            client.sendPacket(new PlayerStatsUpdatePacket(
                    p.getUniqueId().toString(),
                    SerializationUtil.serializePotionEffects(p),
                    p.getHealth(), p.getFoodLevel(),
                    p.getTotalExperience(),
                    p.isSprinting(),
                    p.getGameMode().toString()));
            client.sendPacket(new PlayerInventoryUpdatePacket(
                    p.getUniqueId().toString(),
                    p.getInventory().getHeldItemSlot(),
                    SerializationUtil.itemStackArrayToBase64(p.getInventory().getContents()),
                    SerializationUtil.itemStackArrayToBase64(p.getInventory().getArmorContents()),
                    SerializationUtil.itemStackArrayToBase64(p.getEnderChest().getContents())
            ));
        });
    }
}
