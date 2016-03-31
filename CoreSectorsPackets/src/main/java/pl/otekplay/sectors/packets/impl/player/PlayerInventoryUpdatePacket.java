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
public class PlayerInventoryUpdatePacket extends Packet {
    private String uuidString;
    private int heldSlot;
    private String inventory,armor,enderchest;



    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidString);
        bb.writeInt(heldSlot);
        writeString(bb, inventory);
        writeString(bb, armor);
        writeString(bb, enderchest);
    }

    @Override
    public void read(ByteBuf bb) {
        uuidString = readString(bb);
        heldSlot = bb.readInt();
        inventory = readString(bb);
        armor = readString(bb);
        enderchest = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
