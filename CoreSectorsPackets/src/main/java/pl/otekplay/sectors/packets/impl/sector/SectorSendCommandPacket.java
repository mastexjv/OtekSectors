package pl.otekplay.sectors.packets.impl.sector;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.otekplay.sectors.packets.Packet;
import pl.otekplay.sectors.packets.PacketHandler;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectorSendCommandPacket extends Packet {

    private String command;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, command);
    }

    @Override
    public void read(ByteBuf bb) {
        command = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
    handler.handle(this);
    }
}
