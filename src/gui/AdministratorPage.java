package gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXHyperlink;

@SuppressWarnings("serial")

public class AdministratorPage extends JFrame{
	
	Log4j lj = new Log4j();
	
	session session_obj;
	JPanel panel1=new JPanel();
	JPanel panel2=new JPanel();
	JPanel panel3=new JPanel();
	JPanel panel4=new JPanel();
	JLabel title=new JLabel("E -Student Counter");
	
	
	JButton btnSchlarship=new JButton("Scholarship");
	JButton btnfeme=new JButton("FE/TE");
	JButton btnsete=new JButton("SE/BE");
	JButton btnGeneral=new JButton("General");
	
	JMenuBar bar=new JMenuBar();
	JMenu menu=new JMenu("Menu");
	JMenuItem menuLogout=new JMenuItem("Logout");
	JMenuItem menuChangepass=new JMenuItem("Change Password");
	JMenuItem menuUpdate=new JMenuItem("Update");
	
	JXCollapsiblePane cpane=new JXCollapsiblePane();
	
	public AdministratorPage(session obj)
	{
		session_obj=obj;
		init();
		JXHyperlink hy9=new JXHyperlink();
		hy9.setActionCommand("hy9");
		hy9.setText("<html>Add New Credentials<html>");
		hy9.addActionListener(new MouseHandler());
		add(hy9);
		hy9.setBounds(200, 250, 150, 25);
		
	}
	
	public AdministratorPage(session obj,int i) {
		// TODO Auto-generated constructor stub
		session_obj=obj;
		init();
	}
	
	public void init()
	{
		
		setSize(550, 500);
		setLayout(null);
		
		panel1.setLayout(new GridLayout(3, 1));
		panel2.setLayout(new GridLayout(1, 1));
		panel3.setLayout(new GridLayout(1, 1));
		panel4.setLayout(new GridLayout(3, 1));
		menu.add(menuUpdate);
		menu.add(menuChangepass);
		menu.add(menuLogout);
		bar.add(menu);
		setJMenuBar(bar);
		cpane.setSize(new Dimension(550,150));
		cpane.setBounds(0, 350, 550, 150);
		
		panel1.setSize(new Dimension(550,150));
		panel2.setSize(new Dimension(550,150));
		panel3.setSize(new Dimension(550,150));
		panel4.setSize(new Dimension(550,150));
		
		add(title);
		title.setBounds(200, 25, 150, 25);
		add(btnSchlarship);
		btnSchlarship.setBounds(25, 100, 125, 100);
		add(btnfeme);
		btnfeme.setBounds(175, 100, 100, 100);
		add(btnsete);
		btnsete.setBounds(300, 100, 100, 100);
		add(btnGeneral);
		btnGeneral.setBounds(425, 100, 100, 100);
		
		
		menuLogout.addActionListener(new MouseHandler());
		menuChangepass.addActionListener(new MouseHandler());
		menuUpdate.addActionListener(new MouseHandler());
		btnSchlarship.addActionListener(new MouseHandler());
		//btnSchlarship.addActionListener(cpane.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
		btnfeme.addActionListener(new MouseHandler());
		//btnfeme.addActionListener(cpane.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
		btnsete.addActionListener(new MouseHandler());
		//btnsete.addActionListener(cpane.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
		btnGeneral.addActionListener(new MouseHandler());
		//btnGeneral.addActionListener(cpane.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
		
		cpane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		add(cpane);
		
		//StudentPage.this.validate();
		

		JXHyperlink hy1=new JXHyperlink();
		hy1.setActionCommand("hy1");
		hy1.setText("<html>Notices<html>");
		hy1.addActionListener(new MouseHandler());
		add(hy1);
		hy1.setBounds(200, 285, 150, 25);
		
		JXHyperlink hy2=new JXHyperlink();
		hy2.setActionCommand("hy2");
		hy2.setText("<html>Scholarship Form submission.<html>");
		hy2.addActionListener(new MouseHandler());
		
		JXHyperlink hy3=new JXHyperlink();
		hy3.setActionCommand("hy3");
		hy3.setText("<html>Scholarship Document submission<html>");
		hy3.addActionListener(new MouseHandler());
		
		JXHyperlink hy4=new JXHyperlink();
		hy4.setActionCommand("hy4");
		hy4.setText("<html>Bonafide certificate<html>");
		hy4.addActionListener(new MouseHandler());
		
		JXHyperlink hy8=new JXHyperlink();
		hy8.setActionCommand("hy8");
		hy8.setText("<html>Bonafide certificate<html>");
		hy8.addActionListener(new MouseHandler());
		
		JXHyperlink hy5=new JXHyperlink();
		hy5.setActionCommand("hy5");
		hy5.setText("<html>University exam form submission<html>");
		hy5.addActionListener(new MouseHandler());
		
		JXHyperlink hy6=new JXHyperlink();
		hy6.setActionCommand("hy6");
		hy6.setText("<html>Enquiry desk<html>");
		hy6.addActionListener(new MouseHandler());
		
		JXHyperlink hy7=new JXHyperlink();
		hy7.setActionCommand("hy7");
		hy7.setText("<html>WI -Fi Registration Form<html>");
		hy7.addActionListener(new MouseHandler());
		
		
		
		panel1.add(hy2);
		panel1.add(hy3);
		
		panel2.add(hy4);
		
		panel3.add(hy8);
		
		panel4.add(hy7);
		panel4.add(hy5);
		panel4.add(hy6);
		
		setTitle("Administrator Page");
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	class MouseHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand())
			{
			case "Logout":
				lj.infor("User logout : "+session_obj.getUsername());
				lj.infor("Application exited successfully");
				dispose();
				break;
			case "Change Password":
				ChagePasswordPage obj=new ChagePasswordPage(session_obj);
				obj.show();
				break;
			case "Update":
				RegisterPage obj2=new RegisterPage(session_obj);
				System.out.println("Update");
				break;
			case "Scholarship":
				cpane.removeAll();
				cpane.repaint();
				cpane.add(panel1);
				cpane.revalidate();
				System.out.println("Scholarship");
				break;
			case "FE/TE":
				cpane.removeAll();
				cpane.repaint();
				cpane.add(panel2);
				cpane.revalidate();
				System.out.println("FE/TE");
				break;
			case "SE/BE":
				cpane.removeAll();
				cpane.repaint();
				cpane.add(panel3);
				cpane.revalidate();
				System.out.println("SE/BE");
				break;
			case "General":
				cpane.removeAll();
				cpane.repaint();
				cpane.add(panel4);
				cpane.revalidate();
				System.out.println("BE");
				break;
			case "hy1":
				new notices(0);
				break;
			case "hy2":
				new ASchlorshipPage(session_obj);
				break;
			case "hy3":
				break;
			case "hy4":
				new ABonafidePage(session_obj);
				break;
			case "hy5":
				new AUniversityExamForm();
				break;
			case "hy6":
				new EnquiryDesk(session_obj);
				break;
			case "hy7":
				new AWifiPage();
				break;
			case "hy8":
				new ABonafidePage(session_obj,0);
				break;
			case "hy9":
				new addCredentialsPage();
				break;
					
			}
		}
		
	}
	

}
