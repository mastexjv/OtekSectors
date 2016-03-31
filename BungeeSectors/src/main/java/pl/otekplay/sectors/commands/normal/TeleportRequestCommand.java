package pl.otekplay.sectors.commands.normal;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.packets.impl.player.PlayerTeleportRequestPacket;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.CommandUtil;

public class TeleportRequestCommand extends Command {
    private final GroupType type;
    public TeleportRequestCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);
        CommandUtil.registerVariablesCommand(name,aliases);
        this.type = type;
    }
//OTEK LUBI W DUPE

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
        if (args.length != 1) {
            p.sendMessage(Settings.BAD_USAGE+"/tpa [NICK]");
            return;
        }
        String name = args[0];
        if (!UserManager.isUser(name)) {
            p.sendMessage(Settings.USER_NO_EXIST.replace("%nick%", name));
            return;
        }
        User teleport = UserManager.getUserByName(name);
        if (!teleport.isOnline()) {
            p.sendMessage(Settings.USER_IS_OFFLINE.replace("%nick%", teleport.getName()));
            return;
        }
        if (teleport.getIgnoredUsers().contains(user)) {
            p.sendMessage(ChatUtil.fixColors("&7Gracz &c" + teleport.getName() + " &7ignoruje Cie."));
            return;
        }
        if(teleport.getTpaRequest().containsKey(user.getUuid())) {
            if (!user.hasValidRequest(teleport)) {
                teleport.sendMessage(ChatUtil.fixColors("&7Gracz &c" + user.getName() + " &7wyslal zapytanie o teleport, wpisz &c/tpa " + user.getName() + " &7aby zaakceptowac."));
                user.sendMessage(ChatUtil.fixColors("&7Wyslales graczu &c" + teleport.getName() + " &7zapytanie o teleport."));
                teleport.getTpaRequest().put(user.getUuid(), System.currentTimeMillis());
                return;
            }else{
                user.sendMessage("Wyslales juz zaproszenie do teleportu graczowi "+teleport.getName());
                return;
            }
        }
        user.getTpaRequest().remove(teleport.getUuid());
        user.getSector().getClient().sendPacket(new PlayerTeleportRequestPacket(teleport.getUuid().toString(), 5, 0, user.getUuid().toString()));
        teleport.sendMessage(Settings.TELEPORT_IS_PENDING);
        user.sendMessage(ChatUtil.fixColors("&7Gracz &c" + teleport.getName() + " &7teleportuje sie do Ciebie.."));

    }
}
