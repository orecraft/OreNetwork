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

import java.net.InetSocketAddress;

public class OreNetwork {
    private Bootstrap bootstrap;
    private Channel channelFuture;
    public void start(){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(new ClientHandler());
            Channel ch = bootstrap.bind(1008).sync().channel();
            channelFuture = ch;
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

}
