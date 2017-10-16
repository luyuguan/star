package com.lyg.db;

import java.sql.Connection;

public interface IDataBaseExecuteQuery {
	Object executeQuery(Connection con) throws Exception;
}
