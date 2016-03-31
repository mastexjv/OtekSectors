package ninja.amp.ampmenus.extenders;

import ninja.amp.ampmenus.events.ItemClickEvent;
import ninja.amp.ampmenus.items.MenuItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.UserManager;

/**
 * Created by Administrator on 2/16/2016.
 */
public class PotionExtender extends MenuItem {
    private final int price;
    public PotionExtender(String displayName, ItemStack icon, int price, String... lore) {
        super(displayName, icon, lore);
        this.price = price;
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        Player p = event.getPlayer();
        User user = UserManager.getUser(p.getUniqueId());
        if (!p.getInventory().contains(Material.GOLD_INGOT, price)) {
            p.sendMessage("&8Nie posiadasz wystarczajacej ilosci zlota. Wymagana ilosc to: &c" + price);
            return;
        }
        if (user.can(GroupType.VIP)) {
            p.getInventory().addItem(getIcon());
        }
        p.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, price));
        p.getInventory().addItem(getIcon());
        p.closeInventory();
        p.sendMessage("&2Pomyslnie zakupiles mikstury!");
    }
}
