package pl.otekplay.sectors.commands.admin;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.Ban;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.BanManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.CommandUtil;

public class UnbanCommand extends Command {
    private final GroupType type;
    public UnbanCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);        CommandUtil.registerVariablesCommand(name,aliases);
        this.type = type;
    }//OTEK LUBI W DUPE

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
        if (args.length != 1) {
            commandSender.sendMessage(Settings.BAD_USAGE+"/unban [NICK]");
            return;
        }
        String name = args[0];
        User user = UserManager.getUserByName(name);
        if (!BanManager.isBanned(user)) {
            commandSender.sendMessage(Settings.BAN_IS_NO_EXIST.replace("%nick%", user.getName()));
            return;
        }
        Ban ban = BanManager.getBan(user);
        BanManager.removeBan(ban);
        commandSender.sendMessage(Settings.UNBAN_PLAYER.replace("%nick%", name));
    }
}
