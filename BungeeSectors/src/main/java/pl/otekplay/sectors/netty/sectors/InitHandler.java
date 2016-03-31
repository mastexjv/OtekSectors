package pl.otekplay.sectors.netty.sectors;

import lombok.AllArgsConstructor;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.Util;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import pl.otekplay.sectors.basic.GuildLocation;
import pl.otekplay.sectors.basic.Map;
import pl.otekplay.sectors.data.Guild;
import pl.otekplay.sectors.data.Sector;
import pl.otekplay.sectors.data.User;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.managers.*;
import pl.otekplay.sectors.packets.PacketHandler;
import pl.otekplay.sectors.packets.impl.guild.*;
import pl.otekplay.sectors.packets.impl.player.*;
import pl.otekplay.sectors.packets.impl.sector.SectorChangePacket;
import pl.otekplay.sectors.packets.impl.sector.SectorRegisterPacket;
import pl.otekplay.sectors.packets.impl.sector.SectorUpdateInfoPacket;
import pl.otekplay.sectors.packets.impl.user.UserDisableDropPacket;
import pl.otekplay.sectors.packets.impl.user.UserDropStatsPacket;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.TimeUtil;

import java.util.UUID;

@AllArgsConstructor
public class InitHandler extends PacketHandler {

    private SectorClient client;

    @Override
    public void handle(SectorRegisterPacket packet) {
        String name = packet.getName() + ":" + packet.getMap();
        ServerInfo info = ProxyServer.getInstance().constructServerInfo(name, Util.getAddr(packet.getAddress()), "", false);
        ProxyServer.getInstance().getConfig().getServers().put(name, info);
        SectorManager.registerSector(new Sector(name, info, Map.fromString(packet.getMap()), client, 20.0, packet.getNORTHBORDER(), packet.getSOUTHBORDER(), packet.getEASTBORDER(), packet.getWESTBORDER()));
    }

    @Override
    public void handle(SectorChangePacket packet) {
        ProxiedPlayer pp = BungeeCord.getInstance().getPlayer(UUID.fromString(packet.getStringUUID()));
        if (pp != null) {
            User user = UserManager.getUser(pp.getUniqueId());
            if(!pp.getServer().equals(user.getSector().getServer())) {
                pp.connect(BungeeCord.getInstance().getServerInfo(packet.getNameServer()));
            }
        }
    }

