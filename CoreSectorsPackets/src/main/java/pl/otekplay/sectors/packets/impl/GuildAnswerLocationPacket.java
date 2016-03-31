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
public class GuildAnswerLocationPacket extends Packet {
    private String uuidString,tag,name, location;
    private int action;



    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidString);
        writeString(bb, tag);
        writeString(bb, name);
        bb.writeInt(action);
        writeString(bb, location);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidString = readString(bb);
        tag = readString(bb);
        name = readString(bb);
        action = bb.readInt();
        location = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}