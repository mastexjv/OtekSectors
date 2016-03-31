package pl.otekplay.sectors.data;


import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.SectorManager;
import pl.otekplay.sectors.packets.impl.user.UserRegisterPacket;
import pl.otekplay.sectors.storage.Database;
import pl.otekplay.sectors.storage.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
@ToString
public class User {
    private final UUID uuid;
    private final String name;
    private final Ranking ranking;
    private final List<User> ignoredUsers;
    private final HashMap<UUID, Long> tpaRequest;
    private final HashMap<Integer, Long> lastKits;
    private final List<Fight> loseFights,winFigths;


    private GroupType group;
    private String IP, home, domain;
    private Sector sector;
    private boolean privateMessages, fly, god, vanish;
    private Guild guild;
    private long lastAttackTime;
    private long lastMessageTime;
    private long lastCommandTime;
    private User lastAttacker, lastKiller, lastMessageFrom;
    private int exp,level;

    public User(UUID uuid, String name, String domain, String ip) {
        this.uuid = uuid;
        this.name = name;
        this.domain = domain;
        this.IP = ip;
        this.group = GroupType.PLAYER;
        this.home = "";
        this.ranking = new Ranking(this, 1000, 0, 0);
        this.lastKits = new HashMap<>();
        this.privateMessages = true;
        this.tpaRequest = new HashMap<>();
        this.ignoredUsers = new ArrayList<>();
        this.lastMessageTime = 0;
        this.lastCommandTime = 0;
        this.fly = false;
        this.god = false;
        this.vanish = false;
        this.loseFights = new ArrayList<>();
        this.winFigths = new ArrayList<>();
        this.exp = 0;
        this.level = 0;
    }

    public User(DBObject object) {
        this.uuid = UUID.fromString((String) object.get(Settings.COLUMN_USER_UUID_NAME));
        this.name = (String) object.get(Settings.COLUMN_USER_NAME_NAME);
        this.group = GroupType.valueOf((String) object.get(Settings.COLUMN_USER_GROUP_NAME));
        this.domain = (String) object.get(Settings.COLUMN_USER_DOMAIN_NAME);
        this.IP = (String) object.get(Settings.COLUMN_USER_IP_NAME);
        this.home = (String) object.get(Settings.COLUMN_USER_HOME_NAME);
        this.ranking = new Ranking(this, (Integer) object.get(Settings.COLUMN_USER_POINTS_NAME), (Integer) object.get(Settings.COLUMN_USER_KILLS_NAME), (Integer) object.get(Settings.COLUMN_USER_DEATHS_NAME));
        this.fly = (boolean) object.get(Settings.COLUMN_USER_FLY_NAME);
        this.god = (boolean) object.get(Settings.COLUMN_USER_GOD_NAME);
        this.vanish = (boolean) object.get(Settings.COLUMN_USER_VANISH_NAME);
        this.exp = (int)object.get(Settings.COLUMN_USER_EXP_NAME);
        this.level = (int)object.get(Settings.COLUMN_USER_LEVEL_NAME);
        this.privateMessages = true;
        this.tpaRequest = new HashMap<>();
        this.lastKits = new HashMap<>();
        this.ignoredUsers = new ArrayList<>();
        this.lastMessageTime = 0;
        this.lastCommandTime = 0;
        this.lastCommandTime = 0;
        this.loseFights = new ArrayList<>();
        this.winFigths = new ArrayList<>();
        BasicDBList fightWinsList = (BasicDBList) object.get(Settings.COLUMN_USER_FIGHT_WINS_NAME);
        for(Object s:fightWinsList ){
            String fight = (String) s;
            addFight(Fight.fromString(fight));
        }
        BasicDBList fightLoseList = (BasicDBList) object.get(Settings.COLUMN_USER_FIGHT_LOSE_NAME);
        for(Object s:fightLoseList  ){
            String fight = (String) s;
            addFight(Fight.fromString(fight));
        }
        String sectorName = (String) object.get(Settings.COLUMN_USER_SECTOR_NAME);
        if(SectorManager.isSector(sectorName)){
            this.sector = SectorManager.getSector(sectorName);
        }
        SectorManager.sendPacket(new UserRegisterPacket(uuid.toString(), getName(),getGroup().toString()));

    }



    public void register() {
        DBObject object = BasicDBObjectBuilder.start()
                .add(Settings.COLUMN_USER_UUID_NAME, uuid.toString())
                .add(Settings.COLUMN_USER_NAME_NAME, name)
                .add(Settings.COLUMN_USER_GROUP_NAME,group.toString())
                .add(Settings.COLUMN_USER_SECTOR_NAME, ((sector == null) ? "Brak" : sector.getName()))
                .add(Settings.COLUMN_USER_DOMAIN_NAME, domain)
                .add(Settings.COLUMN_USER_IP_NAME, IP)
                .add(Settings.COLUMN_USER_FLY_NAME, fly)
                .add(Settings.COLUMN_USER_GOD_NAME, god)
                .add(Settings.COLUMN_USER_VANISH_NAME, vanish)
                .add(Settings.COLUMN_USER_HOME_NAME, home)
                .add(Settings.COLUMN_USER_EXP_NAME, exp)
                .add(Settings.COLUMN_USER_LEVEL_NAME, level)
                .add(Settings.COLUMN_USER_POINTS_NAME, ranking.getPoints())
                .add(Settings.COLUMN_USER_KILLS_NAME, ranking.getKills())
                .add(Settings.COLUMN_USER_DEATHS_NAME, ranking.getDeaths())
                .add(Settings.COLUMN_USER_FIGHT_WINS_NAME,getWinFightsString())
                .add(Settings.COLUMN_USER_FIGHT_LOSE_NAME,getLoseFightsString()).get();
        Database.insert(object, Database.getUserTable());
    }



