package pl.otekplay.sectors.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.md_5.bungee.api.config.ServerInfo;
import pl.otekplay.sectors.basic.Map;
import pl.otekplay.sectors.managers.GuildManager;
import pl.otekplay.sectors.managers.SectorManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.netty.sectors.SectorClient;
import pl.otekplay.sectors.packets.impl.guild.GuildAddMemberPacket;
import pl.otekplay.sectors.packets.impl.guild.GuildCreatePacket;
import pl.otekplay.sectors.packets.impl.user.UserRegisterPacket;

@Data
@AllArgsConstructor
public class Sector {
    private final String name;
    private final ServerInfo server;
    private final Map map;
    private final SectorClient client;
    private double TPS;
    private final int
            NORTHBORDER,
            SOUTHBORDER,
            EASTBORDER,
            WESTBORDER;


    public void init() {
        for (User user : UserManager.getUsers()) {
            client.sendPacket(new UserRegisterPacket(user.getUuid().toString(), user.getName(),user.getGroup().toString()));
        }
        for(Guild guild: GuildManager.getGuilds()){
            client.sendPacket(new GuildCreatePacket(guild.getTag(),guild.getName(),guild.getLeader().getUuid().toString(),guild.getLocation().toString()));
            for(User user:guild.getMembers()){
                SectorManager.sendPacket(new GuildAddMemberPacket(user.getUuid().toString(), guild.getTag()));
            }
        }
    }
}
