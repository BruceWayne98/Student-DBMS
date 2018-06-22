import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
@SuppressWarnings("serial")
public class ResultSetTableModel extends AbstractTableModel 
{
	public Connection connection;
	public Statement statement;
	public ResultSet resultset;
	public ResultSetMetaData metadata;
	private int rowcount;
	private boolean connected=false;
	public ResultSetTableModel(String url,String username,String password,String query)
	{
		try
		{
			connection=DriverManager.getConnection(url, username, password);
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			connected=true;
			setQuery(query);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
	public Class getColumnClass(int column)
	{
		if(!connected)
			throw new IllegalStateException("Database connection failed");
		try
		{	
			String classname=metadata.getColumnClassName(column+1);
			return Class.forName(classname);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return Object.class;
	}
	public int getColumnCount()
	{
		if(!connected)
			throw new IllegalStateException("Database connection failed");
		try
		{
			return metadata.getColumnCount();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}
	public int getRowCount()
	{
		if(!connected)
			throw new IllegalStateException("Database connection failed");
		return rowcount;
	}
	public String getColumnName(int column)
	{
		if(!connected)
			throw new IllegalStateException("Database connection failed");
		try
		{
			return metadata.getColumnName(column+1);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return "";
	}
	public Object getValueAt(int row,int column)
	{
		if(!connected)
			throw new IllegalStateException("Database connection failed");
		try
		{
			resultset.absolute(row+1);
			return resultset.getObject(column+1);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return "";
	}
	public void setQuery(String query)
	{
		if(!connected)
			throw new IllegalStateException("Database connection failed");
		try
		{
			statement.executeUpdate(query);
			resultset=statement.executeQuery(query);
			metadata=resultset.getMetaData();
			resultset.last();
			rowcount=resultset.getRow();
			fireTableStructureChanged();
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
	public void disconnect()
	{
		try
		{
			resultset.close();
			statement.close();
			connection.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			connected=false;
		}
	}
}
