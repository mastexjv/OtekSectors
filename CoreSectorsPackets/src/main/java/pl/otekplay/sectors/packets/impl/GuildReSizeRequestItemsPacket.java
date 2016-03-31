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
public class GuildReSizeRequestItemsPacket extends Packet {
    private String uuidString;


    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidString);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidString = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
