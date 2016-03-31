package pl.otekplay.sectors.commands.admin;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.ChatManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.CommandUtil;

public class ChatCommand extends Command {
    private final GroupType type;
    private String nick = "";
    public ChatCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);
        CommandUtil.registerVariablesCommand(name, aliases);
        this.type = type;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) commandSender;
            User senderUser = UserManager.getUser(pp.getUniqueId());
            if (!senderUser.can(type)) {
                pp.sendMessage(Settings.NO_PERM);
                return;
            }
        } else {
            nick = "CONSOLE";
        }
        if (args.length == 0 && args.length > 2) {
            commandSender.sendMessage(Settings.BAD_USAGE+"/chat [LOCK/CC/DELAY/LEVEL] [TIME/LEVEL]");
            return;
        }
        String arg = args[0];
        if(arg.length() == 1) {
            if (arg.equalsIgnoreCase("lock")) {
                ChatManager.lock(nick);
                return;
            }
            if (arg.equalsIgnoreCase("cc")) {
                ChatManager.clear(nick);
                return;
            }
        }

        if(arg.length() == 2) {
            if (arg.equalsIgnoreCase("delay")) {
                long time = Long.parseLong(args[1]);
                Settings.CHAT_DELAY_TIME = time;
                commandSender.sendMessage(ChatUtil.fixColors("&7Ustawiles chat delay na: &c" + (time / 1000) + " &7sekund."));
                return;
            }
            if (arg.equalsIgnoreCase("level")) {
                int level = Integer.parseInt(args[1]);
                ChatManager.changeLevel(level);
                return;
            }
        }
    }
}
