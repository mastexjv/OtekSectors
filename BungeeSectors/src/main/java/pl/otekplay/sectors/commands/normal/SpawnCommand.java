package pl.otekplay.sectors.commands.normal;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.Sector;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.SectorManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.packets.impl.player.PlayerTeleportRequestPacket;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.CommandUtil;

public class SpawnCommand extends Command {
    private final GroupType type;
    public SpawnCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);        CommandUtil.registerVariablesCommand(name,aliases);
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
            pp.sendMessage(Settings.BAD_USAGE+"/spawn");
            return;
        }
        Sector sector = SectorManager.getSectorByLocation(0, 0);
        if (!user.getSector().equals(sector)) {
            pp.connect(sector.getServer());
        }
        sector.getClient().sendPacket(new PlayerTeleportRequestPacket(pp.getUniqueId().toString(), 5, 1, "-33:90:-148"));
        pp.sendMessage(Settings.TELEPORT_IS_PENDING);
    }
}
