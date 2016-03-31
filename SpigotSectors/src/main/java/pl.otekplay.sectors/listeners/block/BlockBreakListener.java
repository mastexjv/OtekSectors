package pl.otekplay.sectors.listeners.block;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import pl.otekplay.sectors.managers.DropManager;
import pl.otekplay.sectors.managers.GeneratorManager;
import pl.otekplay.sectors.managers.GuildManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.CobblexUtil;
import pl.otekplay.sectors.utils.LocationUtil;
import pl.otekplay.sectors.utils.RandomUtil;

public class BlockBreakListener implements Listener {

    private static ItemStack[] is = new ItemStack[]{new ItemStack(Material.DIAMOND, 20),new ItemStack(Material.DIRT, RandomUtil.getRandInt(1, 64)),new ItemStack(Material.STRING, RandomUtil.getRandInt(5, 22)), new ItemStack(Material.GOLDEN_APPLE, RandomUtil.getRandInt(1, 10)), new ItemStack(Material.TNT, 20), new ItemStack(Material.GOLD_BLOCK, 8), new ItemStack(Material.STONE, RandomUtil.getRandInt(1, 64)), new ItemStack(Material.BONE, 24), new ItemStack(Material.CARROT_ITEM, RandomUtil.getRandInt(1, 12)), new ItemStack(Material.GOLDEN_APPLE, RandomUtil.getRandInt(2, 3), (short) 1)};

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        Location loc = b.getLocation();
        if (!GuildManager.canTouch(p, b.getLocation())) {
            e.setCancelled(true);
            return;
        }
        if (LocationUtil.getDistanceFromBorder(loc) < 50) {
            p.sendMessage(Settings.SECTOR_ERROR_BREAK);
            e.setCancelled(true);
            return;
        }
        if (b.getType() == Material.DRAGON_EGG) {
            e.setCancelled(true);
            return;
        }
        GeneratorManager.checkGenerator(e);
        if(DropManager.isBlockedMaterial(b.getType())){
            b.breakNaturally(new ItemStack(Material.AIR));
            e.setCancelled(true);
            return;
        }
        if(b.getType().equals(Material.STONE)) {
            DropManager.checkDrop(e);

        } else if (b.getType().equals(Material.MOSSY_COBBLESTONE)) {
            if (CobblexUtil.getPlacedCobblex().contains(loc)) {
                CobblexUtil.getPlacedCobblex().remove(loc);
                b.breakNaturally(new ItemStack(Material.AIR));
                loc.getWorld().strikeLightningEffect(loc);
                Item item = loc.getWorld().dropItem(loc, is[RandomUtil.getRandInt(0, is.length-1)]);
                item.setVelocity(new Vector(0,1,0));
            }
        }
    }

}
