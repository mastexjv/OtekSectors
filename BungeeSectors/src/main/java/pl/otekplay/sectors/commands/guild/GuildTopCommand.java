package pl.otekplay.sectors.commands.guild;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.GuildRanking;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.RankingManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.CommandUtil;

import java.util.List;

public class GuildTopCommand extends Command {
    private final GroupType type;
    public GuildTopCommand(String name, GroupType type, String... aliases) {
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
//OTEK LUBI W DUPE
        if (args.length != 0) {
            pp.sendMessage(Settings.BAD_USAGE+"/gtop");
            return;
        }

        List<GuildRanking> guildsRanking = RankingManager.getGuildRankings();
        int size = 0;
        if (size < 15) {
            size = guildsRanking.size();
        } else {
            size = 15;
        }
        pp.sendMessage(ChatUtil.fixColors("&7TOP GILDII:"));
        for (int i = 0; i < size; i++) {
            GuildRanking guildRanking = guildsRanking.get(i);
            pp.sendMessage(ChatUtil.fixColors("&8" + (1 + i) + ") &3" + guildRanking.getGuild().getTag() + " - &7" + guildRanking.getPoints() + " &a" + guildRanking.getKills() + " &c" + guildRanking.getDeads()));
        }
    }

}