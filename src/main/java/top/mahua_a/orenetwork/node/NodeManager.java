/*
 * Created on 2019/4/20
 * Author: MaHua_A
 * Copyright 2019 by OreCraft Studio
 * DO NOT MODIFY THESE WORDS
 */

package top.mahua_a.orenetwork.node;

import io.netty.channel.Channel;
import top.mahua_a.orenetwork.OreNetwork;
import top.mahua_a.orenetwork.tlv.HeartBeatPacket;
import top.mahua_a.orenetwork.tlv.PingPacket;
import top.mahua_a.orenetwork.tlv.ReqNodePacket;
import top.mahua_a.orenetwork.tlv.ShakeHandPacket;
import top.mahua_a.orenetwork.util.PacketHelper;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class NodeManager {
    private Set<Node> nodes=new CopyOnWriteArraySet<>();
    private Channel channel;
    private Timer heartTimer = new Timer("HeartBeat");
    private int maxNode = 64;
    private int minNode = 16;
    private boolean busyMode = false;


    public NodeManager(Channel channel){
        this.channel=channel;

        heartTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("当前节点数："+nodes.size());
                for (Node node:nodes){

                    if(node.isLive()){
                        PacketHelper.sendPacket(channel,new HeartBeatPacket(),node.getAddress(),node.getPort());
                        //每次心跳后，发送一个ping
                        PacketHelper.sendPacket(channel,new PingPacket(),node.getAddress(),node.getPort());
                        //应该在接收到心跳包后，执行心跳包逻辑
                        //node.HeartBeat();
                    }else {
                        System.out.println("节点30s内无响应，此节点可能已经离线");
                        if(!node.isServer()) {
                            nodes.remove(node);
                        }else if(shouldNode()){
                            System.out.println("请求重新连接常驻节点");
                            PacketHelper.sendPacket(OreNetwork.getChannel(),new ShakeHandPacket(),node.getAddress(),node.getPort());
                        }
                    }
                }
                if(shouldNode()&&!OreNetwork.isServer()){
                    requestNodes(busyMode);
                }

            }
        },0,10000);

    }
    public void setMaxNode(int maxNode){
        this.maxNode=maxNode;
    }
    public void addNode(Node node){
        if(nodes.size()>=maxNode)  //不能超过最大节点数
            return;
        if(findNode(node.getAddress(),node.getPort())!=null) {
            return;
            //已经存在的节点不再添加
        }
            nodes.add(node);
    }
    public void removeNode(Node node){
        nodes.remove(node);
    }
    public Node findNode(String addr,int port){
        Iterator<Node> it = nodes.iterator();
        while(it.hasNext()){
            Node node = it.next();
            if(node.getAddress().equalsIgnoreCase(addr)){
                if(node.getPort()==port)
                    return node;
            }
        }
        return null;
    }
    public boolean shouldNode(){
        if(busyMode||OreNetwork.isServer()){
            return nodes.size()<maxNode;
        }

        return nodes.size()<minNode;
    }
    private void requestNodes(boolean max){
        if(nodes.size()==0)
            return;
        int requestCount;
        int needNode = 0;
        if(max) {
            needNode = maxNode - nodes.size();
            requestCount = (int) Math.ceil((double) ((maxNode - nodes.size()) / nodes.size()));
        }else{
            if(minNode<=nodes.size())
                return;
            needNode = minNode - nodes.size();
            requestCount = (int) Math.ceil((double) ((minNode - nodes.size()) / nodes.size()));
        }
        for (Node node : nodes) {
            if (node.isLive()&&node.couldReq()&&needNode>0) {
                node.reqNode();
                PacketHelper.sendPacket(channel, new ReqNodePacket(requestCount), node.getAddress(), node.getPort());
                System.out.println("请求节点("+needNode+")");
                needNode=needNode-requestCount;
            }
        }

    }
    public void shutdown(){
        heartTimer.cancel();
        nodes=null;
    }
    //获取可引荐的节点
    public ArrayList<Node> RecommendNode(String ip,int port){
        Iterator<Node> it = nodes.iterator();
        ArrayList<Node> r_nodes = new ArrayList<>();
        while (it.hasNext()){
            Node node = it.next();
            System.out.println(node.getOnline()+"/"+node.getMax());
            if(!node.isServer()&&node.isLive()&&(node.getOnline()<node.getMax())){
                if((!node.getAddress().equalsIgnoreCase(ip))||node.getPort()!=port) {
                    r_nodes.add(node);
                }
            }
        }
        return r_nodes;
    }
    public void setBusyMode(boolean status){
        this.busyMode = status;
    }
    public int getMaxNode(){
        return busyMode?this.maxNode:this.minNode;
    }
    public int getOnlineNode(){
        return nodes.size();
    }

}
