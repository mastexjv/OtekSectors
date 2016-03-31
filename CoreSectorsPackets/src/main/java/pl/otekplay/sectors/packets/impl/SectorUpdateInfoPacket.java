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
public class SectorUpdateInfoPacket extends Packet {
    private String sectorName;
    private double TPS;


    @Override
    public void write(ByteBuf bb) {
        writeString(bb,sectorName);
        bb.writeDouble(TPS);
    }

    @Override
    public void read(ByteBuf bb) {
        sectorName = readString(bb);
        TPS = bb.readDouble();
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
