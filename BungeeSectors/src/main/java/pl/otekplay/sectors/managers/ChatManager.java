package pl.otekplay.sectors.managers;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import pl.otekplay.sectors.storage.Settings;

/**
 * Created by Administrator on 3/9/2016.
 */
public class ChatManager {
    public static int chat_level = 1;
    public static int chat_drop_level = 5;

    private static void cl(){
        for (int i = 0; i < 100; i++) {
            BungeeCord.getInstance().broadcast(" ");
        }
    }

    public static void clear(String nick){
        cl();
        BungeeCord.getInstance().broadcast(Settings.CHAT_CLEAR.replace("%nick%", nick));
    }

    public static void lock(String nick){
        switch (chat_level) {
            case 0: {
                chat_level = 1;
                cl();
                BungeeCord.getInstance().broadcast(Settings.CHAT_ON.replace("%nick%", nick));
            }break;
            case 1: {
                chat_level = 2;
                cl();
                BungeeCord.getInstance().broadcast(Settings.CHAT_VIP.replace("%nick%", nick));
            }break;
            case 3: {
                chat_level = 0;
                cl();
               BungeeCord.getInstance().broadcast(Settings.CHAT_OFF.replace("%nick%", nick));
            }break;
        }
    }

    public static void changeLevel(int level) {
        chat_drop_level = level;
        BungeeCord.getInstance().broadcast(Settings.CHAT_LEVEL_BC.replace("%level%", level+""));
    }

    public static int getChat_drop_level() {
        return chat_drop_level;
    }

    public static int getChat_level() {
        return chat_level;
    }
}
