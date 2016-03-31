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
public class PlayerDestroyGuildPacket extends Packet {
    private String tagGuild, uuidAttacker;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, tagGuild);
        writeString(bb, uuidAttacker);
    }

    @Override
    public void read(ByteBuf bb) {
        tagGuild = readString(bb);
        uuidAttacker = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
