package top.mahua_a.orenetwork.tlv;

import top.mahua_a.orenetwork.util.ByteUtil;

public class ShakeHandPacket implements Tlvbase{
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
