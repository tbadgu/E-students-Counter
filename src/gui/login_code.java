package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.ConnectionManager;

public class login_code 
{
	private String username1;
	private String password;
	private String reg;
	Connection conn;
	
	Log4j lj = new Log4j();
	
	public login_code(String u,String p)
	{
		username1=u;
		password=p;
		String conn_string="jdbc:mysql://"+constants.serverip+"/student_counter";
		ConnectionManager.getinstance().setCredentials(conn_string, constants.mysqluser ,constants.mysqlpasswd);
		conn=ConnectionManager.getinstance().getConnection();
	}
	
	public boolean auth_registered()
	{
		conn=ConnectionManager.getinstance().getConnection();
		if(conn!=null)
		{
			String sql="select * from login_db where username=?";
			try{
					PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	
					stmt.setString(1, username1);
					ResultSet rs=stmt.executeQuery();
					rs.last();
					if(rs.getRow()==1)
					{
						rs.first();
						reg=rs.getString(3);
						if(reg.equals("TRUE"))
							return true;
						else
							return false;
					}
					else
					{
						return false;					}
				} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		return false;
	}
	
	public boolean auth_user()
	{
		conn=ConnectionManager.getinstance().getConnection();
		if(conn!=null)
		{
			String sql="select * from login_db where username=?";
			try{
					PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	
					stmt.setString(1, username1);
					ResultSet rs=stmt.executeQuery();
					rs.last();
					if(rs.getRow()==1)
					{
						rs.first();
						String temp_password=rs.getString(2);
						if(password.equals(temp_password))
							return true;
						else
							return false;
					}
					else
					{
						return false;					}
				} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		return false;
	}
	
	public String get_type()
	{
		lj.infor("User logged in : "+username1);
		conn=ConnectionManager.getinstance().getConnection();
		if(conn!=null)
		{
			String sql="select * from login_db where username=?";
			try{
					PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	
					stmt.setString(1, username1);
					ResultSet rs=stmt.executeQuery();
					rs.last();
					if(rs.getRow()==1)
					{
						rs.first();
						String type=rs.getString(4);
						return type;
					}
			}
				 catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		return null;
	}


}
