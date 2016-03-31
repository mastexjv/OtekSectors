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
public class PlayerTeleportPlayerPacket extends Packet {
    private String uuidPlayer;
    private String uuidTeleporter;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidPlayer);
        writeString(bb, uuidTeleporter);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidPlayer = readString(bb);
        uuidTeleporter = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);

    }
}
