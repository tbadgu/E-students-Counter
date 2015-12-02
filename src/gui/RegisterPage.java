package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.spi.DateFormatProvider;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import db.InputHelper;
@SuppressWarnings("serial")
public class RegisterPage extends JFrame{
	
	RegisterSubmitDialog dialog;
	register_bean bean;
	update_code update_obj;
	
	Log4j lj = new Log4j();

	
	JLabel name=new JLabel("Name:");
	JLabel eligibilityno=new JLabel("Eligibility Number:");
	JLabel rollno=new JLabel("Roll Number:");
	JLabel enrollmentno=new JLabel("Enrollment N0:");
	
	JLabel admissiondate=new JLabel("Admission Date:");
	JLabel lgender=new JLabel("Gender:");
	JLabel dateofbirth=new JLabel("Date of Birth:");
	
	JLabel religion=new JLabel("Religin:");
	JLabel nationality=new JLabel("Nationality:");
	
	JLabel feecategory=new JLabel("Fee Category:");
	JLabel phoneno=new JLabel("Phone Number:");
	JLabel address=new JLabel("Address:");
	
	JTextField tfname=new JTextField(25);
	JTextField tfeligibilityno=new JTextField(25);
	JTextField tfrollno=new JTextField(25);
	JTextField tfenrollmentno=new JTextField(25);
	
	JDateChooser tfadmissiondate=new JDateChooser();
	JRadioButton rdmale=new JRadioButton("Male");
	JRadioButton rdfemale=new JRadioButton("Female");
	ButtonGroup gender=new ButtonGroup();
	
	
	JDateChooser tfdateofbirth=new JDateChooser();
	
	JComboBox<String> tfreligion=new JComboBox<String>();
	JComboBox<String> tfnationality=new JComboBox<String>();
	
	JComboBox<String> tffeecategory=new JComboBox<String>();
	JTextField tfphoneno=new JTextField(25);
	JTextField tfaddress=new JTextField(25);
	
	
	
	JButton btnsubmit=new JButton("Submit");
	JButton btncancel=new JButton("Cancel");
	
