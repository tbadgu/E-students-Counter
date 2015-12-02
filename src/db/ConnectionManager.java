package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager
{
	private static ConnectionManager instance;
	private Connection conn=null;
	
	private String conn_string=null;
	private String username=null;
	private String password=null;
	
	private ConnectionManager()
	{
		
	}
	
	public static ConnectionManager getinstance()
	{
		if(instance==null)
		{
			instance=new ConnectionManager();
		}
		return instance;
	}
	
	public void setCredentials(String c,String u,String p)
	{
		conn_string=c;
		username=u;
		password=p;
	}
	public boolean openConnection()
	{
		try {
			conn=DriverManager.getConnection(conn_string, username, password);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public Connection getConnection()
	{
		if(conn==null)
		{
			if(openConnection())
				return conn;
			else
				return null;
		}
		return conn;
	}
	
	public void closeConnection()
	{
		System.out.println("Closing Connection");
		try {
			conn.close();
			conn=null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}