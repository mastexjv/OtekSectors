package pl.otekplay.sectors.commands.admin;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.CommandUtil;

/**
 * Created by Oskar on 13.02.2016.
 */
public class GroupCommand extends Command {
    private final GroupType type;

    public GroupCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);
        this.type = type;
        CommandUtil.registerVariablesCommand(name,aliases);

    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) commandSender;
            User user = UserManager.getUser(pp.getUniqueId());
            if (!user.can(type)) {
                pp.sendMessage(Settings.NO_PERM);
                return;
            }
            if (args.length != 2) {
                return;
            }
            String name = args[0];
            String group = args[1];
            if(!UserManager.isUser(name)){
                pp.sendMessage(Settings.USER_NO_EXIST.replace("%nick%", name));
                return;
            }
            User groupUser  = UserManager.getUserByName(name);
            groupUser.setGroup(GroupType.valueOf(group));

        }else{
            if (args.length != 2) {
                return;
            }
            String name = args[0];
            String group = args[1];
            if(!UserManager.isUser(name)){
                commandSender.sendMessage(Settings.USER_NO_EXIST.replace("%nick%", name));
                return;
            }
            User groupUser  = UserManager.getUserByName(name);
            groupUser.setGroup(GroupType.valueOf(group));
        }
    }
}
