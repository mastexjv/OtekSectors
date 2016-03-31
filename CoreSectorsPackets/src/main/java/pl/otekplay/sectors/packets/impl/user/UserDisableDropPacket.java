package pl.otekplay.sectors.packets.impl.user;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.otekplay.sectors.packets.Packet;
import pl.otekplay.sectors.packets.PacketHandler;

/**
 * Created by Oskar on 02.03.2016.
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserDisableDropPacket extends Packet {

    private String uuidString;
    private String nameDrop;
    @Override
    public void write(ByteBuf bb) {
        writeString(bb,uuidString);
        writeString(bb,nameDrop);
    }

    @Override
    public void read(ByteBuf bb) {
      uuidString = readString(bb);
        nameDrop = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
       handler.handle(this);
    }
}
