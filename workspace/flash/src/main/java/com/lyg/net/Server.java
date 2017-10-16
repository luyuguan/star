package com.lyg.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class Server {
	
	public static int port = 9797;
	
	public void start()
	{
		realStart();
	}
	
	public void startAsync()
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				realStart();
			}}).start();
	}
	
	void realStart()
	{
		
		EventLoopGroup listen = new NioEventLoopGroup();
		EventLoopGroup workLoop = new NioEventLoopGroup();
		ServerBootstrap boot = new ServerBootstrap();
		boot.group(listen, workLoop)
			.channel(NioServerSocketChannel.class)
//			.option(ChannelOption.SO_REUSEADDR, true)
			.childOption(ChannelOption.TCP_NODELAY, true)
			.childHandler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024*1024*10, 0, 4, 0, 4));
					ch.pipeline().addLast(new RemoteConnection());
					//ch.pipeline().addLast(new SimpleHandler());
				}
			});
		
		try {
			ChannelFuture channelFuture = boot.bind(Server.port).sync();
			System.out.println("Server starts");
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		listen.shutdownGracefully();
		workLoop.shutdownGracefully();
	}
	
}
