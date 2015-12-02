package gui;
import gui.password_generator;
import db.ConnectionManager;
import gui.send_mail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class changepassword_code {
	
	session session_obj;
	Connection conn;
	
	Log4j lj =new Log4j();

	public changepassword_code(session session_obj) {
		this.session_obj = session_obj;
		String conn_string="jdbc:mysql://"+constants.serverip+"/student_counter";
		ConnectionManager.getinstance().setCredentials(conn_string, constants.mysqluser ,constants.mysqlpasswd);
		conn=ConnectionManager.getinstance().getConnection();
		lj.infor("Connection established");

	}


	
	public boolean updatepassword_func(String oldpass,String newpass,String conpass)
	{
		if(newpass.equals(conpass))
		{
			String sql="UPDATE login_db SET password=? where username=?";
			ResultSet rs=null;
			try(
				PreparedStatement stmt=conn.prepareStatement(sql);
				)
			{
				stmt.setString(1, newpass);
				stmt.setString(2, session_obj.getUsername());
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
				lj.errorer("from change password section. "+session_obj.getUsername()+" changed the password.");
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
						lj.errorer("from change password section.");

						e.printStackTrace();
					}
			}
		}
		return false;
		
		
	}

}
