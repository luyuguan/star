package com.lyg.db;

import java.sql.Connection;

public interface IDataBaseExecute {
	void execute(Connection con) throws Exception;

}
