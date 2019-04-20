/*
 * Created on 2019/4/20
 * Author: MaHua_A
 * Copyright 2019 by OreCraft Studio
 * DO NOT MODIFY THESE WORDS
 */


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
