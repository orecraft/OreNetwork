package top.mahua_a.orenetwork.tlv;

import top.mahua_a.orenetwork.util.ByteUtil;

public class HolePunchingPacket implements Packet{
    private String packet = "300001330603";
    @Override
    public byte[] parse() {
        return ByteUtil.toByteArray(packet);
    }
    @Override
    public String getName() {
        return "HolePunchingPacket";
    }
}
