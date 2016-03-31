package pl.otekplay.sectors.packets.impl.user;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.otekplay.sectors.enums.BackupType;
import pl.otekplay.sectors.packets.Packet;
import pl.otekplay.sectors.packets.PacketHandler;

/**
 * Created by Oskar on 28.02.2016.
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserBackupSavePacket extends Packet {
    private String uuidString;
    private BackupType backupType;
    private String inventoryBackup;
    private String armorBackup;
    private String enderchestBackup;

    @Override
    public void write(ByteBuf bb) {
        writeString(bb, uuidString);
        writeString(bb, backupType.toString());
        writeString(bb, inventoryBackup);
        writeString(bb, armorBackup);
        writeString(bb, enderchestBackup);

    }

    @Override
    public void read(ByteBuf bb) {
        uuidString = readString(bb);
        backupType = BackupType.valueOf(readString(bb));
        inventoryBackup = readString(bb);
        armorBackup = readString(bb);
        enderchestBackup = readString(bb);
    }

    @Override
    public void handle(PacketHandler handler) {
handler.handle(this);
    }
}
