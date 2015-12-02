package gui;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import db.ConnectionManager;

@SuppressWarnings("serial")
public class AUniversityExamForm extends JFrame
{

	Connection conn;
	aexamform_code code_obj=new aexamform_code();
	examform_bean bean=new examform_bean();
	
	JLabel rollno=new JLabel("Roll No:");
	JLabel type=new JLabel("Type of Student:");
	JLabel useatno=new JLabel("Seat No:");
	JLabel uuname=new JLabel("University User Name:");
	JLabel upasswd=new JLabel("University password:");
	JLabel Year=new JLabel("Year:");
	JLabel dept=new JLabel("Department:");
	JLabel sem=new JLabel("Semister:");
	JLabel ddno=new JLabel("DD Number:");
	
	JTextField tfrollno=new JTextField(25);
	JComboBox<String> tfrollno1=new JComboBox<String>();
	
	JRadioButton fresher=new JRadioButton("Fresher");
	JRadioButton repeater=new JRadioButton("Repeater");
	ButtonGroup tftype=new ButtonGroup();
	
	JTextField tfuseatno=new JTextField(25);
	JTextField tfuuname=new JTextField(25);
	JTextField tfupasswd=new JTextField(25);
	
	JComboBox<String> tfyear=new JComboBox<String>();
	JComboBox<String> tfdept=new JComboBox<String>();
	
	JRadioButton tffirst=new JRadioButton("First");
	JRadioButton tfsecond=new JRadioButton("Second");
	ButtonGroup tfsem=new ButtonGroup();
	
	JTextField tfddno=new JTextField(25);
	
	JButton btnsubmit=new JButton("Submit");
	JButton btncancel=new JButton("Cancel");
	JButton btnview=new JButton("View Exam Forms");
	
	public AUniversityExamForm()
	{
		setSize(650, 650);
		init();
		add(rollno);
		rollno.setBounds(25, 25, 150, 25);
		add(tfrollno);
		tfrollno.setBounds(200, 25, 150, 25);
		tfrollno.setToolTipText("Roll number of the Student");
		add(btnsubmit);
		btnsubmit.setBounds(150, 475, 150, 25);
		btnsubmit.addActionListener(new ButtonHandler());
		add(btncancel);
		btncancel.setBounds(350, 475, 150, 25);
		btncancel.addActionListener(new ButtonHandler());
		add(btnview);
		btnview.setBounds(250, 525, 150, 25);
		btnview.addActionListener(new ButtonHandler());
	}
	
