package pl.otekplay.sectors.netty.web;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpServerInit extends ChannelInitializer<SocketChannel> {

    public static void main(String[] args) {
        ServerBootstrap b = new ServerBootstrap();
        b.group(new NioEventLoopGroup()).channel(NioServerSocketChannel.class).childHandler(new HttpServerInit());
        ChannelFuture future = b.bind(81).syncUninterruptibly();
        if (future.isSuccess()) {
            future.channel().closeFuture().syncUninterruptibly();
        } else {
            future.cause().printStackTrace();
        }
    }

    @Override// aaaa, bo skype :D
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpRequestDecoder());
        p.addLast(new HttpResponseEncoder());
        p.addLast(new HttpServerHandler());
    }
}
