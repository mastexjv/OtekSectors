package pl.otekplay.sectors.netty;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import pl.otekplay.sectors.Main;
import pl.otekplay.sectors.data.Guild;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.DropManager;
import pl.otekplay.sectors.managers.GuildManager;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.menus.DropMenu;
import pl.otekplay.sectors.menus.PotionShopMenu;
import pl.otekplay.sectors.packets.PacketHandler;
import pl.otekplay.sectors.packets.impl.guild.*;
import pl.otekplay.sectors.packets.impl.player.*;
import pl.otekplay.sectors.packets.impl.sector.SectorSendCommandPacket;
import pl.otekplay.sectors.packets.impl.user.*;
import pl.otekplay.sectors.task.*;
import pl.otekplay.sectors.utils.LocationUtil;
import pl.otekplay.sectors.utils.SerializationUtil;

import java.util.UUID;

public class InitHandler extends PacketHandler {

    private SectorClient client;

    @Override
    public void handle(UserRegisterPacket packet) {
        UUID uuid = UUID.fromString(packet.getStringUUID());
        String name = packet.getName();
        UserManager.createUser(uuid, name, packet.getGroupName());
    }

    @Override
    public void handle(PlayerTeleportLocationPacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidString());
        Location loc = LocationUtil.locFromString(packet.getLocationString());
        PlayerTeleportLocationTask.start(uuid, loc);
    }

    @Override
    public void handle(PlayerTeleportPlayerPacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidPlayer());
        UUID teleport = UUID.fromString(packet.getUuidTeleporter());
        PlayerTeleportPlayerTask.start(uuid, teleport);

    }

    @Override
    public void handle(PlayerTeleportRequestPacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidString());
        RequestTeleportTask.start(uuid, packet.getTime(), packet.getAction(), packet.getTeleportString());
    }

    @Override
    public void handle(PlayerStatsUpdatePacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidString());
        User user = UserManager.getUser(uuid);
        Player p = user.getPlayer();
        p.setSprinting(packet.isSprint());
        p.setHealth(packet.getHealth());
        p.setFoodLevel(packet.getFood());
        p.setTotalExperience(packet.getExp());
        p.getActivePotionEffects().clear();
        for(PotionEffect ef:SerializationUtil.deserializePotionEffects(packet.getPotions())){
            p.addPotionEffect(ef,true);
        }
        p.setGameMode(GameMode.valueOf(packet.getGamemode()));
    }

    @Override
    public void handle(PlayerInventoryUpdatePacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidString());
        User user = UserManager.getUser(uuid);
        Player p = user.getPlayer();
        p.getInventory().setHeldItemSlot(packet.getHeldSlot());
        p.getInventory().setContents(SerializationUtil.itemStackArrayFromBase64(packet.getInventory()));
        p.getInventory().setArmorContents(SerializationUtil.itemStackArrayFromBase64(packet.getArmor()));
        p.getEnderChest().setContents(SerializationUtil.itemStackArrayFromBase64(packet.getEnderchest()));

    }

    @Override
    public void handle(GuildCreatePacket packet) {
        GuildManager.createGuild(packet.getTag(), packet.getName(), UserManager.getUser(UUID.fromString(packet.getLeaderUUID())), packet.getLocation());
    }

    @Override
    public void handle(GuildAddMemberPacket packet) {
        Guild guild = GuildManager.getGuildByTag(packet.getTagGuild());
        User user = UserManager.getUser(UUID.fromString(packet.getMemberUUID()));
        GuildManager.addUserToGuild(user, guild);
    }

    @Override
    public void handle(GuildRemoveMemberPacket packet) {
        User user = UserManager.getUser(UUID.fromString(packet.getMemberUUID()));
        GuildManager.removeUserFromGuild(user);
    }

    @Override
    public void handle(GuildRemovePacket packet) {
        GuildManager.removeGuild(GuildManager.getGuildByTag(packet.getTagGuild()));
    }

    @Override
    public void handle(GuildRequestItemsPacket packet) {
        User user = UserManager.getUser(UUID.fromString(packet.getUuidString()));
        if (user.isOnline()) {
            Player p = user.getPlayer();
            if (p.getInventory().contains(Material.DIRT, 1)) {
                p.getInventory().removeItem(new ItemStack(Material.DIRT, 1));
                Main.getInstance().getClient().sendPacket(new GuildAnswerItemsPacket(packet.getUuidString(), packet.getTag(), packet.getName(), packet.getLocation(), true));
            } else {
                Main.getInstance().getClient().sendPacket(new GuildAnswerItemsPacket(packet.getUuidString(), packet.getTag(), packet.getName(), packet.getLocation(), false));
            }
        }
    }

    @Override
    public void handle(GuildRequestLocationPacket packet) {
        User user = UserManager.getUser(UUID.fromString(packet.getUuidString()));
        if (user.isOnline()) {
            Player p = user.getPlayer();
            Main.getInstance().getClient().sendPacket(new GuildAnswerLocationPacket(packet.getUuidString(), packet.getTag(), packet.getName(), LocationUtil.locToString(p.getLocation()), GuildManager.canCreateGuild(p.getLocation())));
        } else {
            Main.getInstance().getClient().sendPacket(new GuildAnswerLocationPacket(packet.getUuidString(), packet.getTag(), packet.getName(), "NULL", 1));
        }
    }

    @Override
    public void handle(GuildSetAllyPacket packet) {
        Guild g1 = GuildManager.getGuildByTag(packet.getG1Tag());
        Guild g2 = GuildManager.getGuildByTag(packet.getG2Tag());
        GuildManager.setAllyGuild(g1, g2);
    }

    @Override
    public void handle(GuildSetEnemyPacket packet) {
        Guild g1 = GuildManager.getGuildByTag(packet.getG1Tag());
        Guild g2 = GuildManager.getGuildByTag(packet.getG2Tag());
        GuildManager.setEnemyGuild(g1, g2);
    }

    @Override
    public void handle(GuildSwitchPvPPacket packet) {
        Guild guild = GuildManager.getGuildByTag(packet.getGuildTag());
        guild.setPvpInGuild(packet.isPvp());
    }

    @Override
    public void handle(GuildNewLeaderPacket packet) {
        Guild guild = GuildManager.getGuildByTag(packet.getTagGuild());
        User user = UserManager.getUser(UUID.fromString(packet.getLeaderUUID()));
        GuildManager.setNewLeader(guild, user);
    }

    @Override
    public void handle(PlayerSetHomeRequestPacket packet) {
        User user = UserManager.getUser(UUID.fromString(packet.getUuidString()));
        int action = 0;
        Location loc = Bukkit.getWorlds().get(0).getSpawnLocation();
        if (user.isOnline()) {
            Player p = user.getPlayer();
            loc = p.getLocation();
            Guild g = GuildManager.getGuildByLocation(p.getLocation());
            if (g != null) {
                if (user.hasGuild()) {
                    if (!user.getGuild().equals(g) && !user.getGuild().getAlly().contains(g)) {
                        action = 1;
                    }
                } else {
                    action = 1;
                }
            }
        }
        Main.getInstance().getClient().sendPacket(new PlayerSetHomeAnswerPacket(user.getUuid().toString(), LocationUtil.locToString(loc), action));
    }

    @Override
    public void handle(GuildSetHomeRequestPacket packet) {
        User user = UserManager.getUser(UUID.fromString(packet.getUuidString()));
        if (user.isOnline()) {
            Player p = user.getPlayer();
            Guild guild = user.getGuild();
            if (guild.isCub(p.getLocation())) {
                Main.getInstance().getClient().sendPacket(new GuildSetHomeAnswerPacket(packet.getUuidString(), LocationUtil.locToString(p.getLocation()), 0));
            } else {
                Main.getInstance().getClient().sendPacket(new GuildSetHomeAnswerPacket(packet.getUuidString(), LocationUtil.locToString(p.getLocation()), 1));
            }
        }
    }

    @Override
    public void handle(GuildReSizeRequestItemsPacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidString());
        User user = UserManager.getUser(uuid);
        Guild guild = user.getGuild();
        if (user.isOnline()) {
            Player p = user.getPlayer();
            int sizeLevel = guild.getLocation().getSize() - 25;
            sizeLevel = sizeLevel / 5;
            sizeLevel--;
            int items = sizeLevel * 8;
            int needItems = 32 + items;
            ItemStack item = new ItemStack(Material.DIAMOND_BLOCK, needItems);
            if (p.getInventory().contains(item)) {
                p.getInventory().removeItem(item);
                Main.getInstance().getClient().sendPacket(new GuildReSizeAnswerItemsPacket(p.getUniqueId().toString(), true));
            } else {
                Main.getInstance().getClient().sendPacket(new GuildReSizeAnswerItemsPacket(p.getUniqueId().toString(), false));

            }
        }
    }

    @Override
    public void handle(GuildChangeSizePacket packet) {
        Guild guild = GuildManager.getGuildByTag(packet.getTagGuild());
        guild.getLocation().setSize(packet.getSizeGuild());
    }

    private static final ItemStack renewStack = new ItemStack(Material.DIAMOND_BLOCK);

    @Override
    public void handle(GuildRenewRequestPacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidString());
        User user = UserManager.getUser(uuid);
        if (user.isOnline()) {
            Player p = user.getPlayer();
            int action = 1;
            if (p.getInventory().contains(renewStack)) {
                p.getInventory().removeItem(renewStack);
                action = 0;
            }
            Main.getInstance().getClient().sendPacket(new GuildRenewAnswerPacket(packet.getUuidString(), action));
        }
    }

    @Override
    public void handle(SectorSendCommandPacket packet) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), packet.getCommand());
    }

    @Override
    public void handle(UserOpenMenuPacket packet) {
        User user = UserManager.getUser(UUID.fromString(packet.getUuidString()));
        if (user.isOnline()) {
            if (packet.getId() == 1) {
                DropMenu.openInventory(user.getPlayer());
            } else if (packet.getId() == 2) {
                PotionShopMenu.getMenu().open(user.getPlayer());
            }
        }
    }

    @Override
    public void handle(PlayerTeleportRandomPacket packet) {
        PlayerTeleportRandomTask.start(UUID.fromString(packet.getUuidPlayer()));
    }

    @Override
    public void handle(UserGiveKitPacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidString());
        PlayerGiveKitTask.start(uuid, packet.getId());
    }

    @Override
    public void handle(UserGroupUpdatePacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidString());
        User user = UserManager.getUser(uuid);
        GroupType type = GroupType.valueOf(packet.getGroup());
        user.setGroup(type);
    }

    @Override
    public void handle(UserFlyUpdatePacket packet) {
        UUID uuid = UUID.fromString(packet.getUuid());
        User user = UserManager.getUser(uuid);
        Player player = user.getPlayer();
        player.setAllowFlight(!player.getAllowFlight());
    }

    @Override
    public void handle(PlayerSetFlyPacket packet) {
        User user = UserManager.getUser(UUID.fromString(packet.getUuidPlayer()));
        user.setFly(packet.isFlyMode());
    }

    @Override
    public void handle(PlayerSetGodPacket packet) {
        User user = UserManager.getUser(UUID.fromString(packet.getUuidPlayer()));
        user.setGod(packet.isGodMode());
    }

    @Override
    public void handle(PlayerSetVanishPacket packet) {
        User user = UserManager.getUser(UUID.fromString(packet.getUuidPlayer()));
        user.setVanish(packet.isVanishMode());
    }
    @Override
    public void handle(UserDisableDropPacket packet){
        User user = UserManager.getUser(UUID.fromString(packet.getUuidString()));
        user.getDisabledDrops().add(DropManager.getDrop(packet.getNameDrop()));
    }

    @Override
    public void handle(UserDropStatsPacket packet) {
        User user = UserManager.getUser(UUID.fromString(packet.getUuidString()));
        user.setExp(packet.getExp());
        user.setLevel(packet.getLevel());
    }
}