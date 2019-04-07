package top.mahua_a.orenetwork.util;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
import top.mahua_a.orenetwork.tlv.Packet;

import java.net.InetSocketAddress;

public class PacketHelper {
    public static void sendPacket(Channel channel, Packet packet, String addr, int port){
        channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(packet.parse()),new InetSocketAddress(addr,port)));
    }
    public static void sendPacket(Channel channel, Packet packet, InetSocketAddress sender){
        channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(packet.parse()),sender));
    }
}
