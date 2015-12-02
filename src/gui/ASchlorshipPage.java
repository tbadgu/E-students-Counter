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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import db.ConnectionManager;
import db.InputHelper;

@SuppressWarnings("serial")
public class ASchlorshipPage extends JFrame{
	String sql;
	ResultSet rs;
	session session_obj=new session();
	send_mail mail_obj=new send_mail();
	Connection conn=ConnectionManager.getinstance().getConnection();
	
	String username,mailto;
	JLabel rollno=new JLabel("Roll Number:");
	JLabel doc=new JLabel("Documents Submitted:----");
	JLabel uname=new JLabel("Username:");
	JLabel passwd=new JLabel("Password:");
	JLabel comment=new  JLabel("Comments:-");
	
	JComboBox<String> tfrollnum=new JComboBox<String>();
	JTextField tfuname=new JTextField(25);
	JTextField tfpasswd=new JTextField(25);
	JTextArea tfadddoc=new JTextArea();
	
	JButton btnupdate=new JButton("Update");
	JButton btnsend=new JButton("Send");
	
	JCheckBox lc=new JCheckBox("LC");
	JCheckBox form16=new JCheckBox("Form No 16");
	JCheckBox income=new JCheckBox("Income Certificate");
	JCheckBox caste=new JCheckBox("Caste Certificate");
	JCheckBox castevalidity=new JCheckBox("Caste Validity");
	public ASchlorshipPage(session obj)
	{
		
		setVisible(true);
		session_obj=obj;
		setTitle("Scolarship Page");
		setLayout(null);
		setSize(650, 650);
		add(rollno);
		rollno.setBounds(25, 25, 150, 25);
		add(tfrollnum);
		tfrollnum.setBounds(200, 25, 150, 25);
		tfrollnum.setToolTipText("Roll Number of the applied student.");
		add(doc);
		doc.setBounds(25, 75, 150, 25);
		add(lc);
		lc.setBounds(25, 125, 150, 25);
		add(form16);
		form16.setBounds(25, 175, 150, 25);
		add(income);
		income.setBounds(25, 225, 150, 25);
		add(caste);
		caste.setBounds(25, 275, 150, 25);
		add(castevalidity);
		castevalidity.setBounds(25, 325, 150, 25);
		add(uname);
		uname.setBounds(25, 375,150, 25);
		add(tfuname);
		tfuname.setEditable(false);
		tfuname.setBounds(200, 375, 150, 25);
		tfuname.setToolTipText("Schlorship account username.");
		add(passwd);
		passwd.setBounds(25, 425, 150, 25);
		add(tfpasswd);
		tfpasswd.setEditable(false);
		tfpasswd.setBounds(200, 425, 150, 25);
		tfpasswd.setToolTipText("Schlorship account password.");
		add(comment);
		comment.setBounds(25, 475, 150, 25);
		add(tfadddoc);
		tfadddoc.setBounds(200, 475, 150, 25);
		add(btnupdate);
		btnupdate.setBounds(100, 550, 150, 25);
		btnupdate.addActionListener(new ButtonHandler());
		btnupdate.setToolTipText("Update the doument submitted.");
		add(btnsend);
		btnsend.setBounds(250, 550, 150, 25);
		btnsend.addActionListener(new ButtonHandler());
		btnsend.setToolTipText("Send a mail of comments to the user.");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		String conn_string="jdbc:mysql://"+constants.serverip+"/student_counter";
		ConnectionManager.getinstance().setCredentials(conn_string, constants.mysqluser ,constants.mysqlpasswd);
		conn=ConnectionManager.getinstance().getConnection();
		
		conn=ConnectionManager.getinstance().getConnection();
		if(conn!=null)
		{
			String sql="select schlorship_db.username,schlorship_db.s_username,"
					+ "schlorship_db.s_password,schlorship_db.s_type,schlorship_db.s_registered,"
					+ "document_db.lc, document_db.form16,document_db.income,document_db.castecert,"
					+ "document_db.castevalidity,document_db.other,register_db.roll_number from "
					+ "schlorship_db inner join document_db on schlorship_db.username=document_db.username "
					+ "inner join register_db on schlorship_db.username=register_db.username";
			try{
					int i=0;
					PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	
					ResultSet rs=stmt.executeQuery();
					while(rs.next())
					{
						tfrollnum.addItem(rs.getString(12));
						i++;
					}
					System.out.println(i);
				} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		setCursor(null);
		
		tfrollnum.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				// TODO Auto-generated method stub
				conn=ConnectionManager.getinstance().getConnection();
				int rollno=Integer.parseInt((String) tfrollnum.getSelectedItem());
				String sql="select schlorship_db.username,schlorship_db.s_username,"
						+ "schlorship_db.s_password,schlorship_db.s_type,schlorship_db.s_registered,"
						+ "document_db.lc, document_db.form16,document_db.income,document_db.castecert,"
						+ "document_db.castevalidity,document_db.other,register_db.roll_number,login_db.mail from "
						+ "schlorship_db inner join document_db on schlorship_db.username=document_db.username "
						+ "inner join register_db on schlorship_db.username=register_db.username "
						+ "inner join login_db on login_db.username=register_db.username where "
						+ "register_db.roll_number=?";
				try{
						PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	
						stmt.setInt(1, rollno);
						ResultSet rs=stmt.executeQuery();
						rs.next();
						username=rs.getString(1);
						mailto=rs.getString(13);
						tfuname.setText(rs.getString(2));
						tfpasswd.setText(rs.getString(3));
						if(rs.getString(6).equals("TRUE"))
							lc.setSelected(true);
						else
							lc.setSelected(false);
						if(rs.getString(7).equals("TRUE"))
							form16.setSelected(true);
						else
							form16.setSelected(false);
						if(rs.getString(8).equals("TRUE"))
							income.setSelected(true);
						else
							income.setSelected(false);
						if(rs.getString(9).equals("TRUE"))
							caste.setSelected(true);
						else
							caste.setSelected(false);
						if(rs.getString(10).equals("TRUE"))
							castevalidity.setSelected(true);
						else
							castevalidity.setSelected(false);
						
					} catch (SQLException exception) {
							// TODO Auto-generated catch block
							exception.printStackTrace();
						}
				setCursor(null);
				
			}
		});
		
		
	}
	class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch(e.getActionCommand())
			{
			case "Send":
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				String message=tfadddoc.getText();
				if(mail_obj.mail(mailto, "student.counterpict@gmail.com", message))
				{
					setCursor(null);
					SubmitDialog sd=new SubmitDialog("Comments sent Successfully to "+mailto);
					sd.setVisible(true);
				}
				else
				{
					setCursor(null);
					SubmitDialog sd=new SubmitDialog("Comments not sent Successfully to "+mailto);
					sd.setVisible(true);
				}
				break;
				
			case "Update":
		        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				sql="update document_db set lc=?,form16=?,income=?,castecert=?,castevalidity=? where username=?";
				rs=null;
				
				try(
					PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					)
				{
					stmt.setString(6, username);
					if(lc.isSelected())
						stmt.setString(1, "TRUE");
					else
						stmt.setString(1, "FALSE");
					if(form16.isSelected())
						stmt.setString(2, "TRUE");
					else
						stmt.setString(2, "FALSE");
					if(income.isSelected())
						stmt.setString(3, "TRUE");
					else
						stmt.setString(3, "FALSE");
					if(caste.isSelected())
						stmt.setString(4, "TRUE");
					else
						stmt.setString(4, "FALSE");
					if(castevalidity.isSelected())
						stmt.setString(5, "TRUE");
					else
						stmt.setString(5, "FALSE");
					
					int affected=stmt.executeUpdate();
					setCursor(null);
					SubmitDialog sd=new SubmitDialog("Schlorship Documents updated Successfully");
					sd.setVisible(true);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					setCursor(null);
					SubmitDialog sd=new SubmitDialog("Schlorship Documents not updated Successfully");
					sd.setVisible(true);
					System.err.println(e1.getMessage());
					
				}
				finally
				{
					if(rs!=null)
						try {
							rs.close();
						} catch (SQLException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}
				}
				break;
			}
		}
		
	}
	
}