	public RegisterPage()
	{
		gui();
			btnsubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				register_bean bean=new register_bean();
				bean.setUsername(tfeligibilityno.getText());
				bean.setRoll_number(Integer.parseInt(tfrollno.getText()));
				bean.setName(tfname.getText());
				bean.setEnrollment_number(Integer.parseInt(tfenrollmentno.getText()));
				//System.out.println(tfadmissiondate.getDate().toString());
				bean.setAdmission_date(InputHelper.getDateInput(tfadmissiondate.getDate()));
				
				lj.infor("Registration for eligibility no : "+tfeligibilityno.getText());
				if(rdmale.isSelected())
					bean.setGender("M");
				else if(rdfemale.isSelected())
					bean.setGender("F");
				bean.setDob(InputHelper.getDateInput(tfdateofbirth.getDate()));
				bean.setReligion(tfreligion.getSelectedItem().toString());
				bean.setNationality(tfnationality.getSelectedItem().toString());
				bean.setFee_category(tffeecategory.getSelectedItem().toString());
				bean.setPhone_number(Long.parseLong(tfphoneno.getText()));
				bean.setAddress(tfaddress.getText());
				
				register_code register_code_obj=new register_code(bean);
				String result=register_code_obj.auth_checkregistered();
				if(result.equals("true"))
				{
					dialog=new RegisterSubmitDialog("<html>User "+bean.getUsername()+" already Registered!!!</html>");
					dialog.show();
					//already registred cant register
				}
				else if(result.equals("null"))
				{
					dialog=new RegisterSubmitDialog("<html>User "+bean.getUsername()+" is Invalid!!!</html>");
					dialog.show();
					//invalid eligiblity number
				}
				else if(result.equals("false"))
				{
					boolean result1=register_code_obj.auth_submit();
					register_code_obj.auth_setregistered();
					
					
					
					if(result1==true)
					{
						dialog=new RegisterSubmitDialog("<html>User "+bean.getUsername()+" succesfully Registered!!!.Please Check mail id for Password.</html>");
						dialog.show();
						lj.infor("Registration successfull for username " + bean.getUsername() );
						dispose();
						//registration successfull
					}
					else
					{
						dialog=new RegisterSubmitDialog("<html>User "+bean.getUsername()+" unsuccesfully Registered!!!</html>");
						dialog.show();
						lj.infor("Registration Unsuccessfull for username " + bean.getUsername() );

						//registration unsuccessfull
					}
				}
			}
		});
	}
	
	public RegisterPage(session obj)
	{
		gui();
		bean=new register_bean();
		bean.setUsername(obj.getUsername());
		update_obj=new update_code(bean);
		bean=update_obj.getbean();
		tfeligibilityno.setText(bean.getUsername());
		tfeligibilityno.setEditable(false);
		tfrollno.setText(""+bean.getRoll_number());
		tfrollno.setEditable(false);
		tfenrollmentno.setText(""+bean.getEnrollment_number());
		tfenrollmentno.setEditable(false);
		tfadmissiondate.setDate(bean.getAdmission_date());
		tfadmissiondate.setEnabled(false);
		if(bean.getGender().equals("Male"))
		{
			rdmale.setSelected(true);
		}
		else
		{
			rdfemale.setSelected(true);
		}
		rdmale.setEnabled(false);
		rdfemale.setEnabled(false);
		tfreligion.setSelectedItem(bean.getReligion());
		tfreligion.setEditable(false);
		tfnationality.setSelectedItem(bean.getNationality());
		tfnationality.setEditable(false);
		tffeecategory.setSelectedItem(bean.getFee_category());
		tffeecategory.setEditable(false);
			btnsubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				bean.setName(tfname.getText());
				bean.setDob(InputHelper.getDateInput(tfdateofbirth.getDate()));
				bean.setPhone_number(Long.parseLong(tfphoneno.getText()));
				bean.setAddress(tfaddress.getText());
				
				boolean result=update_obj.setbean(bean);
				if(result==true)
				{
					dialog=new RegisterSubmitDialog("<html>User "+bean.getUsername()+" successfully updated!!!</html>");
					dialog.show();
					dispose();
					//already registred cant register
				}
				else if(result==false)
				{
					dialog=new RegisterSubmitDialog("<html>User "+bean.getUsername()+" not successfully updated!!!</html>");
					dialog.show();
				}
			}
		});
		
	}
	public void gui()
	{
		setSize(600, 500);
		setLayout(null);
		setTitle("Registration Page");
		
		add(eligibilityno);
		eligibilityno.setBounds(25, 25, 100, 25);
		add(rollno);
		rollno.setBounds(25, 50, 100, 25);
		add(name);
		name.setBounds(25, 75, 100, 25);
		add(enrollmentno);
		enrollmentno.setBounds(25, 100, 100, 25);
		
		add(admissiondate);
		admissiondate.setBounds(25, 125, 100, 25);
		add(lgender);
		lgender.setBounds(25, 150, 100, 25);
		add(dateofbirth);
		dateofbirth.setBounds(25, 175, 100, 25);
		
		add(religion);
		religion.setBounds(25, 200, 100, 25);
		add(nationality);
		nationality.setBounds(25, 225, 100, 25);
		
		add(feecategory);
		feecategory.setBounds(25, 250, 100, 25);
		add(phoneno);
		phoneno.setBounds(25, 275, 100, 25);
		add(address);
		address.setBounds(25, 300, 100, 25);
		
		add(btnsubmit);
		btnsubmit.setBounds(100, 350, 100, 25);
		add(btncancel);
		btncancel.setBounds(300, 350, 100, 25);

		add(tfeligibilityno);
		tfeligibilityno.setBounds(175, 25, 100, 25);
		tfeligibilityno.setToolTipText("Username of the Credential.");
		add(tfrollno);
		tfrollno.setBounds(175, 50, 100, 25);
		tfrollno.setToolTipText("Roll Number of the current year.");
		add(tfname);
		tfname.setBounds(175, 75, 100, 25);
		tfname.setToolTipText("Entire name of the user.");
		add(tfenrollmentno);
		tfenrollmentno.setBounds(175, 100, 100, 25);
		tfenrollmentno.setToolTipText("Enrollment Number of the user.");
		
		add(tfadmissiondate);
		tfadmissiondate.setDateFormatString("yyyy-mm-dd");
		tfadmissiondate.setBounds(175, 125, 100, 25);
		gender.add(rdmale);
		gender.add(rdfemale);
		add(rdmale);
		add(rdfemale);
		rdmale.setBounds(175, 150, 100, 25);
		rdfemale.setBounds(300, 150, 100, 25);
		
		add(tfdateofbirth);
		tfdateofbirth.setBounds(175, 175, 100, 25);
		tfdateofbirth.setDateFormatString("yyyy-mm-dd");
		
		tfreligion.addItem("Hindu");
		tfreligion.addItem("Shikh");
		tfreligion.addItem("Parshi");
		tfreligion.addItem("Jain");
		tfreligion.addItem("Muslim");
		tfreligion.addItem("christian");
		add(tfreligion);
		tfreligion.setBounds(175, 200, 100, 25);
		tfreligion.setToolTipText("Relgion of the user.");
		
		tfnationality.addItem("Indian");
		tfnationality.addItem("NRI");
		add(tfnationality);
		tfnationality.setBounds(175, 225, 100, 25);
		tfnationality.setToolTipText("Nationality of the user.");
		
		tffeecategory.addItem("General");
		tffeecategory.addItem("SC/ST");
		tffeecategory.addItem("SBC");
		tffeecategory.addItem("OBC");
		tffeecategory.addItem("TFWS");
		tffeecategory.addItem("VJTI");
		add(tffeecategory);
		tffeecategory.setBounds(175, 250, 100, 25);
		tffeecategory.setToolTipText("Fee Category of the user.");
		
		add(tfphoneno);
		tfphoneno.setBounds(175, 275, 100, 25);
		tfphoneno.setToolTipText("Contact Number of the user.");
		
		add(tfaddress);
		tfaddress.setBounds(175, 300, 100, 25);
		tfaddress.setToolTipText("Residential Address of the user.");

		
		btncancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	

	
}

