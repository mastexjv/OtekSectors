package pl.otekplay.sectors.utils;

import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.storage.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandUtil {

    private static final List<String> commands = new ArrayList<>(Arrays.asList("glist","send","server"));

    public static void registerVariablesCommand(String defaultName,String[] aliases){
        commands.add(defaultName);
        for(String s:aliases){
            commands.add(s);
        }
    }
    public static boolean canUseCommand(User user, GroupType type){
        if(!user.can(type)){
            user.sendMessage(Settings.TOO_LOW_RANK.replace("%rank%",type.toString()));
            return false;
        }
        return true;
    }
    public static boolean isCommandExsist(String cmd){
        return commands.contains(cmd);
    }
}
