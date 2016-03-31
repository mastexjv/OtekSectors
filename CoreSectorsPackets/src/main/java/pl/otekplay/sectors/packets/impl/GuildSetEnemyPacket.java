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
public class GuildSetEnemyPacket extends Packet {
    private String g1Tag, g2Tag;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, g1Tag);
        writeString(bb, g2Tag);
    }

    @Override
    public void read(ByteBuf bb) {
        g1Tag = readString(bb);
        g2Tag = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);

    }
}