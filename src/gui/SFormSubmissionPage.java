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

import javax.swing.*;
@SuppressWarnings("serial")
public class SFormSubmissionPage extends JFrame{
	session session_obj=new session();
	schlorship_bean bean=new schlorship_bean();
	sformsubmission_code code_obj;
	SubmitDialog dialog;
	JLabel luname=new JLabel("User Name: ");
	JLabel lpasswd=new JLabel("Password: ");
	JLabel ltype=new JLabel("Type: ");
	JLabel l=new JLabel("To Fill Scholarship Form go to Following Website ");
	private JLabel  website = new JLabel("<html> Website : <a href=\"\">http://www.google.com/</a></html>");
	
	
	JTextField tfuname=new JTextField(25);
	JPasswordField tfpasswd=new JPasswordField(25);
	JComboBox<String> tftype=new JComboBox<String>();

	JRadioButton registered=new JRadioButton("Registered");
	JRadioButton notregistered=new JRadioButton("<html>Not-Registered<html>");
	ButtonGroup group=new ButtonGroup();
	
	JButton btnok=new JButton("Ok");
	JButton btncancel=new JButton("Cancel");
	
	public SFormSubmissionPage(session obj) {
		
		session_obj=obj;
		setSize(600, 500);
		setLayout(null);
		setTitle("Scholarship Form Submission");
		
		add(l);
		l.setBounds(25, 25, 300, 25);
		
		website.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    add(website);
	    website.setBounds(25,50,200,25);
	    website.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://www.google.com/"));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });


		
	    add(luname);
		luname.setBounds(25, 100, 100, 25);
		add(tfuname);
		tfuname.setBounds(150, 100, 100, 25);
		tfuname.setToolTipText("User name of e-Scholarship account");
		
		add(lpasswd);
		lpasswd.setBounds(25, 150, 100, 25);
		add(tfpasswd);
		tfpasswd.setBounds(150, 150, 100, 25);
		tfpasswd.setToolTipText("Password of e-Scholarship account");
		
		add(ltype);
		ltype.setBounds(25, 200, 100, 25);
		
		tftype.addItem("Scholarship");
		tftype.addItem("Freeship");
		add(tftype);
		tftype.setBounds(150, 200, 100, 25);
		tftype.setToolTipText("Select Type of Scholarship");
		
		group.add(registered);
		group.add(notregistered);
		add(registered);
		add(notregistered);
		registered.setBounds(25, 250, 100, 25);
		notregistered.setBounds(150, 250, 150, 25);
		
		add(btnok);
		add(btncancel);
		btnok.setBounds(100, 350, 100, 25);
		btncancel.setBounds(250, 350, 100, 25);
		
		btnok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bean.setUsername(session_obj.getUsername());
				bean.setS_username(tfuname.getText());
				bean.setS_password(tfpasswd.getText());
				bean.setS_type(tftype.getSelectedItem().toString());
				if(registered.isSelected())
					bean.setS_registered("TRUE");
				else
					bean.setS_registered("FALSE");
				
				if(bean.getS_registered().equals("TRUE"))
				{
				code_obj=new sformsubmission_code();
				boolean result=code_obj.setbean(bean);
				if(result==true)
				{
					if(code_obj.sendmail(bean))
					{
					dialog=new SubmitDialog("<html>Schlorship Form Submission Successfull.Please Check your mail id for document details.</html>");
					dialog.show();
					dispose();
					}
					else
					{
						dialog=new SubmitDialog("<html>Schlorship Form Submission not Successfull.</html>");
					}
				}
				else
				{
					dialog=new SubmitDialog("<html>Schlorship Form Submission not Successfull.</html>");
				}
				}
				else
				{
					dialog=new SubmitDialog("<html>Please Register First on the given Website.</html>");
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
