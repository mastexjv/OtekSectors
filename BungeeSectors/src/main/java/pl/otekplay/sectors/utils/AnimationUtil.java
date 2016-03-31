package pl.otekplay.sectors.utils;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.netty.ChannelWrapper;
import pl.otekplay.sectors.data.User;

public class AnimationUtil {
    private static ChannelWrapper getChannel(ProxiedPlayer player) throws Exception {
        ChannelWrapper channel = (ChannelWrapper) AccessUtil.setAccessible(UserConnection.class.getDeclaredField("ch")).get((UserConnection) player);
        return channel;
    }

    public static void unregisterPacketManager(User u) {
        try {
            ProxiedPlayer p = u.getProxiedPlayer();
            if (p == null) {
                return;
            }
            ChannelWrapper channel = getChannel(p);
            channel.getHandle().pipeline().remove("packet_" + u.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerPacketManager(User u) {
        try {
            ProxiedPlayer p = u.getProxiedPlayer();
            if (p != null) {
                ChannelWrapper channel = getChannel(p);
                channel.getHandle().pipeline().addBefore("inbound-boss", "packet_" + u.getName(), new ChannelDuplexHandler() {
                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        String packetName = msg.getClass().getSimpleName();
                        if (packetName.equalsIgnoreCase("respawn")) {
                            return;
                        }
                        super.write(ctx, msg, promise);
                    }

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
