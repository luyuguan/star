package com.lyg.net;

import java.nio.ByteOrder;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.lyg.login.LoginController;
import com.lyg.login.PlayerInfo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;

public class RemoteConnection extends ByteToMessageDecoder{

	public static int ClientsNum;
	public static int NumPerSec;
	
	public static AtomicInteger conNum = new AtomicInteger();
	
	public SocketChannel socketChannel;
	
	private long lastTime;
	
	private int delayTime=0;
	
	public boolean logout = false;
	
	public static List<Integer> samples;
	
	private int id = 0;
	
	public static boolean Contains(List<Integer> sample, int id)
	{
		for(int i = 0;i < sample.size();i++)
		{
			if(id == sample.get(i))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		int num = conNum.incrementAndGet();
		id = num;
		if(num % ClientsNum == 0)
		{
			System.out.print("connection clients num:" + num);
		}
		
		if(Contains(samples, id))
		{
			logout = true;
			System.out.println("id:" + id + "connected");
		}
		
		socketChannel = (SocketChannel) ctx.channel();
        ctx.fireChannelActive();
        lastTime = System.currentTimeMillis();
    }
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		protocolDeal(ctx, in);
		if(in.readableBytes() > 0)
		{
//			System.out.println("dont read done");
			in.skipBytes(in.readableBytes());
		}
	}
	
	void protocolDeal(ChannelHandlerContext ctx, ByteBuf in)
	{
		int id = in.readInt();
		
		if(id == 10001001)
		{
			int retCode = LoginDeal(ctx, in);
			if(retCode != 0)
			{
				
			}
		}
		if(id == 10001003)
		{
			receiveTest(in);
		}
	}
	
	int LoginDeal(ChannelHandlerContext ctx, ByteBuf in)
	{
		int retCode = 0;
		ByteReader byteReader = new ByteReader(in);
		String nameStr = byteReader.readString();
		
		PlayerInfo playInfo = LoginController.FindUser(nameStr);
		if(playInfo == null)
		{
			retCode = 2;
			return retCode;
		}
		ByteBuf byteBuf = UnpooledByteBufAllocator.DEFAULT.heapBuffer();
		ByteWriter writer = new ByteWriter(byteBuf);
		writer.writeint(0);
		writer.writeint(10001004);
		writer.writeString(playInfo.getCharacterName());
		writer.writeint(playInfo.getLoginCount());
		int total = byteBuf.readableBytes();
		byteBuf.setInt(0, total - 4);
		ctx.channel().writeAndFlush(byteBuf);
		return 0;
	}
	
	void receiveTest(ByteBuf in)
	{
		int index = in.readInt();
		int countnum = RemoteConnection.NumPerSec;
		
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
		
		SendBack(index, readTime);
		
		
//		if(logout == false)
//			return;
//		
//		if(index % countnum == 0)
//		{
//			long curTime2 = System.currentTimeMillis();
//			System.out.println("id:" + id + " get" + countnum + " msg, time:" + (curTime2 - lastTime) 
//					+ " delay time:" + (delayTime*1.0f/countnum) );
//			lastTime = curTime2;
//			delayTime = 0;
//		}
	}
	
	void SendBack(int index, int time)
	{
		ByteBuf byteBuf = socketChannel.alloc().buffer();
		ByteWriter writer = new ByteWriter(byteBuf);
		writer.writeint(0);
		writer.writeint(10001004);
		writer.writeint(index);
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
		writer.writeint(time);
		byteBuf.setInt(0, byteBuf.readableBytes() - 4);
		socketChannel.writeAndFlush(byteBuf);
	}
}
