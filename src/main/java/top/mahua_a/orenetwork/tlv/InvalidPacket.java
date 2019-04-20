/*
 * Created on 2019/4/20
 * Author: MaHua_A
 * Copyright 2019 by OreCraft Studio
 * DO NOT MODIFY THESE WORDS
 */


package top.mahua_a.orenetwork.tlv;

import top.mahua_a.orenetwork.util.ByteUtil;

public class InvalidPacket implements Packet {
    private String packet="300001000003";//cmd=0x00
    @Override
    public byte[] parse() {
        return ByteUtil.toByteArray(packet);
    }

    @Override
    public String getName() {
        return "Invalid Packet";
    }
}
