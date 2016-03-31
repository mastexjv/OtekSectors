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
public class GuildSwitchPvPPacket extends Packet {
    private String guildTag;
    private boolean pvp;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, guildTag);
        bb.writeBoolean(pvp);
    }

    @Override
    public void read(ByteBuf bb) {
        guildTag = readString(bb);
        pvp = bb.readBoolean();
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}