package pl.otekplay.sectors.commands.guild;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.GuildManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.packets.impl.guild.GuildRequestLocationPacket;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.CommandUtil;

public class GuildCreateCommand extends Command {
    private final GroupType type;
    public GuildCreateCommand(String name, GroupType type, String... aliases) {
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
        if (args.length != 2) {
            pp.sendMessage(Settings.BAD_USAGE +"/zaloz <tag> <nazwa>");
            return;
        }
        if (user.hasGuild()) {
            pp.sendMessage(Settings.GUILD_CREATE_IS_IN_GUILD);
            return;
        }
        String tag = args[0].toUpperCase();
        if(tag.length() != Settings.GUILD_TAG_LENGHT){
            pp.sendMessage("Tag gildi musi miec 4 znaki.");
            return;
        }
        if (GuildManager.isGuildTag(tag)) {
            pp.sendMessage(Settings.GUILD_CREATE_IS_EXIST_TAG.replace("%tag%", tag));
            return;
        }
        String name = args[1];
        if(name.length() > Settings.GUILD_NAME_MAX_SIZE || name.length() < Settings.GUILD_NAME_MIN_SIZE){
            pp.sendMessage(ChatUtil.fixColors("&7Nazwa gildii musi skladac sie od &c"+Settings.GUILD_NAME_MIN_SIZE+" &7do &c"+Settings.GUILD_NAME_MAX_SIZE+"&7 znakow."));
            return;
        }
        if (GuildManager.isGuildName(name)) {
            pp.sendMessage(Settings.GUILD_CREATE_IS_EXIST_NAME.replace("%name%", name));
            return;
        }
        user.getSector().getClient().sendPacket(new GuildRequestLocationPacket(pp.getUniqueId().toString(),tag,name));
    }
}
