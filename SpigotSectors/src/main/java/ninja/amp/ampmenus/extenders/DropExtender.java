package ninja.amp.ampmenus.extenders;

import ninja.amp.ampmenus.events.ItemClickEvent;
import ninja.amp.ampmenus.items.MenuItem;
import org.bukkit.inventory.ItemStack;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.menus.DropMenu;
import pl.otekplay.sectors.packets.impl.user.UserDisableDropPacket;


/**
 * Created by Administrator on 3/7/2016.
 */
public class DropExtender extends MenuItem{

    public DropExtender(String displayName, ItemStack icon, String... lore) {
        super(displayName, icon ,lore);
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        Main.getInstance().getClient().sendPacket(new UserDisableDropPacket(event.getPlayer().getUniqueId().toString(), getDisplayName()));
        event.getPlayer().closeInventory();
        DropMenu.openInventory(event.getPlayer());
    }
}
