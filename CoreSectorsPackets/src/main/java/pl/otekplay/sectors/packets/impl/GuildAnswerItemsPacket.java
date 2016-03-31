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
public class GuildAnswerItemsPacket extends Packet {
    private String uuidString, tag, name, location;
    private boolean items;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidString);
        writeString(bb, tag);
        writeString(bb, name);
        writeString(bb, location);
        bb.writeBoolean(items);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidString = readString(bb);
        tag = readString(bb);
        name = readString(bb);
        location = readString(bb);
        items = bb.readBoolean();
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}