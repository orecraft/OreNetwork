package top.mahua_a.orenetwork.tlv;

import top.mahua_a.orenetwork.util.ByteUtil;

public class HeartBeatPacket implements Tlvbase {
    private String packet = "3000001000203";
    @Override
    public byte[] parse() {
        return ByteUtil.toByteArray(packet);
    }

    @Override
    public String getName() {
        return "HeartBeat Packet";
    }
}
