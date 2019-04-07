package top.mahua_a.orenetwork.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import top.mahua_a.orenetwork.handler.pack.PacketHandler;
import top.mahua_a.orenetwork.tlv.InvalidPacket;
import top.mahua_a.orenetwork.tlv.ShakeHandPacket;
import top.mahua_a.orenetwork.util.ByteUtil;
import top.mahua_a.orenetwork.util.PacketHelper;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private Map<String, PacketHandler> packetHandlerMap = new HashMap<>();
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
            //PacketHelper.sendPacket(ctx.channel(),new InvalidPacket(),msg.sender());
            return;
        }
        byte[] cmd= ByteUtil.readBytes(bytes,3,2);
        String cmd_str = ByteUtil.toHexString(cmd);
        PacketHandler packetHandler = packetHandlerMap.get(cmd_str);
        if(packetHandler!=null){
            packetHandler.handler(ctx, msg);
        }
    }
    public void regHandler(String cmd,PacketHandler handler){
        packetHandlerMap.put(cmd,handler);
    }
}
