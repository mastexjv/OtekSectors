package pl.otekplay.sectors.utils;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ChatUtil {
    public static String fixColors(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void sendActionBar(Player player, String message) {
        CraftPlayer p = (CraftPlayer) player;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        p.getHandle().playerConnection.sendPacket(new PacketPlayOutChat(cbc, (byte)2));
    }
}
