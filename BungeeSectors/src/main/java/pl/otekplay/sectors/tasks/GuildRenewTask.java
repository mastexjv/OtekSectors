package pl.otekplay.sectors.tasks;

import net.md_5.bungee.BungeeCord;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.data.Guild;
import pl.otekplay.sectors.managers.GuildManager;
import pl.otekplay.sectors.storage.Settings;

import java.util.concurrent.TimeUnit;

public class GuildRenewTask implements Runnable {


    @Override
    public void run() {
        long now = System.currentTimeMillis();
        for (Guild guild : GuildManager.getGuilds()) {
            long renew = guild.getLastRenew();
            if (now > renew) {
                GuildManager.removeGuild(guild);
                BungeeCord.getInstance().broadcast(Settings.GUILD_NO_PAY_BROADCAST.replace("%tag%", guild.getTag()));
            }
        }
    }


    public static void start() {
        BungeeCord.getInstance().getScheduler().schedule(Main.getInstance(), new GuildRenewTask(), 1, TimeUnit.MINUTES);
    }
}
