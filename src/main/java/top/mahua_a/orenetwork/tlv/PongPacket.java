/*
 * Created on 2019/4/20
 * Author: MaHua_A
 * Copyright 2019 by OreCraft Studio
 * DO NOT MODIFY THESE WORDS
 */


package top.mahua_a.orenetwork.tlv;

import top.mahua_a.orenetwork.util.ByteUtil;

public class PongPacket implements Packet{
    private int max = 0;
    private int online = 0;
    private String packet_0 = "3000012202";
    private String packet_1 = "03";

    public PongPacket(int max,int online){
        this.max = max>255?255:max;
        this.online = online>255?255:online;
    }
    @Override
    public byte[] parse() {
        return ByteUtil.toByteArray(packet_0+ByteUtil.countTohex(max)+ByteUtil.countTohex(online)+packet_1);
    }

    @Override
    public String getName() {
        return "PongPacket";
    }
}
