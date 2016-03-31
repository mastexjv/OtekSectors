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
public class GuildNewLeaderPacket extends Packet {
    private String leaderUUID, tagGuild;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, tagGuild);
        writeString(bb, leaderUUID);
    }

    @Override
    public void read(ByteBuf bb) {
        tagGuild = readString(bb);
        leaderUUID = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}