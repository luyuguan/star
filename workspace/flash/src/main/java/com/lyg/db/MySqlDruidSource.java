package com.lyg.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.sql.DataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public class MySqlDruidSource {

	private DataSource source;
	
	private static class DruidDataSourceHolder
	{
		public static MySqlDruidSource Instance = new MySqlDruidSource();
	}
	
	private MySqlDruidSource()
	{
		System.out.println("init data source");
		try {
			InputStream input = new FileInputStream("./dbconfig.properties");
			Properties p = new Properties();
			p.load(input);
			source = DruidDataSourceFactory.createDataSource(p);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MySqlDruidSource getInstance()
	{
		return DruidDataSourceHolder.Instance;
	}
	
	public DataSource getSource()
	{
		return source;
	}
}
