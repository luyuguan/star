package com.lyg.dbtest;

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
import java.util.concurrent.atomic.AtomicInteger;

import com.lyg.db.DataBase;
import com.lyg.db.TableOperator;

public class DBTest {
	
	public static void main(String[] args)
	{
		System.out.println("db test start");
		int num = 1;
		System.out.println("thread num" + num);
		ExecutorService executor = Executors.newFixedThreadPool(num);
		for(int i = 0;i < num;i++)
		{
			executor.submit(new Runnable(){
				@Override
				public void run() {
					testWhole();
				}});
		}
		
		
//		System.out.println("second test");
//		for(int i = 0;i < num;i++)
//		{
//			executor.submit(new Runnable(){
//				@Override
//				public void run() {
//					testWhole();
//				}});
//		}
	}
	
	public static void testWhole()
	{
		int id = integer.incrementAndGet();
		testInit(id);
		insertEvaluate(id, id*10000);
		writeEvaluate(id, id*10000);
		readEvaluate(id, id*10000);
	}
	
	public static int testCount = 10000;
	
	public static AtomicInteger integer = new AtomicInteger();
	
	public static void activate()
	{
		Connection con = DataBase.getConnection();
		if(con == null) {
			System.err.println("could not get connection");
			return;
		}
		
		try {
			String sql = "select * from studenttb where id=0";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DataBase.closeConnection(con);
	}
	
	public static void testInit(int id)
	{
		Connection con = DataBase.getConnection();
		if(con == null) {
			System.err.println("could not get connection");
			return;
		}
		
		try {
			String sql = "drop table studenttb"+id;
			PreparedStatement statement = con.prepareStatement(sql);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DataBase.closeConnection(con);
		
		con = DataBase.getConnection();
		if(con == null) {
			System.err.println("could not get connection");
			return;
		}
		
		try {
			String sql = "create table studenttb"+id + " (id int primary key, age int, name char(20), otherinfo char(255))";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DataBase.closeConnection(con);
	}
	
	public static void insertEvaluate(int tid, int id)
	{
		DaoStudent std = new DaoStudent();
		std.id = id;
		std.name = "zhangsan";
		std.age = 16;
		String testinfo = "asdfasdfsdfsdafsadfsadfsadfsdf";
		for(int i = 0;i < 3;i++)
		{
			testinfo += testinfo;
		}
		std.otherInfo=testinfo;
		long timestart = System.currentTimeMillis();
		for(int i = 0;i < testCount;i++)
		{
			insertTest(tid, std);
			std.id += 1;
		}
		long span = System.currentTimeMillis() - timestart;
		
		System.out.println("thread" + Thread.currentThread().getName() + " id start:" + id + "insert time:" + span);
	}
	
	
	public static void insertTest(int tableid, DaoStudent std)
	{
		Connection con = DataBase.getConnection();
		if(con == null) {
			System.err.println("could not get connection");
			return;
		}
		
		try {
			String sql = "insert studenttb" + tableid + " (id, name, age, otherinfo) values(" + std.id + ",'" + std.name
					+ "'," + std.age + ",'" + std.otherInfo + "')";
					
			PreparedStatement statement = con.prepareStatement(sql);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DataBase.closeConnection(con);
	}
	
	public static void writeEvaluate(int tid, int id)
	{
		DaoStudent std = new DaoStudent();
		std.id = id;
		std.name = "zhangsan";
		std.age = 16;
		String testinfo = "asdfasdfsdfsdafsadfsadfsadfsdf";
		for(int i = 0;i < 3;i++)
		{
			testinfo += testinfo;
		}
		std.otherInfo=testinfo;
		long timestart = System.currentTimeMillis();
		for(int i = 0;i < testCount;i++)
		{
			writeTest(tid, std);
			std.id += 1;
		}
		long span = System.currentTimeMillis() - timestart;
		System.out.println("thread" + Thread.currentThread().getName() + " id:" + id + "write time:" + span);
	}
	
	public static void writeTest(int tid, DaoStudent std)
	{
		Connection con = DataBase.getConnection();
		if(con == null) {
			System.err.println("could not get connection");
			return;
		}
		
		try {
			String sql = "update studenttb" + tid + " set name='" + std.name + "',age=" + std.age 
					+ ",otherinfo='"+std.otherInfo+"' where id="+std.id;
			PreparedStatement statement = con.prepareStatement(sql);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DataBase.closeConnection(con);
	}
	
	public static void readEvaluate(int tid, int id)
	{
		long timestart = System.currentTimeMillis();
		int startid = id;
		for(int i = 0;i < testCount;i++)
		{
			readTest(tid, id);
			id+=1;
		}
		long span = System.currentTimeMillis() - timestart;
		System.out.println("thread" + Thread.currentThread().getName() + " id:" + startid + "read time:" + span);
	}
	
	public static void readTest(int tid, int studentid)
	{
		Connection con = DataBase.getConnection();
		if(con == null) {
			System.err.println("could not get connection");
			return;
		}
		try {
			PreparedStatement statement = con.prepareStatement("select * from studenttb"+ tid + " where id=" + studentid);
			ResultSet result = statement.executeQuery();
			while(result.next())
			{
				DaoStudent std = new DaoStudent();
				std.name = result.getString("name");
				std.id = result.getInt("id");
				std.age = result.getInt("age");
				std.otherInfo = result.getString("otherinfo");
				//System.out.println("student id:" + std.id + " name:" + std.name + "otherinfo:" + std.otherInfo);
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
