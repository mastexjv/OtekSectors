package pl.otekplay.sectors.utils;

import org.bukkit.*;
import org.bukkit.entity.Player;
import pl.otekplay.sectors.managers.GuildManager;
import pl.otekplay.sectors.storage.Settings;

import java.util.Collection;
import java.util.HashSet;

public class LocationUtil {

    public static Location getSpawnLocation() {
        return Bukkit.getWorlds().get(0).getSpawnLocation();
    }

    public static void loadSpawnLocation() {
        World world = Bukkit.getWorlds().get(0);
        world.setSpawnLocation(-33,89,-148);
    }

    public static Location locFromString(String str) {
        String str2loc[] = str.split(":");
        Location loc = new Location(Bukkit.getWorlds().get(0), 0, 0, 0, 0F, 0F);
        loc.setX(Double.parseDouble(str2loc[0]));
        loc.setY(Double.parseDouble(str2loc[1]));
        loc.setZ(Double.parseDouble(str2loc[2]));
        if (str2loc.length == 5) {
            loc.setPitch(Float.parseFloat(str2loc[3]));
            loc.setYaw(Float.parseFloat(str2loc[4]));
        }
        return loc;
    }

    private static Location getRandomCords() {

        return new Location(Bukkit.getWorlds().get(0), RandomUtil.getRandInt(-1500 ,1500), 0, RandomUtil.getRandInt(-1500 ,1500));
    }

    private static boolean isValidLocation(Location loc) {
        Material mat = loc.getBlock().getType();
        if (GuildManager.getGuildByLocation(loc) == null) {
            if ((mat == Material.GRASS) || (mat == Material.SAND) || (mat == Material.DIRT) || (mat == Material.GRAVEL)) {
                return true;
            }
        }
        return false;
    }

    public static Location getValidRandomLocation() {
        for (; ; ) {
            Location loc = getRandomCords();
            System.out.println(loc);
            loc.setY(loc.getWorld().getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ()));
            if (isValidLocation(new Location(loc.getWorld(), loc.getX(), loc.getY() - 1, loc.getZ()))) {
                return loc;
            }
        }
    }
    public static Collection<Chunk> getChunksAroundPlayer(Player player) {
        int[] offset = {-1,0,1};

        World world = player.getWorld();
        int baseX = player.getLocation().getChunk().getX();
        int baseZ = player.getLocation().getChunk().getZ();

        Collection<Chunk> chunksAroundPlayer = new HashSet<>();
        for(int x : offset) {
            for(int z : offset) {
                Chunk chunk = world.getChunkAt(baseX + x, baseZ + z);
                chunksAroundPlayer.add(chunk);
            }
        } return chunksAroundPlayer;
    }

    public static int getDistanceFromBorder(Location loc) {
        int x = loc.getBlockX();
        int z = loc.getBlockZ();
        int distWest = Math.abs(Settings.WESTBORDER - x);
        int distEast = Math.abs(Settings.EASTBORDER - x);
        int distNorth = Math.abs(Settings.NORTHBORDER - z);
        int distSouth = Math.abs(Settings.SOUTHBORDER - z);
        int distX = (distWest < distEast) ? distWest : distEast;
        int distZ = (distNorth < distSouth) ? distNorth : distSouth;
        if (distX > distZ) {
            return distZ;
        }
        return distX;
    }


    public static String locToString(Location loc) {
        return loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getPitch() + ":" + loc.getYaw();
    }

    public static int getHighestBlock(int x, int z, Material mat) {
        for (int i = 50; i < 100; i++) {
            Location loc = new Location(Bukkit.getWorlds().get(0), x, i, z);
            if (loc.getBlock().getType() == mat) {
                return i;
            }
        }
        return 0;
    }

    public static boolean isSameLocation(Location l1, Location l2) {
        if (l1.getWorld().equals(l2.getWorld())) {
            if ((l1.getBlockX() == l2.getBlockX()) && (l1.getBlockY() == l2.getBlockY()) && (l1.getBlockZ() == l2.getBlockZ())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInLoc(Location loc, Location l2, int distance) {
        int distancex = Math.abs(loc.getBlockX() - l2.getBlockX());
        int distancez = Math.abs(loc.getBlockZ() - l2.getBlockZ());
        return (distancex <= distance) && (distancez <= distance);
    }

}
