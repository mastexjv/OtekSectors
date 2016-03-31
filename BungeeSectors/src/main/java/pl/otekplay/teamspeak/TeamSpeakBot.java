package pl.otekplay.teamspeak;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.logging.Level;

/**
 * Created by Oskar on 27.02.2016.
 */
@Data
@RequiredArgsConstructor
public class TeamSpeakBot {
    private final String host, name, pass;
    private TS3Config config;
    private TS3Query query;
    private TS3Api api;

    public void start() {
        config = new TS3Config();
        config.setHost(host);
        config.setDebugLevel(Level.ALL);
        query = new TS3Query(config);
        query.connect();
        api = query.getApi();
        api.login(name, pass);
        api.selectVirtualServerById(1);
        api.setNickname("[BOT]xHC");
        api.sendServerMessage("Polaczyl sie pomyslnie.");
    }
}
