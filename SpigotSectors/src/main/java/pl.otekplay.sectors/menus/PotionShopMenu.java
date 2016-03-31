package pl.otekplay.sectors.menus;

import ninja.amp.ampmenus.extenders.PotionExtender;
import ninja.amp.ampmenus.items.MenuItem;
import ninja.amp.ampmenus.menus.ItemMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.utils.ChatUtil;

/**
 * Created by Administrator on 2/12/2016.
 */
public class PotionShopMenu  {

    public static ItemMenu menu = new ItemMenu(ChatColor.RED+"Sklep z miksturami", ItemMenu.Size.THREE_LINE, Main.getInstance());

    public static void init() {
        menu.fillEmptySlots(new MenuItem(ChatUtil.fixColors("&cPusto"), new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4 )));
        menu.setItem(0, new PotionExtender(ChatUtil.fixColors("&cMikstura zdrowia"), new ItemStack(Material.POTION, 1, (short) 8261), 5, ChatUtil.fixColors("&7Cena: &65 zlota") , ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(1, new PotionExtender(ChatUtil.fixColors("&cMikstura zdrowia &6II"), new ItemStack(Material.POTION, 1, (short) 8229), 8, ChatUtil.fixColors("&7Cena: &68 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(2, new PotionExtender(ChatUtil.fixColors("&6Mikstura odpornosci na ogien"), new ItemStack(Material.POTION, 1, (short) 8227), 30, ChatUtil.fixColors("&7Cena: &630 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(3, new PotionExtender(ChatUtil.fixColors("&7Wydluzona &6mikstura odpornosci na ogien"), new ItemStack(Material.POTION, 1, (short) 8259), 40,ChatUtil.fixColors("&7Cena: &640 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(5, new PotionExtender(ChatUtil.fixColors("&7Miotana &cmikstura zdrowia"), new ItemStack(Material.POTION, 1, (short) 16453), 10, ChatUtil.fixColors("&7Cena: &610 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(6, new PotionExtender(ChatUtil.fixColors("&7Miotana &cmikstura zdrowia &6II"), new ItemStack(Material.POTION, 1, (short) 16421), 15, ChatUtil.fixColors("&7Cena: &615 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(7, new PotionExtender(ChatUtil.fixColors("&7Miotana &6mikstura odpornosci na ogien"), new ItemStack(Material.POTION, 1, (short) 16419), 80, ChatUtil.fixColors("&7Cena: &680 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(8, new PotionExtender(ChatUtil.fixColors("&7Wydluzona miotana &6mikstura odpornosci na ogien"), new ItemStack(Material.POTION, 1, (short) 16451), 110, ChatUtil.fixColors("&7Cena: &6110 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(9, new PotionExtender(ChatUtil.fixColors("&bMikstura szybkosci"), new ItemStack(Material.POTION, 1, (short) 8194), 10,ChatUtil.fixColors("&7Cena: &610 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(10, new PotionExtender(ChatUtil.fixColors("&7Wydluzona &bMikstura szybkosci"),new ItemStack(Material.POTION, 1, (short) 8258), 15 ,  ChatUtil.fixColors("&7Cena: &615 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(11, new PotionExtender(ChatUtil.fixColors("&bMikstura szybkosci &6II"), new ItemStack(Material.POTION, 1, (short) 8226), 20, ChatUtil.fixColors("&7Cena: &620 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(12, new PotionExtender(ChatUtil.fixColors("&dMikstura regeneracji"), new ItemStack(Material.POTION, 1, (short) 8193), 35, ChatUtil.fixColors("&7Cena: &635 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(14, new PotionExtender(ChatUtil.fixColors("&7Miotana &bmikstura szybkosci"), new ItemStack(Material.POTION, 1, (short) 16386), 25, ChatUtil.fixColors("&7Cena: &625 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(15, new PotionExtender(ChatUtil.fixColors("&7Miotana &bmikstura szybkosci &6II"), new ItemStack(Material.POTION, 1, (short) 16418), 32, ChatUtil.fixColors("&7Cena: &632 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(16, new PotionExtender(ChatUtil.fixColors("&7Miotana wydluzona &bmikstura szybkosci"), new ItemStack(Material.POTION, 1, (short) 16450), 40, ChatUtil.fixColors("&7Cena: &640 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(17, new PotionExtender(ChatUtil.fixColors("&7Miotana &dmikstura regeneracji"),new ItemStack(Material.POTION, 1, (short) 16385), 60, ChatUtil.fixColors("&7Cena: &660 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(18, new PotionExtender(ChatUtil.fixColors("&7Wydluzona &dMikstura regeneracji"), new ItemStack(Material.POTION, 1, (short) 8257), 32 , ChatUtil.fixColors("&7Cena: &632 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(19, new PotionExtender(ChatUtil.fixColors("&dMikstura regeneracji &6II"), new ItemStack(Material.POTION, 1, (short) 8225), 40, ChatUtil.fixColors("&7Cena: &640 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(20, new PotionExtender(ChatUtil.fixColors("&4Mikstura sily"), new ItemStack(Material.POTION, 1, (short) 8201), 120, ChatUtil.fixColors("&7Cena: &6120 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(21, new PotionExtender(ChatUtil.fixColors("&7Wydluzona &4mikstura sily"), new ItemStack(Material.POTION, 1, (short) 8265), 128, ChatUtil.fixColors("&7Cena: &6128 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(23, new PotionExtender(ChatUtil.fixColors("&7Miotana &dmikstura regeneracji &6II"), new ItemStack(Material.POTION, 1, (short) 16417), 100 ,ChatUtil.fixColors("&7Cena: &6100 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(24, new PotionExtender(ChatUtil.fixColors("&7Miotana wydluzona &dmikstura regeneracji"), new ItemStack(Material.POTION, 1, (short) 16449), 64,ChatUtil.fixColors("&7Cena: &664 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(25, new PotionExtender(ChatUtil.fixColors("&7Miotana &4mikstura sily"), new ItemStack(Material.POTION, 1, (short) 16393), 200, ChatUtil.fixColors("&7Cena: &6200 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
        menu.setItem(26, new PotionExtender(ChatUtil.fixColors("&7Wydluzona miotana &4mikstura sily"), new ItemStack(Material.POTION, 1, (short) 16457),256,  ChatUtil.fixColors("&7Cena: &6256 zlota"), ChatUtil.fixColors("&7Gracz sztuk: &31"), ChatUtil.fixColors("&6VIP &7sztuk: &32"), ChatUtil.fixColors("&7Kliknij aby zakupic &6Miksture")));
    }

    public static ItemMenu getMenu() {
        return menu;
    }
}
