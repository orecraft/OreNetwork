package top.mahua_a.orenetwork.handler.pack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import top.mahua_a.orenetwork.OreNetwork;
import top.mahua_a.orenetwork.node.Node;
import top.mahua_a.orenetwork.tlv.AcceptShakeHandPacket;
import top.mahua_a.orenetwork.util.PacketHelper;

public class ShakeHandHandle implements PacketHandler{

    @Override
    public void handler(ChannelHandlerContext ctx, DatagramPacket msg) {
        System.out.println("握手包");
        if(!OreNetwork.getNodeManager().shouldNode())
            return;
        //如果节点数已满，则不需要新节点，忽略此包
        Node node= OreNetwork.getNodeManager().findNode(msg.sender().getHostString(),msg.sender().getPort());
        if(node!=null){
            //已有的节点无需握手，忽略此包
            System.out.println("节点中已存在此包");
            return;
        }
        OreNetwork.getNodeManager().addNode(new Node(msg.sender().getHostString(),msg.sender().getPort(),false));
        PacketHelper.sendPacket(ctx.channel(),new AcceptShakeHandPacket(),msg.sender().getHostString(),msg.sender().getPort());
        //发送同意包，并添加节点
    }
}
