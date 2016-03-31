package pl.otekplay.sectors.menus;
import lombok.Getter;
import ninja.amp.ampmenus.extenders.DropExtender;
import ninja.amp.ampmenus.items.StaticMenuItem;
import ninja.amp.ampmenus.menus.ItemMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.data.Drop;
import pl.otekplay.sectors.managers.DropManager;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.DropUtil;

public class DropMenu {
     @Getter
    private static ItemMenu dropMenu;

    public static void openInventory(Player p){
        dropMenu = new ItemMenu(ChatUtil.fixColors("&c&lDrop z kamienia"), ItemMenu.Size.TWO_LINE, Main.getInstance());
        for(int i = 0; i< DropManager.getDrops().size(); i++){
            Drop drop = DropManager.getDrops().get(i);
            dropMenu.setItem(i, new DropExtender(ChatUtil.fixColors(drop.getName()), drop.getItemStack() , ChatUtil.fixColors("&6»&7Szansa: &c" + DropUtil.checkChanceOfDrop(p, drop.getChance())), ChatUtil.fixColors("&7&6»&7Wypada ponizej: &cY: "+drop.getHeight()), ChatUtil.fixColors("&6»&7Fortune: " + DropUtil.isFortuneString(drop.isFortune())), ChatUtil.fixColors("&6»&7Drop: &c"+ DropUtil.isBlockedString(drop, p.getPlayer()))));
        }
        //dropMenu.setItem(17, new DropExtender("stone", ChatUtil.fixColors("&6Kamien"), new ItemStack(Material.COBBLESTONE), ChatUtil.fixColors("&6»&7Kliknij, by wylaczyc drop cobble!"), ChatUtil.fixColors("&6»&7Drop: &c"+ DropManager.isBlockedString("stone", p.getPlayer()))));
        //dropMenu.setItem(16, new DropExtender("messages", ChatUtil.fixColors("&6Wiadomosci"), new ItemStack(Material.BOOK), ChatUtil.fixColors("&6»&7Kliknij, by wylaczyc wiadomosci!"), ChatUtil.fixColors("&6»&7Status: &c"+ DropManager.isBlockedString("messages", p.getPlayer()))));
        dropMenu.open(p);
    }
}
