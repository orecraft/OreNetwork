/*
 * Created on 2019/4/20
 * Author: MaHua_A
 * Copyright 2019 by OreCraft Studio
 * DO NOT MODIFY THESE WORDS
 */


package top.mahua_a.orenetwork.tlv;

import top.mahua_a.orenetwork.node.Node;
import top.mahua_a.orenetwork.util.ByteUtil;

import java.util.List;

public class RecommendPacket implements Packet{
    private List<Node> nodes ;
    private String packet_0="3000010005";
    private String packet_1="03";
    public RecommendPacket(List<Node> nodes){
        this.nodes=nodes;
    }
    @Override
    public byte[] parse() {
        int count = nodes.size();
        StringBuilder sb =new StringBuilder();
        sb.append(ByteUtil.countTohex(count));

        for(Node node:nodes){
            sb.append(ByteUtil.ipToLong(node.getAddress()));
            sb.append(ByteUtil.portTohex(node.getPort()));
        }

        return ByteUtil.toByteArray(packet_0+sb.toString()+packet_1);
    }

    @Override
    public String getName() {
        return "RecommendPacket";
    }
}
