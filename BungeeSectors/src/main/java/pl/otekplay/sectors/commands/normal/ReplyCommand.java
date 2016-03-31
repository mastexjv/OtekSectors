package pl.otekplay.sectors.commands.normal;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.CommandUtil;

public class ReplyCommand extends Command {
    private final GroupType type;
    public ReplyCommand(String name, GroupType type, String... aliases) {
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
            }
        }
        if (args.length < 0) {
            p.sendMessage(Settings.BAD_USAGE+"/r [WIADOMOSC]");
            return;
        }//OTEK LUBI W DUPE
        if (user.getLastMessageFrom() == null) {
            p.sendMessage(ChatUtil.fixColors("&cNie masz komu odpisac!"));
            return;
        }
        if (!user.getLastMessageFrom().isOnline()) {
            p.sendMessage(Settings.USER_IS_OFFLINE.replace("%nick%", user.getLastMessageFrom().getName()));
            return;
        }
        if (user.getLastMessageFrom().getIgnoredUsers().contains(user)) {
            p.sendMessage(ChatUtil.fixColors("&7Gracz &c" + user.getLastMessageFrom().getName() + "&7 ignoruje Cie."));
            return;
        }
        if (!user.getLastMessageFrom().isPrivateMessages()) {
            p.sendMessage(ChatUtil.fixColors("&7Gracz &c" + user.getLastMessageFrom().getName() + " &7ma wylaczone prywatne wiadomosci."));
            return;
        }
        String message = "";
        for (int i = 0; i < args.length; i++) {
            message = message + args[i] + " ";
        }
        p.sendMessage(Settings.MESSAGE_PLAYER_SEND.replace("%message%", message).replace("%user%", user.getLastMessageFrom().getName()));
        user.getLastMessageFrom().sendMessage(Settings.MESSAGE_PLAYER_GET.replace("%message%", message).replace("%user%", p.getName()));
        user.getLastMessageFrom().setLastMessageFrom(user);
    }
}
