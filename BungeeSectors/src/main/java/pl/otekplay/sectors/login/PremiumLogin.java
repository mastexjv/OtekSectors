package pl.otekplay.sectors.login;

import net.md_5.bungee.api.ProxyServer;
import pl.kamcio96.uuidfetcher.ThreadProvider;
import pl.kamcio96.uuidfetcher.UUIDFetcher;
import pl.otekplay.sectors.Main;

import java.util.concurrent.TimeUnit;

public class PremiumLogin implements ThreadProvider {

    private final UUIDFetcher uuidFetcher;

    public PremiumLogin() {
        this.uuidFetcher = new UUIDFetcher(this);
    }

    @Override
    public void createThread(Runnable runnable, long delay, TimeUnit unit) {
        ProxyServer.getInstance().getScheduler().schedule(Main.getInstance(), runnable, delay, unit);
    }

    public UUIDFetcher getUUIDFetcher() {
        return uuidFetcher;
    }
}
