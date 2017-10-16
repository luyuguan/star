package com.lyg.floattest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FloatTest {

	
	public static void main(String[] args)
	{
		long time = System.nanoTime();
		new FloatTest().Test();
		long time2 = System.nanoTime() - time;
		System.out.println("time:" + time2);
	}
	
	public void Test()
	{
		long time = System.nanoTime();
		float total = 10;
		for(int i = 0;i < 100000;i++)
		{
			total += i;
		}
		long spane = System.nanoTime() - time;
		System.out.println("time span" + spane);
	}
	
	
	public void Test2()
	{
		ExecutorService executor = Executors.newFixedThreadPool(4);
		List<Future<Long>> list = new ArrayList<Future<Long>>();
		int count = 100;
		for(int i = 0;i < count;i++)
		{
			Callable<Long> call = new Callable<Long>(){
				@Override
				public Long call() throws Exception {
					long total = 10;
					for(int i = 0;i < 1000;i++)
					{
						total += i;
					}
					return total;
				}
				
			};
			Future<Long> future = executor.submit(call);
			list.add(future);
		}
		long total = 0;
		for(int i = 0;i < count;i++)
		{
			try {
				total += list.get(i).get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("long:" + total);
	}
}
