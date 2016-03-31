package pl.otekplay.sectors.packets.impl.user;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.otekplay.sectors.packets.Packet;
import pl.otekplay.sectors.packets.PacketHandler;

/**
 * Created by Administrator on 2/18/2016.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserFlyUpdatePacket extends Packet {

    private String uuid;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuid);
    }

    @Override
    public void read(ByteBuf bb) {
        uuid = readString(bb);
    }


    @Override
    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
