package gui;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import db.ConnectionManager;
import db.InputHelper;
@SuppressWarnings("serial")
public class AWifiPage extends JFrame {

	String username;
	Connection conn=ConnectionManager.getinstance().getConnection();
	JLabel lname=new JLabel("<html>Name of the User: <html>");
	JLabel lrollno=new JLabel("<html>Roll No<html>");
	JLabel lclass=new JLabel("<html>Class: <html>");
	JLabel ldept=new JLabel("<html>Department: <html>");
	JLabel ldate=new JLabel("<html>Date of registration: <html>");
	JLabel lemail=new JLabel("<html>E-mail id: <html>");
	JLabel ldeviceno=new JLabel("<html>WiFi usage device S.No & make: <html>");
	JLabel lmacadd=new JLabel("<html>MAC Address of wireless Care:<html>");
	JLabel lstatus=new JLabel("Status");
	
	JTextField tfname=new JTextField(50);
	JComboBox<String> tfrollno=new JComboBox<String>();
	JTextField tfclass=new JTextField(50);
	JTextField tfdept=new JTextField(50);
	JTextField tfdate=new JTextField(50);
	JTextField tfemail=new JTextField(50);
	JTextField tfdeviceno=new JTextField(50);
	JTextField tfmacadd=new JTextField(50);
	JTextField tfstatus=new JTextField(25);
	JButton btnverify_forword=new JButton("Verify and Forword");
	
	
	public AWifiPage() {

		setSize(600, 650);
		setLayout(null);
		setTitle("Wireless LAN(Wi-Fi) Registration Form Submission");

		add(lrollno);
		lrollno.setBounds(25, 50, 100, 25);
		add(tfrollno);
		tfrollno.setBounds(150, 50, 100, 25);
		tfrollno.setToolTipText("Roll number of applied student");
		
		add(lname);
		lname.setBounds(25, 100, 100, 30);
		add(tfname);
		tfname.setBounds(150, 100, 100, 25);
		tfname.setEditable(false);
		
		add(lclass);
		lclass.setBounds(25, 150, 100, 25);
		add(tfclass);
		tfclass.setBounds(150, 150, 100, 25);
		tfclass.setEditable(false);
		
		add(ldept);
		ldept.setBounds(25, 200, 100, 25);
		add(tfdept);
		tfdept.setBounds(150, 200, 100, 25);
		tfdept.setEditable(false);
		add(ldate);
		ldate.setBounds(25, 250, 100, 30);
		add(tfdate);
		tfdate.setBounds(150, 250, 100, 25);
		tfdate.setEditable(false);
		
		add(ldeviceno);
		ldeviceno.setBounds(25, 300, 100, 50);
		add(tfdeviceno);
		tfdeviceno.setBounds(150, 300, 200, 25);
		tfdeviceno.setEditable(false);
		add(lemail);
		lemail.setBounds(25, 350, 100, 25);
		add(tfemail);
		tfemail.setBounds(150, 350, 200, 25);
		tfemail.setEditable(false);
		add(lmacadd);
		lmacadd.setBounds(25, 400, 100, 30);
		add(tfmacadd);
		tfmacadd.setBounds(150, 400, 200, 25);
		tfmacadd.setEditable(false);
		add(lstatus);
		lstatus.setBounds(25, 450, 150, 25);
		add(tfstatus);
		tfstatus.setBounds(150, 450, 150, 25);
		tfstatus.setEditable(false);
		add(btnverify_forword);
		btnverify_forword.setBounds(250, 500, 150, 25);
		btnverify_forword.setToolTipText("Click the button to forward to the wifi department.");
		
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		String conn_string="jdbc:mysql://"+constants.serverip+"/student_counter";
		ConnectionManager.getinstance().setCredentials(conn_string, constants.mysqluser ,constants.mysqlpasswd);
		conn=ConnectionManager.getinstance().getConnection();
		
		conn=ConnectionManager.getinstance().getConnection();
		if(conn!=null)
		{
			String sql="select register_db.roll_number from register_db inner join wifiregister_db on register_db.username=wifiregister_db.username";
			try{
					PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	
					ResultSet rs=stmt.executeQuery();
					while(rs.next())
					{
						tfrollno.addItem(rs.getString(1));
					}
				} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		setCursor(null);
		
		tfrollno.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				conn=ConnectionManager.getinstance().getConnection();
				int rollno=Integer.parseInt((String) tfrollno.getSelectedItem());
				String sql="select * from register_db inner join wifiregister_db on "
						+ "register_db.username=wifiregister_db.username inner join login_db on "
						+ "register_db.username=login_db.username where register_db.roll_number=?";
				try{
						PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	
						stmt.setInt(1, rollno);
						ResultSet rs=stmt.executeQuery();
						rs.next();
						username=rs.getString(1);
						tfname.setText(rs.getString(3));
						tfclass.setText(rs.getString(14));
						tfdept.setText(rs.getString(15));
						tfdate.setText(rs.getDate(16).toString());
						tfemail.setText(rs.getString(24));
						tfdeviceno.setText(rs.getString(17));
						tfmacadd.setText(rs.getString(18));
						if(rs.getDate(19).toString().equals("2050-08-30"))
						{
							tfstatus.setText("Not Verified and forwarded");
							btnverify_forword.setEnabled(true);
						}
						else
						{
							tfstatus.setText("Verified and forwarded");
							btnverify_forword.setEnabled(false);
						}
					} catch (SQLException exception) {
							// TODO Auto-generated catch block
							exception.printStackTrace();
						}
				setCursor(null);
			}
		});
		
		btnverify_forword.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==btnverify_forword)
				{
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					String sql="update wifiregister_db set forward_on=? where username=?";
					ResultSet rs=null;
					
					try(
						PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						)
					{
						stmt.setDate(1, InputHelper.getDateInput(new Date()));
						stmt.setString(2, username);
						stmt.executeUpdate();
						setCursor(null);
						SubmitDialog sd=new SubmitDialog("WiFi Form Sucessfully Verified an forwarded");
						sd.setVisible(true);
				}
					catch (SQLException ex) {
						// TODO Auto-generated catch block
						System.err.println(ex.getMessage());
						setCursor(null);
						SubmitDialog sd=new SubmitDialog("WiFi Form Sucessfully not Verified an forwarded");
						sd.setVisible(true);
					}
				
			}
			}
		}
		);
			
	}
	
			
}
