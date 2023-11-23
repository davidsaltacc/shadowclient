package net.shadowclient.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.command.CommandManager;
import net.shadowclient.main.event.EventManager;
import net.shadowclient.main.event.events.ChunkDataEvent;
import net.shadowclient.main.event.events.ChunkDeltaUpdateEvent;
import net.shadowclient.main.event.events.PacketSentEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import java.util.HashMap;
import java.util.Map;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayerNetworkHandlerMixin {

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

    @Inject(method = "sendPacket(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onPacketSent(Packet<?> packet, CallbackInfo ci) {
        PacketSentEvent event = new PacketSentEvent(packet);
        EventManager.fireEvent(event);
        if (event.cancelled) {
            ci.cancel();
        }
    }

    @Inject(method = "onChunkData", at = @At("HEAD"))
    private void onChunkData(ChunkDataS2CPacket packet, CallbackInfo info) {
        WorldChunk chunk = SCMain.mc.world.getChunk(packet.getX(), packet.getZ());
        EventManager.fireEvent(new ChunkDataEvent(chunk));
    }

    @Inject(method = "onChunkDeltaUpdate", at = @At("HEAD"))
    private void onChunkDeltaData(ChunkDeltaUpdateS2CPacket packet, CallbackInfo ci) {
        Map<BlockPos, BlockState> delta = new HashMap<>();
        packet.visitUpdates(delta::put);
        EventManager.fireEvent(new ChunkDeltaUpdateEvent(delta));
    }

}
