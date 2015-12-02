package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Properties;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import org.jdesktop.swingx.JXTipOfTheDay;
import org.jdesktop.swingx.tips.TipLoader;
import org.jdesktop.swingx.tips.TipOfTheDayModel;
public class HomePage extends JFrame{
	
	session session_obj;
	login_code login_code_obj;
	private String u,p,t;
	StudentPage studentpage_obj;
	AdministratorPage adminpage_obj;
	JFileChooser file=new JFileChooser();
	
	Log4j lj = new Log4j();

	
	JLabel title=new JLabel("e-Student Counter");
	
	JLabel notice=new JLabel("Please Login to Continue");
	
	JLabel login=new JLabel("LogIn");
	
	JLabel luname=new JLabel("User Name");
	
	JLabel lpasswd=new JLabel("Password");
	
	JTextField tfuname=new JTextField(9);
	
	JPasswordField tfpasswd=new JPasswordField(25);
	
	JButton btnlogin=new JButton("LogIn");
	
	JButton forgotpasswd=new JButton("Forgot Password");
	JButton help=new JButton("Help");
	JButton register=new JButton("Register");
	
	
	JPopupMenu menu=new JPopupMenu();
	JMenuItem menuCopy=new JMenuItem("Copy");
	JMenuItem menuCut=new JMenuItem("Cut");
	JMenuItem menuPaste=new JMenuItem("Paste");
	JMenuItem menuSelectall=new JMenuItem("Select All");
	
	
	public HomePage()
	{
		//setForeground(Color.cyan);
		lj.infor("Application started.");
		setSize(new Dimension(750,450));
		setResizable(false);
		setLayout(null);
		setTitle("E-Student Counter");
		
		add(title);
		title.setBounds(300, 25, 150, 50);
		
		add(login);
		login.setBounds(335, 100, 100, 50);
		
		add(luname);
		luname.setBounds(200, 150, 150, 50);
		add(tfuname);
		tfuname.setBounds(275, 150, 150, 35);
		tfuname.setToolTipText("Enter the valid credential username.");
		add(lpasswd);
		lpasswd.setBounds(200, 200, 150, 50);
		add(tfpasswd);
		tfpasswd.setBounds(275, 200, 150, 35);
		tfpasswd.setToolTipText("Enter the valid credential password.");
		
		add(btnlogin);
		btnlogin.setBounds(275, 250, 150, 25);
		
		add(forgotpasswd);
		forgotpasswd.setBounds(200, 300, 150, 25);
		
		add(help);
		help.setBounds(375, 300, 150, 25);
		
		add(register);
		register.setBounds(275, 350, 150, 25);

		
		add(notice);
		notice.setBounds(275, 375, 150, 50);
		
		btnlogin.addActionListener(new ButtonHandler());
		forgotpasswd.addActionListener(new ButtonHandler());
		help.addActionListener(new ButtonHandler());
		register.addActionListener(new ButtonHandler());
		
		menu.add(menuSelectall);
		menu.add(menuCut);
		menu.add(menuCopy);
		menu.add(menuPaste);
		
		addMouseListener(new MouseHandler());
		
		menuSelectall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				
				
			}
		});
		
		menuCut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				
			}
		});
		
		menuCopy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				
			}
		});
		menuPaste.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				
			}
		});
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		lj.infor("Application closed");
		
	}
	
	class ButtonHandler implements ActionListener{

		Log4j lj = new Log4j();
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand())
			{
			case "LogIn":
				u=tfuname.getText().toUpperCase();
				p=tfpasswd.getText();
				login_code_obj=new login_code(u, p);
				boolean result,result1;
				result=login_code_obj.auth_registered();
				if(result==true)
				{
					result1=login_code_obj.auth_user();
					if(result1==true)
					{
						session_obj=new session();
						session_obj.setUsername(u);
						session_obj.setPassword(p);
						//session_obj.setTime(new Timestamp());
						t=login_code_obj.get_type();
						if(t.equals("S"))
						{
							dispose();
							session_obj.setType("Student");
							studentpage_obj=new StudentPage(session_obj);
						}
						if(t.equals("A"))
						{
							dispose();
							session_obj.setType("Admin");
							adminpage_obj=new AdministratorPage(session_obj);
							
						}
						if(t.equals("B"))
						{
							dispose();
							session_obj.setType("Sub-Admin");
							adminpage_obj=new AdministratorPage(session_obj,0);
							
						}

					}
					else
					{
						notice.setText("Incorrect Password");
						lj.infor("User entred wrong password");
					}
				}
				else
				{
					notice.setText("User Not Registered!!!");
					lj.warning("User Not Registered!!!");

				}
				
				break;
				
			case "Forgot Password":
				ForgotPasswordPage forgotpassword_obj=new ForgotPasswordPage();
				break;
				
			case "Help":
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
							
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Properties tips = new Properties();
				try {
					tips.load(new FileInputStream("tips.properties"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 
				TipOfTheDayModel model = TipLoader.load(tips);
				JXTipOfTheDay tipdialog = new JXTipOfTheDay(model);
				
				tipdialog.showDialog(null);
				System.out.println("*** Help ***");
				break;
				
			case "Register":
				RegisterPage registerpage_obj=new RegisterPage();
				break;
			}
			
		}
		
	}
	
	class MouseHandler extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			int button=e.getButton();
			if(button==3)
			{
				menu.show(HomePage.this, e.getX(), e.getY());
			}
		}

		
	}

}
