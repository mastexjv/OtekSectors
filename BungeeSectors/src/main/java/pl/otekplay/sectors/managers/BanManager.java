package pl.otekplay.sectors.managers;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import pl.otekplay.sectors.data.Ban;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.storage.Database;
import pl.otekplay.sectors.storage.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BanManager {

    private static final List<Ban> bans = new ArrayList<>();


    public static Ban addBan(User user, String reason, long date, User banner) {
        Ban ban = new Ban(user, date, reason, banner.getName());
        bans.add(ban);
        Database.insert(new BasicDBObjectBuilder()
                .add(Settings.COLUMN_BAN_UUID_NAME, ban.getUser().getUuid().toString())
                .add(Settings.COLUMN_BAN_TIME_NAME, ban.getDate())
                .add(Settings.COLUMN_BAN_REASON_NAME, ban.getReason())
                .add(Settings.COLUMN_BAN_ADMIN_NAME, ban.getBanner())
                .get(), Database.getBanTable());
        return ban;
    }

    public static Ban getBan(User user) {
        return getBan(user.getUuid());
    }

    public static Ban getBan(UUID uuid) {
        for (Ban ban : bans) {
            User user = ban.getUser();
            if (isValidUser(user, uuid)) {
                return ban;
            }
        }
        return null;
    }

    private static boolean isValidUser(User user, UUID uuid) {
        return user.getUuid().equals(uuid);
    }

    public static boolean isBanned(UUID uuid) {
        for (Ban ban : bans) {
            User user = ban.getUser();
            if (isValidUser(user, uuid)) {
                return ban.hasBan();
            }
        }
        return false;
    }

    public static boolean isBanned(User user) {
        return isBanned(user.getUuid());
    }

    public static void removeBan(Ban ban) {
        Database.remove(new BasicDBObjectBuilder().add(Settings.COLUMN_BAN_UUID_NAME,ban.getUser().getUuid().toString()).get(),Database.getBanTable());
        bans.remove(ban);
    }

    public static void loadBans(){

            DBCursor cursor = Database.getBanTable().find();
            while(cursor.hasNext()){
                Ban ban = new Ban(cursor.next());
                bans.add(ban);
            }
            cursor.close();
            System.out.println("Zaladowano "+bans.size()+" banow.");

    }

}
