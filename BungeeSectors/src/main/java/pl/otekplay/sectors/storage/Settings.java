package pl.otekplay.sectors.storage;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.utils.ChatUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Settings {
    //MAIN
    public final static String PREFIX = ChatUtil.fixColors("&7[&c&lx&f&lHC&7] ");
    public final static String BAD_USAGE = PREFIX +ChatUtil.fixColors("&7Poprawne uzycie komendy: &c");
    public final static String NO_PERM = PREFIX +  ChatUtil.fixColors("&cNie posiadasz uprawnien by uzyc tej komendy!");
    //SECTORS
    public final static String SECTOR_VALID_USE = ChatUtil.fixColors("&cPoprawne uzycie komendy /sectors [SERVERS/CORDS]");
    public final static String SECTOR_HEADER_MESSAGE = ChatUtil.fixColors("&c---------------- &7[Sectors] &c----------------");
    public final static String SECTOR_INFO_MESSAGE = ChatUtil.fixColors("&7Sector[&cNazwa: &e%name%&c, TPS: &e%tps%&c, Online: &e%online%&7]");
    public final static String SECTOR_CORDS_MESSAGE = ChatUtil.fixColors("&7Sector[&cNazwa: &e%name%&c, Cords: &e%cords%&7]");
    //FINAL
    public final static String SECTORS_STILL_LOAD = ChatUtil.fixColors("&cSerwer wciaz sie laduje, poczekaj chwilke.");
    public final static String NICK_IS_RESERVED = ChatUtil.fixColors("&cTen nick jest juz zajety na serwerze, sprobuj uzyc nicku %user%");
    public final static String BIND_MESSAGE = PREFIX + ChatUtil.fixColors("&cWlasciciel przypisal te konto do jednego IP.");
    public final static String FAILED_TO_LOGIN = PREFIX + ChatUtil.fixColors("&cWystapil blad z twoim logowaniem, sprobuj zachwile.");
    public final static String WRONG_NICKNAME = PREFIX + ChatUtil.fixColors("&cTwoj nick zawiera niepoprawne znaki.");
    public final static String SERVER_MAX_ONLINE = PREFIX + ChatUtil.fixColors("&cSerwer jest pelny, zakup vipa aby wejsc na pelny serwer.");
    public final static String TOO_LOW_RANK = PREFIX + ChatUtil.fixColors("&cTa komenda nie jest przeznaczona dla Ciebie, trzeba miec conajmniej range %rank%");
    public final static String USER_NO_EXIST = PREFIX + ChatUtil.fixColors("&cGracz o nicku %nick% nie istnieje w bazie.");
    public final static String USER_IS_OFFLINE = PREFIX + ChatUtil.fixColors("&cGracz o nicku %nick% nie jest na serwerze");
    //MESSAGE
    public final static String MESSAGE_VALID_USE = PREFIX + ChatUtil.fixColors("&cPoprawne uzycie komendy message /msg [NICK] WIADOMOSC");
    public final static String MESSAGE_PLAYER_GET = PREFIX + ChatUtil.fixColors("&7Wiadomosc od &e%user%&7 : %message%");
    public final static String MESSAGE_PLAYER_SEND = PREFIX + ChatUtil.fixColors("&7Wyslano do &e%user%&7 : %message%");

    //WHITELIST
    public static boolean WHITELISTMODE = true;
    public final static String WHITELIST_MESSAGE = PREFIX + ChatUtil.fixColors("&cTrwaja prace techniczne na serwerze..");
    public final static String WHITELIST_USER_STATUS = PREFIX + ChatUtil.fixColors("&cGracz %nick% ma status whitelisty: %status%");
    public final static String WHITELIST_USER_REM = PREFIX + ChatUtil.fixColors("&cGracz %nick% zostal usuniety z whitelisty");
    public final static String WHITELIST_USER_ADD = PREFIX + ChatUtil.fixColors("&cGracz %nick% zostal dodany na whiteliste");
    public final static String WHITELIST_USER_ALREADY_EXIST = PREFIX + ChatUtil.fixColors("&cGracz %nick% jest juz na whitelist.");
    public final static String WHITELIST_USER_NO_EXIST = ChatUtil.fixColors("&cGracz %nick% nie istnieje na whitelist");
    public final static String WHITELIST_VALID_USE = ChatUtil.fixColors("&cPoprawne uzycie komendy /wl ON/OFF/DODAJ/USUN [NICK]");
    public final static String WHITELIST_GLOBAL_STATUS = ChatUtil.fixColors("&cWhitelista jest obecnie %status%");

    //GUILD
    public final static String GUILD_NO_EXIST = PREFIX + ChatUtil.fixColors("&7Gildia %tag% nie zostala odnaleziona!");
    public final static String GUILD_NO_GUILD = PREFIX + ChatUtil.fixColors("&7Musisz posiadac gildie!");
    public final static String GUILD_NO_LEADER = PREFIX + ChatUtil.fixColors("&7Musisz byc liderem gildii by to wykonac!");
    public final static String GUILD_NO_ADD_SAME = PREFIX + ChatUtil.fixColors("&7Nie mozesz dodac sam siebie!");
    public final static String GUILD_IS_IN_GUILD_OTHER = PREFIX + ChatUtil.fixColors("&7Gracz &c%nick% &7posiada juz gildie!");
    public final static String GUILD_IS_IN_GUILD_SAME = PREFIX + ChatUtil.fixColors("&7Gracz &c%nick% &7jest juz w Twojej gildii!");
    public final static String GUILD_INVITE_IS_PENDING = PREFIX + ChatUtil.fixColors("&7Zaprosiles juz gracza %nick%");
    public final static String GUILD_INVITE_SEND = PREFIX + ChatUtil.fixColors("&7Wyslales zaproszenie do gracza %nick%");
    public final static String GUILD_INVITE_DELIVER = PREFIX + ChatUtil.fixColors("&7Gildia %tag% zaprosila Cie do ich szeregow. Wpisz /dolacz %tag% by dolaczyc!");
    public final static String GUILD_ALLY_IS_IN_PON = PREFIX + ChatUtil.fixColors("&7Posiadasz juz sojusz z gildia &c%tag%");
    public final static String GUILD_ALLY_SEND = PREFIX + ChatUtil.fixColors("&7Zaprosiles gildie %tag% do sojuszu!");
    public final static String GUILD_ALLY_DELIVER = PREFIX + ChatUtil.fixColors("&7Gildia %tag% zaprosila was do sojuszu!");
    public final static String GUILD_ALLY_BROADCAST = PREFIX + ChatUtil.fixColors("&7Gildia &c%tag% &7zawarla sojusz z gildia &c%tag2%&7!");
    public final static String GUILD_CREATE_IS_IN_GUILD = PREFIX + ChatUtil.fixColors("&7Posiadasz juz gildie!");
    public final static String GUILD_CREATE_BROADCAST = PREFIX + ChatUtil.fixColors("&7Gildia &c%tag% - %name% &7zostala zalozona przez &c%nick%&7!");
    public final static String GUILD_CREATE_IS_EXIST_TAG = PREFIX + ChatUtil.fixColors("&7Gildia o tagu &c%tag% &7juz istnieje!");
    public final static String GUILD_CREATE_ERROR_LENGHT = PREFIX + ChatUtil.fixColors("&7");
    public final static String GUILD_CREATE_ERROR_SPAWN = PREFIX + ChatUtil.fixColors("&cMusisz znajdowac sie 300 kratek od spawnu, by zalozyc gildie!");
    public final static String GUILD_CREATE_ERROR_GUILD = PREFIX + ChatUtil.fixColors("&cMusisz znajdowac sie 150 kratek od innej gildii, by zalozyc gildie!");
    public final static String GUILD_CREATE_ERROR_SECTOR = PREFIX + ChatUtil.fixColors("&cMusisz znajdowac sie 120 kratek od sektora, by zalozyc gildie!");
    public final static String GUILD_CREATE_ERROR_NOITEMS = PREFIX + ChatUtil.fixColors("&cBrak wystarczajacych itemow na zalozenie gildii! Sprawdz liste /gitemy");
    public final static String GUILD_CREATE_IS_EXIST_NAME = PREFIX + ChatUtil.fixColors("&7Gildia o nazwie &c%name% &7juz istnieje!");
    public final static String GUILD_DELETE_BROADCAST = PREFIX + ChatUtil.fixColors("&7Gildia &c%tag% &7zostala rozwiazana!");
    public final static String GUILD_ENEMY_NO_ALLY = PREFIX + ChatUtil.fixColors("&7Nie posiadasz sojuszu z gildia &c%tag%&7!");
    public final static String GUILD_ENEMY_ENEMY_BROADCAST = PREFIX + ChatUtil.fixColors("&7Gildia &c%tag% &7zerwala sojusz z gildia &c%tag2%&7!");
    public final static String GUILD_INFO = ChatUtil.fixColors("&7=== &c%tag% &7 --> &c%name% &7===\n" +
            "&7Lider: &c%leader%\n" +
            "&7Czlonkowie: %members%\n" +
            "&7Sojusznicy: &ally%");
    public final static String GUILD_JOIN_BROADCAST = PREFIX + ChatUtil.fixColors("&7Gracz &c%nick% &7dolaczyl do gildii &c%tag%");
    public final static String GUILD_JOIN_IS_IN_GUILD_LEADER = PREFIX + ChatUtil.fixColors("&7Jestes liderem tej gildii! Musisz ja zamknac by dolaczyc do innej!");
    public final static String GUILD_JOIN_IS_IN_GUILD_OTHER = PREFIX + ChatUtil.fixColors("&7Jestes czlonkiem innej gildii!");
    public final static String GUILD_JOIN_IS_NOT_INVITE = PREFIX + ChatUtil.fixColors("&7Gildia &c%tag% &7nie zaprosila Cie!");
    public final static String GUILD_KICK_NO_GUILD = PREFIX + ChatUtil.fixColors("&7Gracz &c%nick% &7nie posiada gildii!");
    public final static String GUILD_KICK_BROADCAST = PREFIX + ChatUtil.fixColors("&7Gracz &c%nick% &7zostal wyrzucony z gildii &c%tag%");
    public final static String GUILD_LEAVE_LEADER = PREFIX + ChatUtil.fixColors("&7Nie mozesz tego zrobic bedac liderem!");
    public final static String GUILD_LEAVE_BROADCAST = PREFIX + ChatUtil.fixColors("&7Gracz &c%nick% &7opuscil gildie &c%tag%");
    public final static String GUILD_HOME_TELEPORT = PREFIX + ChatUtil.fixColors("&7Trwa teleportacja do gildii.. Nie ruaszaj sie!");
    public final static String GUILD_SIZE_MAX = PREFIX + ChatUtil.fixColors("&cTeren gildii osiagnal maksymalna wartosc!");
    public final static String GUILD_NO_PAY_BROADCAST = ChatUtil.fixColors("*7Gildia &c%tag% &7zostala usunieta z powodu braku oplacenia!");

    //Login
    public final static String LOGIN_NICK_IS_EXIST = ChatUtil.fixColors("&cTwoj nick jest juz zajety! \n" +
            "&7Zmien nick by zagrac na serwerze!");
    public final static String LOGIN_SERVER_ERROR = ChatUtil.fixColors("&cWystapily problemy z serwer! \n" +
            "&7Sprobuj ponownie za chwile!");
    public final static String LOGIN_NICK_BAD = ChatUtil.fixColors("&cTwoj nick jest niepoprawny! \n" +
            "&7Zmien nick by zagrac!");

    //Ban
    public final static String BAN_IS_EXIST = ChatUtil.fixColors("&7Gracz &c%nick% &7ma juz bana! \n" +
            "&7Czas: &c%time%\n" +
            "&7Nadal: &c%admin%\n" +
            "&7Powod: &c%reason% \n");
    public final static String BAN_IS_NO_EXIST = ChatUtil.fixColors("&7Gracz &c%nick% &7nie ma bana!");
    public final static String BAN_BROADCAST = ChatUtil.fixColors("&7Gracz &c%nick% &7zostal zbanowany &7za &c%reason% &7do %time%");
    public final static String BAN_PLAYER_MSG = ChatUtil.fixColors("&cZostales zbanowany! \n" +
            "&7Admin: &c%admin% \n" +
            "&7Powod: &c%reason% \n" +
            "&7Do: &c%time%");
    public final static String UNBAN_PLAYER = ChatUtil.fixColors("&7Odbanowales gracza &c%nick%");

    //Kick
    public final static String KICK_PLAYER_MSG = ChatUtil.fixColors("&cZostales wyrzucony! \n" +
            "&7Admin: &c%admin% \n" +
            "&7Powod: &c%reason%");
    public final static String KICK_BROADCAST = ChatUtil.fixColors("&7Gracz &c%nick% &7zostal wyrzucony przez &c%admin%");

    //Teleport
    public final static String TELEPORT_MSG = ChatUtil.fixColors("&7Teleportacja do gracza &c%nick%&7...");
    public final static String TELEPORT_MSG_PL_TO_PL = ChatUtil.fixColors("&7Teleportacja gracza &c%nick% &7do &c%target%&7...");
    public final static String TELEPORT_MSG_TO_LOC = ChatUtil.fixColors("&7Teleportacja na &c%sector% &7X: &c%x% &7Z: &c%z%");
    public final static String HOME_NO_EXIST = ChatUtil.fixColors("&7Nie odnaleziono koordynatow home! \n" +
            "&7Ustaw go za pomoca: &c/sethome");
    public final static String TELEPORT_IS_PENDING = ChatUtil.fixColors("&7Trwa teleportacja! &cNie ruszaj sie!");

    //Info

    public final static String[] INFO_HELPS = {"&9< --- > &7Pomoc &9< --- >", "/pomoc gildie- Rozpiska komend gildii. ", "/pomoc komendy - Rozpiska komend gracza."};

    public final static String[] INFO_GUILDS = {"&9< --- > &7Gildie &9< --- >", "&c/zaloz [TAG] [NAZWA] &9-> &7Zalozenie gildii","&c/zamknij &9-> &7Usuwa Twoja gildie", "&c/dodaj [NICK] &9-> &7Zaprasza gracza do gildii", "&c/dolacz [TAG] &9-> &7Dolaczasz do gildii" , "&c/wyrzuc [NICK] &9-> &7Wyrzuca gracza z gildii ",
            "&c/odejdz &9-> &7Opuszczasz aktulana gildie", "&c/lider [NICK] &9-> &7Oddajesz lidera gildii", "&c/gpvp &9-> &7Zmienia tryb pvp w gildii", "&c/powieksz &9-> &7Powieksza rozmiar gildii", "&c/odnow &9-> &7Przedluza waznosc gildii", "&c/gustawdom &9->&7 Ustawia dom gildii",
            "&c/gdom &9-> &7Teleportuje do domu gildii", "&c/sojusz [TAG] &9-> &7Zaproszenie innej gildii do sojuszu", "&c/wrogosc [TAG] &9->&7 Zrywasz sojusz z gildia ", "&c/gtop &9-> &7Wyswietla najlepsze gildie", "&c/ginfo [TAG] &9-> &7Wyswietla informacje o gildii", "&9<============================================>"};

    public final static String[] INFO_COMMAND = {"&9< --- > &7Komendy &9< --- > ", "&c/spawn &9->&7 Teleportuje Cie na spawn serwera", "&c/home &9->&7 Teleportuje Cie do ustawionego domu", "&c/sethome &9->&7 Ustawia koordynaty domu", "&c/ignore [NICK] &9->&7 Ignoruje gracza (MSG,TPA)",
            "&c/msg [NICK] [WIADOMOSC] &9->&7 Wysyla wiadomosc do gracza", "&c/r [WIADOMOSC] &9->&7 Odpisuje ostatniej osobie", "&c/ranking [NICK] &9->&7 Wyswietla ranking gracza", "&c/tpa &9->&7 Wysyla prosbe o teleportacje", "&c/wyjebane &9->&7 Wylacza prywatne wiadomosci"};

    public final static String[] INFO_RANKINGS = {"&7Ranking gracza &c%nick%", "&7Punkty: &c%points%", "&7Zabojstwa: &c%kills%", "&7Zgony: &c%deaths%", "&7Miejsce: &c%position%"};


    //Other
    public final static String IGNORE_DELETE = ChatUtil.fixColors("&7Od teraz nie ignorujesz gracza: &c%nick%");
    public final static String IGNORE_ADD = ChatUtil.fixColors("&7Od teraz ignorujesz gracza: &c%nick%");
    public final static String CHAT_ON = ChatUtil.fixColors("&7Chat zostal &cwlaczony &7przez &c%nick%&7!");
    public final static String CHAT_OFF = ChatUtil.fixColors("&7Chat zostal &cwylaczony &7przez &c%nick%&7!");
    public final static String CHAT_CLEAR = ChatUtil.fixColors("&7Chat zostal wyczyszczony przez &c%nick%&7!");
    public final static String CHAT_VIP = ChatUtil.fixColors("&7Chat zostal &cwlaczony &7przez &c%nick%&7 w trybie &6VIP");
    public final static String CHAT_LEVEL_BC = ChatUtil.fixColors("&7Na czacie mozna pisac teraz od &c%level% &7poziomu!");

    public final static int GUILD_DEFAULT_SIZE = 25;
    public final static int GUILD_TAG_LENGHT = 4;
    public final static int GUILD_NAME_MIN_SIZE = 8;
    public final static int GUILD_NAME_MAX_SIZE = 20;
    public static final long WEEK = 1000 * 60 * 60 * 24 * 7;
    public static final long DAY = 1000 * 60 * 60 * 24;
    public static boolean CHAT_MODE = true;
    public static long CHAT_DELAY_TIME = 5000;
    public static String MOTD = ChatUtil.fixColors("&8>> &f&lx&c&lHC.PL &7Najlepszy &c&lHC&7 na polskiej scenie!\n&8>> &7Polub nas na fanpage, zapros znajomych, &6&lGRAJ!");
    public static int MULTIPLER_ONLINE = 1;


    public static final String COLUMN_USER_UUID_NAME = "UUID";
    public static final String COLUMN_USER_NAME_NAME = "Name";
    public static final String COLUMN_USER_SECTOR_NAME = "Sector";
    public static final String COLUMN_USER_GROUP_NAME = "Group";
    public static final String COLUMN_USER_DOMAIN_NAME = "Domain";
    public static final String COLUMN_USER_IP_NAME = "IP";
    public static final String COLUMN_USER_FLY_NAME = "Fly";
    public static final String COLUMN_USER_GOD_NAME = "God";
    public static final String COLUMN_USER_VANISH_NAME = "Vanish";
    public static final String COLUMN_USER_HOME_NAME = "Home";
    public static final String COLUMN_USER_POINTS_NAME = "Points";
    public static final String COLUMN_USER_KILLS_NAME = "Kills";
    public static final String COLUMN_USER_DEATHS_NAME = "Deaths";
    public static final String COLUMN_USER_FIGHT_WINS_NAME = "FightWin";
    public static final String COLUMN_USER_FIGHT_LOSE_NAME = "FightLose";
    public static final String COLUMN_USER_EXP_NAME = "Exp";
    public static final String COLUMN_USER_LEVEL_NAME = "Level";


    public static final String COLUMN_BAN_UUID_NAME = "UUID";
    public static final String COLUMN_BAN_REASON_NAME = "Reason";
    public static final String COLUMN_BAN_TIME_NAME = "Time";
    public static final String COLUMN_BAN_ADMIN_NAME = "Admin";


    public static final String COLUMN_GUILD_TAG_NAME = "Tag";
    public static final String COLUMN_GUILD_NAME_NAME = "Name";
    public static final String COLUMN_GUILD_LOCATION_NAME = "Location";
    public static final String COLUMN_GUILD_LEADER_NAME = "Leader";
    public static final String COLUMN_GUILD_GUILDHOME_NAME = "Home";
    public static final String COLUMN_GUILD_PVPGUILD_NAME = "PvPInGuild";
    public static final String COLUMN_GUILD_CREATEDATE_NAME = "CreateDate";
    public static final String COLUMN_GUILD_LASTRENEW_NAME = "LastRenew";
    public static final String COLUMN_GUILD_LASTDESTROY_NAME = "LastDestroy";
    public static final String COLUMN_GUILD_LIVES_NAME = "Lives";
    public static final String COLUMN_GUILD_MEMBERS_NAME = "Members";
    public static final String COLUMN_GUILD_ALLY_NAME = "Ally";

    private static Configuration yaml;
    public static List<String> whitelist;

    public static void loadSettings() {
        if(!Main.getInstance().getDataFolder().exists()){
            Main.getInstance().getDataFolder().mkdir();
        }
        File file = new File(Main.getInstance().getDataFolder(), "Config.yml");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            ConfigurationProvider provider = YamlConfiguration.getProvider(YamlConfiguration.class);
            yaml = provider.load(file);
            whitelist = new ArrayList<>();
            whitelist.addAll(yaml.getStringList("Whitelist"));
            provider.save(yaml,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
