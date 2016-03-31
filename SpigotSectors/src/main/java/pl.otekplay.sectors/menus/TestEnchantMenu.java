package pl.otekplay.sectors.menus;

import ninja.amp.ampmenus.extenders.EnchantExtender;
import ninja.amp.ampmenus.items.MenuItem;
import ninja.amp.ampmenus.menus.ItemMenu;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.SpaceUtil;

/**
 * Created by Administrator on 2/16/2016.
 */
public class TestEnchantMenu {
    public static boolean isAllowedEnchant(ItemStack item, Enchantment e) {
        return e.canEnchantItem(item);
    }

    public static void openMenu(Player p, int bookshelfs, ItemStack item){
        ItemMenu enchantMenu = new ItemMenu(ChatColor.RED+"Biblioteczki: &2"+bookshelfs, ItemMenu.Size.SIX_LINE, Main.getInstance());
        MenuItem bookcase = new MenuItem(ChatUtil.fixColors("&fx&4HC.PL"), new ItemStack(Material.BOOKSHELF), ChatUtil.fixColors("&7Aktywnych biblioteczek: &c" + bookshelfs));
        MenuItem fill = new MenuItem(ChatUtil.fixColors("&fx&4HC.PL"), new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)4));
        EnchantExtender prot4 = new EnchantExtender(ChatUtil.fixColors("&6Ochrona &aIV"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_ENVIRONMENTAL,4, 50, 25, bookshelfs,ChatUtil.fixColors("&7Koszt: &350 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &325"));
        EnchantExtender prot3 = new EnchantExtender(ChatUtil.fixColors("&6Ochrona &eIII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_ENVIRONMENTAL,3,30,15 ,bookshelfs,ChatUtil.fixColors("&7Koszt: &330 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &315"));
        EnchantExtender prot2 = new EnchantExtender(ChatUtil.fixColors("&6Ochrona &cII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_ENVIRONMENTAL,2, 20, 10, bookshelfs,ChatUtil.fixColors("&7Koszt: &320 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &310"));
        EnchantExtender prot1 =new EnchantExtender(ChatUtil.fixColors("&6Ochrona &4I"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_ENVIRONMENTAL,1,15,7,bookshelfs, ChatUtil.fixColors("&7Koszt: &315 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &37"));

        EnchantExtender fireprot4 = new EnchantExtender(ChatUtil.fixColors("&7Odpornosc na &cogien &aIV"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_FIRE,4,20,10,bookshelfs,ChatUtil.fixColors("&7Koszt: &320 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &310"));
        EnchantExtender fireprot3 = new EnchantExtender(ChatUtil.fixColors("&7Odpornosc na &cogien &eIII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_FIRE,3,15,7,bookshelfs,ChatUtil.fixColors("&7Koszt: &315 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &37"));
        EnchantExtender fireprot2 = new EnchantExtender(ChatUtil.fixColors("&7Odpornosc na &cogien &cII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_FIRE,2,10,5,bookshelfs,ChatUtil.fixColors("&7Koszt: &310 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &35"));
        EnchantExtender fireprot1 = new EnchantExtender(ChatUtil.fixColors("&7Odpornosc na &cogien &4I"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_FIRE,1,5,2,bookshelfs,ChatUtil.fixColors("&7Koszt: &35 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &32"));

        EnchantExtender boomprot4 = new EnchantExtender(ChatUtil.fixColors("&7Ochrona przed &5wybuchem &aIV"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_EXPLOSIONS,4,20,10,bookshelfs,ChatUtil.fixColors("&7Koszt: &320 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &310"));
        EnchantExtender boomprot3 = new EnchantExtender(ChatUtil.fixColors("&7Ochrona przed &5wybuchem &eIII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_EXPLOSIONS,3,15,7,bookshelfs,ChatUtil.fixColors("&7Koszt: &315 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &37"));
        EnchantExtender boomprot2 = new EnchantExtender(ChatUtil.fixColors("&7Ochrona przed &5wybuchem &cII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_EXPLOSIONS,2,10,5,bookshelfs,ChatUtil.fixColors("&7Koszt: &310 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &35"));
        EnchantExtender boomprot1 = new EnchantExtender(ChatUtil.fixColors("&7Ochrona przed &5wybuchem &4I"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_EXPLOSIONS,1,5,2,bookshelfs,ChatUtil.fixColors("&7Koszt: &35 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &32"));

        EnchantExtender projectileprot4 = new EnchantExtender(ChatUtil.fixColors("&7Ochrona przed &3pociskami &aIV"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_PROJECTILE,4,20,10,bookshelfs,ChatUtil.fixColors("&7Koszt: &320 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &310"));
        EnchantExtender projectileprot3 = new EnchantExtender(ChatUtil.fixColors("&7Ochrona przed &3pociskami &eIII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_PROJECTILE,3,15,7,bookshelfs,ChatUtil.fixColors("&7Koszt: &315 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &37"));
        EnchantExtender projectileprot2 = new EnchantExtender(ChatUtil.fixColors("&7Ochrona przed &3pociskami &cII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_PROJECTILE,2,10,5,bookshelfs,ChatUtil.fixColors("&7Koszt: &310 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &35"));
        EnchantExtender projectileprot1 = new EnchantExtender(ChatUtil.fixColors("&7Ochrona przed &3pociskami &4I"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_PROJECTILE,1,5,2,bookshelfs,ChatUtil.fixColors("&7Koszt: &35 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &32"));

        EnchantExtender featherfall4 = new EnchantExtender(ChatUtil.fixColors("&bPowolne opadanie IV"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_FALL,4,35,17,bookshelfs,ChatUtil.fixColors("&7Koszt: &335 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &317"));
        EnchantExtender featherfall3 = new EnchantExtender(ChatUtil.fixColors("&bPowolne opadanie III"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_FALL,3,30,15,bookshelfs,ChatUtil.fixColors("&7Koszt: &330 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &315"));
        EnchantExtender featherfall2 = new EnchantExtender(ChatUtil.fixColors("&bPowolne opadanie II"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_FALL,2,15,7,bookshelfs,ChatUtil.fixColors("&7Koszt: &320 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &310"));
        EnchantExtender featherfall1 = new EnchantExtender(ChatUtil.fixColors("&bPowolne opadanie I"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_FALL,1,10,5,bookshelfs,ChatUtil.fixColors("&7Koszt: &315 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &37"));

        EnchantExtender unb3 = new EnchantExtender(ChatUtil.fixColors("&aNiezniszczalnosc &aIII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DURABILITY,3,40,20,bookshelfs,ChatUtil.fixColors("&7Koszt: &340 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &320"));
        EnchantExtender unb2 = new EnchantExtender(ChatUtil.fixColors("&aNiezniszczalnosc &eII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DURABILITY,2,30,15,bookshelfs,ChatUtil.fixColors("&7Koszt: &330 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &315"));
        EnchantExtender unb1 = new EnchantExtender(ChatUtil.fixColors("&aNiezniszczalnosc &cI"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DURABILITY,1,20,10,bookshelfs,ChatUtil.fixColors("&7Koszt: &320 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &310"));

        EnchantExtender respiration3 = new EnchantExtender(ChatUtil.fixColors("&3Oddychanie &aIII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.OXYGEN,3,15,7,bookshelfs,ChatUtil.fixColors("&7Koszt: &315 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &37"));
        EnchantExtender respiration2 = new EnchantExtender(ChatUtil.fixColors("&3Oddychanie &eII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.OXYGEN,2,10,5,bookshelfs,ChatUtil.fixColors("&7Koszt: &310 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &35"));
        EnchantExtender respiration1 = new EnchantExtender(ChatUtil.fixColors("&3Oddychanie &cI"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.OXYGEN,1,5,2,bookshelfs,ChatUtil.fixColors("&7Koszt: &35 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &32"));

        EnchantExtender thorns3 = new EnchantExtender(ChatUtil.fixColors("&2Ciernie &aIII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.THORNS,3,25,12,bookshelfs,ChatUtil.fixColors("&7Koszt: &325 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &312"));
        EnchantExtender thorns2 = new EnchantExtender(ChatUtil.fixColors("&2Ciernie &eII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.THORNS,2,15,7,bookshelfs,ChatUtil.fixColors("&7Koszt: &315 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &37"));
        EnchantExtender thorns1 = new EnchantExtender(ChatUtil.fixColors("&2Ciernie &cI"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.THORNS,1,10,5,bookshelfs,ChatUtil.fixColors("&7Koszt: &310 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &35"));
        EnchantExtender aquafin1 = new EnchantExtender(ChatUtil.fixColors("&7Wydajnosc pod &bwoda &aI"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.WATER_WORKER,1,20,10,bookshelfs,ChatUtil.fixColors("&7Koszt: &310 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &35"));



        EnchantExtender eff5 = new EnchantExtender(ChatUtil.fixColors("&6Wydajnosc &aV"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DIG_SPEED,5,40,20,bookshelfs,ChatUtil.fixColors("&7Koszt: &340 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &320"));
        EnchantExtender eff4 = new EnchantExtender(ChatUtil.fixColors("&6Wydajnosc &2IV"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DIG_SPEED,4,30,15,bookshelfs,ChatUtil.fixColors("&7Koszt: &330 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &315"));
        EnchantExtender eff3 = new EnchantExtender(ChatUtil.fixColors("&6Wydajnosc &eIII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DIG_SPEED,3,20,10,bookshelfs,ChatUtil.fixColors("&7Koszt: &320 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &310"));
        EnchantExtender eff2 = new EnchantExtender(ChatUtil.fixColors("&6Wydajnosc &cII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DIG_SPEED,2,10,5,bookshelfs,ChatUtil.fixColors("&7Koszt: &310 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &35"));
        EnchantExtender eff1 = new EnchantExtender(ChatUtil.fixColors("&6Wydajnosc &4I"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DIG_SPEED,1,5,2,bookshelfs,ChatUtil.fixColors("&7Koszt: &35 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &32"));

        EnchantExtender fortune3 = new EnchantExtender(ChatUtil.fixColors("&3Szczescie &aIII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.LOOT_BONUS_BLOCKS,3,35,17,bookshelfs,ChatUtil.fixColors("&7Koszt: &335 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &317"));
        EnchantExtender fortune2 = new EnchantExtender(ChatUtil.fixColors("&3Szczescie &eII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.LOOT_BONUS_BLOCKS,2,25,12,bookshelfs,ChatUtil.fixColors("&7Koszt: &325 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &312"));
        EnchantExtender fortune1 = new EnchantExtender(ChatUtil.fixColors("&3Szczescie &cI"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.LOOT_BONUS_BLOCKS,1,15,7,bookshelfs,ChatUtil.fixColors("&7Koszt: &315 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &37"));

        EnchantExtender silk1 = new EnchantExtender(ChatUtil.fixColors("&5Jedwabny dotyk &aI"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.SILK_TOUCH,1,20,10,bookshelfs,ChatUtil.fixColors("&7Koszt: &320 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &310"));



        EnchantExtender sharp5 = new EnchantExtender(ChatUtil.fixColors("&6Ostrosc &aV"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_ALL,5,50,25,bookshelfs,ChatUtil.fixColors("&7Koszt: &350 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &325"));
        EnchantExtender sharp4 = new EnchantExtender(ChatUtil.fixColors("&6Ostrosc &2IV"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_ALL,4,40,20,bookshelfs,ChatUtil.fixColors("&7Koszt: &340 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &320"));
        EnchantExtender sharp3 = new EnchantExtender(ChatUtil.fixColors("&6Ostrosc &eIII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_ALL,3,30,15,bookshelfs,ChatUtil.fixColors("&7Koszt: &330 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &315"));
        EnchantExtender sharp2 = new EnchantExtender(ChatUtil.fixColors("&6Ostrosc &cII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_ALL,2,20,10,bookshelfs,ChatUtil.fixColors("&7Koszt: &320 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &310"));
        EnchantExtender sharp1 = new EnchantExtender(ChatUtil.fixColors("&6Ostrosc &4I"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_ALL,1,10,5,bookshelfs,ChatUtil.fixColors("&7Koszt: &310 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &35"));

        EnchantExtender smite5 = new EnchantExtender(ChatUtil.fixColors("&9Pogromca nieumarlych &aV"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_UNDEAD,5,30,15,bookshelfs,ChatUtil.fixColors("&7Koszt: &330 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &315"));
        EnchantExtender smite4 = new EnchantExtender(ChatUtil.fixColors("&9Pogromca nieumarlych &2IV"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_UNDEAD,4,25,12,bookshelfs,ChatUtil.fixColors("&7Koszt: &325 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &312"));
        EnchantExtender smite3 = new EnchantExtender(ChatUtil.fixColors("&9Pogromca nieumarlych &eIII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_UNDEAD,3,15,7,bookshelfs,ChatUtil.fixColors("&7Koszt: &315 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &37"));
        EnchantExtender smite2 = new EnchantExtender(ChatUtil.fixColors("&9Pogromca nieumarlych &cII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_UNDEAD,2,10,5,bookshelfs,ChatUtil.fixColors("&7Koszt: &310 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &35"));
        EnchantExtender smite1 = new EnchantExtender(ChatUtil.fixColors("&9Pogromca nieumarlych &4I"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_UNDEAD,1,5,2,bookshelfs,ChatUtil.fixColors("&7Koszt: &35 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &32"));

        EnchantExtender bane5 = new EnchantExtender(ChatUtil.fixColors("&5Zmora stawonogow &aV"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_ARTHROPODS,5,30,15,bookshelfs,ChatUtil.fixColors("&7Koszt: &330 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &315"));
        EnchantExtender bane4 = new EnchantExtender(ChatUtil.fixColors("&5Zmora stawonogow &2IV"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_ARTHROPODS,4,25,12,bookshelfs,ChatUtil.fixColors("&7Koszt: &325 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &312"));
        EnchantExtender bane3 = new EnchantExtender(ChatUtil.fixColors("&5Zmora stawonogow &eIII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_ARTHROPODS,3,15,7,bookshelfs,ChatUtil.fixColors("&7Koszt: &315 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &37"));
        EnchantExtender bane2 = new EnchantExtender(ChatUtil.fixColors("&5Zmora stawonogow &cII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_ARTHROPODS,2,10,5,bookshelfs,ChatUtil.fixColors("&7Koszt: &310 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &35"));
        EnchantExtender bane1 = new EnchantExtender(ChatUtil.fixColors("&5Zmora stawonogow &4I"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_ARTHROPODS,1,5,2,bookshelfs,ChatUtil.fixColors("&7Koszt: &35 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &32"));

        EnchantExtender looting3 = new EnchantExtender(ChatUtil.fixColors("&3Grabierz &aIII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.LOOT_BONUS_MOBS,3,35,17,bookshelfs,ChatUtil.fixColors("&7Koszt: &335 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &317"));
        EnchantExtender looting2 = new EnchantExtender(ChatUtil.fixColors("&3Grabierz &eII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.LOOT_BONUS_MOBS,2,25,12,bookshelfs,ChatUtil.fixColors("&7Koszt: &325 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &312"));
        EnchantExtender looting1 = new EnchantExtender(ChatUtil.fixColors("&3Grabierz &cI"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.LOOT_BONUS_MOBS,1,15,7,bookshelfs,ChatUtil.fixColors("&7Koszt: &315 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &37"));

        EnchantExtender fireasp2 = new EnchantExtender(ChatUtil.fixColors("&cZaklety ogien &aII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.FIRE_ASPECT,2,40,20,bookshelfs,ChatUtil.fixColors("&7Koszt: &340 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &320"));
        EnchantExtender fireasp1 = new EnchantExtender(ChatUtil.fixColors("&cZaklety ogien &eI"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.FIRE_ASPECT,1,20,10,bookshelfs,ChatUtil.fixColors("&7Koszt: &320 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &310"));

        EnchantExtender knock2 = new EnchantExtender(ChatUtil.fixColors("&7Odrzut &aII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.KNOCKBACK,2,30,15,bookshelfs,ChatUtil.fixColors("&7Koszt: &330 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &315"));
        EnchantExtender knock1 = new EnchantExtender(ChatUtil.fixColors("&7Odrzut &eI"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.KNOCKBACK,1,15,7,bookshelfs,ChatUtil.fixColors("&7Koszt: &315 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &37"));


        EnchantExtender power6 = new EnchantExtender(ChatUtil.fixColors("&6Moc &4VI"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.ARROW_DAMAGE,6,60,30,bookshelfs,ChatUtil.fixColors("&7Koszt: &360 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &330"));
        EnchantExtender power5 = new EnchantExtender(ChatUtil.fixColors("&6Moc &aV"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.ARROW_DAMAGE,5,50,25,bookshelfs,ChatUtil.fixColors("&7Koszt: &350 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &325"));
        EnchantExtender power4 = new EnchantExtender(ChatUtil.fixColors("&6Moc &2IV"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.ARROW_DAMAGE,4,40,20,bookshelfs,ChatUtil.fixColors("&7Koszt: &340 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &320"));
        EnchantExtender power3 = new EnchantExtender(ChatUtil.fixColors("&6Moc &eIII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.ARROW_DAMAGE,3,30,15,bookshelfs,ChatUtil.fixColors("&7Koszt: &330 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &315"));
        EnchantExtender power2 = new EnchantExtender(ChatUtil.fixColors("&6Moc &cII"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.ARROW_DAMAGE,2,20,10,bookshelfs,ChatUtil.fixColors("&7Koszt: &320 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &310"));
        EnchantExtender power1 = new EnchantExtender(ChatUtil.fixColors("&6Moc &4I"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.ARROW_DAMAGE,1,10,5,bookshelfs,ChatUtil.fixColors("&7Koszt: &310 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &35"));

        EnchantExtender infinity1 = new EnchantExtender(ChatUtil.fixColors("&9Nieskonczonosc &4I"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.ARROW_INFINITE,1,20,15,bookshelfs,ChatUtil.fixColors("&7Koszt: &320 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &315"));

        EnchantExtender plomien3 = new EnchantExtender(ChatUtil.fixColors("&cPlomien &4III"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.ARROW_FIRE,3,30,25,bookshelfs,ChatUtil.fixColors("&7Koszt: &330 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &325"));
        EnchantExtender plomien2 = new EnchantExtender(ChatUtil.fixColors("&9Plomien &4II"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.ARROW_FIRE,2,20,20,bookshelfs,ChatUtil.fixColors("&7Koszt: &320 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &320"));
        EnchantExtender plomien1 = new EnchantExtender(ChatUtil.fixColors("&9Plomien &4I"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.ARROW_FIRE,1,10,15,bookshelfs,ChatUtil.fixColors("&7Koszt: &310 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &315"));

        EnchantExtender punch3 = new EnchantExtender(ChatUtil.fixColors("&3Odrzut &4III"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.ARROW_KNOCKBACK,3,30,25,bookshelfs,ChatUtil.fixColors("&7Koszt: &330 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &325"));
        EnchantExtender punch2 = new EnchantExtender(ChatUtil.fixColors("&3Odrzut &4II"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.ARROW_KNOCKBACK,2,20,20,bookshelfs,ChatUtil.fixColors("&7Koszt: &320 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &320"));
        EnchantExtender punch1 = new EnchantExtender(ChatUtil.fixColors("&3Odrzut &4I"),new ItemStack(Material.ENCHANTED_BOOK), Enchantment.ARROW_KNOCKBACK,1,10,15,bookshelfs,ChatUtil.fixColors("&7Koszt: &310 LvL"), ChatUtil.fixColors("&7Ilosc wymaganych biblioteczek: &315"));

        for (int i = 0 ; i < 9 ; i++) {
            enchantMenu.setItem(i, bookcase);
            enchantMenu.setItem(i+45, bookcase);
        }
        if (getEnchantmentPartTypeForItemStack(item).equals(EnchantType.ARMOR)) {
            enchantMenu.setItem(9, prot4);
            enchantMenu.setItem(10, prot3);
            enchantMenu.setItem(11, prot2);
            enchantMenu.setItem(12, prot1);

            enchantMenu.setItem(18, fireprot4);
            enchantMenu.setItem(19, fireprot3);
            enchantMenu.setItem(20, fireprot2);
            enchantMenu.setItem(21, fireprot1);

            enchantMenu.setItem(27, boomprot4);
            enchantMenu.setItem(28, boomprot3);
            enchantMenu.setItem(29, boomprot2);
            enchantMenu.setItem(30, boomprot1);

            enchantMenu.setItem(36, projectileprot4);
            enchantMenu.setItem(37, projectileprot3);
            enchantMenu.setItem(38, projectileprot2);
            enchantMenu.setItem(39, projectileprot1);

            if (getEnchantTypeForItemStack(item) == EnchantType.BOOTS) {
                enchantMenu.setItem(14, featherfall4);
                enchantMenu.setItem(15, featherfall3);
                enchantMenu.setItem(16, featherfall2);
                enchantMenu.setItem(17, featherfall1);

                enchantMenu.setItem(24, unb3);
                enchantMenu.setItem(25, unb2);
                enchantMenu.setItem(26, unb1);

                enchantMenu.setItem(33, thorns3);
                enchantMenu.setItem(34, thorns2);
                enchantMenu.setItem(35, thorns1);
            } else {
                enchantMenu.setItem(15, unb3);
                enchantMenu.setItem(16, unb2);
                enchantMenu.setItem(17, unb1);

                enchantMenu.setItem(33, thorns3);
                enchantMenu.setItem(34, thorns2);
                enchantMenu.setItem(35, thorns1);
            }

            if (getEnchantTypeForItemStack(item) == EnchantType.HEAD) {
                enchantMenu.setItem(24, respiration3);
                enchantMenu.setItem(25, respiration2);
                enchantMenu.setItem(26, respiration1);

                enchantMenu.setItem(44, aquafin1);
            }
        } else if (getEnchantmentPartTypeForItemStack(item) == EnchantType.TOOL) {
            enchantMenu.setItem(9, eff5);
            enchantMenu.setItem(10, eff4);
            enchantMenu.setItem(11, eff3);
            enchantMenu.setItem(12, eff2);
            enchantMenu.setItem(13, eff1);

            enchantMenu.setItem(18, unb3);
            enchantMenu.setItem(19, unb2);
            enchantMenu.setItem(20, unb1);

            enchantMenu.setItem(27, fortune3);
            enchantMenu.setItem(28, fortune2);
            enchantMenu.setItem(29, fortune1);

            enchantMenu.setItem(44, silk1);

        }else if (getEnchantTypeForItemStack(item) == EnchantType.SWORD){
            enchantMenu.setItem(9, sharp5);
            enchantMenu.setItem(10, sharp4);
            enchantMenu.setItem(11, sharp3);
            enchantMenu.setItem(12, sharp2);
            enchantMenu.setItem(13, sharp1);

            enchantMenu.setItem(18, smite5);
            enchantMenu.setItem(19, smite4);
            enchantMenu.setItem(20, smite3);
            enchantMenu.setItem(21, smite2);
            enchantMenu.setItem(22, smite1);

            enchantMenu.setItem(27, bane5);
            enchantMenu.setItem(28, bane4);
            enchantMenu.setItem(29, bane3);
            enchantMenu.setItem(30, bane2);
            enchantMenu.setItem(31, bane1);

            enchantMenu.setItem(15, looting3);
            enchantMenu.setItem(16, looting2);
            enchantMenu.setItem(17, looting1);

            enchantMenu.setItem(24, unb3);
            enchantMenu.setItem(25, unb2);
            enchantMenu.setItem(26, unb1);

            enchantMenu.setItem(36, fireasp2);
            enchantMenu.setItem(37, fireasp1);

            enchantMenu.setItem(39, knock2);
            enchantMenu.setItem(40, knock1);
        }else if (getEnchantTypeForItemStack(item) == EnchantType.BOW){
            enchantMenu.setItem(9, power6);
            enchantMenu.setItem(10, power5);
            enchantMenu.setItem(11, power4);
            enchantMenu.setItem(12, power3);
            enchantMenu.setItem(13, power2);
            enchantMenu.setItem(14, power1);

            enchantMenu.setItem(18, plomien3);
            enchantMenu.setItem(19, plomien2);
            enchantMenu.setItem(20, plomien1);

            enchantMenu.setItem(27, punch3);
            enchantMenu.setItem(28, punch2);
            enchantMenu.setItem(29, punch1);

            enchantMenu.setItem(36, unb3);
            enchantMenu.setItem(37, unb2);
            enchantMenu.setItem(38, unb1);

            enchantMenu.setItem(44, infinity1);
        }else{
            p.sendMessage(ChatUtil.fixColors("&cNie mozesz zaklac tego przedmiotu !"));
            return;
        }
        enchantMenu.fillEmptySlots(fill);
        enchantMenu.open(p);
    }

    private static EnchantType getEnchantmentPartTypeForItemStack(ItemStack item) {
        //TESTOWO
        switch (item.getType()) {
            case LEATHER_HELMET:
            case IRON_HELMET:
            case GOLD_HELMET:
            case DIAMOND_HELMET:
            case LEATHER_CHESTPLATE:
            case IRON_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
            case LEATHER_LEGGINGS:
            case IRON_LEGGINGS:
            case GOLD_LEGGINGS:
            case DIAMOND_LEGGINGS:
            case LEATHER_BOOTS:
            case IRON_BOOTS:
            case GOLD_BOOTS:
            case DIAMOND_BOOTS:
                return EnchantType.ARMOR;
            case WOOD_PICKAXE:
            case STONE_PICKAXE:
            case IRON_PICKAXE:
            case GOLD_PICKAXE:
            case DIAMOND_PICKAXE:
            case WOOD_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case GOLD_AXE:
            case DIAMOND_AXE:
            case WOOD_SPADE:
            case STONE_SPADE:
            case IRON_SPADE:
            case GOLD_SPADE:
            case DIAMOND_SPADE:
                return EnchantType.TOOL;
        }
        return EnchantType.OTHER;
    }

    public static void enchantInteract(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {
            if (e.getPlayer().getItemInHand() == null) {
                e.getPlayer().sendMessage(ChatUtil.fixColors("&cAby zaklac przedmiot wez go do reki i kliknij ponownie na stol do zaklinania !"));
                return;
            }
            int bookshelfs = 0;
            e.setCancelled(true);
            for (Location loc : SpaceUtil.getWalls(e.getClickedBlock().getLocation(), 2, 2)) {
                if (loc.getBlock().getType() == Material.BOOKSHELF) {
                    bookshelfs++;
                }
            }
            TestEnchantMenu.openMenu(e.getPlayer(), bookshelfs, e.getItem());
            return;
        }
    }

    private static EnchantType getEnchantTypeForItemStack(ItemStack item) {
        //TESTOWO
        switch (item.getType()) {
            case LEATHER_HELMET:
            case IRON_HELMET:
            case GOLD_HELMET:
            case DIAMOND_HELMET:
                return EnchantType.HEAD;
            case LEATHER_CHESTPLATE:
            case IRON_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
                return EnchantType.CHEST;
            case LEATHER_LEGGINGS:
            case IRON_LEGGINGS:
            case GOLD_LEGGINGS:
            case DIAMOND_LEGGINGS:
                return EnchantType.LEGS;
            case LEATHER_BOOTS:
            case IRON_BOOTS:
            case GOLD_BOOTS:
            case DIAMOND_BOOTS:
                return EnchantType.BOOTS;
            case WOOD_SWORD:
            case STONE_SWORD:
            case IRON_SWORD:
            case GOLD_SWORD:
            case DIAMOND_SWORD:
                return EnchantType.SWORD;
            case WOOD_PICKAXE:
            case STONE_PICKAXE:
            case IRON_PICKAXE:
            case GOLD_PICKAXE:
            case DIAMOND_PICKAXE:
                return EnchantType.PICKAXE;
            case BOW:
                return EnchantType.BOW;
        }
        return EnchantType.OTHER;
    }

    public enum EnchantType {
        HEAD, CHEST, LEGS, BOOTS, SWORD, PICKAXE, BOW, OTHER, ARMOR, TOOL
    }
}
