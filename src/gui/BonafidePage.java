
package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JLabel;

import db.InputHelper;

@SuppressWarnings("serial")
public class BonafidePage extends JDialog {
	session session_obj;
	bonafide_bean bean=new bonafide_bean();
	bonafide_code code_obj;
	SubmitDialog dialog;
	
	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton okButton;
	private JButton cancelButton;
	JTextArea area;

	/**
	 * Create the dialog.
	 */
	public BonafidePage(session obj) {
		session_obj=obj;
		setBounds(100, 100, 363, 246);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.WEST);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Submit Application");
				okButton.setActionCommand("Submit Application");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JLabel label = new JLabel("Reason: ");
			getContentPane().add(label, BorderLayout.NORTH);
			area=new JTextArea();
			getContentPane().add(area,BorderLayout.CENTER);
			area.setToolTipText("Reason for the application of bonafide certificate.");
		}
		
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				code_obj=new bonafide_code();
				bean.setUsername(session_obj.getUsername());
				bean.setApplied_on(InputHelper.getDateInput(new Date()));
				bean.setReason(area.getText());
				java.util.Calendar c1=GregorianCalendar.getInstance();
				c1.set(2050, 07, 30); 				//month starts from 0
				java.util.Date temp=c1.getTime();
				bean.setIssued_on(InputHelper.getDateInput(temp));
				boolean result=code_obj.setbean(bean);
				if(result==true)
				{
					dialog=new SubmitDialog("<html>Bonafide Application Submission Successfull.</html>");
					dialog.show();
					dispose();
				}
				else
				{
					dialog=new SubmitDialog("<html>Bonafide Application Submission not Successfull.</html>");
				}
				
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		setVisible(true);
		
	}
	


}
