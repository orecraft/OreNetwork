/*
 * Created on 2019/4/20
 * Author: MaHua_A
 * Copyright 2019 by OreCraft Studio
 * DO NOT MODIFY THESE WORDS
 */


package top.mahua_a.orenetwork.handler.pack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import top.mahua_a.orenetwork.OreNetwork;
import top.mahua_a.orenetwork.node.Node;
import top.mahua_a.orenetwork.tlv.HolePunchingPacket;
import top.mahua_a.orenetwork.tlv.InvalidPacket;
import top.mahua_a.orenetwork.util.ByteUtil;
import top.mahua_a.orenetwork.util.PacketHelper;

public class HolePunchingCmdHandle implements PacketHandler{
    @Override
    public void handler(ChannelHandlerContext ctx, DatagramPacket msg) {
        Node node = OreNetwork.getNodeManager().findNode(msg.sender().getHostString(),msg.sender().getPort());
        if(node ==null){
            System.out.println("陌生节点的握手命令包将被忽略");
            return;
        }
        ByteBuf buf = msg.content();
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        if(data.length!=12){
            //无效的包
            PacketHelper.sendPacket(ctx.channel(),new InvalidPacket(),msg.sender());
            return;
        }
        String ip = ByteUtil.bytesToip(ByteUtil.readBytes(data,5,4));
        int port = Integer.parseInt( ByteUtil.toHexString(ByteUtil.readBytes(data,9,2)),16);
        PacketHelper.sendPacket(ctx.channel(),new HolePunchingPacket(),ip,port);
        System.out.println("接受到服务器打洞命令,现在向"+ip+":"+port+"打洞包");
    }
}
