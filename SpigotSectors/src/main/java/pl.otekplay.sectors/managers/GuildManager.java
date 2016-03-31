package pl.otekplay.sectors.managers;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.basic.GuildLocation;
import pl.otekplay.sectors.data.Guild;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.LocationUtil;
import pl.otekplay.sectors.utils.SpaceUtil;
import pl.otekplay.sectors.utils.TagUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuildManager {
    private static final Map<String,Guild> guilds = new ConcurrentHashMap<>();


    public static void createGuild(String tag, String name, User leader, String location) {
        Guild guild = new Guild(tag, name, leader, true, 0,GuildLocation.fromString(location));
        guild.getMembers().add(leader);
        leader.setGuild(guild);
        guilds.put(tag,guild);
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> createRoomGuild(guild.getLocation()));
    }

    public static Guild getGuildByName(String name) {
        return guilds.get(name);
    }

    public static Guild getGuildByTag(String tag) {
        for (Guild guild : guilds.values()) {
            if (guild.getTag().equalsIgnoreCase(tag)) {
                return guild;
            }
        }
        return null;
    }

    private static final int
            DISTANCE_FROM_GUILD = 150,
            DISTANCE_FROM_SPAWN = 500;

    public static int canCreateGuild(Location loc) {
        if (LocationUtil.isInLoc(LocationUtil.getSpawnLocation(), loc, DISTANCE_FROM_SPAWN)) {
            return 1;
        }
        for (Guild guild : guilds.values()) {
            if (LocationUtil.isInLoc(loc, guild.getBukkitLocation(), DISTANCE_FROM_GUILD)) {
                return 2;
            }
        }
        if (LocationUtil.getDistanceFromBorder(loc) <= 120) {
            return 3;
        }
        return 0;
    }

    public static boolean isGuildTag(String tag) {
        return guilds.containsKey(tag);
    }

    public static boolean isGuildName(String name) {
        return getGuildByName(name) != null;
    }

    public static void addUserToGuild(User user, Guild guild) {
        guild.getMembers().add(user);
        user.setGuild(guild);
    }

    public static void removeUserFromGuild(User user) {
        Guild guild = user.getGuild();
        guild.getMembers().remove(user);
        user.setGuild(null);
    }

    public static void removeGuild(Guild guild) {
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            Location loc = guild.getBukkitLocation();
            loc.getBlock().setType(Material.AIR);
            loc.setY(guild.getBukkitLocation().getY()-1);
            loc.getBlock().setType(Material.AIR);
            loc.getWorld().playEffect(guild.getBukkitLocation(), Effect.EXPLOSION_HUGE,5);
        });
        for (User user : guild.getMembers()) {
            removeUserFromGuild(user);
        }
        guild.getMembers().clear();
        for (Guild g : guild.getAlly()) {
            setEnemyGuild(guild, g);
        }
        guild.setLeader(null);
        guilds.remove(guild.getTag());
    }

    public static Guild getGuildByLocation(Location loc) {
        for (Guild g : guilds.values()) {
            if (g.isCub(loc)) {
                return g;
            }
        }
        return null;
    }

    public static boolean canTouch(Player p, Location loc) {
        User user = UserManager.getUser(p.getUniqueId());
        Guild terrainGuild = null;
        for (Guild guild : guilds.values()) {
            if (guild.isCub(loc)) {
                terrainGuild = guild;
                break;
            }
        }
        if (terrainGuild == null) {
            return true;
        }
        if (!user.hasGuild()) {
            p.sendMessage(Settings.GUILD_ERROR_TERRAIN.replace("%tag%", terrainGuild.getTag()));
            return false;
        }
        if (!user.getGuild().equals(terrainGuild)) {
            p.sendMessage(Settings.GUILD_ERROR_TERRAIN.replace("%tag%", terrainGuild.getTag()));
            return false;
        } else {
            if(user.getGuild().getLastTNT() > System.currentTimeMillis()){
                p.sendMessage("Nie mozesz budowac gdy na twoim terenie wybuchlo tnt.");
                return false;
            }
            return true;
        }
    }

    public static boolean canAttack(User att, User def) {
        if (att.hasGuild() && def.hasGuild()) {
            if (att.getGuild().equals(def.getGuild())) {
                if (!att.getGuild().isPvpInGuild()) {
                    att.sendMessage(Settings.GUILD_PVP_OFF);
                    return false;
                }
            }
        }
        return true;
    }

    public static void setAllyGuild(Guild g1, Guild g2) {
        g1.getAlly().add(g2);
        g2.getAlly().add(g1);
    }

    public static void setEnemyGuild(Guild g1, Guild g2) {
        g1.getAlly().remove(g2);
        g2.getAlly().remove(g1);
    }

    private static void createRoomGuild(GuildLocation gLoc) {
        Location c = new Location(Bukkit.getWorlds().get(0), gLoc.getX(), gLoc.getY(), gLoc.getZ());
        c.setY(c.getY() - 1);
        for (Location loc : SpaceUtil.getSquare(c, 4, 3)) {
            loc.getBlock().setType(Material.AIR);
        }
        for (Location loc : SpaceUtil.getSquare(c, 4)) {
            loc.getBlock().setType(Material.OBSIDIAN);
        }
        for (Location loc : SpaceUtil.getCorners(c, 4, 3)) {
            loc.getBlock().setType(Material.OBSIDIAN);
        }
        c.add(0, 4, 0);
        for (Location loc : SpaceUtil.getWalls(c, 4)) {
            loc.getBlock().setType(Material.OBSIDIAN);
        }
        c = new Location(Bukkit.getWorlds().get(0), gLoc.getX(), gLoc.getY(), gLoc.getZ());
        c.getBlock().setType(Material.DRAGON_EGG);
        c.setY(c.getY()-1);
        c.getBlock().setType(Material.BEDROCK);
    }

    public static void setNewLeader(Guild guild, User user) {
        guild.setLeader(user);
    }
}
