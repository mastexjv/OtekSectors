package pl.otekplay.sectors.packets.impl.guild;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.otekplay.sectors.packets.Packet;
import pl.otekplay.sectors.packets.PacketHandler;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuildAddMemberPacket extends Packet {
    private String memberUUID,tagGuild;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, memberUUID);
        writeString(bb, tagGuild);
    }

    @Override
    public void read(ByteBuf bb) {
        memberUUID = readString(bb);
        tagGuild = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}