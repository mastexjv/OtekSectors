package pl.otekplay.sectors.storage;

import com.mongodb.*;
import lombok.Getter;
import net.md_5.bungee.BungeeCord;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.data.Guild;
import pl.otekplay.sectors.data.User;

public class Database {

    public static final String DATABASE_NAME = "MC";
    public static final String USER_TABLE_NAME = "Users";
    public static final String GUILD_TABLE_NAME = "Guilds";
    public static final String BAN_TABLE_NAME = "Bans";
    @Getter
    private static MongoClient client;
    private static DB db;
    @Getter
    private static DBCollection userTable, guildTable, banTable;

    public static void init(String host, int port) {
        BungeeCord.getInstance().getScheduler().runAsync(Main.getInstance(), () -> {
            try {
                client = new MongoClient(host, port);
                db = client.getDB(DATABASE_NAME);
                if (!db.collectionExists(USER_TABLE_NAME)) {
                    db.createCollection(USER_TABLE_NAME, new BasicDBObject());
                }
                if (!db.collectionExists(GUILD_TABLE_NAME)) {
                    db.createCollection(GUILD_TABLE_NAME, new BasicDBObject());
                }
                if (!db.collectionExists(BAN_TABLE_NAME)) {
                    db.createCollection(BAN_TABLE_NAME, new BasicDBObject());
                }
                userTable = db.getCollection(USER_TABLE_NAME);
                guildTable = db.getCollection(GUILD_TABLE_NAME);
                banTable = db.getCollection(BAN_TABLE_NAME);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        });
    }

    public static void insert(DBObject object, DBCollection collection) {
        System.out.println("INSERT " + object);
        BungeeCord.getInstance().getScheduler().runAsync(Main.getInstance(), () -> collection.insert(object));
    }

    public static void update(DBObject object, User user) {
        BungeeCord.getInstance().getScheduler().runAsync(Main.getInstance(), () -> {
            DBObject r = new BasicDBObject(Settings.COLUMN_USER_UUID_NAME, user.getUuid().toString());
            userTable.update(userTable.find(r).next(), new BasicDBObject("$set", object));
        });
    }
    public static void update(DBObject object, Guild guild) {
        BungeeCord.getInstance().getScheduler().runAsync(Main.getInstance(), () -> {
            DBObject r = new BasicDBObject(Settings.COLUMN_GUILD_TAG_NAME, guild.getTag());
            guildTable.update(guildTable.find(r).next(), new BasicDBObject("$set", object));
        });
    }
    public static void remove(DBObject object,DBCollection collection){
        BungeeCord.getInstance().getScheduler().runAsync(Main.getInstance(), () -> collection.remove(object));
    }


    public static boolean getBoolean(Object object) {
        return Boolean.valueOf(object + "");
    }
}
