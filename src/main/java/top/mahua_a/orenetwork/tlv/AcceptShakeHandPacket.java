package top.mahua_a.orenetwork.tlv;

import top.mahua_a.orenetwork.util.ByteUtil;

public class AcceptShakeHandPacket implements Packet {
    private String packet = "300001020103";
    @Override
    public byte[] parse() {
        return ByteUtil.toByteArray(packet);
    }

    @Override
    public String getName() {
        return "AcceptShakeHandPacket";
    }
}
