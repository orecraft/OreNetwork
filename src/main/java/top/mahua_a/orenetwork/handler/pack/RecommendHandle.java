package top.mahua_a.orenetwork.handler.pack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import top.mahua_a.orenetwork.OreNetwork;
import top.mahua_a.orenetwork.node.Node;
import top.mahua_a.orenetwork.tlv.InvalidPacket;
import top.mahua_a.orenetwork.tlv.ShakeHandPacket;
import top.mahua_a.orenetwork.util.ByteUtil;
import top.mahua_a.orenetwork.util.PacketHelper;

public class RecommendHandle implements PacketHandler{
    @Override
    public void handler(ChannelHandlerContext ctx, DatagramPacket msg) {
        ByteBuf buf = msg.content();
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        if(data.length<13){
            PacketHelper.sendPacket(ctx.channel(),new InvalidPacket(),msg.sender());
        }
        int count = ByteUtil.byteToInt(data[5]);
        if(data.length!=6+1+6*count){
            PacketHelper.sendPacket(ctx.channel(),new InvalidPacket(),msg.sender());
            return;
        }
        for(int x =1;x<=count;x++){
            byte[] addr_port = ByteUtil.readBytes(data,6*x,6);
            String addr = ByteUtil.bytesToip(ByteUtil.readBytes(addr_port,0,4));
            int port = Integer.parseInt(ByteUtil.toHexString(ByteUtil.readBytes(addr_port,4,2)),16);
            Node node = OreNetwork.getNodeManager().findNode(addr,port);
            if(node!=null) {
                PacketHelper.sendPacket(ctx.channel(), new ShakeHandPacket(), addr, port);
            }
        }
    }
}
