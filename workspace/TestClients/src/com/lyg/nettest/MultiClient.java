package com.lyg.nettest;

import java.util.ArrayList;
import java.util.List;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class MultiClient {

	NioEventLoopGroup loop;
	List<ChannelFuture> list;
	int num;

	public static int port = 9797;
		

	public MultiClient(int number)
	{
		this.num = number;
	}
	
	
	void start()
	{
		loop = new NioEventLoopGroup();
		list = new ArrayList<ChannelFuture>();
		for(int i = 0;i < num;i++)
		{
			startOne();
		}
		System.out.println("connection is done");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		SendTest.startSend();
		
		for(int i = 0;i < num;i++)
		{
			ChannelFuture future = list.get(i);
			try {
				future.channel().closeFuture().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		loop.shutdownGracefully();
	}
	
	void startOne()
	{
		Bootstrap boot = new Bootstrap();
		boot.group(loop);
		boot.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024*1024, 0, 4, 0, 4));
					ch.pipeline().addLast(new ClientHandler());
				}
			});
		
		try {
			ChannelFuture future = boot.connect(ClientTest.ip, MultiClient.port).sync();
			list.add(future);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	void SendMsg()
	{
		
	}
}
