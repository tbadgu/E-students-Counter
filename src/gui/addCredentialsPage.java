package gui;

import javax.swing.*;

import db.ConnectionManager;

import java.awt.Cursor;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
@SuppressWarnings("serial")
class addCredentialsPage extends JFrame
{
	
	Connection conn;
	JLabel uname=new JLabel("User Name:");
	JLabel type=new JLabel("Type of credentials:");
	JLabel mail=new JLabel("Email:");
	JLabel branch=new JLabel("Branch:");
	JLabel classname=new JLabel("Class:");
	
	JTextField tfuname=new JTextField(25);
	JTextField tfmail=new JTextField(25);
	JComboBox<String> tftype=new JComboBox<String>();
	JComboBox<String> tfbranch=new JComboBox<String>();
	JComboBox<String> tfclass=new JComboBox<String>();
	
	JButton btnsubmit=new JButton("Submit");
	JButton btncancel=new JButton("Cancel");
	
    public addCredentialsPage()
    {
    setTitle("Create New Credentials.");
    setSize(500,400);
    setLayout(null);
    
    add(uname);
    uname.setBounds(25, 25, 150, 25);
    add(tfuname);
    tfuname.setBounds(200, 25, 150, 25);
    tfuname.setToolTipText("Username of the new Credentials to be added.");
    
    add(type);
    type.setBounds(25, 75, 150, 25);
    add(tftype);
    tftype.setBounds(200, 75, 150, 25);
    tftype.addItem("Administrator");
    tftype.addItem("Sub-Administrator");
    tftype.addItem("Student");
    tftype.setToolTipText("Access level to be granted the new Credentials.");
    
    add(mail);
    mail.setBounds(25, 125, 150, 25);
    add(tfmail);
    tfmail.setBounds(200, 125, 150, 25);
    tfmail.setToolTipText("Registered mail-id of new Credentials.");
    
    add(branch);
    branch.setBounds(25, 175, 150, 25);
    add(tfbranch);
    tfbranch.setBounds(200, 175, 150, 25);
    tfbranch.addItem("Computer");
    tfbranch.addItem("Entc");
    tfbranch.addItem("It");
    tfbranch.addItem("Staff");
    tfbranch.setToolTipText("Branch of the new Credentials.");
    
    add(classname);
    classname.setBounds(25, 225, 150, 25);
    add(tfclass);
    tfclass.setBounds(200, 225, 150, 25);
    tfclass.addItem("FE");
    tfclass.addItem("SE");
    tfclass.addItem("TE");
    tfclass.addItem("BE");
    tfclass.addItem("-");
    tfclass.setToolTipText("Class of the new Credentials.");
    
    add(btnsubmit);
    btnsubmit.setBounds(50, 325, 150, 25);
    btnsubmit.addActionListener(new ButtonHandler());
    add(btncancel);
    btncancel.setBounds(250, 325, 150, 25);
    btncancel.addActionListener(new ButtonHandler());
    
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setVisible(true);
    
    String conn_string="jdbc:mysql://"+constants.serverip+"/student_counter";
	ConnectionManager.getinstance().setCredentials(conn_string, constants.mysqluser ,constants.mysqlpasswd);
	conn=ConnectionManager.getinstance().getConnection();
    
    }
    
    class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand())
			{
			case "Submit":
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				if(setCredentials())
				{
					setCursor(null);
					SubmitDialog sd=new SubmitDialog("Credentials Saved Successfully");
					sd.setVisible(true);
					System.out.println("Credentials saved");
				}
				else
				{
					setCursor(null);
					SubmitDialog sd=new SubmitDialog("Credentials not Saved Successfully");
					sd.setVisible(true);
					System.out.println("Credentials not saved");
				}
				break;
			case "Cancel":
				dispose();
				break;
			}
			
		}
    	
    }
    
    public boolean setCredentials()
    {
    	conn=ConnectionManager.getinstance().getConnection();
    	String sql="insert into login_db(username,registered,type,mail,branch,class) "+
    			"values(?,?,?,?,?,?)";
    			ResultSet rs=null;
    			
    			try(
    				PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    				)
    			{
    				stmt.setString(1, tfuname.getText());
    				stmt.setString(2, "FALSE");
    				String type=tftype.getSelectedItem().toString();
    				if(type.equals("Administrator"))
    					stmt.setString(3,"A");
    				else if(type.equals("Sub-Administrator"))
    					stmt.setString(3,"B");
    				else if(type.equals("Student"))
    					stmt.setString(3,"S");
    				stmt.setString(4, tfmail.getText());
    				stmt.setString(5, tfbranch.getSelectedItem().toString());
    				stmt.setString(6, tfclass.getSelectedItem().toString());
    				
    				
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
    
} 