package com.lyg.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBase {

	public static void init()
	{
		MySqlDruidSource.getInstance();
	}
	
	public static Connection getConnection()
	{
		Connection ret = null;
		try {
			ret = MySqlDruidSource.getInstance().getSource().getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	
	public static void closeConnection(Connection con)
	{
		if(con == null)
			return;
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void execute(IDataBaseExecute operation)
	{
		Connection con = DataBase.getConnection();
		try {
			operation.execute(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DataBase.closeConnection(con); 
		}
	}
	
	public static void execute(String sql)
	{
		DataBase.execute((con)->{
			PreparedStatement statement = con.prepareStatement(sql);
			statement.execute();
		});;
	}
	
	public static Object executeQuery(IDataBaseExecuteQuery operation)
	{
		Connection con = DataBase.getConnection();
		Object ret = null;
		try {
			ret = operation.executeQuery(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DataBase.closeConnection(con); 
		}
		return ret;
	}
}
