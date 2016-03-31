package pl.otekplay.sectors.managers;


import pl.otekplay.sectors.data.User;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    private static final ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap();

    public static void createUser(UUID uuid, String nick,String group) {
        if(!users.containsKey(uuid)){
            User user = new User(uuid,nick,group);
            users.put(uuid,user);
        }
    }

    public static boolean isUser(UUID uuid) {
        return users.containsKey(uuid);
    }

    public static User getUser(UUID uuid) {
        return users.get(uuid);
    }
}