package top.mahua_a.orenetwork.node;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
import top.mahua_a.orenetwork.tlv.HeartBeatPacket;
import top.mahua_a.orenetwork.tlv.ShakeHandPacket;
import top.mahua_a.orenetwork.util.ByteUtil;
import top.mahua_a.orenetwork.util.PacketHelper;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class NodeManager {
    private Set<Node> nodes=new HashSet<>();
    private Channel channel;
    private Timer heartTimer = new Timer("HeartBeat");

    public NodeManager(Channel channel){
        this.channel=channel;
        heartTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                for(Node node:nodes){

                    if(node.isLive()){
                        PacketHelper.sendPacket(channel,new HeartBeatPacket(),node.getAddress(),node.getPort());
                        node.HeartBeat();
                    }else{
                        removeNode(node);
                    }
                }
            }
        },10000);
    }
    public void addNode(Node node){
        nodes.add(node);
    }
    public void removeNode(Node node){
        nodes.remove(node);
    }
}
