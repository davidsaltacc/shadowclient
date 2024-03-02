package net.justacoder.shadowclient.main.module.modules.other;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.network.packet.s2c.play.*;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PacketRecievedEvent;
import net.justacoder.shadowclient.main.event.events.PacketSentEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.EnumSetting;
import net.justacoder.shadowclient.main.setting.settings.StringSetting;
import net.justacoder.shadowclient.main.util.ChatUtils;

@EventListener({PacketSentEvent.class, PacketRecievedEvent.class})
@SearchTags({"packet logger"})
public class PacketLogger extends Module {

    public final EnumSetting<Mode> MODE = new EnumSetting<>("Mode", Mode.ALL);
    public final StringSetting FILTER = new StringSetting("Filter");
    public final EnumSetting<FMode> FMODE = new EnumSetting<>("Filter Mode", FMode.WHITELIST);

    public PacketLogger() {
        super("packetlogger", "Packet Log", "See what packets are being sent between you and the server. ", ModuleCategory.OTHER);
        addSettings(MODE, FILTER, FMODE);
    }

    public String cleanClassName(Packet<?> cl) {
        return translate(cl);
    }

    public boolean filter(String text) {
        if (text.toLowerCase().contains(FILTER.stringValue().toLowerCase()) && FMODE.getEnumValue() == FMode.BLACKLIST) {
            return true;
        }
        return !text.toLowerCase().contains(FILTER.stringValue().toLowerCase()) && FMODE.getEnumValue() == FMode.WHITELIST;
    }

    public void send(String text) {
        if (FILTER.stringValue().length() != 0) {
            if (filter(text)) {
                return;
            }
        }
        ChatUtils.sendMessageClient(text);
    }

    @Override
    public void onEvent(Event event) {
        if (MODE.getEnumValue() == Mode.ALL) {
            if (event instanceof PacketRecievedEvent) {
                send(ChatUtils.Formattings.ITALIC + "RECEIVED " + ChatUtils.Formattings.RESET + cleanClassName(((PacketRecievedEvent) event).packet));
                return;
            }
            send(ChatUtils.Formattings.ITALIC + "SENT " + ChatUtils.Formattings.RESET + cleanClassName(((PacketSentEvent) event).packet));
            return;
        }
        if (MODE.getEnumValue() == Mode.RECEIVED && event instanceof PacketRecievedEvent) {
            send(ChatUtils.Formattings.ITALIC + "RECEIVED " + ChatUtils.Formattings.RESET + cleanClassName(((PacketRecievedEvent) event).packet));
            return;
        }
        if (MODE.getEnumValue() == Mode.SENT && event instanceof PacketSentEvent) {
            send(ChatUtils.Formattings.ITALIC + "SENT " + ChatUtils.Formattings.RESET + cleanClassName(((PacketSentEvent) event).packet));
        }
    }

    public enum Mode {
        ALL("All"),
        RECEIVED("Received"),
        SENT("Sent");


