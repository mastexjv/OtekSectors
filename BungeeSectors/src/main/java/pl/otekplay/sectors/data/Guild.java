package pl.otekplay.sectors.data;


import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.otekplay.sectors.basic.GuildLocation;
import pl.otekplay.sectors.managers.GuildManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Database;
import pl.otekplay.sectors.storage.Settings;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Guild {
    private final String tag, name;
    private final GuildLocation location;
    private final long createDate;
    private User leader;
    private boolean pvpInGuild;
    private String home;
    private final  GuildRanking ranking;
    private long  lastRenew, lastDestroy;
    private int lives;
    private final List<User> members = new ArrayList<>(), intives = new ArrayList<>();
    private final List<Guild> ally = new ArrayList<>(), allyInvitves = new ArrayList<>();


    public Guild(String tag, String name, GuildLocation location, User leader) {
        this.tag = tag;
        this.name = name;
        this.location = location;
        this.leader = leader;
        this.ranking = new GuildRanking(this);
        this.pvpInGuild = true;
        this.home = location.getX() + ":" + location.getY() + ":" + location.getZ();
        this.createDate = System.currentTimeMillis();
        this.lastRenew = createDate + Settings.WEEK;
        this.lastDestroy = 0;
        this.lives = 3;
    }
    public Guild(DBObject object){
        this.tag = (String) object.get(Settings.COLUMN_GUILD_TAG_NAME);
        this.name = (String) object.get(Settings.COLUMN_GUILD_NAME_NAME);
        this.location = GuildLocation.fromString((String) object.get(Settings.COLUMN_GUILD_LOCATION_NAME));
        this.leader = UserManager.getUserByName((String) object.get(Settings.COLUMN_GUILD_LEADER_NAME));
        this.ranking = new GuildRanking(this);
        this.pvpInGuild = Database.getBoolean(Settings.COLUMN_GUILD_PVPGUILD_NAME);
        this.home = (String) object.get(Settings.COLUMN_GUILD_GUILDHOME_NAME);
        this.createDate = (long) object.get(Settings.COLUMN_GUILD_CREATEDATE_NAME);
        this.lastRenew = (long) object.get(Settings.COLUMN_GUILD_LASTRENEW_NAME);
        this.lives = (int) object.get(Settings.COLUMN_GUILD_LIVES_NAME);
        this.lastDestroy = (long) object.get(Settings.COLUMN_GUILD_LASTDESTROY_NAME);
        BasicDBList memberList = (BasicDBList) object.get(Settings.COLUMN_GUILD_MEMBERS_NAME);
        for(Object s:memberList){
            String name = (String) s;
            User user = UserManager.getUserByName(name);
            user.setGuild(this);
            members.add(user);
        }
        BasicDBList allyList = (BasicDBList) object.get(Settings.COLUMN_GUILD_ALLY_NAME);
        for(Object s:allyList){
            String name = (String) s;
            if(GuildManager.isGuildTag(name)){
                Guild guild = GuildManager.getGuildByTag(name);
                if(!this.ally.contains(guild)) {
                    this.ally.add(guild);
                }
                if(!guild.getAlly().contains(this)) {
                    guild.getAlly().add(this);
                }
            }
        }
    }
    public void register(){
        DBObject object = BasicDBObjectBuilder.start()
                .add(Settings.COLUMN_GUILD_TAG_NAME,tag)
                .add(Settings.COLUMN_GUILD_NAME_NAME,name)
                .add(Settings.COLUMN_GUILD_LOCATION_NAME,location.toString())
                .add(Settings.COLUMN_GUILD_LEADER_NAME,leader.getName())
                .add(Settings.COLUMN_GUILD_GUILDHOME_NAME,home)
                .add(Settings.COLUMN_GUILD_PVPGUILD_NAME,pvpInGuild)
                .add(Settings.COLUMN_GUILD_CREATEDATE_NAME,createDate)
                .add(Settings.COLUMN_GUILD_LASTRENEW_NAME,lastRenew)
                .add(Settings.COLUMN_GUILD_LIVES_NAME,lives)
                .add(Settings.COLUMN_GUILD_LASTDESTROY_NAME,lastDestroy)
                .add(Settings.COLUMN_GUILD_MEMBERS_NAME,getMembersStringList())
                .add(Settings.COLUMN_GUILD_ALLY_NAME,getAllyStringList()).get();
        Database.insert(object,Database.getGuildTable());
    }


    public List<String> getMembersStringList(){
        List<String> membersObjects = new ArrayList<>();
        for(User user:members){
            membersObjects.add(user.getName());
        }
        return membersObjects;
    }
    public List<String> getAllyStringList(){
        List<String> membersObjects = new ArrayList<>();
        for(Guild guild:ally){
            membersObjects.add(guild.getTag());
        }
        return membersObjects;
    }
    public void sendMessage(String message) {
        for (User user : members) {
            user.sendMessage(message);
        }
    }


    public void setLeader(User leader) {
        this.leader = leader;
        Database.update(new BasicDBObject(Settings.COLUMN_GUILD_LEADER_NAME, this.leader), this);
    }

    public void setPvpInGuild(boolean pvpInGuild) {
        this.pvpInGuild = pvpInGuild;
        Database.update(new BasicDBObject(Settings.COLUMN_GUILD_PVPGUILD_NAME, this.pvpInGuild), this);
    }

    public void setHome(String home) {
        this.home = home;
        Database.update(new BasicDBObject(Settings.COLUMN_GUILD_GUILDHOME_NAME, this.home), this);
    }


    public void setLastRenew(long lastRenew) {
        this.lastRenew = lastRenew;
        Database.update(new BasicDBObject(Settings.COLUMN_GUILD_LASTRENEW_NAME, this.lastRenew), this);
    }

    public void setLastDestroy(long lastDestroy) {
        this.lastDestroy = lastDestroy;
        Database.update(new BasicDBObject(Settings.COLUMN_GUILD_LASTDESTROY_NAME, this.lastDestroy), this);
    }

    public void setLives(int lives) {
        this.lives = lives;
        Database.update(new BasicDBObject(Settings.COLUMN_GUILD_LIVES_NAME, this.lives), this);
    }
}
