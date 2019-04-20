/*
 * Created on 2019/4/20
 * Author: MaHua_A
 * Copyright 2019 by OreCraft Studio
 * DO NOT MODIFY THESE WORDS
 */


package top.mahua_a.orenetwork.tlv;

import top.mahua_a.orenetwork.util.ByteUtil;

public class ShakeHandPacket implements Packet {
    private String head = "30";
    private String end = "03";
    private String cmd = "0001";
    private String version = "0001";

    @Override
    public byte[] parse() {
        String packet = head+version+cmd+end;
        return ByteUtil.toByteArray(packet);
    }
    @Override
    public String getName() {
        return "Shake hand";
    }
}
