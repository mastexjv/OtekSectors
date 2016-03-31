package pl.otekplay.sectors.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
public class GuildLocation {
    @Getter
    @Setter
    private int x, y, z, size;


    public String toString() {
        return x + ":" + y + ":" + z + ":" + size;
    }

    public static GuildLocation fromString(String location) {
        String str2loc[] = location.split(":");
        GuildLocation loc = new GuildLocation(0, 0, 0, 0);
        loc.setX((int) Double.parseDouble(str2loc[0]));
        loc.setY((int) Double.parseDouble(str2loc[1]));
        loc.setZ((int) Double.parseDouble(str2loc[2]));
        loc.setSize(Integer.parseInt(str2loc[3]));
        return loc;
    }
    public static GuildLocation fromNormalStringLocation(String location,int size){
        String str2loc[] = location.split(":");
        GuildLocation loc = new GuildLocation(0, 0, 0, 0);
        loc.setX((int) Double.parseDouble(str2loc[0]));
        loc.setY((int) Double.parseDouble(str2loc[1]));
        loc.setZ((int) Double.parseDouble(str2loc[2]));
        loc.setSize(size);
        return loc;
    }

}
