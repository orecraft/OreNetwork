package top.mahua_a.orenetwork.tlv;

import top.mahua_a.orenetwork.util.ByteUtil;

public class RecommendPacket implements Tlvbase{
    private String head="3000010003";
    private String ip="";
    private String port="";
    private String end="03";
    public RecommendPacket(String ip,int port){
        this.ip= ByteUtil.ipToLong(ip);
        this.port=Integer.toHexString(port);
    }
    @Override
    public byte[] parse() {
        return new byte[0];
    }

    @Override
    public String getName() {
        return null;
    }
}
