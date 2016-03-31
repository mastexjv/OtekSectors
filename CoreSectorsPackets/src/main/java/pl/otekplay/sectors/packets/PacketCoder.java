package pl.otekplay.sectors.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

public class PacketCoder extends MessageToMessageCodec<ByteBuf, Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> list) throws Exception {
        ByteBuf out = ctx.alloc().buffer();
        int id = PacketManager.getPacketID(packet.getClass());
        out.writeInt(id);
        packet.write(out);
        list.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        int packetId = in.readInt();
        Packet packet = PacketManager.getPacket(packetId);
        if (packet != null) {
            packet.read(in);
            list.add(packet);
            if(in.readableBytes() > 0){
                throw new Exception( "Did not read all bytes from packet " + packet.getClass() + " " + packetId);
            }
        }
    }
}