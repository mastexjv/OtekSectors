package pl.otekplay.sectors.commands.normal;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.CommandUtil;

public class IgnoreCommand extends Command {
    private final GroupType type;
    public IgnoreCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);        CommandUtil.registerVariablesCommand(name,aliases);
        this.type = type;
    }

    @Override//OTEK LUBI W DUPE
    public void execute(CommandSender commandSender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) commandSender;
        User user = UserManager.getUser(p.getUniqueId());
        if (commandSender instanceof ProxiedPlayer) {
            if (!user.can(type)) {
                p.sendMessage(Settings.NO_PERM);
                return;
            }
        }
        if (args.length != 1) {
            p.sendMessage(Settings.BAD_USAGE+"/ignoruj [NICK]");
            return;
        }
        String name = args[0];
        if(!UserManager.isUser(name)){
            p.sendMessage(Settings.USER_NO_EXIST.replace("%nick%", name));
            return;
        }
        User ignore = UserManager.getUserByName(name);

        if(user.getIgnoredUsers().contains(ignore)){
            user.getIgnoredUsers().remove(ignore);
            p.sendMessage(Settings.IGNORE_DELETE.replace("%nick%", ignore.getName()));
            return;
        }
        user.getIgnoredUsers().add(ignore);
        p.sendMessage(Settings.IGNORE_ADD.replace("%nick%", ignore.getName()));
    }
}
