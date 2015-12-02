package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import gui.constants;
import db.ConnectionManager;

public class abonafide_code {
	
	send_mail obj=new send_mail();
	Connection conn=ConnectionManager.getinstance().getConnection();
	
	public abonafide_code() {
		// TODO Auto-generated constructor stub
		String conn_string="jdbc:mysql://"+constants.serverip+"/student_counter";
		ConnectionManager.getinstance().setCredentials(conn_string, constants.mysqluser ,constants.mysqlpasswd);
		conn=ConnectionManager.getinstance().getConnection();
		
	}
	
	public boolean setbean(bonafide_bean bean)
	{
		String sql="update bonafide_db set issued_on=? where username=?";
	
				
				try(
					PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					)
				{
					stmt.setDate(1, bean.getIssued_on());
					stmt.setString(2, bean.getUsername());

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
				
	}

	public boolean sendmail(bonafide_bean bean)
	{
		String sql="select * from login_db where username=?";
				ResultSet rs=null;
				
				try(
					PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					)
				{
					stmt.setString(1, bean.getUsername());

					rs=stmt.executeQuery();
					rs.next();
					obj.mail(rs.getString(5), "student.counterpict@gmail.com", "Please collect your bonafide document.");
					return true;
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
}
