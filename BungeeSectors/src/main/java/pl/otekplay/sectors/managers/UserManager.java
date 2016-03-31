package pl.otekplay.sectors.managers;

import com.mongodb.DBCursor;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.packets.impl.user.UserRegisterPacket;
import pl.otekplay.sectors.storage.Database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private static final ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap();


    public static User createUser(UUID uuid, String nick,  String ip, String domain) {
        User user = new User(uuid, nick,domain,ip);
        users.put(uuid, user);
        RankingManager.addRanking(user.getRanking());
        SectorManager.sendPacket(new UserRegisterPacket(uuid.toString(), user.getName(),user.getGroup().toString()));
        user.register();
        return user;
    }

    public static boolean isUser(UUID uuid) {
        return users.containsKey(uuid);
    }

    public static boolean isUser(String name) {
        return getUserByName(name) != null;
    }

    public static User getUser(UUID uuid) {
        return users.get(uuid);
    }

    public static User getUserByName(String name) {
        for (User user : users.values()) {
            if (user.getName().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }
    public static User getUserByIP(String ip){
        for(User user:users.values()){
            if(user.getIP().equalsIgnoreCase(ip)){
                return user;
            }
        }
        return null;
    }

    public static Collection<User> getUsers() {
        return users.values();
    }

    public static List<User> getOnlineUsers() {
        Collection<User> users = getUsers();
        List<User> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (user.isOnline()) {
                onlineUsers.add(user);
            }
        }
        return onlineUsers;
    }

    public static void loadUsers(){
            DBCursor cursor = Database.getUserTable().find();
            while(cursor.hasNext()){
                User user = new User(cursor.next());
                users.put(user.getUuid(), user);
                RankingManager.addRanking(user.getRanking());
            }
            cursor.close();
            System.out.println("Zaladowano "+users.size()+" graczy.");


    }

}

