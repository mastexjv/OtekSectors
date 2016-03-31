package pl.otekplay.sectors.commands.normal;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.Ranking;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.RankingManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.CommandUtil;

import java.util.List;

public class TopCommand extends Command {
    private final GroupType type;
    public TopCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);
        CommandUtil.registerVariablesCommand(name,aliases);
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
        if (args.length != 0) {
            pp.sendMessage(Settings.BAD_USAGE+"/gtop");
            return;
        }
        List<Ranking> rank = RankingManager.getRankings();
        int size = rank.size();
        if (size < 15) {
            size = rank.size();
        } else {
            size = 15;
        }
        pp.sendMessage(ChatUtil.fixColors("&9<---> &7TOP GRACZY &9<--->"));
        for (int i = 0; i < size; i++) {
            Ranking ranking = rank.get(i);
            pp.sendMessage(ChatUtil.fixColors("&8" + (1 + i) + ") &7" + ranking.getUser().getName() + " - &3" + ranking.getPoints() + " &a&l" + ranking.getKills() + " &c&l" + ranking.getDeaths()));
        }
    }
}