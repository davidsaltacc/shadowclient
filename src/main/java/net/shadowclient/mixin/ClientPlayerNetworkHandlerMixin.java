package net.shadowclient.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.command.CommandManager;
import net.shadowclient.main.event.EventManager;
import net.shadowclient.main.event.events.PacketSentEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

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

}
