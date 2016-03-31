package pl.otekplay.sectors.managers;

import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.netty.web.ArgHandler;
import pl.otekplay.sectors.netty.web.impl.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpManager {

    private static Map<String, ArgHandler> commands = new ConcurrentHashMap<>();

    static {
        commands.put("online", new OnlineHandler());
        commands.put("motd", new MotdHandler());
        commands.put("wl", new WhitelistHandler());
        commands.put("sectors", new SectorsHandler());
        commands.put("userinfo", new UserInfoHandler());
        commands.put("sendcommand", new SendCommandHandler());

    }


    public static byte[] handleMessage(String[] args) {
        StringBuilder response = new StringBuilder();
        ArgHandler handler = commands.get(args[0].toLowerCase());
        if (handler != null) {
            try {
                if (handler.neededArgs() == 0) {
                    handler.handleMessage(args, response);
                } else {
                    if (args.length - 1 >= handler.neededArgs()) {
                        args = Arrays.copyOfRange(args, 1, args.length);
                        handler.handleMessage(args, response);
                    } else {
                        response.append("0\nNot enougth args");
                    }
                }
            } catch (Exception e) {
                StringWriter writer = new StringWriter();
                PrintWriter pw = new PrintWriter(writer);
                e.printStackTrace(pw);
                response.append("0\nException during '").append(handler.getClass().getSimpleName()).append("' handler\n").append(writer.toString());
            }
        } else {
            response.append("0\nWrongArg '").append(args[0]).append("'");
        }
        return response.toString().getBytes(StandardCharsets.UTF_8);
    }
}
