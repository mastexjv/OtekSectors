package pl.otekplay.sectors.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;

import java.util.regex.Pattern;

public class ChatUtil {
    private static final Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-]+)$");

    public static String fixColors(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static boolean isAlphaNumeric(String s) {
        return pattern.matcher(s).find();
    }
}
