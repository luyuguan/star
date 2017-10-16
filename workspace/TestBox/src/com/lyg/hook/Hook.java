package com.lyg.hook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hook {

	public static void main(String[] args)
	{
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
			@Override
			public void run() {
				 Date currentTime = new Date();
			   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			   String dateString = formatter.format(currentTime);
			   byte[] data = dateString.getBytes();
				File file = new File("./test.txt");
				try {
					FileOutputStream fs = new FileOutputStream(file);
					fs.write(data);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}}));
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
}
