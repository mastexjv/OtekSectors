package pl.otekplay.sectors.commands.normal;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.CommandUtil;

public class MessageCommand extends Command {

    private final GroupType type;
    public MessageCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);
        CommandUtil.registerVariablesCommand(name,aliases);
        this.type = type;
    }


    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer p = BungeeCord.getInstance().getPlayer(commandSender.getName());
            User user = UserManager.getUser(p.getUniqueId());
            if (commandSender instanceof ProxiedPlayer) {
                if (!user.can(type)) {
                    p.sendMessage(Settings.NO_PERM);
                    return;
                }
            }//OTEK LUBI W DUPE
            if (args.length > 1) {
                String nick = args[0];
                if (UserManager.isUser(nick)) {
                    User rec = UserManager.getUserByName(nick);
                    if (rec.isOnline()) {
                        if(rec.getIgnoredUsers().contains(user)){
                            p.sendMessage(ChatUtil.fixColors("&7Gracz &c"+rec.getName()+" &7ignoruje Cie."));
                            return;
                        }
                        if(!rec.isPrivateMessages()){
                            p.sendMessage("&7Gracz &c"+rec.getName()+"&7 ma wylaczone prywatne wiadomosci.");
                            return;
                        }
                        String message = "";
                        for (int i = 1; i < args.length; i++) {
                            message = message + args[i] + " ";
                        }
                        p.sendMessage(Settings.MESSAGE_PLAYER_SEND.replace("%message%", message).replace("%user%", rec.getName()));
                        rec.sendMessage(Settings.MESSAGE_PLAYER_GET.replace("%message%", message).replace("%user%", p.getName()));
                        rec.setLastMessageFrom(user);
                    } else {
                        p.sendMessage(Settings.USER_IS_OFFLINE.replace("%user%", rec.getName()));
                    }
                } else {
                    p.sendMessage(Settings.USER_NO_EXIST.replace("%user%", nick));
                }
            } else {
                p.sendMessage(Settings.MESSAGE_VALID_USE);
            }
        }
    }
}
