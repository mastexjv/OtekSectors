package pl.otekplay.sectors;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import pl.otekplay.sectors.commands.admin.*;
import pl.otekplay.sectors.commands.guild.*;
import pl.otekplay.sectors.commands.normal.*;
import pl.otekplay.sectors.enums.GroupType;
import pl.otekplay.sectors.listeners.*;
import pl.otekplay.sectors.managers.TablistManager;
import pl.otekplay.sectors.netty.sectors.SectorChannelInitalizer;
import pl.otekplay.sectors.netty.web.HttpServerInit;
import pl.otekplay.sectors.storage.Database;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.tasks.GuildRenewTask;

public class Main extends Plugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    private EventLoopGroup boss;
    private Channel bindChannel, httpChannel;

    public void onEnable() {
        instance = this;
        Settings.loadSettings();
        Database.init("localhost", 27017);
        initNetty();
        initListeners();
        initCommands();
        initManagers();
        GuildRenewTask.start();
        //AnimationUtil.init();
    }

    private void initNetty() {
        boss = new NioEventLoopGroup(1);
        initSectors();
        initHTTP();
    }

    private void initHTTP() {
        ServerBootstrap b = new ServerBootstrap();
        b.group(boss).channel(NioServerSocketChannel.class).childHandler(new HttpServerInit());
        ChannelFuture future = b.bind(25564).syncUninterruptibly();
        if (future.isSuccess()) {
            getLogger().info("[HTTPSERVER]Bind on: " + future.channel().localAddress());
            httpChannel = future.channel();
        } else {
            future.cause().printStackTrace();
        }

    }

    private void initSectors() {
        ServerBootstrap b = new ServerBootstrap();
        b.group(boss).channel(NioServerSocketChannel.class).childHandler(new SectorChannelInitalizer()).option(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture future = b.bind(29727).syncUninterruptibly();
        if (future.isSuccess()) {
            getLogger().info("[SECTORSERVER]Bind on: " + future.channel().localAddress());
            bindChannel = future.channel();
        } else {
            future.cause().printStackTrace();
        }
    }

    private void initCommands() {
        loadGuildsCommands();
        loadNormalCommands();
        loadAdminCommands();
    }

    private void initListeners() {
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PreLoginListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ServerConnectListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ChatListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PostLoginListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new DisconnectListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ProxyPingListener());
    }

    private void initManagers() {
        TablistManager.init();
    }

    private void loadGuildsCommands() {
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new GuildCreateCommand("zaloz", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new GuildJoinCommand("dolacz", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new GuildAddCommand("dodaj", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new GuildDeleteCommand("zamknij", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new GuildKickCommand("wyrzuc", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new GuildLeaveCommand("odejdz", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new GuildAllyCommand("sojusz", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new GuildEnemyCommand("wrogosc", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new GuildInfoCommand("ginfo", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new GuildPvPCommand("gpvp", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new GuildSetHomeCommand("gustawdom", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new GuildHomeCommand("gdom", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new GuildResizeCommand("powieksz", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new GuildLeaderCommand("lider", GroupType.PLAYER, new String[]{""}));


    }

    private void loadAdminCommands() {
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new KickCommand("kick", GroupType.MODERATOR, ""));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new BanCommand("ban", GroupType.MODERATOR, "zbanuj"));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new UnbanCommand("unban", GroupType.ADMIN, "odbanuj"));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new SendCommand("send", GroupType.OWNER, "sendcommand", "sc"));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new SectorsCommand("sectors", GroupType.ADMIN, ""));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new WhitelistCommand("whitelist", GroupType.ADMIN, "wl"));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new ReloadCommand("reload", GroupType.OWNER, "rl"));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new RootCommand("root", GroupType.PLAYER, "wlasciciel")); // WTF
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new GroupCommand("group", GroupType.OWNER, "ustawgrupe"));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new FlyCommand("fly", GroupType.HELPER, new String[]{"latanie"}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new GodCommand("god", GroupType.HELPER, new String[]{"niesmiertelnosc"}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new RandomTeleportCommand("randomtp", GroupType.MODERATOR, new String[]{"rtp"}));
    }

    private void loadNormalCommands() {
        BungeeCord.getInstance().getPluginManager().registerCommand(this,new DropCommand("drop",GroupType.PLAYER,new String[]{"stone"}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new MessageCommand("msg", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new TeleportCommand("tp", GroupType.MODERATOR, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new TeleportRequestCommand("tpa", GroupType.VIP, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new HomeCommand("home", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new SetHomeCommand("sethome", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new ReplyCommand("reply", GroupType.PLAYER, new String[]{"r"}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new IgnoreCommand("ignore", GroupType.PLAYER, new String[]{"ignoruj"}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new TopCommand("top", GroupType.PLAYER, new String[]{"topgracze"}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new SpawnCommand("spawn", GroupType.PLAYER, new String[]{""}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new MSGOffCommand("wyjebane", GroupType.PLAYER, new String[]{"msgoff", "moff"}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new HelpCommand("pomoc", GroupType.PLAYER, new String[]{"help"}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new FightCommand("walki", GroupType.PLAYER, new String[]{"fight"}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new KitCommand("kit", GroupType.PLAYER, new String[]{"zestaw"}));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new PotionShopCommand("shop", GroupType.PLAYER, new String[]{"potionshop", "efekty", "efekt", "potki"}));
    }



    @Override
    public void onDisable() {
        if (bindChannel != null) {
            bindChannel.close();
        }
        Database.getClient().close();
    }
}
