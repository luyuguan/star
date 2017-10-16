package com.lyg.nettest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ClientHandler extends ByteToMessageDecoder {

	public SocketChannel socketChannel;
	
	public static List<ClientHandler> handlers = new ArrayList<ClientHandler>();	
	
	public static AtomicInteger clientIdCount = new AtomicInteger();
	public AtomicInteger integer = new AtomicInteger();
	
	float delayTime;
	long lastTime;
	
	public boolean logout = false;
	
	public static int countPerSec;
	public static int totalClientNum;
	
	public int id = 0;
	
	public static int sampleNum = 5;
	public static List<Integer> samples = new ArrayList<Integer>();
	private static boolean ContainsId(List<Integer> samples, int id)
	{
		for(int i = 0;i < samples.size();i++)
		{
			if(id == samples.get(i))
			{
				return true;
			}
		}
		return false;
	}
	public static void initSampels()
	{
		
		Random random = new Random();
		for(int i = 0;i < sampleNum;i++)
		{
			samples.add(random.nextInt(totalClientNum) + 1);
		}
		System.out.print("samples:");
		for(int i = 0;i < samples.size();i++)
		{
			System.out.print("" + samples.get(i) + " ");
		}
		System.out.println("");
	}
	
	
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		socketChannel = (SocketChannel) ctx.channel();
		id = clientIdCount.incrementAndGet();
		
		if(ContainsId(samples, id))
		{
			logout = true;
		}
		
		//SendTest.Test(this);
        ctx.fireChannelActive();
        synchronized(handlers)
        {
        	handlers.add(this);
        }
    }
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int id = in.readInt();
		if(id == 10001004)
		{
			receiveTest(in);
		}
	}
	
	void receiveTest(ByteBuf in)
	{
		int index = in.readInt();
		
		ByteReader reader = new ByteReader(in);
		int[] result = reader.readintArray();
		
		int total = 0;
		for(int i = 0;i < result.length-1;i++)
		{
			total += result[i];
		}
		
		if(total != result[result.length - 1])
		{
			System.err.println("data wrong");
		}
		
		
		
		int readTime = reader.readint();
		int curTime = (int)(System.currentTimeMillis() % 10000000);
		
		delayTime += curTime - readTime;
		
		if(logout == false)
			return;
	
		if(index % countPerSec == 0)
		{
			long curTime2 = System.currentTimeMillis();
			long span = curTime2 - lastTime;
			System.out.println("id:" + id + " get" + index + " msg, time:" + span
					+ " delay time:" + (delayTime*1.0f/countPerSec) );
			lastTime = curTime2;
			delayTime = 0;
		}
	}
	

}
