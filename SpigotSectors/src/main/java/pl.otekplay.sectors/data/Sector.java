package pl.otekplay.sectors.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.otekplay.sectors.basic.Map;
import pl.otekplay.sectors.netty.SectorClient;

@AllArgsConstructor
@Data
public class Sector {
    private Map map;
    private String name;
    private SectorClient client;
}
