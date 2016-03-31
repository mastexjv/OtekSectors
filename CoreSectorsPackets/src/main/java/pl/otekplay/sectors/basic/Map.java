package pl.otekplay.sectors.basic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Map {
    private final int x, z;

    public String toString() {
        return x + ":" + z;
    }

    public static Map fromString(String map) {
        String[] mapArg = map.split(":");
        return new Map(Integer.valueOf(mapArg[0]), Integer.valueOf(mapArg[1]));
    }
}