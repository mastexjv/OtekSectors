package pl.otekplay.sectors.commands.normal;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.Ranking;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.CommandUtil;

public class RankingCommand extends Command {
    private final GroupType type;
    public RankingCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);        CommandUtil.registerVariablesCommand(name,aliases);
        this.type = type;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) commandSender;
        User user = UserManager.getUser(p.getUniqueId());
        if (commandSender instanceof ProxiedPlayer) {
            if (!user.can(type)) {
                p.sendMessage(Settings.NO_PERM);
                return;
            }//OTEK LUBI W DUPE
        }
        Ranking rank = null;
        if (args.length > 1) {
            p.sendMessage(Settings.BAD_USAGE +"/ranking [NICK]");
            return;
        } else if (args.length == 0) {
            rank = UserManager.getUser(p.getUniqueId()).getRanking();
        } else if (args.length == 1) {
            String name = args[0];
            if (!UserManager.isUser(name)) {
                p.sendMessage(Settings.USER_NO_EXIST.replace("%nick%", name));
                return;
            }
            rank = UserManager.getUserByName(name).getRanking();
        }
        for(String s:Settings.INFO_RANKINGS){
            p.sendMessage(s.replace("%nick%",  rank.getUser().getName()).replace("%points%", ""+rank.getPoints()).replace("%kills%", ""+rank.getKills()).replace("%deaths%", ""+ rank.getDeaths()).replace("%positon%", ""+rank.getPlace()));
        }
    }
}
