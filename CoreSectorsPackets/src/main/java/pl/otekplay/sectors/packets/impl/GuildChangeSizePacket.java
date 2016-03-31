package pl.otekplay.sectors.packets.impl;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.otekplay.sectors.packets.Packet;
import pl.otekplay.sectors.packets.PacketHandler;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GuildChangeSizePacket extends Packet {
    private String tagGuild;
    private int sizeGuild;




    @Override
    public void write(ByteBuf bb) {
        writeString(bb, tagGuild);
        bb.writeInt(sizeGuild);

    }

    @Override
    public void read(ByteBuf bb) {
        tagGuild = readString(bb);
        sizeGuild = bb.readInt();
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}