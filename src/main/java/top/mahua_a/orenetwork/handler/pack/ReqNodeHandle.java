package top.mahua_a.orenetwork.handler.pack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import top.mahua_a.orenetwork.OreNetwork;
import top.mahua_a.orenetwork.node.Node;
import top.mahua_a.orenetwork.tlv.InvalidPacket;
import top.mahua_a.orenetwork.util.ByteUtil;
import top.mahua_a.orenetwork.util.PacketHelper;

public class ReqNodeHandle implements PacketHandler{
    @Override
    public void handler(ChannelHandlerContext ctx, DatagramPacket msg) {
        Node node = OreNetwork.getNodeManager().findNode(msg.sender().getHostString(),msg.sender().getPort());
        if(node==null){
            System.out.println("来自未成功握手的数据包，已忽略");
            return;
        }
        ByteBuf buf = msg.content();
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        if(data.length!=7){
            PacketHelper.sendPacket(ctx.channel(),new InvalidPacket(),msg.sender());
            return;
        }
        int count = ByteUtil.byteToInt(data[5]);
        System.out.println("请求了:"+count+"个节点");
    }
}