        final String name;
        Mode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public enum FMode {
        BLACKLIST("Blacklist"),
        WHITELIST("Whitelist");


        final String name;
        FMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public String translate(Packet<?> in) { // this cant be real lol (it will just print "class_NNNN" else) TODO test
        if (in instanceof AdvancementTabC2SPacket) { return "AdvancementTabPacket"; }
        if (in instanceof BoatPaddleStateC2SPacket) { return "BoatPaddleStatePacket"; }
        if (in instanceof BookUpdateC2SPacket) { return "BookUpdatePacket"; }
        if (in instanceof ButtonClickC2SPacket) { return "ButtonClickPacket"; }
        if (in instanceof ChatMessageC2SPacket) { return "ChatMessagePacket"; }
        if (in instanceof ClickSlotC2SPacket) { return "ClickSlotPacket"; }
        if (in instanceof ClientCommandC2SPacket) { return "ClientCommandPacket"; }
        if (in instanceof ClientSettingsC2SPacket) { return "ClientSettingsPacket"; }
        if (in instanceof ClientStatusC2SPacket) { return "ClientStatusPacket"; }
        if (in instanceof CloseHandledScreenC2SPacket) { return "CloseHandledScreenPacket"; }
        if (in instanceof CommandExecutionC2SPacket) { return "CommandExecutionPacket"; }
        if (in instanceof CraftRequestC2SPacket) { return "CraftRequestPacket"; }
        if (in instanceof CreativeInventoryActionC2SPacket) { return "CreativeInventoryActionPacket"; }
        if (in instanceof CustomPayloadC2SPacket) { return "CustomPayloadPacket"; }
        if (in instanceof HandSwingC2SPacket) { return "HandSwingPacket"; }
        if (in instanceof JigsawGeneratingC2SPacket) { return "JigsawGeneratingPacket"; }
        if (in instanceof KeepAliveC2SPacket) { return "KeepAlivePacket"; }
        if (in instanceof MessageAcknowledgmentC2SPacket) { return "MessageAcknowledgementPacket"; }
        if (in instanceof PickFromInventoryC2SPacket) { return "PickFromInventoryPacket"; }
        if (in instanceof PlayerActionC2SPacket) { return "PlayerActionPacket"; }
        if (in instanceof PlayerInputC2SPacket) { return "PlayerInputPacket"; }
        if (in instanceof PlayerInteractBlockC2SPacket) { return "PlayerInteractBlockPacket"; }
        if (in instanceof PlayerInteractEntityC2SPacket) { return "PlayerInteractEntityPacket"; }
        if (in instanceof PlayerInteractItemC2SPacket) { return "PlayerInteractItemPacket"; }
        if (in instanceof PlayerMoveC2SPacket.Full) { return "PlayerMovePacket.Full"; }
        if (in instanceof PlayerMoveC2SPacket.LookAndOnGround) { return "PlayerMovePacket.LookAndOnGround"; }
        if (in instanceof PlayerMoveC2SPacket.OnGroundOnly) { return "PlayerMovePacket.OnGroundOnly"; }
        if (in instanceof PlayerMoveC2SPacket.PositionAndOnGround) { return "PlayerMovePacket.PositionAndOnGround"; }
        if (in instanceof PlayerSessionC2SPacket) { return "PlayerSessionPacket"; }
        if (in instanceof PlayPongC2SPacket) { return "PlayPongPacket"; }
        if (in instanceof QueryBlockNbtC2SPacket) { return "QueryBlockNbtPacket"; }
        if (in instanceof QueryEntityNbtC2SPacket) { return "QueryEntityNbtPacket"; }
        if (in instanceof RecipeBookDataC2SPacket) { return "RecipeBookDataPacket"; }
        if (in instanceof RenameItemC2SPacket) { return "RenameItemPacket"; }
        if (in instanceof RequestCommandCompletionsC2SPacket) { return "RequestCommandCompletionsPacket"; }
        if (in instanceof ResourcePackStatusC2SPacket) { return "ResourcePackStatusPacket"; }
        if (in instanceof SelectMerchantTradeC2SPacket) { return "SelectMerchantTradePacket"; }
        if (in instanceof SpectatorTeleportC2SPacket) { return "SpectatorTeleportPacket"; }
        if (in instanceof TeleportConfirmC2SPacket) { return "TeleportConfirmPacket"; }
        if (in instanceof UpdateBeaconC2SPacket) { return "UpdateBeaconPacket"; }
        if (in instanceof UpdateCommandBlockC2SPacket) { return "UpdateCommandBlockPacket"; }
        if (in instanceof UpdateDifficultyC2SPacket) { return "UpdateDifficultyPacket"; }
        if (in instanceof UpdateDifficultyLockC2SPacket) { return "UpdateDifficultyLockPacket"; }
        if (in instanceof UpdateJigsawC2SPacket) { return "UpdateJigsawPacket"; }
        if (in instanceof UpdatePlayerAbilitiesC2SPacket) { return "UpdatePlayerAbilitiesPacket"; }
        if (in instanceof UpdateSelectedSlotC2SPacket) { return "UpdateSelectedSlotPacket"; }
        if (in instanceof UpdateSignC2SPacket) { return "UpdateSignPacket"; }
        if (in instanceof UpdateStructureBlockC2SPacket) { return "UpdateStructureBlockPacket"; }
        if (in instanceof VehicleMoveC2SPacket) { return "VehicleMovePacket"; }

        if (in instanceof AdvancementUpdateS2CPacket) { return "AdvancementUpdatePacket"; }
        if (in instanceof BlockBreakingProgressS2CPacket) { return "BlockBreakingProgressPacket"; }
        if (in instanceof BlockEntityUpdateS2CPacket) { return "BlockEntityUpdatePacket"; }
        if (in instanceof BlockEventS2CPacket) { return "BlockEventPacket"; }
        if (in instanceof BlockUpdateS2CPacket) { return "BlockUpdatePacket"; }
        if (in instanceof BossBarS2CPacket) { return "BossBarPacket"; }
        if (in instanceof BundleS2CPacket) { return "BundlePacket"; }
        if (in instanceof ChatMessageS2CPacket) { return "ChatMessagePacket"; }
        if (in instanceof ChatSuggestionsS2CPacket) { return "ChatSuggestionsPacket"; }
        if (in instanceof ChunkBiomeDataS2CPacket) { return "ChunkBiomeDataPacket"; }
        if (in instanceof ChunkDataS2CPacket) { return "ChunkDataPacket"; }
        if (in instanceof ChunkDeltaUpdateS2CPacket) { return "ChunkDeltaUpdatePacket"; }
        if (in instanceof ChunkLoadDistanceS2CPacket) { return "ChunkLoadDistancePacket"; }
        if (in instanceof ChunkRenderDistanceCenterS2CPacket) { return "ChunkRenderDistancePacket"; }
        if (in instanceof ClearTitleS2CPacket) { return "ClearTitlePacket"; }
        if (in instanceof CloseScreenS2CPacket) { return "CloseScreenPacket"; }
        if (in instanceof CommandSuggestionsS2CPacket) { return "CommandSuggestionsPacket"; }
        if (in instanceof CommandTreeS2CPacket) { return "CommandTreePacket"; }
        if (in instanceof CooldownUpdateS2CPacket) { return "CooldownUpdatePacket"; }
        if (in instanceof CraftFailedResponseS2CPacket) { return "CraftFailedResponsePacket"; }
        if (in instanceof CustomPayloadS2CPacket) { return "CustomPayloadPacket"; }
        if (in instanceof DamageTiltS2CPacket) { return "DamageTiltPacket"; }
        if (in instanceof DeathMessageS2CPacket) { return "DeathMessagePacket"; }
        if (in instanceof DifficultyS2CPacket) { return "DifficultyPacket"; }
        if (in instanceof DisconnectS2CPacket) { return "DisconnectPacket"; }
        if (in instanceof EndCombatS2CPacket) { return "EndCombatPacket"; }
        if (in instanceof EnterCombatS2CPacket) { return "EnterCombatPacket"; }
        if (in instanceof EntitiesDestroyS2CPacket) { return "EntitiesDestroyPacket"; }
        if (in instanceof EntityAnimationS2CPacket) { return "EntityAnimationPacket"; }
        if (in instanceof EntityAttachS2CPacket) { return "EntityAttachPacket"; }
        if (in instanceof EntityAttributesS2CPacket) { return "EntityAttributesPacket"; }
        if (in instanceof EntityDamageS2CPacket) { return "EntityDamagePacket"; }
        if (in instanceof EntityEquipmentUpdateS2CPacket) { return "EntityEquipmentUpdatePacket"; }
        if (in instanceof EntityPassengersSetS2CPacket) { return "EntityPassengersSetPacket"; }
        if (in instanceof EntityPositionS2CPacket) { return "EntityPositionPacket"; }
        if (in instanceof EntityS2CPacket.MoveRelative) { return "EntityPacket.MoveRelative"; }
        if (in instanceof EntityS2CPacket.Rotate) { return "EntityPacket.Rotate"; }
        if (in instanceof EntityS2CPacket.RotateAndMoveRelative) { return "EntityPacket.RotateAndMoveRelative"; }
        if (in instanceof EntitySetHeadYawS2CPacket) { return "EntitySetHeadYawPacket"; }
        if (in instanceof EntitySpawnS2CPacket) { return "EntitySpawnPacket"; }
        if (in instanceof EntityStatusEffectS2CPacket) { return "EntityStatusEffectPacket"; }
        if (in instanceof EntityStatusS2CPacket) { return "EntityStatusPacket"; }
        if (in instanceof EntityTrackerUpdateS2CPacket) { return "EntityTrackerUpdatePacket"; }
        if (in instanceof EntityVelocityUpdateS2CPacket) { return "EntityVelocityUpdatePacket"; }
        if (in instanceof ExperienceBarUpdateS2CPacket) { return "ExperienceBarUpdatePacket"; }
        if (in instanceof ExperienceOrbSpawnS2CPacket) { return "ExperienceOrbSpawnPacket"; }
        if (in instanceof ExplosionS2CPacket) { return "ExplosionPacket"; }
        if (in instanceof FeaturesS2CPacket) { return "FeaturesPacket"; }
        if (in instanceof GameJoinS2CPacket) { return "GameJoinPacket"; }
        if (in instanceof GameMessageS2CPacket) { return "GameMessagePacket"; }
        if (in instanceof GameStateChangeS2CPacket) { return "GameStateChangePacket"; }
        if (in instanceof HealthUpdateS2CPacket) { return "HealthUpdatePacket"; }
        if (in instanceof InventoryS2CPacket) { return "InventoryPacket"; }
        if (in instanceof ItemPickupAnimationS2CPacket) { return "ItemPickupAnimationPacket"; }
        if (in instanceof KeepAliveS2CPacket) { return "KeepAlivePacket"; }
        if (in instanceof LightUpdateS2CPacket) { return "LightUpdatePacket"; }
        if (in instanceof LookAtS2CPacket) { return "LookAtPacket"; }
        if (in instanceof MapUpdateS2CPacket) { return "MapUpdatePacket"; }
        if (in instanceof NbtQueryResponseS2CPacket) { return "NbtQueryResponsePacket"; }
        if (in instanceof OpenHorseScreenS2CPacket) { return "OpenHorseScreenPacket"; }
        if (in instanceof OpenScreenS2CPacket) { return "OpenScreenPacket"; }
        if (in instanceof OpenWrittenBookS2CPacket) { return "OpenWrittenBookPacket"; }
        if (in instanceof OverlayMessageS2CPacket) { return "OverlayMessagePacket"; }
        if (in instanceof ParticleS2CPacket) { return "ParticlePacket"; }
        if (in instanceof PlayerAbilitiesS2CPacket) { return "PlayerAbilitiesPacket"; }
        if (in instanceof PlayerActionResponseS2CPacket) { return "PlayerActionResponsePacket"; }
        if (in instanceof PlayerListHeaderS2CPacket) { return "PlayerListHeaderPacket"; }
        if (in instanceof PlayerListS2CPacket) { return "PlayerListPacket"; }
        if (in instanceof PlayerPositionLookS2CPacket) { return "PlayerPositionLookPacket"; }
        if (in instanceof PlayerRemoveS2CPacket) { return "PlayerRemovePacket"; }
        if (in instanceof PlayerRespawnS2CPacket) { return "PlayerRespawnPacket"; }
        if (in instanceof PlayerSpawnPositionS2CPacket) { return "PlayerSpawnPositionPacket"; }
        if (in instanceof PlayerSpawnS2CPacket) { return "PlayerSpawnPacket"; }
        if (in instanceof PlayPingS2CPacket) { return "PlayPingPacket"; }
        if (in instanceof PlaySoundFromEntityS2CPacket) { return "PlaySoundFromEntityPacket"; }
        if (in instanceof PlaySoundS2CPacket) { return "PlaySoundPacket"; }
        if (in instanceof ProfilelessChatMessageS2CPacket) { return "ProfilelessChatMessagePacket"; }
        if (in instanceof RemoveEntityStatusEffectS2CPacket) { return "RemoveEntityStatusEffectPacket"; }
        if (in instanceof RemoveMessageS2CPacket) { return "RemoveMessagePackage"; }
        if (in instanceof ResourcePackSendS2CPacket) { return "ResourcePackSendPacket"; }
        if (in instanceof ScoreboardDisplayS2CPacket) { return "ScoreboardDisplayPacket"; }
        if (in instanceof ScoreboardObjectiveUpdateS2CPacket) { return "ScoreboardObjectiveUpdatePacket"; }
        if (in instanceof ScoreboardPlayerUpdateS2CPacket) { return "ScoreboardPlayerUpdatePacket"; }
        if (in instanceof ScreenHandlerPropertyUpdateS2CPacket) { return "ScreenHandlerPropertyUpdatePacket"; }
        if (in instanceof ScreenHandlerSlotUpdateS2CPacket) { return "ScreenHandlerSlotUpdatePacket"; }
        if (in instanceof SelectAdvancementTabS2CPacket) { return "SelectAdvancementTabPacket"; }
        if (in instanceof ServerMetadataS2CPacket) { return "ServerMetadataPacket"; }
        if (in instanceof SetCameraEntityS2CPacket) { return "SetCameraEntityPacket"; }
        if (in instanceof SetTradeOffersS2CPacket) { return "SetTradeOffersPacket"; }
        if (in instanceof SignEditorOpenS2CPacket) { return "SignEditorOpenPacket"; }
        if (in instanceof SimulationDistanceS2CPacket) { return "SimulationDistancePacket"; }
        if (in instanceof StatisticsS2CPacket) { return "StatisticsPacket"; }
        if (in instanceof StopSoundS2CPacket) { return "StopSoundPacket"; }
        if (in instanceof SubtitleS2CPacket) { return "SubtitlePacket"; }
        if (in instanceof SynchronizeRecipesS2CPacket) { return "SynchroniseRecipesPacket"; }
        if (in instanceof SynchronizeTagsS2CPacket) { return "SynchroniseTagsPacket"; }
        if (in instanceof TeamS2CPacket) { return "TeamPacket"; }
        if (in instanceof TitleFadeS2CPacket) { return "TitleFadePacket"; }
        if (in instanceof TitleS2CPacket) { return "TitlePacket"; }
        if (in instanceof UnloadChunkS2CPacket) { return "UnloadChunkPacket"; }
        if (in instanceof UnlockRecipesS2CPacket) { return "UnlockRecipesPacket"; }
        if (in instanceof UpdateSelectedSlotS2CPacket) { return "UpdateSelectedSlotPacket"; }
        if (in instanceof VehicleMoveS2CPacket) { return "VehicleMovePacket"; }
        if (in instanceof WorldBorderCenterChangedS2CPacket) { return "WorldBorderCenterChangedPacket"; }
        if (in instanceof WorldBorderInitializeS2CPacket) { return "WorldBorderInitializePacket"; }
        if (in instanceof WorldBorderInterpolateSizeS2CPacket) { return "WorldBorderInterpolateSizePacket"; }
        if (in instanceof WorldBorderSizeChangedS2CPacket) { return "WorldBorderSizeChangedPacket"; }
        if (in instanceof WorldBorderWarningBlocksChangedS2CPacket) { return "WorldBorderWarningBlocksChangedPacket"; }
        if (in instanceof WorldBorderWarningTimeChangedS2CPacket) { return "WorldBorderWarningTimeChangedPacket"; }
        if (in instanceof WorldEventS2CPacket) { return "WorldEventPacket"; }
        if (in instanceof WorldTimeUpdateS2CPacket) { return "WorldTimeUpdatePacket"; }

        return "Packet";
    }
}
