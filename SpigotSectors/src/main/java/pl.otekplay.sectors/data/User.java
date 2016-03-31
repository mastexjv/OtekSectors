package pl.otekplay.sectors.data;


import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.otekplay.sectors.enums.GroupType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class User {

    private final UUID uuid;
    private final String name;
    private GroupType group;
    private Guild guild;
    private boolean serverChange, god, fly, vanish;
    private long lastAttack = 0;
    private final List<Drop> disabledDrops = new ArrayList<>();
    private int exp = 0;
    private int level = 0;
    public User(UUID uuid,String name,String group){
        this.uuid = uuid;
        this.name = name;
        this.group = GroupType.valueOf(group);
    }

    public void setFly(boolean fly) {
        this.fly = fly;
        if(getPlayer().isOnline()) {
            getPlayer().setAllowFlight(fly);
        }
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public boolean isOnline() {
        return getPlayer() != null;
    }

    public boolean hasGuild() {
        return getGuild() != null;
    }

    public void sendMessage(String message){
        if(isOnline()){
            getPlayer().sendMessage(message);
        }
    }
    public boolean hasCombat(){
        if ((lastAttack + 10000) > System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
    public boolean can(GroupType type){
        return group.can(type);
    }

    public void hidePlayer(){
        if(isOnline()) {
            Player user = getPlayer();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (user.equals(p)) {
                    continue;
                }
                p.hidePlayer(user);
            }
        }
    }
    public void showPlayer(){
        if(isOnline()) {
            Player user = getPlayer();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (user.equals(p)) {
                    continue;
                }
                p.showPlayer(user);
            }
        }
    }

}
