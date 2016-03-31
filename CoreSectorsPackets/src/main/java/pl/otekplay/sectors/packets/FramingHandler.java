package pl.otekplay.sectors.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

public class FramingHandler extends ByteToMessageCodec<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        int bodyLen = msg.readableBytes();
        out.ensureWritable( 4 + bodyLen );
        out.writeInt(bodyLen);
        out.writeBytes(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in instanceof EmptyByteBuf || in.readableBytes() < 4) return;
        in.markReaderIndex();
        int length = in.readInt();
        if(length > in.readableBytes()) {
            in.resetReaderIndex();
            return;
        }
        ByteBuf packet = ctx.alloc().buffer(length);
        in.readBytes(packet, length);
        out.add(packet);
    }
}