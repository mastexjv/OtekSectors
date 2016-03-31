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
public class PlayerAttackPlayerPacket extends Packet {
    private String uuidDef,uuidAtt;
    private long attackTime;
    @Override
    public void write(ByteBuf bb) {
        writeString(bb,uuidDef);
        writeString(bb,uuidAtt);
        bb.writeLong(attackTime);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidDef = readString(bb);
        uuidAtt = readString(bb);
        attackTime = bb.readLong();
    }

    @Override
    public void handle(PacketHandler handler) {
handler.handle(this);
    }
}
