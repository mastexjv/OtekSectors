package pl.otekplay.sectors.packets;

import io.netty.buffer.ByteBuf;

import java.lang.String;
import java.nio.charset.Charset;
import java.util.UUID;

public abstract class Packet {

    public abstract void write(ByteBuf bb);

    public abstract void read(ByteBuf bb);

    public abstract void handle(PacketHandler handler);

    private static Charset UTF_8 = Charset.forName("UTF-8");

    public static String readString(ByteBuf buf) {
        short length = buf.readShort();
        byte[] sb = new byte[length];
        buf.readBytes(sb);
        return new String(sb, UTF_8);
    }

    public static void writeString(ByteBuf buf, String s) {
        byte[] sb = s.getBytes(UTF_8);
        buf.writeShort(sb.length);
        buf.writeBytes(sb);
    }

    public static UUID readUUID(ByteBuf buf) {
        return new UUID(buf.readLong(), buf.readLong());
    }

    public static void writeUUID(ByteBuf buf, UUID uuid) {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }
}