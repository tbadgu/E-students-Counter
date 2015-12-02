package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.ConnectionManager;

public class update_code {
	String conn_string;
	Connection conn;
	register_bean bean;
	
	public update_code(register_bean ip)
	{
		bean=ip;
		String conn_string="jdbc:mysql://"+constants.serverip+"/student_counter";
		ConnectionManager.getinstance().setCredentials(conn_string, constants.mysqluser ,constants.mysqlpasswd);
		conn=ConnectionManager.getinstance().getConnection();
	}
	
	public register_bean getbean()
	{
		conn=ConnectionManager.getinstance().getConnection();
		if(conn!=null)
		{
			String sql="select * from register_db where username=?";
			try{
					PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	
					stmt.setString(1, bean.getUsername());
					ResultSet rs=stmt.executeQuery();
					rs.last();
					if(rs.getRow()==1)
					{
						bean.setRoll_number(rs.getInt(2));
						bean.setName(rs.getString(3));
						bean.setEnrollment_number(rs.getInt(4));
						bean.setAdmission_date(rs.getDate(5));
						bean.setGender(rs.getString(6));
						bean.setDob(rs.getDate(7));
						bean.setReligion(rs.getString(8));
						bean.setNationality(rs.getString(9));
						bean.setFee_category(rs.getString(10));
						bean.setPhone_number(rs.getLong(11));
						bean.setAddress(rs.getString(12));
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
	
	
	public boolean setbean(register_bean ip)
	{
		bean=ip;
		String sql="UPDATE register_db SET name=?,dob=?,phone_number=?,address=? where username=?";
		ResultSet rs=null;
		try(
			PreparedStatement stmt=conn.prepareStatement(sql);
			)
		{
			stmt.setString(1, bean.getName());
			stmt.setDate(2, bean.getDob());
			stmt.setLong(3, bean.getPhone_number());
			stmt.setString(4, bean.getAddress());
			stmt.setString(5, bean.getUsername());
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
