package pl.otekplay.sectors.packets;

import pl.otekplay.sectors.packets.impl.guild.*;
import pl.otekplay.sectors.packets.impl.player.*;
import pl.otekplay.sectors.packets.impl.sector.SectorChangePacket;
import pl.otekplay.sectors.packets.impl.sector.SectorRegisterPacket;
import pl.otekplay.sectors.packets.impl.sector.SectorSendCommandPacket;
import pl.otekplay.sectors.packets.impl.sector.SectorUpdateInfoPacket;
import pl.otekplay.sectors.packets.impl.user.*;

public class PacketHandler {

    public void exception(Throwable cause) throws Exception {
    }

    public void connected() throws Exception {
    }

    public void disconnected() throws Exception {
    }


    public void handle(SectorRegisterPacket serverRegisterPacket) {
    }

    public void handle(SectorChangePacket sectorChangePacket) {

    }

    public void handle(UserRegisterPacket userRegisterPacket) {

    }

    public void handle(PlayerTeleportLocationPacket playerTeleportLocationPacket) {
    }

    public void handle(PlayerInventoryUpdatePacket playerInventoryUpdatePacket) {
    }

    public void handle(PlayerStatsUpdatePacket playerStatsUpdatePacket) {
    }

    public void handle(PlayerSendMessagePacket playerSendMessagePacket) {
    }

    public void handle(GuildCreatePacket guildCreatePacket) {
    }

    public void handle(GuildAddMemberPacket guildAddMemberPacket) {
    }

    public void handle(GuildRemovePacket guildRemoveGuildPacket) {
    }

    public void handle(GuildRemoveMemberPacket guildRemoveMemberPacket) {
    }

    public void handle(GuildRequestItemsPacket guildRequestItemsPacket) {
    }

    public void handle(GuildRequestLocationPacket guildRequestLocationPacket) {
    }

    public void handle(GuildAnswerLocationPacket guildAnswerLocationPacket) {
    }

    public void handle(GuildAnswerItemsPacket guildAnswerItemsPacket) {

    }

    public void handle(GuildSetAllyPacket guildSetAllyPacket) {
    }

    public void handle(GuildSetEnemyPacket guildSetEnemyPacket) {

    }

    public void handle(GuildSwitchPvPPacket guildSwitchPvPPacket) {
    }

    public void handle(PlayerTeleportPlayerPacket playerTeleportPlayerPacket) {

    }

    public void handle(GuildNewLeaderPacket guildNewLeaderPacket) {

    }

    public void handle(SectorUpdateInfoPacket sectorUpdateInfoPacket) {

    }

    public void handle(PlayerTeleportRequestPacket playerTeleportRequestPacket) {

    }

    public void handle(PlayerTeleportAnswerPacket playerTeleportAnswerPacket) {

    }


    public void handle(PlayerSetHomeRequestPacket playerSetHomeRequestPacket) {
    }

    public void handle(PlayerSetHomeAnswerPacket packet) {
    }

    public void handle(PlayerAttackPlayerPacket playerAttackPlayerPacket) {
    }

    public void handle(PlayerDeathPacket playerDeathPacket) {
    }

    public void handle(GuildSetHomeRequestPacket guildSetHomeRequestPacket) {
    }

    public void handle(GuildSetHomeAnswerPacket guildSetHomeAnswerPacket) {
    }

    public void handle(GuildReSizeRequestItemsPacket guildReSizeRequestPacket) {
    }

    public void handle(GuildReSizeAnswerItemsPacket guildReSizeAnswerItemsPacket) {
    }

    public void handle(GuildChangeSizePacket guildChangeSizePacket) {
    }

    public void handle(GuildRenewRequestPacket guildRenewRequestPacket) {
    }

    public void handle(GuildRenewAnswerPacket guildRenewAnswerPacket) {

    }

    public void handle(SectorSendCommandPacket sectorSendCommandPacket) {
    }


    public void handle(PlayerDestroyGuildPacket playerDestroyGuildPacket) {

    }

    public void handle(UserOpenMenuPacket userOpenMenuPacket) {

    }

    public void handle(PlayerTeleportRandomPacket playerTeleportRandomPacket) {

    }

    public void handle(UserGroupUpdatePacket userGroupUpdatePacket) {
    }

    public void handle(UserGiveKitPacket userGiveKitPacket) {
    }

    public void handle(UserFlyUpdatePacket flyPacket) {
    }


    public void handle(UserGodUpdatePacket userGodUpdatePacket) {

    }

    public void handle(PlayerSetFlyPacket playerSetFlyPacket) {
    }

    public void handle(PlayerSetVanishPacket playerSetVanishPacket) {
    }

    public void handle(PlayerSetGodPacket playerSetGodPacket) {
    }

    public void handle(UserDisableDropPacket packet) {
    }

    public void handle(UserBackupSavePacket userBackupSavePacket) {
    }

    public void handle(UserDropStatsPacket userDropStatsPacket) {
    }
}
