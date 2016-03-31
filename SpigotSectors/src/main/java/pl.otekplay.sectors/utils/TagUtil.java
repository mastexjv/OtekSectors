package pl.otekplay.sectors.utils;

import net.minecraft.server.v1_9_R1.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_9_R1.Scoreboard;
import net.minecraft.server.v1_9_R1.ScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.managers.UserManager;

public class TagUtil {
    private static Scoreboard scoreboard = new Scoreboard();

    public static void createBoard(Player p) {
        try {
            ScoreboardTeam team = null;
            if (scoreboard.getPlayerTeam(Bukkit.getPlayer(p.getName()).getName()) == null) {
                team = scoreboard.createTeam(p.getName());
            }
            scoreboard.addPlayerToTeam(p.getName(), team.getName());
            team.setPrefix("");
            team.setDisplayName("");
            team.setSuffix("");
            PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 0);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            Bukkit.getOnlinePlayers().stream().filter(pp -> pp != p).forEach(pp -> ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(packet));
            Bukkit.getOnlinePlayers().stream().filter(pp -> pp != p).forEach(pp -> {
                ScoreboardTeam t = scoreboard.getTeam(pp.getName());
                PacketPlayOutScoreboardTeam packetShow = new PacketPlayOutScoreboardTeam(t, 0);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetShow);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void updateBoard(Player p) {
        if(p != null) {
            ScoreboardTeam team = scoreboard.getPlayerTeam(p.getName());
            User user = UserManager.getUser(p.getUniqueId());
            team.setDisplayName("");
            for (Player online : Bukkit.getOnlinePlayers()) {
                team.setPrefix(getValidPrefix(user, UserManager.getUser(online.getUniqueId())));
                PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 2);
                ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }


    public static String getValidPrefix(User get, User send) {
        ChatColor color = ChatColor.DARK_RED;
        if(get.hasGuild() && send.hasGuild()){
            if(get.getGuild().equals(send.getGuild())){
                color = ChatColor.GREEN;
            }else if(get.getGuild().getAlly().contains(send.getGuild())){
                color = ChatColor.YELLOW;
            }
        }
        String tag = "";
        if (get.hasGuild()) {
            tag = ChatColor.GRAY + "[" + color + get.getGuild().getTag() + ChatColor.GRAY + "] ";
        }
        return tag + ChatColor.WHITE;
    }

    public static void removeBoard(Player p) {
        try {
            if (scoreboard.getPlayerTeam(Bukkit.getPlayer(p.getName()).getName()) == null) {
                return;
            }
            ScoreboardTeam team = scoreboard.getPlayerTeam(p.getName());
            scoreboard.removePlayerFromTeam(p.getName(), team);
            PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 1);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            Bukkit.getOnlinePlayers().stream().filter(pp -> pp != p).forEach(pp -> ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(packet));
            Bukkit.getOnlinePlayers().stream().filter(pp -> pp != p).forEach(pp -> {
                ScoreboardTeam t = scoreboard.getTeam(pp.getName());
                PacketPlayOutScoreboardTeam packetHide = new PacketPlayOutScoreboardTeam(t, 1);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetHide);
            });
            scoreboard.removeTeam(team);
        } catch (Exception e) {
          e.printStackTrace();
        }

    }


}
