package com.lyg.innerstatic.test;

import java.io.IOException;

public class TestClass {

	private static class InnerClass
	{
		public static TestClass instance = new TestClass();
	}
	
	public static TestClass getInstance()
	{
		return InnerClass.instance;
	}
	
//	public static TestClass instance2 = new TestClass();
	
	private TestClass()
	{
		System.out.println("init testclass");
	}
	
	
	public static void main(String[] args)
	{
		System.out.println("start");
		TestClass.getInstance();
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
