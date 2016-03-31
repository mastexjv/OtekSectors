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

public class HelpCommand  extends Command{
    private final GroupType type;
    public HelpCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);        CommandUtil.registerVariablesCommand(name,aliases);
    this.type = type;
    }

    @Override//OTEK LUBI W DUPE
    public void execute(CommandSender commandSender, String[] args) {

        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer)commandSender;
            User user = UserManager.getUser(pp.getUniqueId());
            if (!user.can(type)) {
                pp.sendMessage(Settings.NO_PERM);
                return;
            }
        }
        if(args.length == 0) {
            for(String s:Settings.INFO_HELPS){
                commandSender.sendMessage(ChatUtil.fixColors(s));
            }
            return;
        }
        String arg = args[0];
        if(arg.equalsIgnoreCase("gildie")){
            for(String s:Settings.INFO_GUILDS){
                commandSender.sendMessage(ChatUtil.fixColors(s));
            }
        }else if(arg.equalsIgnoreCase("komendy")){
            for(String s:Settings.INFO_COMMAND){
                commandSender.sendMessage(ChatUtil.fixColors(s));
            }
        }

    }
}
