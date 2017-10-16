package com.lyg.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.lyg.db.DataBase;

public class LoginController {

	private Map<String, PlayerInfo> players = new ConcurrentHashMap<String, PlayerInfo>();
	
	public static PlayerInfo FindUser(String userId)
	{
		PlayerInfo info = FindUserFromDb2(userId);
		if(info == null)
		{
			info = new PlayerInfo();
			info.setUserId(userId);
			info.setCharacterName(userId);
			info.setLoginCount(1);
			InsertToDb(info);
		}
		else
		{
			info.setLoginCount(info.getLoginCount() + 1);
			UpdateToDb(info);
		}
		return info;
	}
	
	private static PlayerInfo FindUserFromDb(String userId)
	{
		Connection con = DataBase.getConnection();
		PlayerInfo ret = new PlayerInfo();
		ret.setUserId(userId);
		try {
			String sql = "select * from playerinfo_tb where user_id='" + userId+"'";
			PreparedStatement statement = con.prepareStatement(sql);
			//PreparedStatement statement = con.prepareStatement("select * from playerinfo_tb");
			ResultSet result = statement.executeQuery();
			if(!result.next())
			{
				return null;
			}
			ret.setCharacterName(result.getString("char_name"));
			ret.setLoginCount(result.getInt("login_count"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DataBase.closeConnection(con); 
		}
		return ret;
	}
	
	private static PlayerInfo FindUserFromDb2(String userId)
	{
		Object ret = DataBase.executeQuery((con) -> {
			String sql = "select * from playerinfo_tb where user_id='" + userId+"'";
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			if(result.next())
			{
				PlayerInfo info = new PlayerInfo();
				info.setUserId(userId);
				info.setCharacterName(result.getString("char_name"));
				info.setLoginCount(result.getInt("login_count"));
				return info;
			}
			else
			{
				return null;
			}
		});
		return (PlayerInfo)ret;
	}
	
	private static void InsertToDb(PlayerInfo playInfo)
	{
		String insert = "insert into playerinfo_tb (user_id, char_name, login_count) values('" + playInfo.getUserId() 
		+ "','" + playInfo.getCharacterName() +"'," + playInfo.getLoginCount() +  ")";
		DataBase.execute(insert);
	}
	
	private static void UpdateToDb(PlayerInfo playInfo)
	{
		String update = "update playerinfo_tb set char_name='" + playInfo.getCharacterName() + "', login_count=" + 
				playInfo.getLoginCount() + " where user_id='" + playInfo.getUserId() + "'";
		DataBase.execute(update);
	}
	
}
