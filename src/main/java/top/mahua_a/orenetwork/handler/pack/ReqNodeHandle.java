package top.mahua_a.orenetwork.handler.pack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import top.mahua_a.orenetwork.OreNetwork;
import top.mahua_a.orenetwork.node.Node;
import top.mahua_a.orenetwork.tlv.InvalidPacket;
import top.mahua_a.orenetwork.tlv.Packet;
import top.mahua_a.orenetwork.tlv.RecommendPacket;
import top.mahua_a.orenetwork.tlv.ReqNodePacket;
import top.mahua_a.orenetwork.util.ByteUtil;
import top.mahua_a.orenetwork.util.PacketHelper;

import java.util.ArrayList;

public class ReqNodeHandle implements PacketHandler{
    @Override
    public void handler(ChannelHandlerContext ctx, DatagramPacket msg) {
        Node node = OreNetwork.getNodeManager().findNode(msg.sender().getHostString(),msg.sender().getPort());
        if(node==null&&OreNetwork.isServer()){
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
        System.out.println("请求:"+count+"个节点");
        ArrayList<Node> r_nodes=OreNetwork.getNodeManager().RecommendNode(msg.sender().getHostString(),msg.sender().getPort());
        if(r_nodes.size()==0) {
            System.out.println("无节点可分配");
            return;
        }
        ArrayList<Node> t_nodes = new ArrayList<>();
        for(Node node_:r_nodes){
            t_nodes.add(node_);
            if(t_nodes.size()==64){
                PacketHelper.sendPacket(ctx.channel(),new RecommendPacket(t_nodes),msg.sender());
                t_nodes.clear();
            }
        }
        if(t_nodes.size()!=0){
            PacketHelper.sendPacket(ctx.channel(),new RecommendPacket(t_nodes),msg.sender());
            t_nodes.clear();
        }

    }
}
