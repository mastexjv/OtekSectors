package pl.otekplay.sectors.packets.impl.user;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.otekplay.sectors.packets.Packet;
import pl.otekplay.sectors.packets.PacketHandler;

/**
 * Created by Oskar on 16.02.2016.
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserGroupUpdatePacket extends Packet {
    private String uuidString;
    private String group;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidString);
        writeString(bb,group);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidString = readString(bb);
        group = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}