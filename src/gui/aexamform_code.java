package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.ConnectionManager;

public class aexamform_code {
	
	Connection conn;
	examform_bean bean=new examform_bean();
	
	public aexamform_code() {
		// TODO Auto-generated constructor stub
		String conn_string="jdbc:mysql://"+constants.serverip+"/student_counter";
		ConnectionManager.getinstance().setCredentials(conn_string, constants.mysqluser ,constants.mysqlpasswd);
		conn=ConnectionManager.getinstance().getConnection();
		
	}
	
	public boolean setbean(examform_bean bean)
	{
		String sql="insert into examform_db "+
				"values(?,?,?,?,?,?,?,?,?)";
				ResultSet rs=null;
				
				try(
					PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					)
				{
					stmt.setInt(1, bean.roll_number);
					stmt.setString(2, bean.type);
					stmt.setString(3, bean.seat_no);
					stmt.setString(4, bean.uni_uname);
					stmt.setString(5, bean.uni_passwd);
					stmt.setString(6, bean.year);
					stmt.setString(7, bean.dept);
					stmt.setString(8, bean.semester);
					stmt.setInt(9, bean.dd_number);
					
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
					if(e.getMessage().equals("Duplicate entry '3312' for key 'PRIMARY'"))
						System.out.println("Duplicate entry"); //display gui
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

	public examform_bean getbean(int roll_num)
	{
		ResultSet rs=null;
		String sql="select * from examform_db where examform_db.roll_number=?";
		try{
				PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	
				stmt.setInt(1, roll_num);
				rs=stmt.executeQuery();
				rs.next();
				bean.setRoll_number(roll_num);
				bean.setType(rs.getString(2));
				bean.setSeat_no(rs.getString(3));
				bean.setUni_uname(rs.getString(4));
				bean.setUni_passwd(rs.getString(5));
				bean.setYear(rs.getString(6));
				bean.setDept(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setDd_number(rs.getInt(9));
				return bean;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.err.println(e.getMessage());
					return null;
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
