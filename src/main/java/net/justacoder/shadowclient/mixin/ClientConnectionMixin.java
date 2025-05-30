package net.justacoder.shadowclient.mixin;

import io.netty.channel.ChannelHandlerContext;
import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.event.events.PacketSentEvent;
import net.justacoder.shadowclient.mixininterface.IClientConnection;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.PacketReceivedEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin implements IClientConnection {

    @Shadow protected abstract void channelRead0(ChannelHandlerContext par1, Object par2) throws Exception;

    private boolean createReceivedEvents = true;
    private boolean createSentEvents = true;

    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/listener/PacketListener;accepts(Lnet/minecraft/network/packet/Packet;)Z", shift = At.Shift.AFTER), cancellable = true)
    private void onChannelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {

        if (ShadowClientMain.mc.player != null && (Object) this != ShadowClientMain.mc.player.networkHandler.getConnection()) {
            return;
        }

        if (createReceivedEvents) {
            PacketReceivedEvent event = new PacketReceivedEvent(packet);
            EventManager.fireEvent(event);

            if (event.cancelled) {
                ci.cancel();
            }
        }

    }

    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;Z)V", at = @At("HEAD"), cancellable = true)
    private void onSend(Packet<?> packet, @Nullable PacketCallbacks callbacks, boolean flush, CallbackInfo ci) {

        if (ShadowClientMain.mc.player != null && (Object) this != ShadowClientMain.mc.player.networkHandler.getConnection()) {
            return;
        }

        if (createSentEvents) {
            PacketSentEvent event = new PacketSentEvent(packet);
            EventManager.fireEvent(event);

            if (event.cancelled) {
                ci.cancel();
            }
        }
    }

    @Override
    public void createEventsForIncoming(boolean create) {
        this.createReceivedEvents = create;
    }

    @Override
    public void createEventsForOutgoing(boolean create) {
        this.createSentEvents = create;
    }

    @Override
    public void invokeChannelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet) throws Exception {
        channelRead0(channelHandlerContext, packet);
    }

}
