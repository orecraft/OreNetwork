package top.mahua_a.orenetwork.handler.pack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import top.mahua_a.orenetwork.OreNetwork;
import top.mahua_a.orenetwork.node.Node;

public class HeartBeatHandle implements PacketHandler{
    @Override
    public void handler(ChannelHandlerContext ctx, DatagramPacket msg) {
        System.out.println("心跳包");
        Node node= OreNetwork.getNodeManager().findNode(msg.sender().getHostString(),msg.sender().getPort());
        if(node==null){
            //并非来自已知节点的包，忽略此包
            System.out.println("来自陌生节点的包");
            return;
        }
        node.HeartBeat();
    }
}
