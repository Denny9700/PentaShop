package penta.database.core;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.LinkedList;
import java.util.List;

import penta.database.Configuration;

public class ConnectionPool {

	private static List<Connection> freeConnections;
	static{
		freeConnections = new LinkedList<Connection>();
		try { Class.forName("com.mysql.jdbc.Driver"); }
		catch(ClassNotFoundException e) { System.out.println("Driver not found: " + e.getMessage()); }
	}
	
	private static synchronized Connection createConnection() throws SQLException
	{
		Connection conn = null;
		String ipAddress = Configuration.IP_ADDRESS;
		String port = Configuration.PORT;
		String database = Configuration.DB_NAME;
		
		String connectionString = "jdbc:mysql://" + ipAddress + ":" + port + "/" + database;
		conn = DriverManager.getConnection(connectionString, Configuration.USERNAME, Configuration.PASSWORD);
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	public static synchronized Connection getConnection() throws SQLException
	{
		Connection conn;
		if(!freeConnections.isEmpty())
		{
			conn = (Connection)freeConnections.get(0);
			freeConnections.remove(0);
			
			try{
				if(conn.isClosed())
					conn = getConnection();
			} catch(SQLException e){
				conn.close();
				conn = getConnection();
			}
		}
		else
			conn = createConnection();
		
		return conn;
	}
	
	public static synchronized void releaseConnection(Connection conn)
	{
		if(conn != null)
			freeConnections.add(conn);
	}
	
}
