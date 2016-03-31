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
public class SectorChangePacket extends Packet {

    private String nameServer;
    private String stringUUID;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, nameServer);
        writeString(bb, stringUUID);

    }

    @Override
    public void read(ByteBuf bb) {
        nameServer = readString(bb);
        stringUUID = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
