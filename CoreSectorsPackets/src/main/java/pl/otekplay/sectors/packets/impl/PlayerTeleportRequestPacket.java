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
public class PlayerTeleportRequestPacket extends Packet {
    private String uuidString;
    private int time;
    private int action;
    private String teleportString;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidString);
        bb.writeInt(time);
        bb.writeInt(action);
        writeString(bb, teleportString);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidString = readString(bb);
        time = bb.readInt();
        action = bb.readInt();
        teleportString = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
