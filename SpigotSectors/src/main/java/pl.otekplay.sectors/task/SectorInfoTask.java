package pl.otekplay.sectors.task;

import net.minecraft.server.v1_9_R1.MinecraftServer;
import org.bukkit.scheduler.BukkitRunnable;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.packets.impl.sector.SectorUpdateInfoPacket;
import pl.otekplay.sectors.utils.DoubleUtil;

public class SectorInfoTask extends BukkitRunnable {
    @Override
    public void run() {
        Main.getInstance().getClient().sendPacket(new SectorUpdateInfoPacket(Main.getInstance().getSector().getName(), DoubleUtil.round(MinecraftServer.getServer().recentTps[0],2)));
    }
}
