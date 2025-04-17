package net.justacoder.shadowclient.main.module.modules.other;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.*;
import net.minecraft.network.packet.c2s.config.ReadyC2SPacket;
import net.minecraft.network.packet.c2s.config.SelectKnownPacksC2SPacket;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import net.minecraft.network.packet.c2s.login.EnterConfigurationC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginKeyC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginQueryResponseC2SPacket;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.network.packet.c2s.query.QueryPingC2SPacket;
import net.minecraft.network.packet.c2s.query.QueryRequestC2SPacket;
import net.minecraft.network.packet.s2c.common.*;
import net.minecraft.network.packet.s2c.config.*;
import net.minecraft.network.packet.s2c.login.*;
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
import net.minecraft.network.packet.s2c.query.PingResultS2CPacket;
import net.minecraft.network.packet.s2c.query.QueryResponseS2CPacket;

@EventListener({PacketSentEvent.class, PacketRecievedEvent.class})
@SearchTags({"packet logger"})
public class PacketLogger extends Module {

    public final EnumSetting<Mode> MODE = new EnumSetting<>("Mode", Mode.ALL);
    public final StringSetting FILTER = new StringSetting("Filter");
    public final EnumSetting<FMode> FMODE = new EnumSetting<>("Filter Mode", FMode.WHITELIST);

