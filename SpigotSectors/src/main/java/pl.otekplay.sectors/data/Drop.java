package pl.otekplay.sectors.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Data
public class Drop {
    private final String name;
    private final double chance;
    private final int height;
    private final int exp;
    private final boolean fortune;
    private final ItemStack itemStack;
}
