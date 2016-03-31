package pl.otekplay.sectors.packets.impl.player;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.otekplay.sectors.packets.Packet;
import pl.otekplay.sectors.packets.PacketHandler;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerTeleportLocationPacket extends Packet {
    private String uuidString;
    private String locationString;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidString);
        writeString(bb, locationString);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidString = readString(bb);
        locationString = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
