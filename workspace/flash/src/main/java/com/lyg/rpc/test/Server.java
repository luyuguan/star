package com.lyg.rpc.test;

import com.baidu.jprotobuf.pbrpc.transport.RpcServer;

public class Server {

	public static void main(String[] args)
	{
		new Server().start();
	}
	
	public void start()
	{
		RpcServer rpcServer = new RpcServer();
		EchoServiceImpl echoServiceImpl = new EchoServiceImpl();
		rpcServer.registerService(echoServiceImpl);
		rpcServer.start(10101);
	}
}
