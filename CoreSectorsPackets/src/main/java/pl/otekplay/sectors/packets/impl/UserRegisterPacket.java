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
public class UserRegisterPacket extends Packet {

    private String stringUUID;
    private String name;
    private String groupName;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, stringUUID);
        writeString(bb, name);
        writeString(bb,groupName);


    }

    @Override
    public void read(ByteBuf bb) {
        stringUUID = readString(bb);
        name = readString(bb);
        groupName = readString(bb);

    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
