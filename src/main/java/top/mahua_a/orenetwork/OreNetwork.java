package top.mahua_a.orenetwork;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import top.mahua_a.orenetwork.handler.ClientHandler;
import top.mahua_a.orenetwork.handler.pack.*;
import top.mahua_a.orenetwork.node.Node;
import top.mahua_a.orenetwork.node.NodeManager;

import java.util.ArrayList;
import java.util.List;

public class OreNetwork {
    private static Bootstrap bootstrap;
    private static Channel channel;
    private static NodeManager nodeManager;
    private static ClientHandler clientHandler;

    private static List<Node> seeds = new ArrayList<>();
    public static void start(boolean server){
        int port= 0;
        if(server)
            port = 1008;
        EventLoopGroup group = new NioEventLoopGroup();
        clientHandler = new ClientHandler();
        //注册包处理器
        clientHandler.regHandler("0001",new ShakeHandHandle());
        clientHandler.regHandler("0002",new HeartBeatHandle());
        clientHandler.regHandler("0201",new AcceptShakeHandHandle());
        clientHandler.regHandler("3306",new HolePunchingHandle());
        clientHandler.regHandler("3307",new HolePunchingCmdHandle());
        clientHandler.regHandler("0004",new ReqNodeHandle());
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(clientHandler);
            channel = bootstrap.bind(port).sync().channel();
            nodeManager=new NodeManager(channel);
            //初始化工作
            if(!server) {
                //如果不是常驻节点，将加入种子节点
                for(Node node : seeds){
                    nodeManager.addNode(node);
                }
            }
            channel.closeFuture().await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
    public static void shutdown(){
        nodeManager.shutdown();
        channel.close();
        nodeManager=null;
        channel=null;
        System.out.println("Bye Bye!");
    }

    public static Bootstrap getBootstrap() {
        return bootstrap;
    }

    public static Channel getChannel() {
        return channel;
    }

    public static NodeManager getNodeManager() {
        return nodeManager;
    }
    public static void addSeedNode(Node node){
        seeds.add(node);
    }
}
