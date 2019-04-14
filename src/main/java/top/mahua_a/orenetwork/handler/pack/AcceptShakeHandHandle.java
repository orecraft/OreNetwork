package top.mahua_a.orenetwork.handler.pack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import top.mahua_a.orenetwork.OreNetwork;
import top.mahua_a.orenetwork.node.Node;

public class AcceptShakeHandHandle implements PacketHandler{
    @Override
    public void handler(ChannelHandlerContext ctx, DatagramPacket msg) {
        System.out.println("对方同意握手，将对方添加到节点列表");
        OreNetwork.getNodeManager().addNode(new Node(msg.sender().getHostString(),msg.sender().getPort(),false));
    }
}
