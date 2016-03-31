package pl.otekplay.sectors.packets.impl;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.otekplay.sectors.packets.Packet;
import pl.otekplay.sectors.packets.PacketHandler;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectorRegisterPacket extends Packet {

    private String name, address, map;
    private int
            NORTHBORDER,
            SOUTHBORDER,
            EASTBORDER,
            WESTBORDER;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, name);
        writeString(bb, address);
        writeString(bb, map);
        bb.writeInt(NORTHBORDER);
        bb.writeInt(SOUTHBORDER);
        bb.writeInt(EASTBORDER);
        bb.writeInt(WESTBORDER);
    }

    @Override
    public void read(ByteBuf bb) {
        name = readString(bb);
        address = readString(bb);
        map = readString(bb);
        NORTHBORDER = bb.readInt();
        SOUTHBORDER = bb.readInt();
        EASTBORDER = bb.readInt();
        WESTBORDER = bb.readInt();
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
