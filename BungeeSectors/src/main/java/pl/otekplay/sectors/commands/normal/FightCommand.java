package pl.otekplay.sectors.commands.normal;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.Fight;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.CommandUtil;
import pl.otekplay.sectors.utils.TimeUtil;

/**
 * Created by Oskar on 13.02.2016.
 */
public class FightCommand extends Command {
    private final GroupType type;

    public FightCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);
        CommandUtil.registerVariablesCommand(name, aliases);
        this.type = type;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) sender;
        User user = UserManager.getUser(p.getUniqueId());
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("wygrane")) {
                p.sendMessage( ChatUtil.fixColors("&2Twoje wygrane walki:"));
                for (int i = 0; i > user.getWinFigths().size(); i++) {
                    Fight fight = user.getWinFigths().get(i);
                    int num = i + 1;
                    p.sendMessage("&6[" + num + "]" + "&3Data: " + TimeUtil.getDate(fight.getTime()) + "&3Pokonales: &4" + fight.getLoser().getName() + " &3Otrzymales: &4+" + fight.getPlus());
                }

            } else if (args[0].equalsIgnoreCase("przegrane")) {
                p.sendMessage(ChatUtil.fixColors("&4Twoje przegrane walki:"));
                for (int i = 0; i > user.getLoseFights().size(); i++) {
                    Fight fight = user.getLoseFights().get(i);
                    int num = i + 1;
                    p.sendMessage("&6[" + num + "]" + "&3Data: " + TimeUtil.getDate(fight.getTime()) + "&3Pokonany przez: &4" + fight.getWinner().getName() + " &3Odjeto: &4-" + fight.getLose());
                }
            }
        } else if (args.length == 2) {
            if (!UserManager.isUser(args[0])) {
                p.sendMessage(Settings.USER_NO_EXIST.replace("%nick%", args[0]));
                 return;
            }
            user = UserManager.getUserByName(args[0]);
            if (args[1].equalsIgnoreCase("wygrane")) {
                p.sendMessage( ChatUtil.fixColors("&7Wygrane walki gracza &c" + user.getName()+ " &7:"));
                for (int i = 0; i < user.getWinFigths().size(); i++) {
                    Fight fight = user.getWinFigths().get(i);
                    p.sendMessage( "&6[" + i + 1 + "]" + "&3Data: " + TimeUtil.getDate(fight.getTime()) + "&3Pokonales: &4" + fight.getLoser().getName()+ " &3Otrzymales: &4+" + fight.getPlus());
                }

            } else if (args[1].equalsIgnoreCase("przegrane")) {
                p.sendMessage(ChatUtil.fixColors( "&7Przegrane walki gracza &c" + user.getName()+ " &7:"));

                for (int i = 0; i < user.getLoseFights().size(); i++) {
                    Fight fight = user.getLoseFights().get(i);
                    p.sendMessage("&6[" + i + 1 + "]" + "&3Data: " + TimeUtil.getDate(fight.getTime()) + "&3Pokonany przez: &4" + fight.getLoser().getName() + " &3Odjeto: &4-" + fight.getLose());
                }
            }
        } else {
            p.sendMessage(Settings.BAD_USAGE+"/walki [NICK] [WYGRANE/PRZEGRANE]");
        }
    }
}
