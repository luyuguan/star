package com.lyg.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.lyg.db.DataBase;
import com.lyg.db.TableOperator;

public class DBTest {
	
	public static void main(String[] args)
	{
		System.out.println("db test start");
		//test();
		test2(20);
	}
	
	public static void readTest()
	{
		Connection con = DataBase.getConnection();
		if(con == null) {
			System.err.println("could not get connection");
			return;
		}
		try {
			PreparedStatement statement = con.prepareStatement("select * from studenttb where id=" + 1000);
			ResultSet result = statement.executeQuery();
			while(result.next())
			{
				String name = result.getString("name");
				int id = result.getInt("id");
				System.out.println("student id:" + id + " name:" + name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DataBase.closeConnection(con);
	}
	
	public static void test()
	{
		Connection con = DataBase.getConnection();
		if(con == null) {
			System.err.println("could not get connection");
			return;
		}
		
		try {
			PreparedStatement statement = con.prepareStatement("select * from studenttb");
			ResultSet result = statement.executeQuery();
			while(result.next())
			{
				String name = result.getString("name");
				int id = result.getInt("id");
				System.out.println("student id:" + id + " name:" + name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void test2(int times)
	{
		TableOperator table = new TableOperator();
		//table.createTable();
		
		int cpunum = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(cpunum);
		List<Future<Long>> result = new ArrayList<Future<Long>>();
		
		Long startTime = System.currentTimeMillis();
		
		for(int i = 0;i < times;i++)
		{
			Future<Long> future = executor.submit(new Callable<Long>() {
				@Override
				public Long call() throws Exception {
					long start = System.currentTimeMillis();
					table.insert();
					long end = System.currentTimeMillis();
					return end - start;
				}});
			result.add(future);
		}
		executor.shutdown();
		try {
			while(!executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Long endTime = System.currentTimeMillis(); 
		
		long num = 0;
		try {
			for(int i = 0;i < result.size();i++)
			{
				num += result.get(i).get();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		System.out.println("TotalTime:" + num);
		System.out.println("Time:" + (endTime - startTime));
	}
	
}
