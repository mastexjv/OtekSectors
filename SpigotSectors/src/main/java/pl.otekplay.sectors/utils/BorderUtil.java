package pl.otekplay.sectors.utils;

import net.minecraft.server.v1_9_R1.*;
import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.basic.Map;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.packets.impl.player.PlayerTeleportLocationPacket;
import pl.otekplay.sectors.packets.impl.sector.SectorChangePacket;
import pl.otekplay.sectors.storage.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BorderUtil {

    public static void knockLineBorder(Player p) {
        Location l = p.getLocation().subtract(p.getWorld().getSpawnLocation());
        double distance = p.getLocation().distance(p.getWorld().getSpawnLocation());
        Vector v = l.toVector().add(new Vector(0, 5, 0)).multiply(1.25 / distance);
        p.setVelocity(v.multiply(-1.5));
        p.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
    }

    public static void knockLinePvP(Player p) {
        Location l = p.getLocation().subtract(p.getWorld().getSpawnLocation());
        double distance = p.getLocation().distance(p.getWorld().getSpawnLocation());
        Vector v = l.toVector().add(new Vector(0, 3, 0)).multiply(1 / distance);
        p.setVelocity(v);
        p.sendMessage(ChatUtil.fixColors("&cPodczas walki uciekanie na spawn jest zabronione!"));
    }

    public static boolean checkBorder(Player p) throws InterruptedException {
        User user = UserManager.getUser(p.getUniqueId());
        Map map = Main.getInstance().getSector().getMap();
        int
                x = p.getLocation().getBlockX(),
                z = p.getLocation().getBlockZ(),
                changeX = 0,
                changeZ = 0;

        if (x > Settings.EASTBORDER) {
            changeX++;
        } else if (x < Settings.WESTBORDER) {
            changeX--;
        } else if (z > Settings.SOUTHBORDER) {
            changeZ++;
        } else if (z < Settings.NORTHBORDER) {
            changeZ--;
        }
        if (changeX != 0 || changeZ != 0) {
            user.setServerChange(true);
            if (user.hasCombat()) {
                p.sendMessage(ChatUtil.fixColors("&cPodczas walki nie mozesz zmienic sektora!"));
                knockLineBorder(p);
                user.setServerChange(false);
                return false;
            }
            if (changeX == 2 || changeZ == 2) {
                if (p.getVehicle() != null) {
                    p.getVehicle().setPassenger(null);
                }
                p.sendMessage(ChatUtil.fixColors("&cGranica mapy zostala osiagnieta!"));
                knockLineBorder(p);
                user.setServerChange(false);
                return false;
            }
            Map sendMap = new Map(map.getX() + changeX, map.getZ() + changeZ);
            p.getLocation().add(sendMap.getX(), 0, sendMap.getZ());
            CraftPlayer cp = (CraftPlayer) p;
            EntityPlayer ep = cp.getHandle();
            PlayerConnection con = ep.playerConnection;
            List<Entity> copyEntities = p.getWorld().getEntities();
            for (Entity en : copyEntities) {
                con.sendPacket(new PacketPlayOutEntityDestroy(en.getEntityId()));
            }
            Collection<Chunk> saveChunks = LocationUtil.getChunksAroundPlayer(p);
            List<Chunk> copyChunks = new ArrayList<>(Arrays.asList(p.getWorld().getLoadedChunks()));
            for (Chunk chunk :copyChunks) {
                if (saveChunks.contains(chunk)) {
                    continue;
                }
                net.minecraft.server.v1_9_R1.Chunk clearChunk = new net.minecraft.server.v1_9_R1.Chunk(((CraftWorld) chunk.getWorld()).getHandle(), new ChunkSnapshot(), chunk.getX(), chunk.getZ());
                con.sendPacket(new PacketPlayOutMapChunk(clearChunk, true, 0));
            }
            Main.getInstance().getClient().sendPacket(new SectorChangePacket("Sector:" + sendMap.toString(), p.getUniqueId().toString()));
            Main.getInstance().getClient().sendPacket(new PlayerTeleportLocationPacket(
                    p.getUniqueId().toString()
                    , LocationUtil.locToString(p.getLocation())));
            return true;
        }
        return false;

    }
}
