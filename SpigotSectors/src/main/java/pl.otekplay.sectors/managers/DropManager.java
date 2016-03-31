package pl.otekplay.sectors.managers;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.data.Drop;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.packets.impl.user.UserDropStatsPacket;
import pl.otekplay.sectors.utils.ChatUtil;
import pl.otekplay.sectors.utils.DropUtil;
import pl.otekplay.sectors.utils.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DropManager {
    @Getter
    private static final List<Drop> drops = new ArrayList<>();
    private static final List<Material> blockedOres = new ArrayList<>(Arrays.asList(Material.REDSTONE_ORE, Material.COAL_ORE, Material.LAPIS_ORE, Material.IRON_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.GOLD_ORE));
    private static final ItemStack itemAir = new ItemStack(Material.AIR);
    private static final ItemStack itemCobbleStone = new ItemStack(Material.COBBLESTONE);


    public static void registerDrop(String name, double chance, int height, int exp, boolean fortune, ItemStack item) {
        drops.add(new Drop(name, chance, height, exp, fortune, item));
    }
    public static boolean isBlockedMaterial(Material mat){
        return blockedOres.contains(mat);
    }

    public static Drop getDrop(String name) {
        for (Drop drop : drops) {
            if (drop.getName().equalsIgnoreCase(name)) {
                return drop;
            }
        }
        return null;
    }

    public static boolean isDrop(String name) {
        return getDrop(name) != null;
    }


    public static int getAmountOfDropWithFortune(ItemStack itemStack) {
        if (itemStack == null) {
            return 1;
        }
        if (!itemStack.hasItemMeta()) {
            return 1;
        }
        if (!itemStack.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS)) {
            return 1;
        }
        int lvl = itemStack.getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS);
        return RandomUtil.getRandInt(1, lvl+1);
    }



    public static void checkDrop(BlockBreakEvent e) {
        User user = UserManager.getUser(e.getPlayer().getUniqueId());
        if (e.getBlock().getType().equals(Material.OBSIDIAN)) {
            e.getPlayer().giveExp(19);
            return;
        }
        if (blockedOres.contains(e.getBlock().getType())) {
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            return;
        }
        if (!e.getBlock().getType().equals(Material.STONE)) {
            return;
        }
        e.getPlayer().giveExp(7);
        DropUtil.checkLevel(e.getPlayer());
      //  if (!DropUtil.isBlocked(e.getPlayer(), )) {
    //        e.getPlayer().getInventory().addItem(new ItemStack(Material.COBBLESTONE));
     //   } todo: blokowanie cobble i tych msg, jutro ogarnac.
        e.setCancelled(true);
        e.getBlock().setType(Material.AIR);
        for (Drop drop : drops) {
            if (e.getBlock().getLocation().getY() > drop.getHeight()) {
                continue;
            }
            double chance = drop.getChance();
            if (user.can(GroupType.VIP)) {
                chance = chance * (1 + 0.3);
            }
            if (RandomUtil.getChance(chance)) {
                ItemStack item = drop.getItemStack().clone();
                item.setAmount(DropManager.getAmountOfDropWithFortune(e.getPlayer().getItemInHand()));
                int exp = item.getAmount() * drop.getExp();
                user.setExp(user.getExp() + exp);
               // if (!DropManager.isBlocked("messages", e.getPlayer())) {
               //     e.getPlayer().sendMessage(ChatUtil.fixColor("&7Trafiles na: &c" + drop.getName() + " &7x&c" + item.getAmount() + "&7. &7Exp &c+" + exp + "&7."));
               // } todo: wiadomosci kurwa blokowac jebany cwelu zrob to drop, put fake drop wagonik i stone
                if (DropUtil.isBlocked(drop, e.getPlayer())) {
                    return;
                }
                e.getPlayer().getInventory().addItem(item);
                return;
            }
        }
    }

    public static void loadDrops() {
        registerDrop(ChatUtil.fixColors("&6Diament"), 1.5, 35, 25, true, new ItemStack(Material.DIAMOND));
        registerDrop(ChatUtil.fixColors("&6Wegiel"), 3, 65, 6,true, new ItemStack(Material.COAL));
        registerDrop(ChatUtil.fixColors("&6Zloto"), 2.0, 35, 9,true, new ItemStack(Material.GOLD_INGOT));
        registerDrop(ChatUtil.fixColors("&6Zelazo"), 2.5, 65,7 ,true, new ItemStack(Material.IRON_INGOT));
        registerDrop(ChatUtil.fixColors("&6Ksiazka"), 0.2, 35,5, true, new ItemStack(Material.BOOK));
        registerDrop(ChatUtil.fixColors("&6Perla"), 0.05, 35,400, false, new ItemStack(Material.ENDER_PEARL));
        registerDrop(ChatUtil.fixColors("&6Lapis"), 2, 65, 5, true, new ItemStack(Material.INK_SACK, 1, (short)4));
        registerDrop(ChatUtil.fixColors("&6Nic"), 0.25, 65,25, true, new ItemStack(Material.STRING));
        registerDrop(ChatUtil.fixColors("&6Redstone"), 0.7, 65 ,5, true , new ItemStack(Material.REDSTONE));
        registerDrop(ChatUtil.fixColors("&6Emerald"), 1.1, 65 ,20, true , new ItemStack(Material.EMERALD));
        registerDrop(ChatUtil.fixColors("&6Proch"), 0.5, 65 ,15, true , new ItemStack(Material.SULPHUR));
        registerDrop(ChatUtil.fixColors("&6Piasek"), 1.4, 65 ,5, true , new ItemStack(Material.SAND));
        registerDrop(ChatUtil.fixColors("&6Strzala"), 0.5, 35 ,15, false , new ItemStack(Material.ARROW));
        registerDrop(ChatUtil.fixColors("&6Slime"), 1, 35,15, true, new ItemStack(Material.SLIME_BALL));
    }
}
