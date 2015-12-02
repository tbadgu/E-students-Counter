package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ChagePasswordPage extends JFrame{
	session session_obj;
	String oldpass,newpass,conpass;
	
	SubmitDialog dialog;
	JLabel old=new JLabel("Old Password: ");
	JLabel pnew=new JLabel("New Password: ");
	JLabel pcon=new JLabel("Confirm Password:");
	
	JPasswordField tfold=new JPasswordField(25);
	JPasswordField tfpnew=new JPasswordField(25);
	JPasswordField tfpcon=new JPasswordField(25);
	
	JButton btnSubmit=new JButton("Submit");
	JButton btnCancel=new JButton("Cancel");
	
	Log4j lj = new Log4j();
	
	
	public ChagePasswordPage(session obj)
	{
		session_obj=obj;
		setSize(500, 500);
		setLayout(null);
		setTitle("Update Password");
		
		
		
		add(old);
		old.setBounds(25, 50, 100, 25);
		add(tfold);
		tfold.setBounds(150, 50, 100, 25);
		tfold.setToolTipText("Previous Password");
		add(pnew);
		pnew.setBounds(25, 100, 100, 25);
		add(tfpnew);
		tfpnew.setBounds(150, 100, 100, 25);
		tfpnew.setToolTipText("New Password");
		add(pcon);
		pcon.setBounds(25, 150, 150, 25);
		add(tfpcon);
		tfpcon.setBounds(150, 150, 100, 25);
		tfpcon.setToolTipText("Confirm Password");
		add(btnSubmit);
		btnSubmit.setBounds(100, 350, 100, 25);
		add(btnCancel);
		btnCancel.setBounds(300, 350, 100, 25);
		
		btnSubmit.addActionListener(new ButtonHandler());
		btnCancel.addActionListener(new ButtonHandler());
			
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		lj.infor("Setting password for user " +session_obj.getUsername());
	}
	
	class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand())
			{
			case "Submit":
				oldpass=tfold.getText();
				newpass=tfpnew.getText();
				conpass=tfpcon.getText();
				changepassword_code obj=new changepassword_code(session_obj);
				boolean result=obj.updatepassword_func(oldpass, newpass, conpass);
				if(result==true)
				{
					dialog=new SubmitDialog("<html>The Password has been changed.</html>");
					dialog.show();
					dispose();
				}
				else
				{
					dialog=new SubmitDialog("<html>The Password has been not been changed.</html>");
				}
				break;
				
			case "Cancel":
				ChagePasswordPage.this.dispose();
				break;
			}
			
		}
		
	}
	
}
