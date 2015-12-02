package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.ConnectionManager;

public class forgotpassword_code {
	String username;
	String password;
	String mail;
	String sender;
	String message;
	send_mail send_mail_obj=new send_mail();
	Connection conn;
	
	session session_obj = new session();
	Log4j lj = new Log4j();

	
	public forgotpassword_code(String u)
	{
		username=u;
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
					stmt.setString(1, username);
					ResultSet rs=stmt.executeQuery();
					rs.last();
					if(rs.getRow()==1)
					{
						rs.first();
						String reg=rs.getString(3);
						if(reg.equals("TRUE"))
						{
							password=rs.getString(2);
							mail=rs.getString(5);
							return true;
						}
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
	
	public boolean recover_password()
	{
		sender="admin@gmail.com";
		message="The Password for User "+username+" is "+password;
		lj.infor("Password recovery mail sent to user "+username);
		return send_mail_obj.mail(mail, sender, message);
		
	}

}
