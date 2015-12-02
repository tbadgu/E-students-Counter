package gui;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.*;

import db.ConnectionManager;
import db.InputHelper;
@SuppressWarnings("serial")
public class ABonafidePage extends JFrame{
	String query;
	ResultSet rs1;
	session session_obj=new session();
	Connection conn=ConnectionManager.getinstance().getConnection();
	abonafide_code code_obj=new abonafide_code();
	bonafide_bean bean=new bonafide_bean();
	JLabel lrollnum=new JLabel("Roll Number:");
	JLabel lstatus=new JLabel("Status:");
	JLabel lreason=new JLabel("Reason:");
	JLabel lapplieddate=new JLabel("Applied Date:");
	JButton btnissue=new JButton("Issue");
	JButton btnnext=new JButton("Next");
	JTextField tfstatus=new JTextField();
	JTextField tfapplieddate=new JTextField();
	JTextArea tfreason=new JTextArea();
	JComboBox<String> tfrollnum=new JComboBox<String>();
	
	public ABonafidePage(session obj) {
		session_obj=obj;
		query="select * from register_db inner join bonafide_db on register_db.username=bonafide_db.username where (((register_db.roll_number>3000) and (register_db.roll_number<4000)) or ((register_db.roll_number>1000) and (register_db.roll_number<2000))) group by register_db.roll_number";
		init();
	}
	
	public ABonafidePage(session obj,int i) {
		session_obj=obj;
		query="select * from register_db inner join bonafide_db on register_db.username=bonafide_db.username where (((register_db.roll_number>4000) and (register_db.roll_number<5000)) or ((register_db.roll_number>4000) and (register_db.roll_number<5000))) group by register_db.roll_number";
		init();
	}
	
	public void init()
	{
		setVisible(true);
		setSize(600, 500);
		setLayout(null);
		setTitle("Bonafide Approval");
		
		add(lrollnum);
		lrollnum.setBounds(25 ,25, 150,25);
		add(tfrollnum);
		tfrollnum.setBounds(200, 25, 150, 25);
		tfrollnum.setToolTipText("Roll number of applied student.");
		
		add(lstatus);
		lstatus.setBounds(25, 75, 150, 25);
		add(tfstatus);
		tfstatus.setBounds(200, 75, 150, 25);
		tfstatus.setEditable(false);
		add(btnissue);
		btnissue.setBounds(350, 75, 150, 25);
		btnissue.setEnabled(false);
		btnissue.setToolTipText("Press the button to Issue the bonafide application.");
		
		add(lreason);
		lreason.setBounds(25, 125, 150, 25);
		add(tfreason);
		tfreason.setBounds(200, 125, 150, 25);
		tfreason.setEditable(false);
		tfreason.setToolTipText("Reason of bonafide application.");
		
		add(lapplieddate);
		lapplieddate.setBounds(25, 175, 150, 25);
		add(tfapplieddate);
		tfapplieddate.setBounds(200, 175, 150, 25);
		tfapplieddate.setEditable(false);
		tfapplieddate.setToolTipText("Date of application.");
		
		add(btnnext);
		btnnext.setEnabled(false);
		btnnext.setBounds(150, 225, 100, 25);
		btnnext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if(rs1.next())
					{
						int rollno=Integer.parseInt((String) tfrollnum.getSelectedItem());
						bean.setRollno(rollno);
						bean.setApplied_on(rs1.getDate(14));
						bean.setReason((rs1.getString(15)));
						bean.setUsername(rs1.getString(1));
						bean.setIssued_on(InputHelper.getDateInput(new Date()));
						tfapplieddate.setText(rs1.getDate(14).toString());
						tfreason.setText(rs1.getString(15));
						if(rs1.getDate(16).toString().equals("2050-08-30"))
						{
							tfstatus.setText("Not Issued");
							btnissue.setEnabled(true);
						}
						else
						{
							tfstatus.setText("Issued");
							btnissue.setEnabled(false);
						}
					}
					else
					{
						rs1.beforeFirst();
						rs1.next();
						int rollno=Integer.parseInt((String) tfrollnum.getSelectedItem());
						bean.setRollno(rollno);
						bean.setApplied_on(rs1.getDate(14));
						bean.setReason((rs1.getString(15)));
						bean.setUsername(rs1.getString(1));
						bean.setIssued_on(InputHelper.getDateInput(new Date()));
						tfapplieddate.setText(rs1.getDate(14).toString());
						tfreason.setText(rs1.getString(15));
						if(rs1.getDate(16).toString().equals("2050-08-30"))
						{
							tfstatus.setText("Not Issued");
							btnissue.setEnabled(true);
						}
						else
						{
							tfstatus.setText("Issued");
							btnissue.setEnabled(false);
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		String conn_string="jdbc:mysql://"+constants.serverip+"/student_counter";
		ConnectionManager.getinstance().setCredentials(conn_string, constants.mysqluser ,constants.mysqlpasswd);
		
		conn=ConnectionManager.getinstance().getConnection();
		if(conn!=null)
		{
			
			try{
					PreparedStatement stmt=conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);	
					ResultSet rs=stmt.executeQuery();
					while(rs.next())
					{
						tfrollnum.addItem(rs.getString(2));
					}
				} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		setCursor(null);
		
		tfrollnum.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				conn=ConnectionManager.getinstance().getConnection();
				int rollno=Integer.parseInt((String) tfrollnum.getSelectedItem());
				String sql="select * from register_db inner join bonafide_db on register_db.username=bonafide_db.username where register_db.roll_number=?";
				try{
						PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	
						stmt.setInt(1, rollno);
						rs1=stmt.executeQuery();
						rs1.last();
						if(rs1.getRow()>1)
							btnnext.setEnabled(true);
						else 
							btnnext.setEnabled(false);
						rs1.beforeFirst();
						rs1.next();
						bean.setRollno(rollno);
						bean.setApplied_on(rs1.getDate(14));
						bean.setReason((rs1.getString(15)));
						bean.setUsername(rs1.getString(1));
						bean.setIssued_on(InputHelper.getDateInput(new Date()));
						tfapplieddate.setText(rs1.getDate(14).toString());
						tfreason.setText(rs1.getString(15));
						if(rs1.getDate(16).toString().equals("2050-08-30"))
						{
							tfstatus.setText("Not Issued");
							btnissue.setEnabled(true);
						}
						else
						{
							tfstatus.setText("Issued");
							btnissue.setEnabled(false);
						}
						
					} catch (SQLException exception) {
							// TODO Auto-generated catch block
							exception.printStackTrace();
						}
				setCursor(null);
			}
		});
		
		btnissue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==btnissue)
				{
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					if(code_obj.setbean(bean))
					{
						code_obj.sendmail(bean);	//add if bonafide mail successfully submitted
						setCursor(null);
						SubmitDialog sd=new SubmitDialog("Bonafide application Sucessfully Issued and Mail has been Sent");
						sd.setVisible(true);
					}
					else
					{
						setCursor(null);
						SubmitDialog sd=new SubmitDialog("Bonafide application Sucessfully not Issued");
						sd.setVisible(true);
					}
					}
			}
		});
		
	}
	
}
