package com.lyg.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableOperator {

	final int Count = 20;
	
	String insertSql = null;
	
	public TableOperator()
	{
		createInsertSql();
	}
	
	void createInsertSql()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("INSERT testtb (");
		for(int i = 0;i < Count;i++)
		{
			stringBuilder.append('F');
			stringBuilder.append(i);
			if(i != Count - 1)
			{
				stringBuilder.append(',');
			}
			else
			{
				stringBuilder.append(')');
			}
		}
		stringBuilder.append(" values (");
		for(int i = 0;i < Count;i++)
		{
			stringBuilder.append('?');
			if(i != Count - 1)
			{
				stringBuilder.append(',');
			}
			else
			{
				stringBuilder.append(')');
			}
		}
		insertSql = stringBuilder.toString();
	}
	
	public void createTable()
	{
		Connection con = DataBase.getConnection();
		if(con == null)
		{
			return;
		}
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("CREATE TABLE testtb (id INT AUTO_INCREMENT PRIMARY KEY,");
		for(int i = 0;i < Count;i++)
		{
			stringBuilder.append('F');
			stringBuilder.append(i);
			stringBuilder.append(" INT");
			if(i != Count - 1)
			{
				stringBuilder.append(',');
			}
			else
			{
				stringBuilder.append(')');
			}
		}
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(stringBuilder.toString());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				if(statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DataBase.closeConnection(con);
		}
		
	}
	
	public void insert()
	{
		Connection con = DataBase.getConnection();
		if(con == null)
		{
			return;
		}
		
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(insertSql);
			for(int i = 0;i < Count;i++)
			{
				statement.setInt(i+1, i);
			}
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				if(statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DataBase.closeConnection(con);
		}
	}
}
