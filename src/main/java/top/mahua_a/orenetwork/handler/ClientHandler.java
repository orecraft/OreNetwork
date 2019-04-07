package top.mahua_a.orenetwork.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import top.mahua_a.orenetwork.Main;
import top.mahua_a.orenetwork.tlv.InvalidPacket;
import top.mahua_a.orenetwork.tlv.ShakeHandPacket;
import top.mahua_a.orenetwork.util.ByteUtil;

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
                DatagramPacket reply = new DatagramPacket(Unpooled.copiedBuffer(new ShakeHandPacket().parse()), msg.sender());
                System.out.println(msg.sender().getAddress().getHostAddress()+":"+msg.sender().getPort());
                ctx.writeAndFlush(reply);
                break;
            case "0002":
                System.out.println("心跳包");
                break;
            case "0003":
                System.out.println("退出(debug)");
                Main.oreNetwork.shutdown();
                break;
        }
    }
}
