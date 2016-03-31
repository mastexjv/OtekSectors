package pl.otekplay.sectors.listeners.player;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.data.Guild;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.GuildManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.menus.TestEnchantMenu;
import pl.otekplay.sectors.packets.impl.player.PlayerDestroyGuildPacket;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;

public class PlayerInteractListener implements Listener {


    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(p.getItemInHand() != null) {
            if ((p.getItemInHand().getTypeId() == 373) && (p.getItemInHand().getData().getData() == 41)) {
                e.setCancelled(true);
                p.sendMessage(ChatUtil.fixColors("&cMikstura Sila II sa zablokowane !"));
                return;
            }
        }
        if (e.getClickedBlock() == null) {
            return;
        }
        Block b = e.getClickedBlock();
        if (b.getType() == Material.DRAGON_EGG) {
            e.setCancelled(true);

            if (GuildManager.getGuildByLocation(b.getLocation()) == null) {
                return;
            }
            User user = UserManager.getUser(p.getUniqueId());
            if (!user.hasGuild()) {
                p.sendMessage(Settings.GUILD_ATTACK_NO_GUILD);
                return;
            }
            Guild guild = user.getGuild();
            Guild guildLoc = GuildManager.getGuildByLocation(b.getLocation());
            if (guild.equals(guildLoc)) {
                p.sendMessage(Settings.GUILD_ATTACK_OUR);
                return;
            }
            if (guild.getAlly().contains(guildLoc)) {
                p.sendMessage(Settings.GUILD_ATTACK_ALLY);
                return;
            }
            Main.getInstance().getClient().sendPacket(new PlayerDestroyGuildPacket(guildLoc.getTag(), p.getUniqueId().toString()));
        }
        TestEnchantMenu.enchantInteract(e);
    }
}
