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
public class PlayerSetGodPacket extends Packet {
    private String uuidPlayer;
    private boolean godMode;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidPlayer);
        bb.writeBoolean(godMode);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidPlayer = readString(bb);
        godMode = bb.readBoolean();
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}