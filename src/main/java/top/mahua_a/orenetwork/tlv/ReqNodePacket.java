/*
 * Created on 2019/4/20
 * Author: MaHua_A
 * Copyright 2019 by OreCraft Studio
 * DO NOT MODIFY THESE WORDS
 */


package top.mahua_a.orenetwork.tlv;

import top.mahua_a.orenetwork.util.ByteUtil;

public class ReqNodePacket implements Packet{
    private String packet_0="3000010004";
    private String packet_1="03";
    private int count = 0;

    public ReqNodePacket(int count){
        if(count>255){
            this.count=255;
        }else {
            this.count = count;
        }

    }
    @Override
    public byte[] parse() {
        return ByteUtil.toByteArray(packet_0+ByteUtil.countTohex(count)+packet_1);
    }

    @Override
    public String getName() {
        return "ReqNodePacket";
    }
}