	public AUniversityExamForm(int i)
	{
		setSize(650, 550);
		init();
		add(rollno);
		rollno.setBounds(25, 25, 150, 25);
		add(tfrollno1);
		tfrollno1.setBounds(200, 25, 150, 25);
		tfrollno1.setToolTipText("Roll number of the applied Student");
		
		add(btncancel);
		btncancel.setBounds(250, 475, 150, 25);
		btncancel.addActionListener(new ButtonHandler());
		
		tfuseatno.setEditable(false);
		tfuuname.setEditable(false);
		tfupasswd.setEditable(false);
		tfyear.setEnabled(false);
		tfdept.setEnabled(false);
		tfddno.setEditable(false);
		
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		String conn_string="jdbc:mysql://"+constants.serverip+"/student_counter";
		ConnectionManager.getinstance().setCredentials(conn_string, constants.mysqluser ,constants.mysqlpasswd);
		conn=ConnectionManager.getinstance().getConnection();
		
		if(conn!=null)
		{
			String sql="select * from examform_db";
			try{
					PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	
					ResultSet rs=stmt.executeQuery();
					while(rs.next())
					{
						tfrollno1.addItem(rs.getString(1));
					}
				} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		setCursor(null);
		
		tfrollno1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				int roll_num=Integer.parseInt(tfrollno1.getSelectedItem().toString());
				bean=code_obj.getbean(roll_num);
				tfuseatno.setText(bean.getSeat_no());
				tfuuname.setText(bean.getUni_uname());
				tfupasswd.setText(bean.getUni_passwd());
				tfyear.setSelectedItem(bean.getYear());
				tfdept.setSelectedItem(bean.getDept());
				tfddno.setText(""+bean.getDd_number());
				if(bean.getType().equals("Fresher"))
				{
					fresher.setSelected(true);
					fresher.setEnabled(false);
					repeater.setEnabled(false);
				}
				else
				{
					repeater.setSelected(true);
					fresher.setEnabled(false);
					repeater.setEnabled(false);
				}
				
				if(bean.getSemester().equals("First"))
				{
					tffirst.setSelected(true);
					tffirst.setEnabled(false);
					tfsecond.setEnabled(false);
				}
				else
				{
					tfsecond.setSelected(true);
					tffirst.setEnabled(false);
					tfsecond.setEnabled(false);
				}
				setCursor(null);
				
			}
		});
	}
	
	public void init()
	{
		setTitle("Administrator University Exam Form ");
		setLayout(null);
		
		add(type);
		type.setBounds(25, 75, 150, 25);
		tftype.add(fresher);
		tftype.add(repeater);
		add(fresher);
		fresher.setBounds(200, 75, 150, 25);
		add(repeater);
		repeater.setBounds(400, 75, 150, 25);
		
		add(useatno);
		useatno.setBounds(25, 125, 150, 25);
		add(tfuseatno);
		tfuseatno.setBounds(200, 125, 150, 25);
		
		add(uuname);
		uuname.setBounds(25, 175, 150, 25);
		add(tfuuname);
		tfuuname.setBounds(200, 175, 150, 25);
		
		add(upasswd);
		upasswd.setBounds(25, 225, 150, 25);
		add(tfupasswd);
		tfupasswd.setBounds(200, 225, 150, 25);
		
		add(Year);
		Year.setBounds(25, 275, 150, 25);
		tfyear.addItem("FE");
		tfyear.addItem("SE");
		tfyear.addItem("TE");
		tfyear.addItem("BE");
		tfyear.addItem("ME");
		add(tfyear);
		tfyear.setBounds(200, 275, 150, 25);
		
		add(dept);
		dept.setBounds(25, 325, 150, 25);
		tfdept.addItem("Computer");
		tfdept.addItem("E & TC");
		tfdept.addItem("IT");
		add(tfdept);
		tfdept.setBounds(200, 325, 150, 25);
		
		add(sem);
		sem.setBounds(25, 375, 150, 25);
		tfsem.add(tffirst);
		tfsem.add(tfsecond);
		add(tffirst);
		tffirst.setBounds(200, 375, 150, 25);
		add(tfsecond);
		tfsecond.setBounds(400, 375, 150, 25);
		
		add(ddno);
		ddno.setBounds(25, 425, 150, 25);
		add(tfddno);
		tfddno.setBounds(200, 425, 150, 25);
		
		
	    
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand())
			{
			case "Submit":
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				bean.setRoll_number(Integer.parseInt(tfrollno.getText()));
				if(fresher.isSelected())
					bean.setType("Fresher");
				else if(repeater.isSelected())
					bean.setType("Repeater");
				bean.setSeat_no(tfuseatno.getText());
				bean.setUni_uname(tfuuname.getText());
				bean.setUni_passwd(tfupasswd.getText());
				bean.setYear(tfyear.getSelectedItem().toString());
				bean.setDept(tfdept.getSelectedItem().toString());
				if(tffirst.isSelected())
					bean.setSemester("First");
				else if(tfsecond.isSelected())
					bean.setSemester("Second");
				bean.setDd_number(Integer.parseInt(tfddno.getText()));
				if(code_obj.setbean(bean))
				{
					setCursor(null);
					SubmitDialog sd=new SubmitDialog("Exam Form Stored Sucessfully");
					sd.setVisible(true);
					System.out.println("Successfully stored");
				}
				else 
				{
					setCursor(null);
					SubmitDialog sd=new SubmitDialog("Exam Form not Stored Sucessfully");
					sd.setVisible(true);
					System.out.println("Not successfully stored");
				}
					
				break;
				
			case "View Exam Forms":
				dispose();
				new AUniversityExamForm(0);
				break;
				
			case "Cancel":
				dispose();
				break;
			}
			
		}
    	
    }
	
}
