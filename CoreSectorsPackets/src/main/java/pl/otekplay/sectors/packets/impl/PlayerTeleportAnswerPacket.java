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
public class PlayerTeleportAnswerPacket extends Packet {
    private String uuidString;
    private int actionTeleport;
    private int action;
    private String teleportString;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidString);
        bb.writeInt(actionTeleport);
        bb.writeInt(action);
        writeString(bb, teleportString);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidString = readString(bb);
        actionTeleport = bb.readInt();
        action = bb.readInt();
        teleportString = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
