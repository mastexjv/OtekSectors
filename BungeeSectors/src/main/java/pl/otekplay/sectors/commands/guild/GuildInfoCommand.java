package pl.otekplay.sectors.commands.guild;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.Guild;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.GuildManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.CommandUtil;

public class GuildInfoCommand extends Command {
    private final GroupType type;
    public GuildInfoCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);        CommandUtil.registerVariablesCommand(name,aliases);
        this.type = type;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ProxiedPlayer pp = (ProxiedPlayer) commandSender;
        User user = UserManager.getUser(pp.getUniqueId());

        if (commandSender instanceof ProxiedPlayer) {
            if (!user.can(type)) {
                pp.sendMessage(Settings.NO_PERM);
                return;//OTEK LUBI W DUPE
            }
        }
        if (args.length != 1) {
            pp.sendMessage(Settings.BAD_USAGE + "/ginfo <tag>");
            return;
        }
        String tag = args[0];
        if (!GuildManager.isGuildTag(tag)) {
            pp.sendMessage(Settings.GUILD_NO_EXIST.replace("%tag%", tag));
            return;
        }
        Guild search = GuildManager.getGuildByTag(tag);
        String members = "[";
        for (User mem : search.getMembers()) {
            members = members + ((mem.isOnline()) ? ", " + ChatColor.GREEN + mem.getName() + ", " : ", " + ChatColor.RED + mem.getName());
        }
        members = members + "]";
        String allyString = "[";
        for (Guild ally : search.getAlly()) {
            allyString = allyString + " " + ally.getTag() + " ";
        }
        allyString = allyString + "]";
        pp.sendMessage(Settings.GUILD_INFO.replace("%members%", members).replace("%tag%", search.getTag()).replace("%name%", search.getName()).replace("%leader%", search.getLeader().getName()).replace("%ally%", allyString));

    }
}