    public boolean can(GroupType type){
        return group.can(type);
    }
    public ProxiedPlayer getProxiedPlayer() {
        return ProxyServer.getInstance().getPlayer(uuid);
    }

    public boolean isOnline() {
        return getProxiedPlayer() != null;
    }

    public boolean hasGuild() {
        return getGuild() != null;
    }

    public void sendMessage(String message) {
        if (isOnline()) {
            getProxiedPlayer().sendMessage(message);
        }
    }

    public boolean hasValidRequest(User user) {
            long time = tpaRequest.get(user.getUuid());
            return (System.currentTimeMillis() - time) < 30000;
    }

    public boolean hasCombat() {
        return (lastAttackTime + 10000) > System.currentTimeMillis();
    }

    public void setIP(String IP) {
        this.IP = IP;
        Database.update(new BasicDBObject(Settings.COLUMN_USER_IP_NAME , this.IP), this);
    }

    public void setFly(boolean fly) {
        this.fly = fly;
        Database.update(new BasicDBObject(Settings.COLUMN_USER_FLY_NAME, this.fly), this);
    }

    public void setGod(boolean god) {
        this.god = god;
        Database.update(new BasicDBObject(Settings.COLUMN_USER_GOD_NAME, this.god), this);
    }

    public void setVanish(boolean vanish) {
        this.vanish = vanish;
        Database.update(new BasicDBObject(Settings.COLUMN_USER_VANISH_NAME, this.vanish), this);
    }

    public void setHome(String home) {
        this.home = home;
        Database.update(new BasicDBObject(Settings.COLUMN_USER_HOME_NAME, this.home), this);
    }

    public void setDomain(String domain) {
        this.domain = domain;
        Database.update(new BasicDBObject(Settings.COLUMN_USER_DOMAIN_NAME, this.domain), this);
    }

    public void setSector(Sector sector) {
        this.sector = sector;
        Database.update(new BasicDBObject(Settings.COLUMN_USER_SECTOR_NAME, this.sector.getName()), this);
    }
    public void setGroup(GroupType type){
        this.group = type;
        Database.update(new BasicDBObject(Settings.COLUMN_USER_GROUP_NAME,type.toString()),this);
    }

    public void setLevel(int level) {
        this.level = level;
        Database.update(new BasicDBObject(Settings.COLUMN_USER_LEVEL_NAME, level), this);
    }

    public void setExp(int exp) {
        this.exp = exp;
        Database.update(new BasicDBObject(Settings.COLUMN_USER_EXP_NAME, exp), this);
    }

    public void setPrivateMessages(boolean privateMessages) {
        this.privateMessages = privateMessages;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public void setLastAttackTime(long lastAttackTime) {
        this.lastAttackTime = lastAttackTime;
    }

    public void setLastMessageTime(long lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public void setLastAttacker(User lastAttacker) {
        this.lastAttacker = lastAttacker;
    }

    public void setLastKiller(User lastKiller) {
        this.lastKiller = lastKiller;
    }

    public void setLastMessageFrom(User lastMessageFrom) {
        this.lastMessageFrom = lastMessageFrom;
    }

    public void setLastCommandTime(long lastCommandTime) {
        this.lastCommandTime = lastCommandTime;
    }
    public void addFight(Fight fight) {
        if (fight.getWinner().equals(this)) {
            winFigths.add(fight);
            Database.update(new BasicDBObjectBuilder().add(Settings.COLUMN_USER_FIGHT_WINS_NAME,getWinFightsString()).get(),this);
        } else {
            loseFights.add(fight);
            Database.update(new BasicDBObjectBuilder().add(Settings.COLUMN_USER_FIGHT_LOSE_NAME,getLoseFightsString()).get(),this);
        }
    }
    public List<String> getWinFightsString(){
        List<String> list = new ArrayList<>();
        for(Fight fight:winFigths){
             list.add(fight.toString());
        }
        return list;
    }
    public List<String> getLoseFightsString(){
        List<String> list = new ArrayList<>();
        for(Fight fight:loseFights){
            list.add(fight.toString());
        }
        return list;
    }
    public boolean canUseKit(int i){
        if(lastKits.containsKey(i)){
            long time = lastKits.get(i);
            if(time > System.currentTimeMillis()){
                return false;
            }
            return true;
        }
        return true;
    }


}
