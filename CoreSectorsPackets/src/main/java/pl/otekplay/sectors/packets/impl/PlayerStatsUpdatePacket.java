package pl.otekplay.sectors.packets.impl;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omg.PortableServer.POA;
import pl.otekplay.sectors.packets.Packet;
import pl.otekplay.sectors.packets.PacketHandler;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStatsUpdatePacket extends Packet {
    private String uuidString,potions;
    private double health;
    private int food,exp;
    private boolean sprint;
    private String gamemode;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidString);
        bb.writeDouble(health);
        bb.writeInt(food);
        bb.writeInt(exp);
        bb.writeBoolean(sprint);
        writeString(bb,potions);
        writeString(bb,gamemode);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidString = readString(bb);
        health = bb.readDouble();
        food = bb.readInt();
        exp = bb.readInt();
        sprint = bb.readBoolean();
        potions = readString(bb);
        gamemode = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);

    }
}
