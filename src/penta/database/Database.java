package penta.database;
import java.util.ArrayList;

import penta.database.core.ConnectionPool;
import penta.database.util.DtoManager;
import penta.database.util.StatementFactory;
import penta.database.util.Tuple;

import java.sql.*;

public class Database {
	
	private StatementFactory factory;
	private static Database instance;
	
	private Database() { factory = new StatementFactory(); }
	public static Database GetInstance()
	{
		if(instance == null)
			instance = new Database();
		
		return instance;
	}
	
	public <T> int Insert(T object) throws SQLException
	{
		Connection conn = null;
		PreparedStatement statement = null;
		DtoManager manager = new DtoManager(object);
		
		try{
			conn = ConnectionPool.getConnection();
			String tableName = manager.GetTableName();
			
			ArrayList<Tuple<String, String>> mapping = manager.Map();
			if(tableName == null)
				throw new IllegalArgumentException("Insert failed, table name is null");
			
			if(mapping == null)
				throw new IllegalArgumentException("Insert failed, mapping is null");
			
			String sql = "INSERT INTO " + tableName + " (";
			for(int i = 0; i < mapping.size() - 1; i++)
				sql += mapping.get(i).GetKey() + ",";
			sql+= mapping.get(mapping.size() - 1).GetKey() + ") VALUES(";
			
			for(int i = 0; i < mapping.size() - 1; i++)
				sql += "?,";
			sql += ("?);");
			
			int value = 0;
			statement = factory.CreateInsertStatement(conn, object, sql);
			value = statement.executeUpdate();
			conn.commit();
			
			ResultSet rs = statement.getGeneratedKeys();
			if(rs.next())
				value = rs.getInt(1);
			
			return value;
		}
		finally{
			try { if(statement != null) statement.close(); }
			finally { ConnectionPool.releaseConnection(conn); }
		}
	}
	
	public <T> void Update(T object) throws SQLException
	{
		Connection conn = null;
		PreparedStatement statement = null;
		DtoManager manager = new DtoManager(object);
		
		try{
			conn = ConnectionPool.getConnection();
			String tableName = manager.GetTableName();
			
			ArrayList<Tuple<String, String>> mapping = manager.Map();
			if(mapping == null)
				throw new IllegalArgumentException("Update failed, mapping is null");
			
			if(tableName  == null)
				throw new IllegalArgumentException("Update failed, table name is null");
			
			String sql = "UPDATE " + tableName + " SET ";
			for(int i = 0; i < mapping.size() - 1; i++)
				sql += mapping.get(i).GetKey() + "=?, ";
			sql += mapping.get(mapping.size() - 1).GetKey() + "=?";
			sql += " WHERE " + manager.GetPrimaryKeyName() + "=?;";
			
			statement = factory.CreateUpdateStatement(conn, object, sql);
			statement.executeUpdate();
			conn.commit();
		}
		finally{
			try { if(statement != null) statement.close(); }
			finally { ConnectionPool.releaseConnection(conn); }
		}
	}
	
	public <T> void Delete(T object) throws SQLException
	{
		Connection conn = null;
		PreparedStatement statement = null;
		DtoManager manager = new DtoManager(object);
		
		try{
			conn = ConnectionPool.getConnection();
			String tableName = manager.GetTableName();
			
			ArrayList<Tuple<String, String>> mapping = manager.Map();
			if(mapping == null)
				throw new IllegalArgumentException("Delete failed, mapping is null");
			
			if(tableName == null)
				throw new IllegalArgumentException("Delete failed, table name is null");
			
			String primaryKeyName = manager.GetPrimaryKeyName();
			if(primaryKeyName == null)
				throw new IllegalArgumentException("Delete failed, primary key name is null");
			
			String sql = "DELETE FROM " + tableName + " WHERE " + primaryKeyName + "=?;";
			
			statement = factory.CreateDeleteStatement(conn, object, sql);
			statement.executeUpdate();
			conn.commit();
		}
		finally{
			try { if(statement != null) statement.close(); }
			finally { ConnectionPool.releaseConnection(conn); }
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> Find(Object type, String query) throws SQLException
	{
		Connection conn = null;
		PreparedStatement statement = null;
		DtoManager manager = new DtoManager(type);
		
		conn = ConnectionPool.getConnection();
		String tableName = manager.GetTableName();
		if(tableName == null)
			throw new IllegalArgumentException("Error, table name is null");
		
		statement = factory.CreateRetriveStatement(conn, query);
		ResultSet result = statement.executeQuery();
		
		return (ArrayList<T>)DtoManager.MakeDtoObjectArray(result, type);
		
		/*
		try {
			conn = ConnectionPool.getConnection();
			String tableName = manager.GetTableName();
			if(tableName == null)
				throw new IllegalArgumentException("Error, table name is null");
			
			statement = factory.CreateRetriveStatement(conn, query);
			ResultSet result = statement.executeQuery();
			
			return (ArrayList<T>)DtoManager.MakeDtoObjectArray(result, type);
		}
		finally {
			try { if(statement != null) statement.close(); }
			finally { ConnectionPool.releaseConnection(conn); }
		}
		*/
	}
}
