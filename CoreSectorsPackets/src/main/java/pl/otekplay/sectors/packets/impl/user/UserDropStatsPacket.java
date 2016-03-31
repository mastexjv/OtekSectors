package pl.otekplay.sectors.packets.impl.user;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.otekplay.sectors.packets.Packet;
import pl.otekplay.sectors.packets.PacketHandler;

/**
 * Created by Administrator on 3/8/2016.
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserDropStatsPacket extends Packet {
    private String uuidString;
    private int exp;
    private int level;


    @Override
    public void write(ByteBuf bb) {
        writeString(bb,uuidString);
        bb.writeInt(exp);
        bb.writeInt(level);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidString = readString(bb);
        exp = bb.readInt();
        level = bb.readInt();
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}