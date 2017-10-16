package com.lyg.hottest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import com.lyg.hotclass.HotAction;
import com.lyg.hotload.IAction;

public class HotLoadTest {

	public static IAction action;
	
	public static void main(String[] args)
	{	
		int byt;
		try {
			byt = System.in.read();
			while(byt > 0)
			{
				System.out.println("do it");
				doit();
				byt = System.in.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void doit()
	{
		File file = new File("/Users/champ/Desktop/hotaction.jar");
		URLClassLoader loader=null;
		try {
			URL url = file.toURI().toURL();
			loader = new URLClassLoader(new URL[]{url});
			Class<?> clazz = loader.loadClass("com.lyg.hotclass.HotAction");
			Object obj = clazz.newInstance();
			IAction action = (IAction)obj;
			action.invoke(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				if(loader != null)
				{
					loader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
