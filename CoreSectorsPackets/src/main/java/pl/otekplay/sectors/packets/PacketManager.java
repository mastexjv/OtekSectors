package pl.otekplay.sectors.packets;

import pl.otekplay.sectors.packets.impl.guild.*;
import pl.otekplay.sectors.packets.impl.player.*;
import pl.otekplay.sectors.packets.impl.sector.SectorChangePacket;
import pl.otekplay.sectors.packets.impl.sector.SectorRegisterPacket;
import pl.otekplay.sectors.packets.impl.sector.SectorSendCommandPacket;
import pl.otekplay.sectors.packets.impl.sector.SectorUpdateInfoPacket;
import pl.otekplay.sectors.packets.impl.user.UserGiveKitPacket;
import pl.otekplay.sectors.packets.impl.user.UserGroupUpdatePacket;
import pl.otekplay.sectors.packets.impl.user.UserOpenMenuPacket;
import pl.otekplay.sectors.packets.impl.user.UserRegisterPacket;

import java.util.HashMap;
import java.util.Map;

public class PacketManager {


    private static final Map<Integer, Class<? extends Packet>> BY_ID = new HashMap<>();
    private static final Map<Class<? extends Packet>, Integer> BY_CLASS = new HashMap<>();

    static {
        register(1, SectorRegisterPacket.class);
        register(2, SectorChangePacket.class);
        register(3, SectorUpdateInfoPacket.class);
        register(4, SectorSendCommandPacket.class);
        register(10, PlayerTeleportRandomPacket.class);
        register(11, PlayerTeleportLocationPacket.class);
        register(12, PlayerTeleportPlayerPacket.class);
        register(13, PlayerStatsUpdatePacket.class);
        register(14, PlayerInventoryUpdatePacket.class);
        register(15, PlayerSendMessagePacket.class);
        register(16, PlayerTeleportRequestPacket.class);
        register(17, PlayerTeleportAnswerPacket.class);
        register(18, PlayerSetHomeRequestPacket.class);
        register(19, PlayerSetHomeAnswerPacket.class);
        register(20, PlayerAttackPlayerPacket.class);
        register(21, PlayerDeathPacket.class);
        register(22, PlayerDestroyGuildPacket.class);
        register(23, PlayerSetFlyPacket.class);
        register(24, PlayerSetGodPacket.class);
        register(25, PlayerSetVanishPacket.class);
        register(30, GuildCreatePacket.class);
        register(31, GuildRemovePacket.class);
        register(32, GuildAddMemberPacket.class);
        register(33, GuildRemoveMemberPacket.class);
        register(34, GuildRequestItemsPacket.class);
        register(35, GuildRequestLocationPacket.class);
        register(36, GuildAnswerItemsPacket.class);
        register(37, GuildAnswerLocationPacket.class);
        register(38, GuildSetAllyPacket.class);
        register(39, GuildSetEnemyPacket.class);
        register(40, GuildNewLeaderPacket.class);
        register(41, GuildSwitchPvPPacket.class);
        register(42, GuildSetHomeRequestPacket.class);
        register(43, GuildSetHomeAnswerPacket.class);
        register(44, GuildReSizeAnswerItemsPacket.class);
        register(45, GuildReSizeRequestItemsPacket.class);
        register(46, GuildChangeSizePacket.class);
        register(60, UserRegisterPacket.class);
        register(61, UserGroupUpdatePacket.class);
        register(62, UserOpenMenuPacket.class);
        register(63, UserGiveKitPacket.class);

    }

    private static void register(int id, Class<? extends Packet> packetClass){
        BY_ID.put(id, packetClass);
        BY_CLASS.put(packetClass, id);
    }

    public static Packet getPacket(int id) {
        try {
            return BY_ID.get(id).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static int getPacketID(Class<? extends Packet> packetClass){
        return BY_CLASS.get(packetClass);
    }

}

