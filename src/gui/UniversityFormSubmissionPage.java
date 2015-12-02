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
public class UniversityFormSubmissionPage extends JFrame{
	session session_obj;
	
	JLabel l=new JLabel("<html> To Fill University Exame Form go to Following Website  <html>");
	
	JLabel  website = new JLabel("<html> Website : <a href=\"\">http://www.google.com/</a></html>");
	
	JButton btnok=new JButton("Ok");
	JButton btncancel=new JButton("Cancel");
	
	public UniversityFormSubmissionPage(session obj) {
		session_obj=obj;
		setSize(500, 400);
		setLayout(null);
		setTitle("Scholarship Form Submission");
		
		add(l);
		l.setBounds(50, 50, 350, 25);
		
		website.setCursor(new Cursor(Cursor.HAND_CURSOR));
	     add(website);
	     website.setBounds(100, 100, 300, 25);
	     website.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                try {
	                    Desktop.getDesktop().browse(new URI("http://www.google.com/webhp?nomo=1&hl=fr"));
	                } catch (URISyntaxException | IOException ex) {
	                    //It looks like there's a problem
	                }
	            }
	        });
		
		add(btnok);
		add(btncancel);
		btnok.setBounds(100, 300, 100, 25);
		btncancel.setBounds(250, 300, 100, 25);
		
		btnok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
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
