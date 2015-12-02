package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.ConnectionManager;

public class register_code {
	String conn_string;
	Connection conn;
	register_bean bean;
	String mail="null";
	String pass;
	
	public register_code(register_bean ip)
	{
		bean=ip;
		String conn_string="jdbc:mysql://"+constants.serverip+"/student_counter";
		ConnectionManager.getinstance().setCredentials(conn_string, constants.mysqluser ,constants.mysqlpasswd);
		conn=ConnectionManager.getinstance().getConnection();
	}
	
	public String auth_checkregistered()
	{
		conn=ConnectionManager.getinstance().getConnection();
		if(conn!=null)
		{
			String sql="select * from login_db where username=?";
			try{
					PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	
					stmt.setString(1, bean.getUsername());
					ResultSet rs=stmt.executeQuery();
					rs.last();
					if(rs.getRow()==1)
					{
						rs.first();
						String reg=rs.getString(3);
						if(reg.equals("TRUE"))
							return "true";
						else
						{
							mail=rs.getString(5);
							return "false";
						}
					}
					else
					{
						return "null";					}
				} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		return "null";
	}
	
	public boolean auth_submit()
	{

		String sql="insert into register_db "+
		"values(?,?,?,?,?,?,?,?,?,?,?,?)";
		ResultSet rs=null;
		
		try(
			PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			)
		{
			stmt.setString(1, bean.getUsername());
			stmt.setInt(2, bean.getRoll_number());
			stmt.setString(3, bean.getName());
			stmt.setInt(4, bean.getEnrollment_number());
			stmt.setDate(5, bean.getAdmission_date());
			stmt.setString(6, bean.getGender());
			stmt.setDate(7, bean.getDob());
			stmt.setString(8, bean.getReligion());
			stmt.setString(9, bean.getNationality());
			stmt.setString(10, bean.getFee_category());
			stmt.setLong(11, bean.getPhone_number());
			stmt.setString(12, bean.getAddress());
			
			int affected=stmt.executeUpdate();
			if(affected==1)
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			return false;
		}
		finally
		{
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public boolean auth_setregistered()
	{

		String sql="UPDATE login_db SET registered=?,password=? where username=?";
		ResultSet rs=null;
		password_generator obj=new password_generator();
		pass=obj.getRandomPassword();
		send_mail send_mail_obj=new send_mail();
		String sender="admin@gmail.com";
		String message="The Password for User "+bean.getUsername()+" is "+pass;
		send_mail_obj.mail(mail, sender, message);
		try(
			PreparedStatement stmt=conn.prepareStatement(sql);
			)
		{
			stmt.setString(1, "TRUE");
			stmt.setString(2, pass);
			stmt.setString(3, bean.getUsername());
			int affected=stmt.executeUpdate();
			if(affected==1)
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		finally
		{
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}



}
