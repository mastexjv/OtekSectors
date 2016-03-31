package pl.otekplay.sectors.data;

import lombok.Data;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.ChatUtil;

import java.util.Arrays;

/**
 * Created by Oskar on 16.02.2016.
 */
@Data
public class Kit {
    private final int ID;
    private final String name, itemName;
    private final String[] itemLore;
    private final ItemStack[] items;


    public Kit(int ID,String name,String itemName,String[] itemLore,ItemStack[] items){
        this.ID = ID;
        this.name = name;
        this.itemName = itemName;
        this.itemLore = itemLore;
        this.items = items;
        init();
    }

    private void init(){
        for(ItemStack item:items){
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            meta.setLore(Arrays.asList(itemLore));
            item.setItemMeta(meta);
        }
    }
    public void equip(Player p){
        PlayerInventory inv = p.getInventory();
        for(ItemStack item:items){
            inv.addItem(item);
        }
        p.sendMessage(ChatUtil.fixColors(Settings.PREFIX+ ChatColor.GREEN+"Otrzymales zestaw "+ChatColor.RED+name));
    }
}
