package pl.otekplay.sectors.commands.admin;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.CommandUtil;

public class KickCommand extends Command {
    private final GroupType type;
    public KickCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);        CommandUtil.registerVariablesCommand(name,aliases);
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
        if (args.length < 1) {
            commandSender.sendMessage(Settings.BAD_USAGE+"/kick [NICK] [POWOD]");
            return;
        }
        String name = args[0];
        String reason = args[1];
        for (int i = 2; i < args.length; i++) {
            reason = reason + " " + args[i];
        }
        if (!UserManager.isUser(name)) {
            commandSender.sendMessage(Settings.USER_NO_EXIST.replace("%nick%", name));
            return;
        }
        User user = UserManager.getUserByName(name);
        if (!user.isOnline()) {
            commandSender.sendMessage(Settings.USER_IS_OFFLINE.replace("%nick%", name));
            return;
        }
        user.getProxiedPlayer().disconnect(Settings.KICK_PLAYER_MSG.replace("%admin%", commandSender.getName()).replace("%reason%", reason));
        BungeeCord.getInstance().broadcast(Settings.KICK_BROADCAST.replace("%nick%", user.getName()).replace("%admin%", commandSender.getName()));
    }
}
