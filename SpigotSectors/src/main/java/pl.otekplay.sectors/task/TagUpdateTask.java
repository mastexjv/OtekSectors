package pl.otekplay.sectors.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.otekplay.sectors.utils.TagUtil;

public class TagUpdateTask implements Runnable {
    @Override
    public void run() {
        for(Player p: Bukkit.getOnlinePlayers()){
            TagUtil.updateBoard(p);
        }
    }
}
