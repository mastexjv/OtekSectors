package pl.otekplay.sectors.storage;

import org.bukkit.configuration.file.FileConfiguration;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.utils.ChatUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Settings {
    //MAINSERVER
    public static InetAddress ADDRESS;
    public static int PORT;
    public final static String PREFIX = ChatUtil.fixColors("&7[&c&lx&f&lHC&7] ");

    //SECTOR

    public static int MAPX, MAPZ;

    public static String[] directions = new String[]{"NORTH", "SOUTH", "EAST", "WEST"};

    public static int NORTHBORDER;
    public static int SOUTHBORDER;
    public static int EASTBORDER;
    public static int WESTBORDER;

    public static void loadSettings() {
        FileConfiguration cfg = Main.getInstance().getConfig();
        cfg.addDefault("Main.Server.IP", "localhost");
        cfg.addDefault("Main.Server.Port", 29727);
        cfg.addDefault("Sector.Map.X", 0);
        cfg.addDefault("Sector.Map.Z", 0);
        for (String s : directions) {
            cfg.addDefault("Sector.Border." + s, 1000);
        }
        cfg.options().copyDefaults(true);
        Main.getInstance().saveConfig();
        try {
            ADDRESS = InetAddress.getByName(cfg.getString("Main.Server.IP"));
            PORT = cfg.getInt("Main.Server.Port");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        MAPX = cfg.getInt("Sector.Map.X");
        MAPZ = cfg.getInt("Sector.Map.Z");
        NORTHBORDER = cfg.getInt("Sector.Border.NORTH");
        SOUTHBORDER = cfg.getInt("Sector.Border.SOUTH");
        EASTBORDER = cfg.getInt("Sector.Border.EAST");
        WESTBORDER = cfg.getInt("Sector.Border.WEST");
    }

    //SECTOR MSG
    public static final String SECTOR_ERROR_BUILD = ChatUtil.fixColors("&cNie mozesz budowac na granicy spawna!");
    public static final String SECTOR_ERROR_BREAK = ChatUtil.fixColors("&cNie mozesz niszczyc na granicy spawna!");

    //Guild MSG
    public static final String GUILD_ERROR_TERRAIN = ChatUtil.fixColors("&7Ten teren nalezy do gildii &c%tag%");
    public static final String GUILD_PVP_OFF = ChatUtil.fixColors("&cPvP w Twojej gildii jest wylaczone!");
    public static final String GUILD_ATTACK_OUR = ChatUtil.fixColors("&cNie mozesz podbic wlasnej gildii!");
    public static final String GUILD_ATTACK_NO_GUILD = ChatUtil.fixColors("&cMusisz posiadac gildie by to zrobic!");
    public static final String GUILD_ATTACK_ALLY = ChatUtil.fixColors("&cNie mozesz podbic sojuszniczej gildii!");

    //Generator
    public static final String GENERATOR_BOYFARMER_PLACE = ChatUtil.fixColors("&7Postawiles &cboyfarmera");
    public static final String GENERATOR_SANDFARMER_PLACE = ChatUtil.fixColors("&7Postawiles &csandfarmera");
    public static final String GENERATOR_PLACE = ChatUtil.fixColors("&7Postawiles &cgenerator &7by zaczal dzialac postaw na nim blok &cobsa/kamienia");
    public static final String GENERATOR_DIGGER_PLACE = ChatUtil.fixColors("&7Postawiles &ckopacza fosy");

    public static final String USER_DROP_LVL_UP = ChatUtil.fixColors("&7Awansowales na &3{LEVEL} &7poziom!");
}
