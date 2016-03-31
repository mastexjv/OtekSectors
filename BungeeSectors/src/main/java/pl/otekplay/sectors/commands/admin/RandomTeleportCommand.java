package pl.otekplay.sectors.commands.admin;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.packets.impl.player.PlayerTeleportRandomPacket;
import pl.otekplay.sectors.utils.CommandUtil;

/**
 * Created by Oskar on 19.02.2016.
 */
public class RandomTeleportCommand extends Command {
    private GroupType type;
    public RandomTeleportCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);
        this.type = type;
        CommandUtil.registerVariablesCommand(name,aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        ProxiedPlayer p = (ProxiedPlayer) sender;
        User user= UserManager.getUser(p.getUniqueId());
        user.getSector().getClient().sendPacket(new PlayerTeleportRandomPacket(p.getUniqueId().toString()));
    }
}
