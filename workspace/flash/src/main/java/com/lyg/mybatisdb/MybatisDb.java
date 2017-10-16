package com.lyg.mybatisdb;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisDb {

	private SqlSessionFactory sessionFactory;
	
	private static class MybatisDbHandler
	{
		private static MybatisDb instance = new MybatisDb();
	}
	
	public static MybatisDb getInstance()
	{
		return MybatisDbHandler.instance;
	}
	
	private MybatisDb()
	{
		init();
	}
	
	private void init()
	{
		String resource = "xml/mybatisconfig.xml";
		InputStream inputStream = null;
		try {
			//inputStream = new FileInputStream(resource);
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		if(sessionFactory == null)
		{
			System.err.println("sqlsessionfactory init failed");
		}
	}
	
	public SqlSessionFactory getSessionFactory()
	{
		return this.sessionFactory;
	}
	
	public SqlSession getSqlSession()
	{
		return this.sessionFactory.openSession();
	}
	
	public static void main(String[] args)
	{
		test();
	}
	
	
	public static void test()
	{
		String resource = "xml/mybatisconfig.xml";
		InputStream inputStream = null;
		try {
			//inputStream = new FileInputStream(resource);
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		try {
		 String statement = "xml.usermapper.getStudent";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql
        Student user = session.selectOne(statement, 10001);
        System.out.println(user);
		} finally {
		  session.close();
		}
		
		session = sqlSessionFactory.openSession();
		try {
		 Mapper mapper = session.getMapper(Mapper.class);
         Student std =  mapper.getStudent(10002);
         System.out.println(std);
         Student s2 = new Student();
         s2.setId(111);
         s2.setName("asdfsdf");
         mapper.insertStudent(s2);
         Student s3 = mapper.getStudent(111);
         System.out.println(s3);
		} finally {
		  session.close();
		}
	}
	
}
