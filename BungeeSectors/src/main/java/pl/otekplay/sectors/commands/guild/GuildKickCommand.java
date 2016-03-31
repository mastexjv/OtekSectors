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

public class GuildKickCommand extends Command {
    private final GroupType type;
    public GuildKickCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);        CommandUtil.registerVariablesCommand(name,aliases);
        this.type = type;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ProxiedPlayer pp = (ProxiedPlayer) commandSender;
        User user = UserManager.getUser(pp.getUniqueId());//OTEK LUBI W DUPE
        if (commandSender instanceof ProxiedPlayer) {
            if (!user.can(type)) {
                pp.sendMessage(Settings.NO_PERM);
                return;
            }
        }
        if (args.length != 1) {
            pp.sendMessage(Settings.BAD_USAGE + "/wyrzuc <nick>");
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
        String name = args[0];
        if (!UserManager.isUser(name)) {
            pp.sendMessage(Settings.USER_NO_EXIST.replace("%nick%", name));
            return;
        }
        User toRem = UserManager.getUserByName(name);
        if(!toRem.hasGuild()){
            pp.sendMessage(Settings.GUILD_KICK_NO_GUILD.replace("%nick%", name));
            return;
        }
        if(!toRem.getGuild().equals(guild)){
            pp.sendMessage(Settings.GUILD_KICK_NO_GUILD.replace("%nick%", name));
            return;
        }
        GuildManager.removeUserFromGuild(toRem);
        BungeeCord.getInstance().broadcast(Settings.GUILD_KICK_BROADCAST.replace("%nick%", toRem.getName()).replace("%tag%", guild.getTag()));
    }
}