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
public class GuildSetHomeAnswerPacket extends Packet {
    private String uuidString,locationString;
    private int action;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidString);
        bb.writeInt(action);
        writeString(bb,locationString);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidString = readString(bb);
        action = bb.readInt();
        locationString = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
