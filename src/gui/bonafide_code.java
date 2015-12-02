package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.ConnectionManager;

public class bonafide_code {
	
	Connection conn=ConnectionManager.getinstance().getConnection();
	
	public bonafide_code() {
		// TODO Auto-generated constructor stub
		String conn_string="jdbc:mysql://"+constants.serverip+"/student_counter";
		ConnectionManager.getinstance().setCredentials(conn_string, constants.mysqluser ,constants.mysqlpasswd);
		conn=ConnectionManager.getinstance().getConnection();
		
	}
	
	public boolean setbean(bonafide_bean bean)
	{
		String sql="insert into bonafide_db "+
				"values(?,?,?,?)";
				ResultSet rs=null;
				
				try(
					PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					)
				{
					stmt.setString(1, bean.getUsername());
					stmt.setDate(2, bean.getApplied_on());
					stmt.setString(3, bean.getReason());
					stmt.setDate(4, bean.getIssued_on());
					
					
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

}
