package pl.otekplay.sectors.commands.admin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.CommandUtil;

public class WhitelistCommand extends Command {
    private final GroupType type;
    public WhitelistCommand(String whitelist, GroupType type, String... args) {
        super(whitelist,"",args);
        CommandUtil.registerVariablesCommand(whitelist,args);
        this.type = type;
    }
//OTEK LUBI W DUPE


    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) commandSender;
            User senderUser = UserManager.getUser(pp.getUniqueId());
            if (!senderUser.can(type)) {
                pp.sendMessage(Settings.NO_PERM);
                return;
            }
        }
        if (args.length == 1) {
            String arg = args[0];
            if (arg.equalsIgnoreCase("on") || arg.equalsIgnoreCase("wlacz")) {
                Settings.WHITELISTMODE = true;
                commandSender.sendMessage(Settings.WHITELIST_GLOBAL_STATUS.replace("%status%", (Settings.WHITELISTMODE) ? ChatColor.DARK_GREEN + "wlaczona" : ChatColor.DARK_RED + "wylaczona"));
            } else if (arg.equalsIgnoreCase("off") || arg.equalsIgnoreCase("wylacz")) {
                Settings.WHITELISTMODE = false;
                commandSender.sendMessage(Settings.WHITELIST_GLOBAL_STATUS.replace("%status%", (Settings.WHITELISTMODE) ? ChatColor.DARK_GREEN + "wlaczona" : ChatColor.DARK_RED + "wylaczona"));
            } else if (arg.equalsIgnoreCase("status") || arg.equalsIgnoreCase("tryb")) {
                commandSender.sendMessage(Settings.WHITELIST_GLOBAL_STATUS.replace("%status%", (Settings.WHITELISTMODE) ? ChatColor.DARK_GREEN + "wlaczona" : ChatColor.DARK_RED + "wylaczona"));
            } else {
                commandSender.sendMessage(Settings.WHITELIST_VALID_USE);
            }
        } else if (args.length == 2) {
            String arg = args[0];
            String nick = args[1];
            boolean whitelist = Settings.whitelist.contains(nick);
            if (arg.equalsIgnoreCase("dodaj") || arg.equalsIgnoreCase("add")) {
                if (whitelist) {
                    commandSender.sendMessage(Settings.WHITELIST_USER_ALREADY_EXIST.replace("%nick%",nick));
                } else {
                    Settings.whitelist.add(nick);
                    commandSender.sendMessage(Settings.WHITELIST_USER_ADD.replace("%nick%", nick));
                }
            } else if (arg.equalsIgnoreCase("usun") || arg.equalsIgnoreCase("rem")) {
                if (whitelist) {
                    Settings.whitelist.remove(nick);
                    commandSender.sendMessage(Settings.WHITELIST_USER_REM.replace("%nick%", nick));
                } else {
                    commandSender.sendMessage(Settings.WHITELIST_USER_NO_EXIST.replace("%nick%", nick));
                }
            } else if (arg.equalsIgnoreCase("status") || arg.equalsIgnoreCase("tryb")) {
                commandSender.sendMessage(Settings.WHITELIST_USER_STATUS.replace("%nick%", nick).replace("%status", whitelist ? ChatColor.DARK_GREEN + "wlaczony" : ChatColor.DARK_RED + "wylaczony"));
            } else if (arg.equalsIgnoreCase("list")) {
                commandSender.sendMessage("Na liscie jest: ");
                for(String s:Settings.whitelist){
                    commandSender.sendMessage(s);
                }
            } else {
                commandSender.sendMessage(Settings.WHITELIST_VALID_USE);
            }
        } else {
            commandSender.sendMessage(Settings.WHITELIST_VALID_USE);
        }
    }
}
