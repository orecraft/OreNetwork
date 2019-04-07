package top.mahua_a.orenetwork;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import top.mahua_a.orenetwork.handler.ClientHandler;
import top.mahua_a.orenetwork.handler.pack.HeartBeatHandle;
import top.mahua_a.orenetwork.handler.pack.ShakeHandHandle;
import top.mahua_a.orenetwork.node.Node;
import top.mahua_a.orenetwork.node.NodeManager;

import java.net.InetSocketAddress;

public class OreNetwork {
    private static Bootstrap bootstrap;
    private static Channel channelFuture;
    private static NodeManager nodeManager;
    private static ClientHandler clientHandler;
    public void start(){
        EventLoopGroup group = new NioEventLoopGroup();
        clientHandler = new ClientHandler();
        //注册包处理器
        clientHandler.regHandler("0001",new ShakeHandHandle());
        clientHandler.regHandler("0002",new HeartBeatHandle());
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(clientHandler);
            channelFuture = bootstrap.bind(1008).sync().channel();
            nodeManager=new NodeManager(channelFuture);
            channelFuture.closeFuture().await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
    public void shutdown(){
        channelFuture.close();
    }

    public static Bootstrap getBootstrap() {
        return bootstrap;
    }

    public static Channel getChannel() {
        return channelFuture;
    }

    public static NodeManager getNodeManager() {
        return nodeManager;
    }
}
