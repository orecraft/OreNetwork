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
    private Long lastreq=0L;
    private int max;
    private int Online;
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
    public void reqNode(){
        this.lastreq=System.currentTimeMillis();
    }
    public boolean couldReq(){
        if(server)
            return true;
        return  System.currentTimeMillis()-lastreq>=5000;
    }

    public int getMax() {
        return max;
    }

    public int getOnline() {
        return Online;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setOnline(int online) {
        Online = online;
    }
}
