package top.mahua_a.orenetwork.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Udp{
    private IPacketHandler rev;
    private DatagramSocket datagramSocket;
    private Thread handle;
    public Udp(IPacketHandler rev) throws SocketException {
        this.rev = rev;
        datagramSocket = new DatagramSocket(1024);
        handle = new Thread(() -> handle());
        handle.start();
    }
    public void send(DatagramPacket packet) throws IOException {
        if(datagramSocket!=null)
            datagramSocket.send(packet);
    }
    private void handle(){
        System.out.println("Start packet handler");

        while (datagramSocket!=null&&(!datagramSocket.isClosed())) {

            try {
                byte[] buffer=new byte[1024];
                DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(packet);
                rev.handle(packet);
            } catch (IOException e) {
               // e.printStackTrace();
            }
        }
    }
    public boolean isClose(){
        return datagramSocket==null;
    }
    public void close(){
        this.datagramSocket.close();
        this.datagramSocket=null;
    }

}
