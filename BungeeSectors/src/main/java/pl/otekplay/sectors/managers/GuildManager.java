package pl.otekplay.sectors.managers;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import net.md_5.bungee.BungeeCord;
import pl.otekplay.sectors.basic.GuildLocation;
import pl.otekplay.sectors.data.Guild;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.packets.impl.guild.*;
import pl.otekplay.sectors.storage.Database;
import pl.otekplay.sectors.storage.Settings;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuildManager {

    private static final Map<String, Guild> guilds = new ConcurrentHashMap<>();

    public static Guild registerGuild(String tag, String name, User leader, GuildLocation location) {
        Guild guild = new Guild(tag, name, location, leader);
        guild.getMembers().add(leader);
        leader.setGuild(guild);
        guilds.put(tag, guild);
        RankingManager.addRanking(guild.getRanking());
        SectorManager.sendPacket(new GuildCreatePacket(tag, name, leader.getUuid().toString(), location.toString()));
        guild.register();
        BungeeCord.getInstance().broadcast(Settings.GUILD_CREATE_BROADCAST.replace("%nick%", leader.getName()).replace("%tag%", tag).replace("%name%", name));
        return guild;
    }

    public static Guild getGuildByName(String name) {
        for (Guild guild : guilds.values()) {
            if (guild.getName().equalsIgnoreCase(name)) {
                return guild;
            }
        }
        return null;
    }

    public static Guild getGuildByTag(String tag) {
        for (Guild guild : guilds.values()) {
            if (guild.getTag().equalsIgnoreCase(tag)) {
                return guild;
            }
        }
        return null;
    }

    public static boolean isGuildTag(String tag) {
        return getGuildByTag(tag) != null;
    }

    public static boolean isGuildName(String name) {
        return getGuildByName(name) != null;
    }

    public static void addUserToGuild(User user, Guild guild) {
        guild.getIntives().remove(user);
        guild.getMembers().add(user);
        user.setGuild(guild);
            Database.update(new BasicDBObjectBuilder().add(Settings.COLUMN_GUILD_MEMBERS_NAME, guild.getMembersStringList()).get(), guild);

        SectorManager.sendPacket(new GuildAddMemberPacket(user.getUuid().toString(), guild.getTag()));
    }

    public static void removeUserFromGuild(User user) {
        Guild guild = user.getGuild();
        guild.getMembers().remove(user);
        user.setGuild(null);
        Database.update(new BasicDBObjectBuilder().add(Settings.COLUMN_GUILD_MEMBERS_NAME, guild.getMembersStringList()).get(), guild);
        SectorManager.sendPacket(new GuildRemoveMemberPacket(user.getUuid().toString()));
    }

    public static void removeGuild(Guild guild) {
        for (User user : guild.getMembers()) {
            user.setGuild(null);
        }
        guild.setLeader(null);
        SectorManager.sendPacket(new GuildRemovePacket(guild.getTag()));
        Database.remove(new BasicDBObjectBuilder().add(Settings.COLUMN_GUILD_TAG_NAME,guild.getTag()).get(),Database.getGuildTable());
        guilds.remove(guild);
    }


    public static Collection<Guild> getGuilds() {
        return guilds.values();
    }

    public static void setAllyGuilds(Guild g1, Guild g2) {
        g1.getAllyInvitves().remove(g2);
        g1.getAlly().add(g2);
        g2.getAlly().add(g1);
        Database.update(new BasicDBObject(Settings.COLUMN_GUILD_ALLY_NAME,g1.getAlly()),g1);
        Database.update(new BasicDBObject(Settings.COLUMN_GUILD_ALLY_NAME,g2.getAlly()),g2);

        SectorManager.sendPacket(new GuildSetAllyPacket(g1.getTag(), g2.getTag()));
    }

    public static void removeAllyGuilds(Guild g1, Guild g2) {
        g1.getAlly().remove(g2);
        g2.getAlly().remove(g1);
        Database.update(new BasicDBObject(Settings.COLUMN_GUILD_ALLY_NAME,g1.getAlly()),g1);
        Database.update(new BasicDBObject(Settings.COLUMN_GUILD_ALLY_NAME,g2.getAlly()),g2);
        SectorManager.sendPacket(new GuildSetEnemyPacket(g1.getTag(), g2.getTag()));
    }

    public static void loadGuilds() {
        DBCursor cursor = Database.getGuildTable().find();
        while (cursor.hasNext()) {
            Guild guild = new Guild(cursor.next());
            guilds.put(guild.getTag(), guild);
            RankingManager.addRanking(guild.getRanking());
        }
        cursor.close();
        System.out.println("Zaladowano " + guilds.size() + " gildii.");


    }

    public static void setNewLeader(Guild guild, User newLeader) {
        guild.setLeader(newLeader);
    }
}
