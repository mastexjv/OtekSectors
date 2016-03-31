package pl.otekplay.sectors.packets.impl.player;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.otekplay.sectors.packets.Packet;
import pl.otekplay.sectors.packets.PacketHandler;

/**
 * Created by Oskar on 21.02.2016.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerSetVanishPacket extends Packet {
    private String uuidPlayer;
    private boolean vanishMode;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidPlayer);
        bb.writeBoolean(vanishMode);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidPlayer = readString(bb);
        vanishMode = bb.readBoolean();
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}