    @Override
    public void handle(PlayerTeleportLocationPacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidString());
        User user = UserManager.getUser(uuid);
        user.getSector().getClient().sendPacket(packet);
    }

    @Override
    public void handle(PlayerStatsUpdatePacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidString());
        User user = UserManager.getUser(uuid);
        user.getSector().getClient().sendPacket(packet);
    }

    @Override
    public void handle(PlayerTeleportAnswerPacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidString());
        User user = UserManager.getUser(uuid);
        if (packet.getActionTeleport() == 0) {
            if (packet.getAction() == 1) {
                if (user.isOnline()) {
                    String[] args = packet.getTeleportString().split(":");
                    int x = (int) Double.parseDouble(args[0]);
                    int z = (int) Double.parseDouble(args[2]);
                    Sector sec = SectorManager.getSectorByLocation(x, z);
                    if (!user.getSector().equals(sec)) {
                        user.getProxiedPlayer().connect(sec.getServer());
                    }
                    sec.getClient().sendPacket(new PlayerTeleportLocationPacket(user.getUuid().toString(), packet.getTeleportString()));
                    user.getProxiedPlayer().sendMessage(Settings.PREFIX+""+ ChatColor.GRAY+ "Teleportacja na "+ChatColor.RED + sec.getName() + " X: " + x + " Z:" + z);
                }
            } else if (packet.getAction() == 0) {
                User teleport = UserManager.getUser(UUID.fromString(packet.getTeleportString()));
                if (user.isOnline()) {
                    if (teleport.isOnline()) {
                        if (!user.getSector().equals(teleport)) {
                            user.getProxiedPlayer().connect(teleport.getSector().getServer());
                        }
                        teleport.getSector().getClient().sendPacket(new PlayerTeleportPlayerPacket(user.getUuid().toString(), teleport.getUuid().toString()));
                    } else {
                        user.sendMessage(Settings.PREFIX+ ""+ChatColor.GRAY+"Gracz " +ChatColor.RED+""+ teleport.getName() + ChatColor.GRAY+" sie wylogowal.");
                    }
                }
            }
        } else {
            user.sendMessage(Settings.PREFIX+ ""+ChatColor.GRAY+"Ruszyles sie podczas teleportacji."+ChatColor.RED+ " Teleportacja zostala anulowana!");
        }
    }

    @Override
    public void handle(PlayerInventoryUpdatePacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidString());
        User user = UserManager.getUser(uuid);
        user.getSector().getClient().sendPacket(packet);
    }

    @Override
    public void handle(PlayerSendMessagePacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidString());
        User user = UserManager.getUser(uuid);
        String prefix = user.getGroup().getPrefix();
        boolean hasGuild = user.hasGuild();
        if (hasGuild) {
            if (packet.getMessage().startsWith("!")) {
                String mes = packet.getMessage().replaceFirst("!", "");
                user.getGuild().sendMessage(ChatColor.BLUE+"[GUILD] "+ChatColor.AQUA + user.getName() + ChatColor.GRAY+" :" +ChatColor.GREEN+ mes);
                return;
            } else if (packet.getMessage().startsWith("!!")) {
                String mes = packet.getMessage().replaceFirst("!!", "");
                for (Guild g : user.getGuild().getAlly()) {
                    g.sendMessage(ChatColor.LIGHT_PURPLE+"[Sojusz] "+ChatColor.BLUE+"[" + user.getGuild().getTag() + "]"+ChatColor.AQUA + user.getName() + " :" + ChatColor.GREEN + mes);
                }
                return;
            }
        }

        if (ChatManager.getChat_level() == 0) {
            if (user.can(GroupType.HELPER)) {
                return;
            }
            user.sendMessage(Settings.PREFIX+ ChatColor.RED+"Chat na serwerze jest obecnie wylaczony.");
            return;
        }
        if (ChatManager.getChat_level() == 2) {
            if (user.can(GroupType.VIP)) {
                return;
            }
            user.sendMessage(Settings.PREFIX+ ChatColor.RED+"Chat na serwerze jest obecnie wlaczony w trybie " + ChatColor.GOLD + "VIP");
        }
        if (user.getLastMessageTime() > System.currentTimeMillis()) {
            if (user.can(GroupType.HELPER)) {
                return;
            }
            user.sendMessage(Settings.PREFIX+ChatColor.RED+"Przed wyslaniem kolejnej wiadomosci musisz odczekac 5 sekund!");
            return;
        }

        for (ProxiedPlayer pp : BungeeCord.getInstance().getPlayers()) {
            String tag = "";
            User get = UserManager.getUser(pp.getUniqueId());
            if (hasGuild && get.hasGuild()) {
                if (user.getGuild().equals(get.getGuild())) {
                    tag = ChatColor.GRAY + "[" + ChatColor.GREEN + user.getGuild().getTag() + ChatColor.GRAY + "] ";
                } else if (user.getGuild().getAlly().contains(get.getGuild())) {
                    tag = ChatColor.GRAY + "[" + ChatColor.YELLOW + user.getGuild().getTag() + ChatColor.GRAY + "] ";
                } else {
                    tag = ChatColor.GRAY + "[" + ChatColor.DARK_RED + user.getGuild().getTag() + ChatColor.GRAY + "] ";
                }
            } else if (hasGuild) {
                tag = ChatColor.GRAY + "[" + ChatColor.RED + user.getGuild().getTag() + ChatColor.GRAY + "] ";
            }
            pp.sendMessage(tag + prefix.replace("{MSG}", packet.getMessage()).replace("{NICK}", user.getName()).replace("{LEVEL}", user.getLevel()+""));
        }
        user.setLastMessageTime(System.currentTimeMillis() + Settings.CHAT_DELAY_TIME);
    }

    @Override
    public void handle(GuildAnswerLocationPacket packet) {
        User user = UserManager.getUser(UUID.fromString(packet.getUuidString()));
        if (packet.getAction() == 0) {
            user.getSector().getClient().sendPacket(new GuildRequestItemsPacket(packet.getUuidString(), packet.getTag(), packet.getName(), packet.getLocation()));
        } else if (packet.getAction() == 1) {
            if (user.isOnline()) {
                user.getProxiedPlayer().sendMessage(Settings.GUILD_CREATE_ERROR_SPAWN);
            }
        } else if (packet.getAction() == 2) {
            if (user.isOnline()) {
                user.getProxiedPlayer().sendMessage(Settings.GUILD_CREATE_ERROR_GUILD);
            }
        } else if (packet.getAction() == 3) {
            if (user.isOnline()) {
                user.getProxiedPlayer().sendMessage(Settings.GUILD_CREATE_ERROR_SECTOR);
            }
        }
    }

    @Override
    public void handle(GuildAnswerItemsPacket packet) {
        if (packet.isItems()) {
            GuildManager.registerGuild(packet.getTag(), packet.getName(), UserManager.getUser(UUID.fromString(packet.getUuidString())), GuildLocation.fromNormalStringLocation(packet.getLocation(), Settings.GUILD_DEFAULT_SIZE));
        } else {
            ProxiedPlayer pp = BungeeCord.getInstance().getPlayer(UUID.fromString(packet.getUuidString()));
            if (pp != null) {
                pp.sendMessage(Settings.GUILD_CREATE_ERROR_NOITEMS);
            }
        }
    }

    @Override
    public void handle(SectorUpdateInfoPacket packet) {
        SectorManager.getSector(packet.getSectorName()).setTPS(packet.getTPS());
    }

    @Override
    public void handle(PlayerSetHomeAnswerPacket packet) {
        User user = UserManager.getUser(UUID.fromString(packet.getUuidString()));
        if (packet.getAction() == 0) {
            user.setHome(packet.getLocationString());
            user.sendMessage(Settings.PREFIX+""+ChatColor.GREEN+ " Dom zostal poprawie ustawiony!");
        } else if (packet.getAction() == 1) {
            user.sendMessage(Settings.PREFIX+""+ChatColor.RED+"Nie mozesz ustawic domu gildi na terenie wrogiej gildii.");
        }
    }

    @Override
    public void handle(PlayerDeathPacket packet) {
        User user = UserManager.getUser(UUID.fromString(packet.getUuidDef()));
        if (user.hasCombat()) {
            user.setLastAttackTime(0);
            user.setLastKiller(user.getLastAttacker());
            user.setLastAttacker(null);
            if (user.getLastKiller() != null) {
                RankingManager.calculateRanking(user);
            }
        }
    }

    @Override
    public void handle(PlayerAttackPlayerPacket packet) {
        User def = UserManager.getUser(UUID.fromString(packet.getUuidDef()));
        User att = UserManager.getUser(UUID.fromString(packet.getUuidAtt()));
        def.setLastAttacker(att);
        def.setLastAttackTime(packet.getAttackTime());
        att.setLastAttackTime(packet.getAttackTime());
    }

    @Override
    public void handle(GuildSetHomeAnswerPacket packet) {
        User user = UserManager.getUser(UUID.fromString(packet.getUuidString()));
        Guild guild = user.getGuild();
        if (packet.getAction() == 0) {
            guild.setHome(packet.getLocationString());
            guild.sendMessage(Settings.PREFIX+""+ChatColor.GREEN+"Dom gildii zostal ustawiony.");
        } else if (packet.getAction() == 1) {
            user.sendMessage(Settings.PREFIX+""+ChatColor.GREEN+"Dom gildii musi sie znajdowac na terenie Twojej gildii!");
        }
    }

    @Override
    public void handle(GuildReSizeAnswerItemsPacket packet) {
        User user = UserManager.getUser(UUID.fromString(packet.getUuidString()));
        if (packet.isHasItems()) {
            Guild guild = user.getGuild();
            guild.getLocation().setSize(guild.getLocation().getSize() + 5);
            BungeeCord.getInstance().broadcast(ChatColor.GRAY+"Gildia "+ChatColor.RED + guild.getTag() +ChatColor.GRAY+ " zostala powiekszona do: " +ChatColor.RED+ guild.getLocation().getSize() * 2 + "x" + guild.getLocation().getSize() * 2);
            SectorManager.sendPacket(new GuildChangeSizePacket(guild.getTag(), guild.getLocation().getSize()));
        } else {
            int sizeLevel = user.getGuild().getLocation().getSize() - 25;
            sizeLevel = sizeLevel / 5;
            sizeLevel--;
            int items = sizeLevel * 8;
            int needItems = 32 + items;
            user.sendMessage(Settings.PREFIX+""+ChatColor.GRAY+"Aby powiekszyc gildie potrzebujesz "+ChatColor.RED+ + needItems + ChatColor.GRAY+" diamentowych blokow.");
        }
    }

    @Override
    public void handle(GuildRenewAnswerPacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidString());
        User user = UserManager.getUser(uuid);
        Guild guild = user.getGuild();
        if (packet.getAction() == 0) {
            guild.setLastRenew(guild.getLastRenew() + Settings.WEEK);
            guild.sendMessage("Twoja gildia zostala przedluzona do: " + TimeUtil.getDate(guild.getLastRenew()));
        } else if (packet.getAction() == 1) {
            user.sendMessage(Settings.PREFIX+""+ChatColor.RED+"Aby przedluzyc gildie potrzebujesz 64 blokow diamentu!");
        }
    }

    @Override
    public void handle(PlayerDestroyGuildPacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidAttacker());
        Guild guild = GuildManager.getGuildByTag(packet.getTagGuild());
        User user = UserManager.getUser(uuid);
        if (System.currentTimeMillis() > guild.getLastDestroy()) {
            if (guild.getLives() == 1) {
                BungeeCord.getInstance().broadcast(ChatColor.GRAY+"Gildia " +ChatColor.RED+ guild.getTag() +ChatColor.GRAY+ " zostala zniszczona przez "+ChatColor.RED + user.getName());
                GuildManager.removeGuild(guild);
            } else {
                BungeeCord.getInstance().broadcast(ChatColor.GRAY+"Gracz " +ChatColor.RED+ user.getName() +ChatColor.GRAY+ " podbil gildie " +ChatColor.RED+ guild.getTag() + ChatColor.GRAY+" zostalo jej "+ ChatColor.RED+ (guild.getLives() - 1) + ChatColor.GRAY+" zyc");
                guild.setLives(guild.getLives() - 1);
                guild.setLastDestroy(System.currentTimeMillis() + Settings.DAY);
            }
        } else {
            user.sendMessage(ChatColor.GRAY+"Gildia "+ChatColor.RED + guild.getTag() + ChatColor.GRAY+" zostala juz podbita w ciagu ostatnich 24h.");
        }
    }

    @Override
    public void handle(UserDisableDropPacket packet){
        SectorManager.sendPacket(packet);
    }

    @Override
    public void handle(UserDropStatsPacket packet) {
        UUID uuid = UUID.fromString(packet.getUuidString());
        User user = UserManager.getUser(uuid);
        user.setExp(packet.getExp());
        user.setLevel(packet.getLevel());
        SectorManager.sendPacket(packet);
    }
}
