package pl.otekplay.sectors.netty.sectors;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pl.otekplay.sectors.managers.ClientManager;
import pl.otekplay.sectors.packets.Packet;
import pl.otekplay.sectors.packets.PacketHandler;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class SectorClient extends SimpleChannelInboundHandler<Packet> {

    private Channel ch;
    private volatile boolean closed;
    private Collection<PacketHandler> handlers = new CopyOnWriteArrayList<>();

    public SectorClient() {
        ClientManager.registerSectorClient(this);
        addHandler(new InitHandler(this));
    }

    public void sendPacket(Packet packet) {
        if (!closed) {
            ch.writeAndFlush(packet).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
    }

    public boolean isClosed() {
        return closed;
    }

    public void close() {
        if (!closed) {
            closed = true;
            ch.flush();
            ch.close();
        }
    }

    public void addHandler(PacketHandler handler) {
        this.handlers.add(handler);
    }

    protected void handlePacket(Packet packet) {
        for(PacketHandler handler:handlers){
            System.out.println(packet.toString());
            packet.handle(handler);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ch = ctx.channel();
        for(PacketHandler handler : handlers){
            try {
                handler.connected();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        for(PacketHandler handler : handlers){
            try {
                handler.disconnected();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlePacket(packet);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        for(PacketHandler handler : handlers){
            try {
                handler.exception(cause);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
