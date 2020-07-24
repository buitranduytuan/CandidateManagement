/**
 *@author TuanBTD
 *@date Dec 30, 2019
 *@version version
 */

package day2.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
	public static Connection getMyConnect() {
		Connection conn = null;
		String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=CandidateDB;user=sa;password=sa";
//		String hostName = "localhost";
//		String database = "CandidateDB";
//		String sqlInstanceName = "SQLEXPRESS";
//		String dbURL  = "jdbc:jtds:sqlserver://" + hostName + ":1433/"+ database + ";instance=" + sqlInstanceName;
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			 Class.forName("net.sourceforge.jtds.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, "sa", "sa");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
}