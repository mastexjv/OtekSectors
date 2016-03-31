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
public class PlayerDeathPacket extends Packet {
    private String uuidDef;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidDef);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidDef = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
