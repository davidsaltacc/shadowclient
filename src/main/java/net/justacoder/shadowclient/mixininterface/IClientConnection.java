package net.justacoder.shadowclient.mixininterface;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.packet.Packet;

public interface IClientConnection {

    void createEventsForIncoming(boolean create);
    void createEventsForOutgoing(boolean create);

    void invokeChannelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet) throws Exception;

}
