package top.mahua_a.orenetwork.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import top.mahua_a.orenetwork.tlv.InvalidPacket;
import top.mahua_a.orenetwork.tlv.ShakeHandPacket;
import top.mahua_a.orenetwork.util.ByteUtil;

import java.net.InetSocketAddress;

public class ClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buf = msg.copy().content();
        byte[] bytes=new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        if(
                bytes.length<6||
                bytes[0]!=(byte) Integer.parseInt("30",16)||
                bytes[bytes.length-1]!=(byte) Integer.parseInt("03",16)
        ){
            System.out.println("无效的数据");
            DatagramPacket reply = new DatagramPacket(Unpooled.copiedBuffer(new InvalidPacket().parse()), msg.sender());
            ctx.writeAndFlush(reply);
            return;
        }

        byte[] cmd= ByteUtil.readBytes(bytes,3,2);
        switch (ByteUtil.toHexString(cmd)){
            case "0001":
                System.out.println(msg.sender().getAddress().getHostAddress()+":"+msg.sender().getPort());
                ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(new ShakeHandPacket().parse()), msg.sender()));
                break;
            case "0002":
                System.out.println("心跳包");
                break;
            case "0003":
                System.out.println("引荐");
                if(bytes.length<12){
                    ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(new InvalidPacket().parse()), msg.sender()));
                    return;
                }
                byte[] ip_bytes=ByteUtil.readBytes(bytes,5,4);
                byte[] port_bytes=ByteUtil.readBytes(bytes,9,2);
                System.out.println(Integer.parseInt(ByteUtil.toHexString(port_bytes),16));
                System.out.println(ByteUtil.bytesToip(ip_bytes));
                ctx.writeAndFlush(
                        new DatagramPacket(Unpooled.copiedBuffer(new ShakeHandPacket().parse()), new InetSocketAddress(ByteUtil.bytesToip(ip_bytes),Integer.parseInt(ByteUtil.toHexString(port_bytes),16)))
                );
                break;
        }
    }
}