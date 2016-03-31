package ninja.amp.ampmenus.extenders;

import ninja.amp.ampmenus.events.ItemClickEvent;
import ninja.amp.ampmenus.items.MenuItem;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.otekplay.sectors.menus.TestEnchantMenu;
import pl.otekplay.sectors.utils.ChatUtil;

/**
 * Created by Administrator on 2/16/2016.
 */
public class EnchantExtender extends MenuItem {
    private int levelEnchant, levelCost, needBooks, readyBooks;
    private Enchantment enchant;
    public EnchantExtender(String displayName, ItemStack icon, Enchantment enchant, int levelEnchant, int levelCost, int needBooks, int readyBooks, String... lore) {
        super(displayName, icon, lore);
        this.enchant = enchant;
        this.levelEnchant = levelEnchant;
        this.levelCost = levelCost;
        this.needBooks = needBooks;
        this.readyBooks = readyBooks;
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();
        if (item == null) {
            player.sendMessage(ChatUtil.fixColors("&cW rece musisz miec przedmiot, ktory chesz zaklac !"));
            return;
        }
        if (readyBooks < needBooks) {
            player.sendMessage(ChatUtil.fixColors("&cNie wystarczajaca ilosc biblioteczek otacza &5stol do zaklinania&7. &7Aby to zrobic potrzebujesz jeszcze: &3" + (needBooks - readyBooks) + " &7biblioteczek wokol stolu"));
            return;
        }
        if (player.getLevel() < levelCost && !player.getGameMode().equals(GameMode.CREATIVE)) {
            player.sendMessage(ChatUtil.fixColors("&7Musisz miec &3" + levelCost + " poziom EXP &7aby zaklac ten przedmiot."));
            return;
        }
        if (!TestEnchantMenu.isAllowedEnchant(item, enchant)) {
            player.sendMessage(ChatUtil.fixColors("&cNie mozesz juz dodac tego zaklecia !"));
            return;
        }
        if (item.containsEnchantment(enchant)) {
            if (item.getEnchantmentLevel(enchant) >= levelEnchant) {
                player.sendMessage(ChatUtil.fixColors("&cTen przedmiot posiada juz to zaklecie na wyzszym badz rownym poziomie!"));
                return;
            }
        }
        item.addUnsafeEnchantment(enchant, levelEnchant);
        player.sendMessage(ChatUtil.fixColors("&aGratulacje &7zaczarowales przedmiot !"));
        if (player.getGameMode() == GameMode.CREATIVE) {
            player.sendMessage(ChatUtil.fixColors("&3poziom EXP&7 zostal pobrany z trybu gry: &3KREATYWNY"));
        } else {
            player.setLevel(player.getLevel() - levelCost);
        }
    }
}
