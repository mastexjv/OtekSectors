package pl.otekplay.sectors.managers;

import pl.otekplay.sectors.netty.sectors.SectorClient;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {

    private static final List<SectorClient> clients = new ArrayList<>();

    public static void registerSectorClient(SectorClient client){
        clients.add(client);
    }
}
