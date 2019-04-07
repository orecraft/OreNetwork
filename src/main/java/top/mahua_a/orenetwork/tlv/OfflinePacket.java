package top.mahua_a.orenetwork.tlv;

import top.mahua_a.orenetwork.util.ByteUtil;

public class OfflinePacket implements Tlvbase{
    private String packet =  "300001330003";
    @Override
    public byte[] parse() {
        return ByteUtil.toByteArray(packet);
    }

    @Override
    public String getName() {
        return "OfflinePacket";
    }
}
