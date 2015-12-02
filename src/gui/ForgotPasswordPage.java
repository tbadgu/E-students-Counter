package gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class ForgotPasswordPage extends JFrame{
	String username;
	forgotpassword_code obj;
	JLabel luname=new JLabel("User Name");
	JLabel lstatus=new JLabel();
	JTextField tfuname=new JTextField(25);

	JButton btnSubmit=new JButton("Submit");
	
	public ForgotPasswordPage()
	{
		setSize(250, 250);
		setLayout(null);
		
		add(luname);
		luname.setBounds(25, 50, 100, 25);
		add(tfuname);
		tfuname.setBounds(100, 50, 100, 25);
		add(btnSubmit);
		btnSubmit.setBounds(75, 100, 100, 25);
		add(lstatus);
		lstatus.setBounds(50, 125, 180, 125);
		
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				username=tfuname.getText();
				obj=new forgotpassword_code(username);
				boolean result=obj.auth_registered();
				if(result==true)
				{
					if(obj.recover_password())
					{
						tfuname.setText("");
						lstatus.setText("<html>Status : Password recovery for User "+username+" is Successfull!!! Please Check Your Mail</html>");
					}
					else
					{
						tfuname.setText("");
						lstatus.setText("<html>Status : Password recovery for User "+username+" is UnSuccessfull!!!</html>");
					}
				}
				else
				{
					tfuname.setText("");
					lstatus.setText("<html>Status : User "+username+" is not Registered!!!</html>");
				}
				
			}
		});
		setTitle("Forgot Password");
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	
}
