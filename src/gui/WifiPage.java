package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;
@SuppressWarnings("serial")
public class WifiPage extends JFrame {
	session session_obj;
	wifi_code objCode;
	wifi_bean bean;
	JLabel lname=new JLabel("<html>Name of the User: <html>");
	JLabel lrollno=new JLabel("<html>Roll No.: <html>");
	JLabel lclass=new JLabel("<html>Class: <html>");
	JLabel ldept=new JLabel("<html>Department: <html>");
	JLabel ldate=new JLabel("<html>Date of registration: <html>");
	JLabel lemail=new JLabel("<html>E-mail id: <html>");
	JLabel ldeviceno=new JLabel("<html>WiFi usage device S.No & make: <html>");
	JLabel lmacadd=new JLabel("<html>MAC Address of wireless Care:<html>");
	
	JLabel l=new JLabel("<html>I understand that the wireless LAN access is provided to me "
			+ "for my Academic work only.I also agree that I will not share this account with any person "
			+ "and will take care in protecting the same</html>");
	
	JTextField tfname=new JTextField(50);
	JTextField tfrollno=new JTextField(50);
	JTextField tfclass=new JTextField(50);
	JComboBox<String> tfdept=new JComboBox<String>();
	JDateChooser tfdate=new JDateChooser();
	JTextField tfemail=new JTextField(50);
	JTextField tfdeviceno=new JTextField(50);
	JTextField tfmacadd=new JTextField(50);
	
	JButton btnSubmit=new JButton("Submit");
	JButton btnCancel=new JButton("Cancel");
	
	
	public WifiPage(session obj) {
		session_obj=obj;
		setSize(600, 650);
		setLayout(null);
		setTitle("Wireless LAN(Wi-Fi) Registration Form");
		add(lname);
		lname.setBounds(25, 50, 100, 30);
		add(tfname);
		tfname.setBounds(150, 50, 100, 25);
		
		add(lrollno);
		lrollno.setBounds(25, 100, 100, 25);
		add(tfrollno);
		tfrollno.setBounds(150, 100, 100, 25);
		
		add(lclass);
		lclass.setBounds(25, 150, 100, 25);
		add(tfclass);
		tfclass.setBounds(150, 150, 100, 25);
		
		add(ldept);
		ldept.setBounds(25, 200, 100, 25);
		tfdept.addItem("Computer:");
		tfdept.addItem("IT");
		tfdept.addItem("E&TC");
		add(tfdept);
		tfdept.setBounds(150, 200, 100, 25);
		
		add(ldate);
		ldate.setBounds(25, 250, 100, 30);
		add(tfdate);
		tfdate.setBounds(150, 250, 100, 25);
		tfdate.setDateFormatString("yyyy-mm-dd");
		
		add(ldeviceno);
		ldeviceno.setBounds(25, 300, 100, 50);
		add(tfdeviceno);
		tfdeviceno.setBounds(150, 300, 200, 25);
		
		add(lemail);
		lemail.setBounds(25, 350, 100, 25);
		add(tfemail);
		tfemail.setBounds(150, 350, 200, 25);
		
		add(lmacadd);
		lmacadd.setBounds(25, 400, 100, 30);
		add(tfmacadd);
		tfmacadd.setBounds(150, 400, 200, 25);
		tfmacadd.setToolTipText("<html>(Note:To get MAC address of laptop plesae do following:"
				+ "Go to Start->Run"
				+ "Type cmd and enter."
				+ "Then at prompt type:ipconfig/all"
				+ "this will give details."
				+ "Do disable any microsoft virtual wirelass adaptor first </html>");
		
		add(l);
		l.setBounds(25, 450, 500, 50);
		
		add(btnSubmit);
		btnSubmit.setBounds(150, 550, 100, 25);
		add(btnCancel);
		btnCancel.setBounds(300, 550, 100, 25);
		
		btnSubmit.addActionListener(new ButtonHandler());
		btnCancel.addActionListener(new ButtonHandler());
		
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		objCode=new wifi_code(session_obj);
		bean=objCode.getbean();
		tfname.setText(bean.getName());
		tfname.setEditable(false);
		tfrollno.setText(""+bean.getRollno());
		tfrollno.setEditable(false);
		tfemail.setText(bean.getMail_id());
		tfemail.setEditable(false);
		
	}
	class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			SubmitDialog dialog;
			switch(e.getActionCommand())
			{
			case "Submit":
				bean.setClassname(tfclass.getText());
				bean.setDept(tfdept.getSelectedItem().toString());
				bean.setDevice_no(tfdeviceno.getText());
				bean.setMacadd(tfmacadd.getText());
				bean.setReg_date(new Date(tfdate.getDate().getYear(),tfdate.getDate().getMonth(),tfdate.getDate().getDate()));
				boolean result=objCode.setbean(bean);
				if(result==true)
				{
					dialog=new SubmitDialog("<html>Wifi Refistration Successfull.</html>");
					dialog.show();
					dispose();
				}
				else
				{
					dialog=new SubmitDialog("<html>Wifi Refistration not Successfull.</html>");
				}
				break;
				
			case "Cancel":
				dispose();
				break;
			}
		}
		
	}
	
}
