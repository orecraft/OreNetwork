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
import top.mahua_a.orenetwork.tlv.PongPacket;
import top.mahua_a.orenetwork.util.PacketHelper;

public class PingHandle implements PacketHandler{
    @Override
    public void handler(ChannelHandlerContext ctx, DatagramPacket msg) {
        PacketHelper.sendPacket(ctx.channel(),new PongPacket(OreNetwork.getNodeManager().getMaxNode(),OreNetwork.getNodeManager().getOnlineNode()),msg.sender());
    }
}
