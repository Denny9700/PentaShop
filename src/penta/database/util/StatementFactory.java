package penta.database.util;
import java.util.ArrayList;
import java.sql.*;

public class StatementFactory {

	public PreparedStatement CreateInsertStatement(Connection conn, Object dto, String sql) throws SQLException
	{
		DtoManager manager = new DtoManager(dto);
		PreparedStatement preparedStatement = conn.prepareStatement(sql, new String[] {manager.GetPrimaryKeyName()});
		
		ArrayList<Tuple<String, String>> table = manager.Map();
		for(int i = 0; i < table.size(); i++)
			preparedStatement.setObject(i + 1, table.get(i).GetValue());
		
		return preparedStatement;
	}
	
	public PreparedStatement CreateDeleteStatement(Connection conn, Object dto, String sql) throws SQLException
	{
		DtoManager manager = new DtoManager(dto);
		PreparedStatement preparedStatement = conn.prepareStatement(sql, new String[] {manager.GetPrimaryKeyName()});
		
		String keyValue = manager.GetPrimaryKey();
		preparedStatement.setObject(1, keyValue);
		
		return preparedStatement;
	}
	
	public PreparedStatement CreateUpdateStatement(Connection conn, Object dto, String sql) throws SQLException
	{
		DtoManager manager = new DtoManager(dto);
		PreparedStatement preparedStatement = conn.prepareStatement(sql, new String[] {manager.GetPrimaryKeyName()});
		
		ArrayList<Tuple<String, String>> table = manager.Map();
		for(int i = 0; i < table.size(); i++)
			preparedStatement.setObject(i + 1, table.get(i).GetValue());
		preparedStatement.setObject(table.size() + 1, manager.GetPrimaryKey());
		
		return preparedStatement;
	}
	
	public PreparedStatement CreateRetriveStatement(Connection conn, String sql) throws SQLException
	{
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		return preparedStatement;
	}
	
}
