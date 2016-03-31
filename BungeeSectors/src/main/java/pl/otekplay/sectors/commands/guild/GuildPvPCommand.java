package pl.otekplay.sectors.commands.guild;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.Guild;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.SectorManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.packets.impl.guild.GuildSwitchPvPPacket;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.CommandUtil;

public class GuildPvPCommand extends Command {
    private final GroupType type;
    public GuildPvPCommand(String name, GroupType type, String... aliases) {
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
                return;
            }
        }
        if (args.length != 0) {
            pp.sendMessage(Settings.BAD_USAGE + "/gpvp");
            return;
        }//OTEK LUBI W DUPE
        if (!user.hasGuild()) {
            pp.sendMessage(Settings.GUILD_NO_GUILD);
            return;
        }
        Guild guild = user.getGuild();
        if (!guild.getLeader().equals(user)) {
            pp.sendMessage(Settings.GUILD_NO_LEADER);
            return;
        }
        guild.setPvpInGuild(!guild.isPvpInGuild());
        pp.sendMessage((guild.isPvpInGuild() ? ChatColor.GREEN + "Wlaczyles" : ChatColor.RED + "Wylaczyles") + ChatColor.GRAY + " pvp w gildii.");
        guild.sendMessage(ChatColor.GRAY + "PvP w Twojej gildii jest teraz: " + ((guild.isPvpInGuild() ? ChatColor.GREEN + "Wlaczone" : ChatColor.RED + "Wylaczone")));
        SectorManager.sendPacket(new GuildSwitchPvPPacket(guild.getTag(), guild.isPvpInGuild()));
    }
}