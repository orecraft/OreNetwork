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
import top.mahua_a.orenetwork.node.Node;
import top.mahua_a.orenetwork.node.NodeManager;

import java.net.InetSocketAddress;

public class OreNetwork {
    private static Bootstrap bootstrap;
    private static Channel channelFuture;
    private static NodeManager nodeManager;
    public void start(){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(new ClientHandler());
            channelFuture = bootstrap.bind(1008).sync().channel();
            nodeManager=new NodeManager(channelFuture);
            nodeManager.addNode(new Node("127.0.0.1",5438));
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
