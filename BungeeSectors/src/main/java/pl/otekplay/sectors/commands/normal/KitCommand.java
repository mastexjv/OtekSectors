package pl.otekplay.sectors.commands.normal;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.enums.KitType;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.packets.impl.user.UserGiveKitPacket;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.CommandUtil;

/**
 * Created by Oskar on 17.02.2016.
 */
public class KitCommand extends Command {
    private final GroupType type;

    public KitCommand(String name, GroupType type, String... aliases) {
        super(name, "", aliases);
        CommandUtil.registerVariablesCommand(name, aliases);
        this.type = type;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) commandSender;
            User user = UserManager.getUser(pp.getUniqueId());
            if (!user.can(type)) {
                pp.sendMessage(Settings.NO_PERM);
                return;
            }
            if (args.length != 1) {
                pp.sendMessage(Settings.BAD_USAGE + "/kit <zestaw>");
                return;
            }

            String arg = args[0];
            if (arg.equalsIgnoreCase("vip")) {
                if (!user.can(GroupType.VIP)) {
                    pp.sendMessage(ChatUtil.fixColors("&7Musisz posiadac range &cVIP &7by uzyc tego zestawu!"));
                    return;
                }
                if (!user.canUseKit(KitType.VIP.getID())) {
                    pp.sendMessage(ChatUtil.fixColors("&cPsst.. Musisz jeszcze troszke poczekac!"));
                    return;
                }
                user.getLastKits().put(KitType.VIP.getID(), System.currentTimeMillis() + KitType.VIP.getCooldown());
                user.getSector().getClient().sendPacket(new UserGiveKitPacket(user.getUuid().toString(), KitType.VIP.getID()));
            }else{
                pp.sendMessage(ChatUtil.fixColors("&7Dostepne zestawy: &c[VIP]"));
            }
        }
    }
}
