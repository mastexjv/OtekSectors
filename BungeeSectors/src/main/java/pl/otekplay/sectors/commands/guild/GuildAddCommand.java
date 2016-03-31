package pl.otekplay.sectors.commands.guild;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.Guild;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.CommandUtil;

public class GuildAddCommand extends Command {
    private final GroupType type;
    public GuildAddCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);        CommandUtil.registerVariablesCommand(name,aliases);
        this.type = type;
    }
    //OTEK LUBI W DUPE
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
            pp.sendMessage(Settings.BAD_USAGE + "/dodaj <nick>");
            return;
        }
        String name = args[0];
        if (!UserManager.isUser(name)) {
            pp.sendMessage(Settings.USER_NO_EXIST.replace("%nick%", name));
            return;
        }
        User toAdd = UserManager.getUserByName(name);
        if (!toAdd.isOnline()) {
            pp.sendMessage(Settings.USER_IS_OFFLINE.replace("%nick%", toAdd.getName()));
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
        if (toAdd.equals(user)) {
            pp.sendMessage(Settings.GUILD_NO_ADD_SAME);
            return;
        }
        if (toAdd.hasGuild()) {
            if (toAdd.getGuild().equals(user.getGuild())) {
                pp.sendMessage(Settings.GUILD_IS_IN_GUILD_SAME.replace("%nick%", toAdd.getName()));
            } else {
                pp.sendMessage(Settings.GUILD_IS_IN_GUILD_OTHER.replace("%nick%", toAdd.getName()));
            }
            return;
        }
        if (guild.getIntives().contains(toAdd)) {
            pp.sendMessage(Settings.GUILD_INVITE_IS_PENDING.replace("%nick%", toAdd.getName()));
            return;
        }
        guild.getIntives().add(toAdd);
        toAdd.getProxiedPlayer().sendMessage(Settings.GUILD_INVITE_DELIVER.replace("%tag%", guild.getTag()));

        pp.sendMessage(Settings.GUILD_INVITE_SEND.replace("%nick%", toAdd.getName()));
    }
}
