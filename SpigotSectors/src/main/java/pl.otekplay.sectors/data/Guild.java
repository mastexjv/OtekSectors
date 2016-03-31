package pl.otekplay.sectors.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.otekplay.sectors.basic.GuildLocation;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Guild {

    private final String tag, name;
    private User leader;
    private boolean pvpInGuild;
    private long lastTNT;
    private final GuildLocation location;
    private final List<User> members = new ArrayList<>();
    private final List<Guild> ally = new ArrayList<>();

    public Location getBukkitLocation() {
        return new Location(Bukkit.getWorlds().get(0), location.getX(), location.getY(), location.getZ());
    }

    public boolean isCub(Location loc) {
        int x = location.getX();
        int z = location.getZ();
        int size = location.getSize();
        int distancex = Math.abs(loc.getBlockX() - x);
        int distancez = Math.abs(loc.getBlockZ() - z);
        return (distancex <= size) && (distancez <= size);
    }

}