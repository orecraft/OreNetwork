package top.mahua_a.orenetwork.handler.pack;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public interface PacketHandler {
    void handler(ChannelHandlerContext ctx, DatagramPacket msg);
}
