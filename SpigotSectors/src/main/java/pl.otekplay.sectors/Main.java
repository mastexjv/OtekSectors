package pl.otekplay.sectors;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.Setter;
import ninja.amp.ampmenus.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.otekplay.sectors.basic.Map;
import pl.otekplay.sectors.data.Sector;
import pl.otekplay.sectors.listeners.block.BlockBreakListener;
import pl.otekplay.sectors.listeners.block.BlockDispenseListener;
import pl.otekplay.sectors.listeners.block.BlockPistonExtendListener;
import pl.otekplay.sectors.listeners.block.BlockPlaceListener;
import pl.otekplay.sectors.listeners.craft.CraftItemListener;
import pl.otekplay.sectors.listeners.craft.PrepareItemCraftListener;
import pl.otekplay.sectors.listeners.entity.EntityDamageByEntityListener;
import pl.otekplay.sectors.listeners.entity.EntityDamageListener;
import pl.otekplay.sectors.listeners.player.*;
import pl.otekplay.sectors.listeners.sign.SignChangeListener;
import pl.otekplay.sectors.listeners.vehicle.VehicleMoveListener;
import pl.otekplay.sectors.managers.DropManager;
import pl.otekplay.sectors.managers.KitManager;
import pl.otekplay.sectors.netty.SectorClient;
import pl.otekplay.sectors.packets.FramingHandler;
import pl.otekplay.sectors.packets.PacketCoder;
import pl.otekplay.sectors.packets.impl.sector.SectorRegisterPacket;
import pl.otekplay.sectors.recipes.GeneratorRecipes;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.task.GlobalnfoTask;
import pl.otekplay.sectors.task.SectorInfoTask;
import pl.otekplay.sectors.task.TagUpdateTask;
import pl.otekplay.sectors.utils.CobblexUtil;
import pl.otekplay.sectors.utils.LocationUtil;

public class Main extends JavaPlugin implements Listener {

    private static Main instance;
    @Getter
    @Setter
    private SectorClient client;
    @Getter
    @Setter
    private Sector sector;

    public static Main getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        Settings.loadSettings();
        initNetty();
        initManagers();
        initSector();
        initListeners();
        initRecipes();
        initThreads();
        initMenus();
        initUtils();
    }

    private void initListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerBucketEmptyListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerBucketFillListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPistonExtendListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new VehicleMoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new CraftItemListener(),this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new PrepareItemCraftListener(),this);
        Bukkit.getPluginManager().registerEvents(new BlockDispenseListener(),this);
        Bukkit.getPluginManager().registerEvents(new SignChangeListener(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerSpawnLocationListener(),this);
    }

    private void initSector() {
        Bukkit.getWorlds().get(0).setSpawnLocation(-33,90,-148);
        Map map = new Map(Settings.MAPX, Settings.MAPZ);
        sector = new Sector(map, "Sector:" + map.toString(), client);
        client.sendPacket(new SectorRegisterPacket("Sector", Bukkit.getIp() + ":" + Bukkit.getPort(), map.toString(), Settings.NORTHBORDER, Settings.SOUTHBORDER, Settings.EASTBORDER, Settings.WESTBORDER));
        new SectorInfoTask().runTaskTimer(this, 0, 20 * 10);
    }
    private void initMenus(){
        MenuListener.getInstance().register(this);
    }
    private void initRecipes(){
        GeneratorRecipes.initRecipes();
    }
    private void initManagers(){
        DropManager.loadDrops();
        KitManager.loadKits();
    }
    private void initThreads(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), new TagUpdateTask(), 20 * 10, 20 * 10);
        GlobalnfoTask.start();
    }
    private void initUtils(){
        LocationUtil.loadSpawnLocation();
        CobblexUtil.init();
    }


    private void initNetty() {
        EventLoopGroup group = new NioEventLoopGroup();
        new Bootstrap().channel(NioSocketChannel.class)
                .group(group)
                .handler(new ChannelInitializer<Channel>() {
                    public static final String FIELD_PREPENDER = "field-prepender";
                    public static final String PACKET_CODER = "packet-coder";
                    public static final String PACKET_HANDLER = "packet-handler";

                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        try {
                            channel.config().setOption(ChannelOption.IP_TOS, 0x18);
                        } catch (ChannelException ignored) {
                        }
                        channel.config().setAllocator(PooledByteBufAllocator.DEFAULT);
                        channel.pipeline()
                                .addLast(FIELD_PREPENDER, new FramingHandler())
                                .addLast(PACKET_CODER, new PacketCoder())
                                .addLast(PACKET_HANDLER, client = new SectorClient());
                    }
                }).option(ChannelOption.SO_KEEPALIVE, true)
                .connect("localhost", Settings.PORT).syncUninterruptibly();
    }
}
