package com.lyg.nettest;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.socket.SocketChannel;

public class SendTest {

	private static AtomicInteger integer = new AtomicInteger();
	private static boolean send = false;
	public static int sendTime = 10;
	
	public static void startSend()
	{
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		executor.scheduleAtFixedRate(()->{
			allSend();
		}, 0, 1000/sendTime, TimeUnit.MILLISECONDS);
	}
	
	public static void allSend()
	{
		List<ClientHandler> handlers = ClientHandler.handlers;
		for(int i = 0;i < handlers.size();i++)
		{
			ClientHandler handler = handlers.get(i);
			Send(handler);
		}
	}
	
	public static void Test(ClientHandler client)
	{
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		executor.scheduleAtFixedRate(()->{
//			if(send == true)
//				return;
//			send = true;
			for(int i = 0;i < 8000*10;i++)
			{
				Send(client);
			}
			client.socketChannel.flush();
		}, 0, 1, TimeUnit.SECONDS);
		
	}
	
	static void Send(ClientHandler client)
	{
		ByteBuf byteBuf = client.socketChannel.alloc().buffer();
		ByteWriter writer = new ByteWriter(byteBuf);
		writer.writeint(0);
		writer.writeint(10001003);
		writer.writeint(client.integer.getAndIncrement());
		int[] test = new int[5];
		Random random = new Random(128);
		int total = 0;
		for(int i = 0;i < 4;i++)
		{
			test[i] = random.nextInt();
			total += test[i];
		}
		test[4] = total;
		writer.writeintArray(test);
		long time = System.currentTimeMillis();
		int writeTime = (int)(time % 10000000);
		writer.writeint(writeTime);
		byteBuf.setInt(0, byteBuf.readableBytes() - 4);
		client.socketChannel.writeAndFlush(byteBuf);
	}
	
}
