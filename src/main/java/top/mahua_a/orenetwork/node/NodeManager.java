package top.mahua_a.orenetwork.node;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
import top.mahua_a.orenetwork.tlv.AcceptShakeHandPacket;
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
    private int maxNode = 64;

    public NodeManager(Channel channel){
        this.channel=channel;
        heartTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                for(Node node:nodes){

                    if(node.isLive()){
                        PacketHelper.sendPacket(channel,new HeartBeatPacket(),node.getAddress(),node.getPort());
                        //应该在接收到心跳包后，执行心跳包逻辑
                        //node.HeartBeat();
                    }else{
                        removeNode(node);
                    }
                }
            }
        },10000);
    }
    public void setMaxNode(int maxNode){
        this.maxNode=maxNode;
    }
    public void addNode(Node node){
        if(nodes.size()>=maxNode)  //不能超过最大节点数
            return;
        if(findNode(node.getAddress(),node.getPort())!=null){
            return;
            //已经存在的节点不再添加
        }
        nodes.add(node);
        //回应对方，接受添加节点请求

    }
    public void removeNode(Node node){
        nodes.remove(node);
    }
    public Node findNode(String addr,int port){
        for(Node node:nodes){
            if(node.getAddress().equalsIgnoreCase(addr)){
                if(node.getPort()==port)
                    return node;
            }
        }
        return null;
    }
    public boolean shouldNode(){
        return nodes.size()<maxNode;
    }
}
