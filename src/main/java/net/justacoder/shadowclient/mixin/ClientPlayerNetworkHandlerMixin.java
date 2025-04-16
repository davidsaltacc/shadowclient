package net.justacoder.shadowclient.mixin;

import com.mojang.brigadier.ParseResults;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.command.CommandManager;
import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.ChunkDeltaUpdateEvent;
import net.justacoder.shadowclient.main.event.events.PacketSentEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayerNetworkHandlerMixin extends ClientCommonNetworkHandler implements ClientPlayPacketListener, TickablePacketListener {

    @Shadow protected abstract ParseResults<CommandSource> parse(String command);

    protected ClientPlayerNetworkHandlerMixin(MinecraftClient client, ClientConnection connection, ClientConnectionState connectionState) {
        super(client, connection, connectionState);
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void sendMessage(String content, CallbackInfo ci) {
        if (SCMain.interceptMessage(content)) {
            CommandManager.execute(content);
            ci.cancel();
        }
    }

    @Inject(method = "onGameJoin", at = @At(value = "TAIL"))
    private void onGameJoined(GameJoinS2CPacket packet, CallbackInfo ci) {
        SCMain.onWorldJoined();
    }

    @Override
    public void sendPacket(Packet<?> packet) {
        PacketSentEvent event = new PacketSentEvent(packet);
        EventManager.fireEvent(event);
        if (event.cancelled) {
            return;
        }
        super.sendPacket(packet);
    }

    @Inject(method = "onChunkDeltaUpdate", at = @At("HEAD"))
    private void onChunkDeltaData(ChunkDeltaUpdateS2CPacket packet, CallbackInfo ci) {
        Map<BlockPos, BlockState> delta = new ConcurrentHashMap<>();
        packet.visitUpdates(delta::put);
        EventManager.fireEvent(new ChunkDeltaUpdateEvent(delta));
    }

}
