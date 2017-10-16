package com.lyg.redistest;

import java.util.HashMap;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisTest {

	public RedisTest()
	{
		
	}
	
	public void run()
	{
		Jedis jedis = new Jedis("localhost");
		jedis.set("name", "wudi");
		System.out.println(jedis.get("name"));
		HashMap<String, String> test = new HashMap<String, String>();
		test.values();
		test.put("lili", "a beautiful girl");
		test.put("jim", "a foreign boy");
		jedis.hmset("students", test);
		jedis.hset("students", "lili", "asdfasdf");
		HashMap<String, String> stuMap = (HashMap<String, String>) jedis.hgetAll("students");
		for(String str : stuMap.values())
		{
			System.out.println(str);
		}
	}
}
