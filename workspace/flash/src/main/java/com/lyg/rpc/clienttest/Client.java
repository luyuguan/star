package com.lyg.rpc.clienttest;

import com.baidu.jprotobuf.pbrpc.client.ProtobufRpcProxy;
import com.baidu.jprotobuf.pbrpc.transport.RpcClient;
import com.lyg.rpc.test.EchoInfo;

public class Client {

	public void start()
	{
		RpcClient rpcClient = new RpcClient();
		// ����EchoService����
		ProtobufRpcProxy<EchoService> pbrpcProxy = new ProtobufRpcProxy<EchoService>(rpcClient, EchoService.class);
		pbrpcProxy.setPort(10101);
		// ��̬���ɴ���ʵ��
		EchoService echoService = pbrpcProxy.proxy();
		EchoInfo request = new EchoInfo();
		request.message = "hello";
		EchoInfo response = echoService.echo(request);
		System.out.println(response.message);
		rpcClient.stop();
	}
	
	public static void main(String[] args)
	{
		new Client().start();
	}
	
}
