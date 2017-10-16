import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import com.lyg.dbtest.DBTest;
import com.lyg.net.RemoteConnection;
import com.lyg.net.Server;

public class Main {
	public static void main(String[] args) 
	{
		init();
		Server server = new Server();
		server.start();
	}
	
	private static void init()
	{
		int sampleNum = 0;
		try {
			InputStream file = new FileInputStream("./test.properties");
			Properties pro = new Properties();
			pro.load(file);
			int numPerSec = Integer.parseInt(pro.getProperty("countPerSec"));
			int clientsNum = Integer.parseInt(pro.getProperty("clientNum"));
			Server.port = Integer.parseInt(pro.getProperty("port"));
			RemoteConnection.NumPerSec = numPerSec;
			RemoteConnection.ClientsNum = clientsNum;
			sampleNum = Integer.parseInt(pro.getProperty("sampleNum"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		Random random = new Random();

		List<Integer> sample = new ArrayList<Integer>();
		RemoteConnection.samples = sample;
		System.out.print("random:");
		for(int i = 0;i < sampleNum;i++)
		{
			int value = random.nextInt(RemoteConnection.ClientsNum);
			sample.add(value);
			System.out.print("" + value + " ");
		}
		System.out.println("");
	}
}
