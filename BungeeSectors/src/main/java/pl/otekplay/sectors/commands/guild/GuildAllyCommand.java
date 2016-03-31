package pl.otekplay.sectors.commands.guild;

import net.md_5.bungee.BungeeCord;
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

public class GuildAllyCommand extends Command {
    private final GroupType type;
    public GuildAllyCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);        CommandUtil.registerVariablesCommand(name,aliases);
        this.type = type;
    }//OTEK LUBI W DUPE

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ProxiedPlayer pp = (ProxiedPlayer) commandSender;
        User user = UserManager.getUser(pp.getUniqueId());
        if (commandSender instanceof ProxiedPlayer) {
            if (!user.can(type)) {
                pp.sendMessage(Settings.NO_PERM);
                return;
            }
        }
        if (args.length != 1) {
            pp.sendMessage(Settings.BAD_USAGE +"/ally [TAG]");
            return;
        }
        if (!user.hasGuild()) {
            pp.sendMessage(Settings.GUILD_NO_GUILD);
            return;
        }
        Guild guild = user.getGuild();
        if (!guild.getLeader().equals(user)) {
            pp.sendMessage(Settings.GUILD_NO_LEADER);
            return;
        }
        String tag = args[0];
        if (!GuildManager.isGuildTag(tag)) {
            pp.sendMessage(Settings.GUILD_NO_EXIST.replace("%tag%", tag));
            return;
        }
        Guild search = GuildManager.getGuildByTag(tag);
        if (guild.getAlly().contains(search)) {
            pp.sendMessage(Settings.GUILD_ALLY_IS_IN_PON.replace("%tag%", search.getTag()));
            return;
        }
        if (!guild.getAllyInvitves().contains(search)) {
            search.getAllyInvitves().add(guild);
            pp.sendMessage(Settings.GUILD_ALLY_SEND.replace("%tag%", search.getTag()));
            search.sendMessage(Settings.GUILD_ALLY_DELIVER.replace("%tag%", guild.getTag()));
            return;
        }
        GuildManager.setAllyGuilds(guild, search);
        BungeeCord.getInstance().broadcast(Settings.GUILD_ALLY_BROADCAST.replace("%tag%", guild.getTag()).replace("%tag2%", search.getTag()));
    }
}
