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

public class GuildJoinCommand  extends Command {
    private final GroupType type;
    public GuildJoinCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);        CommandUtil.registerVariablesCommand(name,aliases);
        this.type = type;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ProxiedPlayer pp = (ProxiedPlayer) commandSender;
        User user = UserManager.getUser(pp.getUniqueId());
//OTEK LUBI W DUPE
        if (commandSender instanceof ProxiedPlayer) {
            if (!user.can(type)) {
                pp.sendMessage(Settings.NO_PERM);
                return;
            }
        }
        if (args.length != 1) {
            pp.sendMessage(Settings.BAD_USAGE+"/zaloz [TAG] [NAZWA]");
            return;
        }
        if(user.hasGuild()){
            if(user.getGuild().getLeader().equals(user)){
                pp.sendMessage(Settings.GUILD_JOIN_IS_IN_GUILD_LEADER);
            }else {
                pp.sendMessage(Settings.GUILD_JOIN_IS_IN_GUILD_OTHER);
            }
            return;
        }
        String tag = args[0];
        if(!GuildManager.isGuildTag(tag)){
            pp.sendMessage(Settings.GUILD_NO_EXIST);
            return;
        }
        Guild guild = GuildManager.getGuildByTag(tag);
        if(!guild.getIntives().contains(user)){
            pp.sendMessage(Settings.GUILD_JOIN_IS_NOT_INVITE.replace("tag", tag));
            return;
        }
        GuildManager.addUserToGuild(user,guild);
        BungeeCord.getInstance().broadcast(Settings.GUILD_JOIN_BROADCAST.replace("%tag%", guild.getTag()).replace("%nick%", user.getName()));
    }
}