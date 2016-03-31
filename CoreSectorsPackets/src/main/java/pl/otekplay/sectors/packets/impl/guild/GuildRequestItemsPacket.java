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
public class GuildRequestItemsPacket extends Packet {
    private String uuidString,tag,name,location;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidString);
        writeString(bb, tag);
        writeString(bb, name);
        writeString(bb, location);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidString = readString(bb);
        tag = readString(bb);
        name = readString(bb);
        location = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
