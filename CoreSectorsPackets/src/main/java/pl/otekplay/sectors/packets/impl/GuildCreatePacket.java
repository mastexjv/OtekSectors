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
public class GuildCreatePacket extends Packet {
    private String tag, name, leaderUUID, location;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, tag);
        writeString(bb, name);
        writeString(bb, leaderUUID);
        writeString(bb, location);
    }

    @Override
    public void read(ByteBuf bb) {
        tag = readString(bb);
        name = readString(bb);
        leaderUUID = readString(bb);
        location = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}