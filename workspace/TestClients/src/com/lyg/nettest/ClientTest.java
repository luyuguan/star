package com.lyg.nettest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClientTest {

	public static int testClients = 10;
	public static String ip = "";
	
	public static void main(String[] args)
	{
		init();
		new MultiClient(testClients).start();
	}
	
	
	private static void init()
	{
		try {
			InputStream file = new FileInputStream("./test.properties");
			Properties pro = new Properties();
			pro.load(file);
			int numPerSec = Integer.parseInt(pro.getProperty("countPerSec"));
			int clientsNum = Integer.parseInt(pro.getProperty("clientNum"));
			MultiClient.port = Integer.parseInt(pro.getProperty("port"));
			SendTest.sendTime = numPerSec;
			ClientTest.testClients = clientsNum;
			ClientTest.ip = pro.getProperty("ip");
			ClientHandler.totalClientNum = clientsNum;
			ClientHandler.countPerSec = numPerSec;
		} catch (IOException e) {
			e.printStackTrace();
		}
		ClientHandler.initSampels();
	}
	
}
