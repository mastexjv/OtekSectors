package pl.otekplay.sectors.commands.admin;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.SectorManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.packets.impl.sector.SectorSendCommandPacket;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.CommandUtil;

public class SendCommand extends Command {
    private final GroupType type;
    public SendCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);        CommandUtil.registerVariablesCommand(name,aliases);
        this.type = type;
    }
//OTEK LUBI W DUPE

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) commandSender;
            User senderUser = UserManager.getUser(pp.getUniqueId());
            if (!senderUser.can(type)) {
                pp.sendMessage(Settings.NO_PERM);
                return;
            }
        }
        if (args.length < 2) {
            commandSender.sendMessage(Settings.BAD_USAGE+"/send [SERVER/ALL] [KOMENDA]");
            return;
        }
        String name = args[0];
        String message = "";
        for (int i = 1; i < args.length; i++) {
            message = message + args[i] + " ";
        }
        if (name.equalsIgnoreCase("ALL")) {
            SectorManager.sendPacket(new SectorSendCommandPacket(message));
        } else if (SectorManager.isSector(name)) {
            SectorManager.sendPacket(SectorManager.getSector(name),new SectorSendCommandPacket(message));
        }else{
            commandSender.sendMessage(ChatUtil.fixColors("&cPodana nazwa sektora jest bledna!"));
        }
    }
}
