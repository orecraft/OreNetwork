package top.mahua_a.orenetwork.handler.pack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import top.mahua_a.orenetwork.util.ByteUtil;

public class HolePunchingHandle implements PacketHandler{
    @Override
    public void handler(ChannelHandlerContext ctx, DatagramPacket msg) {
        //不需要任何处理
    }
}
