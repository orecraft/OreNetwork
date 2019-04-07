package top.mahua_a.orenetwork.tlv;

import top.mahua_a.orenetwork.util.ByteUtil;

public class RecommendPacket implements Packet {
    private String head="3000010003";
    private String ip="";
    private String port="";
    private String end="03";
    public RecommendPacket(String ip,int port){
        this.ip= ByteUtil.ipToLong(ip);
        byte[] port_byte=new byte[2];
        port_byte=ByteUtil.toByteArray(Integer.toHexString(port));
        this.port=ByteUtil.toHexString(port_byte);
    }
    @Override
    public byte[] parse() {
        return  ByteUtil.toByteArray(head+ip+port+end);
    }

    @Override
    public String getName() {
        return "RecommendPacket";
    }
}
