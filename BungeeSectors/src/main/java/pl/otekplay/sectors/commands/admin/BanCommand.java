package pl.otekplay.sectors.commands.admin;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.Ban;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.BanManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.CommandUtil;
import pl.otekplay.sectors.utils.TimeUtil;

public class BanCommand extends Command {
    private final GroupType type;
    public BanCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);
        CommandUtil.registerVariablesCommand(name,aliases);
        this.type = type;
    }


    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ProxiedPlayer pp = (ProxiedPlayer) commandSender;
        if (commandSender instanceof ProxiedPlayer) {
            User senderUser = UserManager.getUser(pp.getUniqueId());
            if (!senderUser.can(type)) {
                pp.sendMessage(Settings.NO_PERM);
                return;
            }
        }
        if (args.length < 2) {
            commandSender.sendMessage(Settings.BAD_USAGE+"/ban [NICK] [CZAS] [POWOD] #[PERMBAN = CZAS = 0]");
            return;
        }
        String name = args[0];
        String time = args[1];
        String reason = args[2];
        for (int i = 3; i < args.length; i++) {
            reason = reason + " " + args[i];
        }
        if (!UserManager.isUser(name)) {
            commandSender.sendMessage(Settings.USER_NO_EXIST.replace("%nick%", name));
            return;
        }
        User target = UserManager.getUserByName(name);
        if (BanManager.isBanned(target)) {
            Ban ban = BanManager.getBan(target);
            commandSender.sendMessage(Settings.BAN_BROADCAST.replace("%nick%", target.getName()).replace("%time%", ban.getStringDate()).replace("%admin%", ban.getBanner()).replace("%reason%", ban.getReason()));
            return;
        }
        long date = TimeUtil.parseString(time);
        if(date != 0){
            date = date + System.currentTimeMillis();
        }
        Ban ban = BanManager.addBan(target, reason, date, UserManager.getUser(pp.getUniqueId()));
        BungeeCord.getInstance().broadcast(Settings.BAN_BROADCAST.replace("%nick%", ban.getUser().getName()).replace("%reason%", ban.getReason()).replace("%time%", ban.getStringDate()));
        pp.sendMessage(ChatUtil.fixColors("&7Zbanowales gracza &c" + ban.getUser().getName()));
        if (target.isOnline()) {
            target.getProxiedPlayer().disconnect(Settings.BAN_PLAYER_MSG.replace("%admin%", ban.getBanner()).replace("%time%", ban.getStringDate()).replace("%reason%", ban.getReason()));
        }

    }
}
