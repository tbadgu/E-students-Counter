package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import db.ConnectionManager;
import db.InputHelper;

public class sformsubmission_code {
	
	Connection conn=ConnectionManager.getinstance().getConnection();
	send_mail mail_obj=new send_mail();
	
	public sformsubmission_code() {
		// TODO Auto-generated constructor stub
		String conn_string="jdbc:mysql://"+constants.serverip+"/student_counter";
		ConnectionManager.getinstance().setCredentials(conn_string, constants.mysqluser ,constants.mysqlpasswd);
		conn=ConnectionManager.getinstance().getConnection();
		
	}
	
	
	
	public boolean setbean(schlorship_bean bean)
	{
				String sql="insert into schlorship_db "+
				"values(?,?,?,?,?,?)";
				ResultSet rs=null;
				
				try(
					PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					)
				{
					stmt.setString(1, bean.getUsername());
					stmt.setString(2, bean.getS_username());
					stmt.setString(3, bean.getS_password());
					stmt.setString(4, bean.getS_type());
					stmt.setString(5, bean.getS_registered());
					stmt.setString(6, "No Documents Submitted");
					
					
					
					int affected=stmt.executeUpdate();
					
					sql="insert into document_db "+
							"values(?,?,?,?,?,?,?)";
					rs=null;
		
					PreparedStatement stmt1=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					{
						stmt1.setString(1, bean.getUsername());
						stmt1.setString(2, "FALSE");
						stmt1.setString(3, "FALSE");
						stmt1.setString(4, "FALSE");
						stmt1.setString(5, "FALSE");
						stmt1.setString(6, "FALSE");
						stmt1.setString(7, "FALSE");
						
						
						
						int affected1=stmt1.executeUpdate();
					
					if(affected==1 && affected1==1)
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				}catch (SQLException e) {
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
	
	public boolean sendmail(schlorship_bean bean)
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
						String to=rs.getString(5);
						String from="student.counterpict@gmail.com";
						String message="Submit the following documents:1)Leaving Certificate "
								+ "2)Form 16 3)Income Certificate 4)Caste Certificate "
								+ "5)Caste Validity";
						mail_obj.mail(to, from, message);
						return true;
					}
					
				}
					catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
		return false;
	}
}
