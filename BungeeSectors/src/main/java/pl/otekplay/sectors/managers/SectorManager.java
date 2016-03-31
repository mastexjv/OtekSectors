package pl.otekplay.sectors.managers;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.basic.Map;
import pl.otekplay.sectors.data.Sector;
import pl.otekplay.sectors.packets.Packet;
import pl.otekplay.sectors.packets.impl.sector.SectorSendCommandPacket;
import pl.otekplay.sectors.utils.RandomUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SectorManager {


    private static final List<Sector> sectors = Collections.synchronizedList(new ArrayList<Sector>());


    public static boolean isSector(String name) {
        for (Sector sector : sectors) {
            if (sector.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static Sector getSector(String name) {
        for (Sector sector : sectors) {
            if (sector.getName().equalsIgnoreCase(name)) {
                return sector;
            }
        }
        return null;
    }

    public static Sector getSector(Map map) {
        for (Sector sec : sectors) {
            if (sec.getMap().equals(map)) {
                return sec;
            }
        }
        return null;
    }

    public static Sector getSectorByLocation(String loc) {
        String[] locs = loc.split(":");
        int x = Integer.parseInt(locs[0]);
        int z = Integer.parseInt(locs[2]);
        return getSectorByLocation(x, z);
    }


    public static Sector getSectorByLocation(int x, int z) {
        for (Sector sec : sectors) {
            if (x <= sec.getEASTBORDER() && (x >= sec.getWESTBORDER())) {
                if (z <= sec.getSOUTHBORDER() && (z >= sec.getNORTHBORDER())) {
                    return sec;
                }
            }
        }
        return null;
    }

    public static Sector getSector(ServerInfo info) {
        for (Sector sector : sectors) {
            if (sector.getServer().equals(info)) {
                return sector;
            }
        }
        return null;
    }

    public static List<Sector> getSectors() {
        return sectors;
    }

    public static void sendPacket(Packet packet) {
        for (Sector sec : sectors) {
            sendPacket(sec, packet);
        }
    }

    public static void sendPacket(Sector sector, Packet packet) {
        sector.getClient().sendPacket(packet);
    }

    public static void sendPacketExceptSector(Packet packet,Sector sector){
        for (Sector sec : sectors) {
            if(sec.equals(sector)){
                continue;
            }
            sendPacket(sec, packet);
        }
    }
    public static void registerSector(Sector sector) {
        sectors.add(sector);
        sector.init();
        sector.getClient().sendPacket(new SectorSendCommandPacket("time set 0"));
        if(sectors.size() > 8){
            BungeeCord.getInstance().getScheduler().runAsync(Main.getInstance(), () -> {
                UserManager.loadUsers();
                GuildManager.loadGuilds();
                BanManager.loadBans();
            });
        }
    }
    public static Sector getRandomSector(){
        Sector sector = sectors.get(RandomUtil.getRandInt(0,8));
        return sector;
    }
}
