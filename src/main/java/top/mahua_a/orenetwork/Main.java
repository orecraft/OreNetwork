package top.mahua_a.orenetwork;

import top.mahua_a.orenetwork.network.IPacketHandler;
import top.mahua_a.orenetwork.network.Udp;
import top.mahua_a.orenetwork.tlv.ShakeHandPacket;
import top.mahua_a.orenetwork.util.ByteUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;

import static java.lang.Thread.sleep;

public class Main {
    public static boolean shouldClose = false;

    public static void main(String[] args){
        Udp udp=null;
        try {
            udp=new Udp(new PackHandler());
        } catch (SocketException e) {
            e.printStackTrace();
        }
     //   udp.handle();

        byte[] bytes="hi".getBytes();

        try {
            bytes=new ShakeHandPacket().parse();
            udp.send(new DatagramPacket(bytes,bytes.length,InetAddress.getByName("127.0.0.1"),1024));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!shouldClose){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Packet not found!Waiting....");
        }
        System.out.println("Exiting...");


        udp.close();


    }

}
class PackHandler implements IPacketHandler{
    @Override
    public void handle(DatagramPacket packet) {
        byte[] bytes = new byte[packet.getLength()];
        System.arraycopy(packet.getData(),0,bytes,0,packet.getLength());


        if(bytes[0]==(byte) Integer.parseInt("30",16)&&bytes[packet.getLength()-1]==(byte) Integer.parseInt("03",16)){
            System.out.println("有效的数据包");
            byte[] cmd= ByteUtil.readBytes(bytes,3,2);
            if(ByteUtil.toHexString(cmd).equalsIgnoreCase("0001")){
                System.out.println("握手包（00 01）");
            }
        }else{
            System.out.println("无效的数据包");
        }
        if(new String(packet.getData(),0,packet.getLength()).equalsIgnoreCase("00")) {
            Main.shouldClose = true;
        }
    }
}
