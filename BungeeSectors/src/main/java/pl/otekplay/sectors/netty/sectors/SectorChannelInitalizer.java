package pl.otekplay.sectors.netty.sectors;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import pl.otekplay.sectors.packets.FramingHandler;
import pl.otekplay.sectors.packets.PacketCoder;

public class SectorChannelInitalizer extends ChannelInitializer<Channel> {

    public static final String FIELD_PREPENDER = "field-prepender";
    public static final String PACKET_CODER = "packet-coder";
    public static final String PACKET_HANDLER = "packet-handler";

    @Override
    protected void initChannel(Channel channel) throws Exception {
        try {
            channel.config().setOption(ChannelOption.IP_TOS, 0x18);
        } catch (ChannelException ignored) {}
        channel.config().setAllocator(PooledByteBufAllocator.DEFAULT);
        channel.pipeline()
                .addLast(FIELD_PREPENDER, new FramingHandler())
                .addLast(PACKET_CODER, new PacketCoder())
                .addLast(PACKET_HANDLER, new SectorClient());
    }
}
