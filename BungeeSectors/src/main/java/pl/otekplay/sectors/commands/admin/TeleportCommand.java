package pl.otekplay.sectors.commands.admin;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.Sector;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.SectorManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.packets.impl.player.PlayerTeleportLocationPacket;
import pl.otekplay.sectors.packets.impl.player.PlayerTeleportPlayerPacket;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.CommandUtil;

public class TeleportCommand extends Command {
    private final GroupType type;
    public TeleportCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);        CommandUtil.registerVariablesCommand(name,aliases);
        this.type = type;
    }//OTEK LUBI W DUPE


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
        if (args.length == 1) {
            String name = args[0];
            if (UserManager.isUser(name)) {
                User user = UserManager.getUser(pp.getUniqueId());
                User teleport = UserManager.getUserByName(name);
                if (teleport.isOnline()) {
                    if (!pp.getServer().getInfo().equals(teleport.getSector().getServer())) {
                        pp.connect(teleport.getSector().getServer());
                    }
                    teleport.getSector().getClient().sendPacket(new PlayerTeleportPlayerPacket(user.getUuid().toString(), teleport.getUuid().toString()));
                    pp.sendMessage("Teleportacja do gracza " + teleport.getName() + " ...");
                    pp.sendMessage(Settings.TELEPORT_MSG.replace("%nick%", teleport.getName()));
                } else {
                    user.sendMessage(Settings.USER_IS_OFFLINE.replace("%nick%", teleport.getName()));
                }
            } else {
                pp.sendMessage(Settings.USER_NO_EXIST.replace("%nick%", args[0]));

            }
        } else if (args.length == 2) {
            String name = args[0];
            String teleportName = args[1];
            if (!UserManager.isUser(name)) {
                pp.sendMessage(Settings.USER_NO_EXIST.replace("%nick%", name));
                return;
            }
            if (!UserManager.isUser(teleportName)) {
                pp.sendMessage(Settings.USER_NO_EXIST.replace("%nick%", teleportName));
                return;
            }
            User user = UserManager.getUserByName(name);
            User teleport = UserManager.getUserByName(teleportName);
            if (!user.isOnline()) {
                pp.sendMessage(Settings.USER_IS_OFFLINE.replace("%nick%", user.getName()));
                return;
            }
            if (!teleport.isOnline()) {
                pp.sendMessage(Settings.USER_IS_OFFLINE.replace("%nick%", teleport.getName()));
                return;
            }
            if (!user.getSector().getServer().equals(teleport.getSector().getServer())) {
                user.getProxiedPlayer().connect(teleport.getSector().getServer());
            }
            teleport.getSector().getClient().sendPacket(new PlayerTeleportPlayerPacket(user.getUuid().toString(), teleport.getUuid().toString()));
            pp.sendMessage(Settings.TELEPORT_MSG_PL_TO_PL.replace("%nick%", user.getName()).replace("%target%", teleport.getName()));
        } else if (args.length == 3) {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            int z = Integer.parseInt(args[2]);
            User user = UserManager.getUser(pp.getUniqueId());
            Sector sec = SectorManager.getSectorByLocation(x, z);
            if (sec == null) {
                pp.sendMessage(ChatUtil.fixColors("&cLokacja nie zostala odnaleziona na zadnym z sektorow!"));
                return;
            }
            if (!user.getSector().equals(sec)) {
                pp.connect(sec.getServer());
            }
            sec.getClient().sendPacket(new PlayerTeleportLocationPacket(pp.getUniqueId().toString(), x + ":" + y + ":" + z));
            pp.sendMessage(Settings.TELEPORT_MSG_TO_LOC.replace("%sector%", sec.getName()).replace("%x%", ""+x).replace("%z%", ""+z));
        }

    }
}
