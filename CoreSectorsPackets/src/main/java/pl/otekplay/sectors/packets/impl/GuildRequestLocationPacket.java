package pl.otekplay.sectors.packets.impl;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.otekplay.sectors.packets.Packet;
import pl.otekplay.sectors.packets.PacketHandler;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuildRequestLocationPacket extends Packet {
    private String uuidString, tag, name;


    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidString);
        writeString(bb, tag);
        writeString(bb, name);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidString = readString(bb);
        tag = readString(bb);
        name = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
