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
public class GuildReSizeAnswerItemsPacket extends Packet {
    private String uuidString;
    private boolean hasItems;




    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidString);
        bb.writeBoolean(hasItems);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidString = readString(bb);
        hasItems = bb.readBoolean();
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}