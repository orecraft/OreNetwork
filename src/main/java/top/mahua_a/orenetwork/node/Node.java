/*
 * Created on 2019/4/20
 * Author: MaHua_A
 * Copyright 2019 by OreCraft Studio
 * DO NOT MODIFY THESE WORDS
 */



package top.mahua_a.orenetwork.node;

public class Node {
    private String address;
    private int port;
    private Long lasttime=System.currentTimeMillis();
    private boolean server;

    public Node(String address,int port,boolean isServer){
        this.address=address;
        this.port=port;
        this.server = isServer;
        if(isServer)
            lasttime=0L;
    }
    public void HeartBeat(){
        lasttime=System.currentTimeMillis();
    }
    public boolean isLive(){
        return System.currentTimeMillis()-lasttime<=30*1000; //30秒没有心跳则认为已死亡
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    public boolean isServer() {
        return server;
    }



}
