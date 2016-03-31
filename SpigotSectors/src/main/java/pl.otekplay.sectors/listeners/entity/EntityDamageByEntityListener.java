package pl.otekplay.sectors.listeners.entity;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.data.Guild;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.GuildManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.packets.impl.player.PlayerAttackPlayerPacket;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if(e.isCancelled()){
            e.setCancelled(true);
            return;
        }
        if (e.getEntity() instanceof Player) {
            Player pDef = (Player) e.getEntity();
            User def = UserManager.getUser(pDef.getUniqueId());
            Player pAtt = null;
            if (e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) e.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    pAtt = (Player) arrow.getShooter();
                }
            } else if (e.getDamager() instanceof Player) {
                pAtt = (Player) e.getDamager();
            }
            if (pAtt == null) {
                return;
            }
            if(pDef.equals(pAtt)){
                e.setCancelled(true);
                return;
            }
            User att = UserManager.getUser(pAtt.getUniqueId());
            if (!GuildManager.canAttack(att, def)) {
                e.setCancelled(true);
                return;
            }
            if(att.hasGuild() && def.hasGuild()){
                Guild guildAtt = att.getGuild();
                Guild guildDef = def.getGuild();
                if(guildAtt.equals(guildDef)){
                    if(!guildAtt.isPvpInGuild()){
                        e.setCancelled(true);
                    }
                }else if(guildAtt.getAlly().contains(guildDef)){
                    e.setCancelled(true);
                }
            }
            long attack = System.currentTimeMillis();
            def.setLastAttack(attack);
            att.setLastAttack(attack);
            Main.getInstance().getClient().sendPacket(new PlayerAttackPlayerPacket(def.getUuid().toString(),att.getUuid().toString(),attack));
        }
    }
}
