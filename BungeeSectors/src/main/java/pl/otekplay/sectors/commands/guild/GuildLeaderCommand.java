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
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.CommandUtil;

public class GuildLeaderCommand extends Command {
    private final GroupType type;
    public GuildLeaderCommand(String name, GroupType type, String... aliases) {
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
            pp.sendMessage(Settings.BAD_USAGE+"/lider [NICK]");
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
        String nick = args[0];
        if (!UserManager.isUser(nick)) {
            pp.sendMessage(Settings.USER_NO_EXIST.replace("%nick%", nick));
            return;
        }
        User newLeader = UserManager.getUserByName(nick);
        if (!guild.getMembers().contains(newLeader)) {
            pp.sendMessage(ChatUtil.fixColors("&7Gracz &c" + newLeader.getName() + " &7musi byc w twojej gildii."));
            return;
        }
        GuildManager.setNewLeader(guild, newLeader);
        BungeeCord.getInstance().broadcast(ChatUtil.fixColors("&7Gracz &c" + user.getName() + " &7oddal lidera &c" + newLeader.getName()));
    }
}
