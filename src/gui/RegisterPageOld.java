package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import db.InputHelper;
@SuppressWarnings("serial")
public class RegisterPageOld extends JFrame{
	RegisterSubmitDialog dialog;
	
	JLabel name=new JLabel("Name:");
	JLabel eligibilityno=new JLabel("Eligibility Number:");
	JLabel rollno=new JLabel("Roll Number:");
	JLabel enrollmentno=new JLabel("Enrollment Number:");
	
	JLabel admissiondate=new JLabel("Admission Date:");
	JLabel gender=new JLabel("Gender:");
	JLabel dateofbirth=new JLabel("Date of Birth:");
	
	JLabel religion=new JLabel("Religion:");
	JLabel nationality=new JLabel("Nationality:");
	
	JLabel feecategory=new JLabel("Fee Category:");
	JLabel phoneno=new JLabel("Phone Number:");
	JLabel address=new JLabel("Address:");
	
	JTextField tfname=new JTextField(25);
	JTextField tfeligibilityno=new JTextField(25);
	JTextField tfrollno=new JTextField(25);
	JTextField tfenrollmentno=new JTextField(25);
	
	JTextField tfadmissiondate=new JTextField(25);
	JTextField tfgender=new JTextField(25);
	JTextField tfdateofbirth=new JTextField(25);
	
	JTextField tfreligion=new JTextField(25);
	JTextField tfnationality=new JTextField(25);
	
	JTextField tffeecategory=new JTextField(25);
	JTextField tfphoneno=new JTextField(25);
	JTextField tfaddress=new JTextField(25);
	
	
	
	JButton btnsubmit=new JButton("Submit");
	JButton btncancel=new JButton("Cancel");
	public RegisterPageOld()
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
		add(gender);
		gender.setBounds(25, 150, 100, 25);
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
		add(tfrollno);
		tfrollno.setBounds(175, 50, 100, 25);
		add(tfname);
		tfname.setBounds(175, 75, 100, 25);
		add(tfenrollmentno);
		tfenrollmentno.setBounds(175, 100, 100, 25);
		
		add(tfadmissiondate);
		tfadmissiondate.setBounds(175, 125, 100, 25);
		add(tfgender);
		tfgender.setBounds(175, 150, 100, 25);
		add(tfdateofbirth);
		tfdateofbirth.setBounds(175, 175, 100, 25);
		
		add(tfreligion);
		tfreligion.setBounds(175, 200, 100, 25);
		add(tfnationality);
		tfnationality.setBounds(175, 225, 100, 25);
		
		add(tffeecategory);
		tffeecategory.setBounds(175, 250, 100, 25);
		add(tfphoneno);
		tfphoneno.setBounds(175, 275, 100, 25);
		add(tfaddress);
		tfaddress.setBounds(175, 300, 100, 25);
		
		btnsubmit.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				
				register_bean bean=new register_bean();
				bean.setUsername(tfeligibilityno.getText());
				bean.setRoll_number(Integer.parseInt(tfrollno.getText()));
				bean.setName(tfname.getText());
				bean.setEnrollment_number(Integer.parseInt(tfenrollmentno.getText()));
				//bean.setAdmission_date(InputHelper.getDateInput(tfadmissiondate.getText()));
				bean.setGender(tfgender.getText());
				//bean.setDob(InputHelper.getDateInput(tfdateofbirth.getText()));
				bean.setReligion(tfreligion.getText());
				bean.setNationality(tfnationality.getText());
				bean.setFee_category(tffeecategory.getText());
				bean.setPhone_number(Integer.parseInt(tfphoneno.getText()));
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
						dispose();
						//registration successfull
					}
					else
					{
						dialog=new RegisterSubmitDialog("<html>User "+bean.getUsername()+" unsuccesfully Registered!!!</html>");
						dialog.show();
						//registration unsuccessfull
					}
				}
				
			}
		});
		
		
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