    public PacketLogger() {
        super("packetlogger", ModuleCategory.OTHER);
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
        if (!FILTER.stringValue().isEmpty()) {
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

    public String translate(Packet<?> in) {

        if (in instanceof ClientOptionsC2SPacket) { return "common.ClientOptions"; }
        if (in instanceof CommonPongC2SPacket) { return "common.CommonPong"; }
        if (in instanceof CookieResponseC2SPacket) { return "common.CookieResponse"; }
        if (in instanceof CustomPayloadC2SPacket) { return "common.CustomPayload"; }
        if (in instanceof KeepAliveC2SPacket) { return "common.KeepAlive"; }
        if (in instanceof ResourcePackStatusC2SPacket) { return "common.ResourcePackStatus"; }

        if (in instanceof ReadyC2SPacket) { return "config.Ready"; }
        if (in instanceof SelectKnownPacksC2SPacket) { return "config.SelectKnownPacks"; }

        if (in instanceof HandshakeC2SPacket) { return "handshake.Handshake"; }

        if (in instanceof EnterConfigurationC2SPacket) { return "login.EnterConfiguration"; }
        if (in instanceof LoginHelloC2SPacket) { return "login.LoginHello"; }
        if (in instanceof LoginKeyC2SPacket) { return "login.LoginKey"; }
        if (in instanceof LoginQueryResponseC2SPacket) { return "login.LoginQueryResponse"; }

        if (in instanceof AcknowledgeChunksC2SPacket) { return "play.AcknowledgeChunks"; }
        if (in instanceof AcknowledgeReconfigurationC2SPacket) { return "play.AcknowledgeReconfiguration"; }
        if (in instanceof AdvancementTabC2SPacket) { return "play.AdvancementTab"; }
        if (in instanceof BoatPaddleStateC2SPacket) { return "play.BoatPaddleState"; }
        if (in instanceof BookUpdateC2SPacket) { return "play.BookUpdate"; }
        if (in instanceof ButtonClickC2SPacket) { return "play.ButtonClick"; }
        if (in instanceof ChatCommandSignedC2SPacket) { return "play.ChatCommandSigned"; }
        if (in instanceof ChatMessageC2SPacket) { return "play.ChatMessage"; }
        if (in instanceof ClickSlotC2SPacket) { return "play.ClickSlot"; }
        if (in instanceof ClientCommandC2SPacket) { return "play.ClientCommand"; }
        if (in instanceof ClientStatusC2SPacket) { return "play.ClientStatus"; }
        if (in instanceof CloseHandledScreenC2SPacket) { return "play.CloseHandledScreen"; }
        if (in instanceof CommandExecutionC2SPacket) { return "play.CommandExecution"; }
        if (in instanceof CraftRequestC2SPacket) { return "play.CraftRequest"; }
        if (in instanceof CreativeInventoryActionC2SPacket) { return "play.CreativeInventoryAction"; }
        if (in instanceof DebugSampleSubscriptionC2SPacket) { return "play.DebugSampleSubscription"; }
        if (in instanceof HandSwingC2SPacket) { return "play.HandSwing"; }
        if (in instanceof JigsawGeneratingC2SPacket) { return "play.JigsawGenerating"; }
        if (in instanceof MessageAcknowledgmentC2SPacket) { return "play.MessageAcknowledgment"; }
        if (in instanceof PlayerActionC2SPacket) { return "play.PlayerAction"; }
        if (in instanceof PlayerInputC2SPacket) { return "play.PlayerInput"; }
        if (in instanceof PlayerInteractBlockC2SPacket) { return "play.PlayerInteractBlock"; }
        if (in instanceof PlayerInteractEntityC2SPacket) { return "play.PlayerInteractEntity"; }
        if (in instanceof PlayerInteractItemC2SPacket) { return "play.PlayerInteractItem"; }
        if (in instanceof PlayerMoveC2SPacket) { return "play.PlayerMove"; }
        if (in instanceof PlayerSessionC2SPacket) { return "play.PlayerSession"; }
        if (in instanceof QueryBlockNbtC2SPacket) { return "play.QueryBlockNbt"; }
        if (in instanceof QueryEntityNbtC2SPacket) { return "play.QueryEntityNbt"; }
        if (in instanceof RecipeBookDataC2SPacket) { return "play.RecipeBookData"; }
        if (in instanceof RecipeCategoryOptionsC2SPacket) { return "play.RecipeCategoryOptions"; }
        if (in instanceof RenameItemC2SPacket) { return "play.RenameItem"; }
        if (in instanceof RequestCommandCompletionsC2SPacket) { return "play.RequestCommandCompletions"; }
        if (in instanceof SelectMerchantTradeC2SPacket) { return "play.SelectMerchantTrade"; }
        if (in instanceof SlotChangedStateC2SPacket) { return "play.SlotChangedState"; }
        if (in instanceof SpectatorTeleportC2SPacket) { return "play.SpectatorTeleport"; }
        if (in instanceof TeleportConfirmC2SPacket) { return "play.TeleportConfirm"; }
        if (in instanceof UpdateBeaconC2SPacket) { return "play.UpdateBeacon"; }
        if (in instanceof UpdateCommandBlockC2SPacket) { return "play.UpdateCommandBlock"; }
        if (in instanceof UpdateCommandBlockMinecartC2SPacket) { return "play.UpdateCommandBlockMinecart"; }
        if (in instanceof UpdateDifficultyC2SPacket) { return "play.UpdateDifficulty"; }
        if (in instanceof UpdateDifficultyLockC2SPacket) { return "play.UpdateDifficultyLock"; }
        if (in instanceof UpdateJigsawC2SPacket) { return "play.UpdateJigsaw"; }
        if (in instanceof UpdatePlayerAbilitiesC2SPacket) { return "play.UpdatePlayerAbilities"; }
        if (in instanceof UpdateSelectedSlotC2SPacket) { return "play.UpdateSelectedSlot"; }
        if (in instanceof UpdateSignC2SPacket) { return "play.UpdateSign"; }
        if (in instanceof UpdateStructureBlockC2SPacket) { return "play.UpdateStructureBlock"; }
        if (in instanceof VehicleMoveC2SPacket) { return "play.VehicleMove"; }

        if (in instanceof QueryPingC2SPacket) { return "query.QueryPing"; }
        if (in instanceof QueryRequestC2SPacket) { return "query.QueryRequest"; }

        if (in instanceof CommonPingS2CPacket) { return "common.CommonPing"; }
        if (in instanceof CookieRequestS2CPacket) { return "common.CookieRequest"; }
        if (in instanceof CustomPayloadS2CPacket) { return "common.CustomPayload"; }
        if (in instanceof CustomReportDetailsS2CPacket) { return "common.CustomReportDetails"; }
        if (in instanceof DisconnectS2CPacket) { return "common.Disconnect"; }
        if (in instanceof KeepAliveS2CPacket) { return "common.KeepAlive"; }
        if (in instanceof ResourcePackRemoveS2CPacket) { return "common.ResourcePackRemove"; }
        if (in instanceof ResourcePackSendS2CPacket) { return "common.ResourcePackSend"; }
        if (in instanceof ServerLinksS2CPacket) { return "common.ServerLinks"; }
        if (in instanceof ServerTransferS2CPacket) { return "common.ServerTransfer"; }
        if (in instanceof StoreCookieS2CPacket) { return "common.StoreCookie"; }
        if (in instanceof SynchronizeTagsS2CPacket) { return "common.SynchronizeTags"; }

        if (in instanceof DynamicRegistriesS2CPacket) { return "config.DynamicRegistries"; }
        if (in instanceof FeaturesS2CPacket) { return "config.Features"; }
        if (in instanceof ReadyS2CPacket) { return "config.Ready"; }
        if (in instanceof ResetChatS2CPacket) { return "config.ResetChat"; }
        if (in instanceof SelectKnownPacksS2CPacket) { return "config.SelectKnownPacks"; }

        if (in instanceof LoginCompressionS2CPacket) { return "login.LoginCompression"; }
        if (in instanceof LoginDisconnectS2CPacket) { return "login.LoginDisconnect"; }
        if (in instanceof LoginHelloS2CPacket) { return "login.LoginHello"; }
        if (in instanceof LoginQueryRequestS2CPacket) { return "login.LoginQueryRequest"; }
        if (in instanceof LoginSuccessS2CPacket) { return "login.LoginSuccess"; }

        if (in instanceof AdvancementUpdateS2CPacket) { return "play.AdvancementUpdate"; }
        if (in instanceof BlockBreakingProgressS2CPacket) { return "play.BlockBreakingProgress"; }
        if (in instanceof BlockEntityUpdateS2CPacket) { return "play.BlockEntityUpdate"; }
        if (in instanceof BlockEventS2CPacket) { return "play.BlockEvent"; }
        if (in instanceof BlockUpdateS2CPacket) { return "play.BlockUpdate"; }
        if (in instanceof BossBarS2CPacket) { return "play.BossBar"; }
        if (in instanceof BundleDelimiterS2CPacket) { return "play.BundleDelimiter"; }
        if (in instanceof BundleS2CPacket) { return "play.Bundle"; }
        if (in instanceof ChatMessageS2CPacket) { return "play.ChatMessage"; }
        if (in instanceof ChatSuggestionsS2CPacket) { return "play.ChatSuggestions"; }
        if (in instanceof ChunkBiomeDataS2CPacket) { return "play.ChunkBiomeData"; }
        if (in instanceof ChunkDataS2CPacket) { return "play.ChunkData"; }
        if (in instanceof ChunkDeltaUpdateS2CPacket) { return "play.ChunkDeltaUpdate"; }
        if (in instanceof ChunkLoadDistanceS2CPacket) { return "play.ChunkLoadDistance"; }
        if (in instanceof ChunkRenderDistanceCenterS2CPacket) { return "play.ChunkRenderDistanceCenter"; }
        if (in instanceof ChunkSentS2CPacket) { return "play.ChunkSent"; }
        if (in instanceof ClearTitleS2CPacket) { return "play.ClearTitles+"; }
        if (in instanceof CloseScreenS2CPacket) { return "play.CloseScreen"; }
        if (in instanceof CommandSuggestionsS2CPacket) { return "play.CommandSuggestions"; }
        if (in instanceof CommandTreeS2CPacket) { return "play.CommandTree"; }
        if (in instanceof CooldownUpdateS2CPacket) { return "play.CooldownUpdate"; }
        if (in instanceof CraftFailedResponseS2CPacket) { return "play.CraftFailedResponse"; }
        if (in instanceof DamageTiltS2CPacket) { return "play.DamageTilt"; }
        if (in instanceof DeathMessageS2CPacket) { return "play.DeathMessage"; }
        if (in instanceof DebugSampleS2CPacket) { return "play.DebugSample"; }
        if (in instanceof DifficultyS2CPacket) { return "play.Difficulty"; }
        if (in instanceof EndCombatS2CPacket) { return "play.EndCombat"; }
        if (in instanceof EnterCombatS2CPacket) { return "play.EnterCombat"; }
        if (in instanceof EnterReconfigurationS2CPacket) { return "play.EnterReconfiguration"; }
        if (in instanceof EntitiesDestroyS2CPacket) { return "play.EntitiesDestroy"; }
        if (in instanceof EntityAnimationS2CPacket) { return "play.EntityAnimation"; }
        if (in instanceof EntityAttachS2CPacket) { return "play.EntityAttach"; }
        if (in instanceof EntityAttributesS2CPacket) { return "play.EntityAttributes"; }
        if (in instanceof EntityDamageS2CPacket) { return "play.EntityDamage"; }
        if (in instanceof EntityEquipmentUpdateS2CPacket) { return "play.EntityEquipmentUpdate"; }
        if (in instanceof EntityPassengersSetS2CPacket) { return "play.EntityPassengersSet"; }
        if (in instanceof EntityPositionS2CPacket) { return "play.EntityPosition"; }
        if (in instanceof EntityS2CPacket) { return "play.Entity"; }
        if (in instanceof EntitySetHeadYawS2CPacket) { return "play.EntitySetHeadYaw"; }
        if (in instanceof EntitySpawnS2CPacket) { return "play.EntitySpawn"; }
        if (in instanceof EntityStatusEffectS2CPacket) { return "play.EntityStatusEffect"; }
        if (in instanceof EntityStatusS2CPacket) { return "play.EntityStatus"; }
        if (in instanceof EntityTrackerUpdateS2CPacket) { return "play.EntityTrackerUpdate"; }
        if (in instanceof EntityVelocityUpdateS2CPacket) { return "play.EntityVelocityUpdate"; }
        if (in instanceof ExperienceBarUpdateS2CPacket) { return "play.ExperienceBarUpdate"; }
        if (in instanceof ExperienceOrbSpawnS2CPacket) { return "play.ExperienceOrbSpawn"; }
        if (in instanceof ExplosionS2CPacket) { return "play.Explosion"; }
        if (in instanceof GameJoinS2CPacket) { return "play.GameJoin"; }
        if (in instanceof GameMessageS2CPacket) { return "play.GameMessage"; }
        if (in instanceof GameStateChangeS2CPacket) { return "play.GameStateChange"; }
        if (in instanceof HealthUpdateS2CPacket) { return "play.HealthUpdate"; }
        if (in instanceof InventoryS2CPacket) { return "play.Inventory"; }
        if (in instanceof ItemPickupAnimationS2CPacket) { return "play.ItemPickupAnimation"; }
        if (in instanceof LightUpdateS2CPacket) { return "play.LightUpdate"; }
        if (in instanceof LookAtS2CPacket) { return "play.LookAt"; }
        if (in instanceof MapUpdateS2CPacket) { return "play.MapUpdate"; }
        if (in instanceof NbtQueryResponseS2CPacket) { return "play.NbtQueryResponse"; }
        if (in instanceof OpenHorseScreenS2CPacket) { return "play.OpenHorseScreen"; }
        if (in instanceof OpenScreenS2CPacket) { return "play.OpenScreen"; }
        if (in instanceof OpenWrittenBookS2CPacket) { return "play.OpenWrittenBook"; }
        if (in instanceof OverlayMessageS2CPacket) { return "play.OverlayMessage"; }
        if (in instanceof ParticleS2CPacket) { return "play.Particle"; }
        if (in instanceof PlayerAbilitiesS2CPacket) { return "play.PlayerAbilities"; }
        if (in instanceof PlayerActionResponseS2CPacket) { return "play.PlayerActionResponse"; }
        if (in instanceof PlayerListHeaderS2CPacket) { return "play.PlayerListHeader"; }
        if (in instanceof PlayerListS2CPacket) { return "play.PlayerList"; }
        if (in instanceof PlayerPositionLookS2CPacket) { return "play.PlayerPositionLook"; }
        if (in instanceof PlayerRemoveS2CPacket) { return "play.PlayerRemove"; }
        if (in instanceof PlayerRespawnS2CPacket) { return "play.PlayerRespawn"; }
        if (in instanceof PlayerSpawnPositionS2CPacket) { return "play.PlayerSpawnPosition"; }
        if (in instanceof PlaySoundFromEntityS2CPacket) { return "play.PlaySoundFromEntity"; }
        if (in instanceof PlaySoundS2CPacket) { return "play.PlaySound"; }
        if (in instanceof ProfilelessChatMessageS2CPacket) { return "play.ProfilelessChatMessageS2CPacket"; }
        if (in instanceof ProjectilePowerS2CPacket) { return "play.ProjectilePower"; }
        if (in instanceof RemoveEntityStatusEffectS2CPacket) { return "play.RemoveEntityStatusEffect"; }
        if (in instanceof RemoveMessageS2CPacket) { return "play.RemoveMessage"; }
        if (in instanceof ScoreboardDisplayS2CPacket) { return "play.ScoreboardDisplay"; }
        if (in instanceof ScoreboardObjectiveUpdateS2CPacket) { return "play.ScoreboardObjectiveUpdate"; }
        if (in instanceof ScoreboardScoreResetS2CPacket) { return "play.ScoreboardScoreReset"; }
        if (in instanceof ScoreboardScoreUpdateS2CPacket) { return "play.ScoreboardScoreUpdate"; }
        if (in instanceof ScreenHandlerPropertyUpdateS2CPacket) { return "play.ScreenHandlerPropertyUpdate"; }
        if (in instanceof ScreenHandlerSlotUpdateS2CPacket) { return "play.ScreenHandlerSlotUpdate"; }
        if (in instanceof SelectAdvancementTabS2CPacket) { return "play.SelectAdvancementTab"; }
        if (in instanceof ServerMetadataS2CPacket) { return "play.ServerMetadata"; }
        if (in instanceof SetCameraEntityS2CPacket) { return "play.SetCameraEntity"; }
        if (in instanceof SetTradeOffersS2CPacket) { return "play.SetTradeOffers"; }
        if (in instanceof SignEditorOpenS2CPacket) { return "play.SignEditorOpen"; }
        if (in instanceof SimulationDistanceS2CPacket) { return "play.SimulationDistance"; }
        if (in instanceof StartChunkSendS2CPacket) { return "play.StartChunkSend"; }
        if (in instanceof StatisticsS2CPacket) { return "play.Statistics"; }
        if (in instanceof StopSoundS2CPacket) { return "play.StopSound"; }
        if (in instanceof SubtitleS2CPacket) { return "play.Subtitles"; }
        if (in instanceof SynchronizeRecipesS2CPacket) { return "play.SynchronizeRecipes"; }
        if (in instanceof TeamS2CPacket) { return "play.Team"; }
        if (in instanceof TickStepS2CPacket) { return "play.TickStep"; }
        if (in instanceof TitleFadeS2CPacket) { return "play.TitleFade"; }
        if (in instanceof TitleS2CPacket) { return "play.Title"; }
        if (in instanceof UnloadChunkS2CPacket) { return "play.UnloadChunks"; }
        if (in instanceof UpdateSelectedSlotS2CPacket) { return "play.UpdateSelectedSlot"; }
        if (in instanceof UpdateTickRateS2CPacket) { return "play.UpdateTickRate"; }
        if (in instanceof VehicleMoveS2CPacket) { return "play.VehicleMove"; }
        if (in instanceof WorldBorderCenterChangedS2CPacket) { return "play.WorldBorderCenterChanged"; }
        if (in instanceof WorldBorderInitializeS2CPacket) { return "play.WorldBorderInitialize"; }
        if (in instanceof WorldBorderInterpolateSizeS2CPacket) { return "play.WorldBorderInterpolateSize"; }
        if (in instanceof WorldBorderSizeChangedS2CPacket) { return "play.WorldBorderSizeChanged"; }
        if (in instanceof WorldBorderWarningBlocksChangedS2CPacket) { return "play.WorldBorderWarningBlocksChanged"; }
        if (in instanceof WorldBorderWarningTimeChangedS2CPacket) { return "play.WorldBorderWarningTimeChanged"; }
        if (in instanceof WorldEventS2CPacket) { return "play.WorldEvent"; }
        if (in instanceof WorldTimeUpdateS2CPacket) { return "play.WorldTimeUpdate"; }

        if (in instanceof PingResultS2CPacket) { return "query.PingResult"; }
        if (in instanceof QueryResponseS2CPacket) { return "query.QueryResponse"; }

        return "??? Packet";

    }
}
