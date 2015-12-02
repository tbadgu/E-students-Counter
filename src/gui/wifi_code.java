package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.GregorianCalendar;

import db.ConnectionManager;
import db.InputHelper;

public class wifi_code {
	
	
	session session_obj;
	Connection conn=ConnectionManager.getinstance().getConnection();
	
	public wifi_code(session obj) {
		// TODO Auto-generated constructor stub
		session_obj=obj;
		String conn_string="jdbc:mysql://"+constants.serverip+"/student_counter";
		ConnectionManager.getinstance().setCredentials(conn_string, constants.mysqluser ,constants.mysqlpasswd);
		conn=ConnectionManager.getinstance().getConnection();
		
	}
	
	public wifi_bean getbean()
	{
		wifi_bean bean=new wifi_bean();
		conn=ConnectionManager.getinstance().getConnection();
		if(conn!=null)
		{
			String sql="select * from register_db inner join login_db on register_db.username=login_db.username where register_db.username=?";
			try{
					PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	
					stmt.setString(1, session_obj.getUsername());
					ResultSet rs=stmt.executeQuery();
					rs.last();
					if(rs.getRow()==1)
					{
						rs.first();
						bean.setRollno(rs.getInt(2));
						bean.setName(rs.getString(3));
						bean.setMail_id(rs.getString(17));
						return bean;
					}
					else
					{
						return null;					}
				} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		return null;
	}
	
	public boolean setbean(wifi_bean bean)
	{
		String sql="insert into wifiregister_db "+
				"values(?,?,?,?,?,?,?)";
				ResultSet rs=null;
				
				try(
					PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					)
				{
					stmt.setString(1, session_obj.getUsername());
					stmt.setString(2, bean.getClassname());
					stmt.setString(3, bean.getDept());
					stmt.setDate(4, InputHelper.getDateInput(bean.getReg_date()));
					stmt.setString(5, bean.getDevice_no());
					stmt.setString(6, bean.getMacadd());
					java.util.Calendar c1=GregorianCalendar.getInstance();
					c1.set(2050, 07, 30); 				//month starts from 0
					java.util.Date temp=c1.getTime();
					stmt.setDate(7, InputHelper.getDateInput(temp));
					
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
