package pl.otekplay.sectors.commands.admin;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.Sector;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.SectorManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.CommandUtil;

public class SectorsCommand extends Command {
    private final GroupType type;
    public SectorsCommand(String name, GroupType type, String... aliases) {
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
            ProxiedPlayer p = BungeeCord.getInstance().getPlayer(commandSender.getName());
            if (args.length == 1) {
                String arg = args[0];
                if (arg.equalsIgnoreCase("servers") || (arg.equalsIgnoreCase("status") || arg.equalsIgnoreCase("tps"))) {
                    p.sendMessage(Settings.SECTOR_HEADER_MESSAGE);
                    for (Sector sector : SectorManager.getSectors()) {
                        p.sendMessage(Settings.SECTOR_INFO_MESSAGE.replace("%online%", sector.getServer().getPlayers().size() + "").replace("%tps%", sector.getTPS() + "").replace("%name%", sector.getName()));
                    }
                } else if (arg.equalsIgnoreCase("cords") || arg.equalsIgnoreCase("location")) {
                    p.sendMessage(Settings.SECTOR_HEADER_MESSAGE);
                    for (Sector sector : SectorManager.getSectors()) {
                        p.sendMessage(Settings.SECTOR_CORDS_MESSAGE.replace("%cords%", "N: "+sector.getNORTHBORDER()+" S:"+sector.getSOUTHBORDER()+" E: "+sector.getEASTBORDER()+" W: "+sector.getWESTBORDER()).replace("%name%", sector.getName()));
                    }
                }
            }else{
                p.sendMessage(Settings.SECTOR_VALID_USE);
            }

        }
    }
}
