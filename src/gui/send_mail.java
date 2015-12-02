package gui;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class send_mail {
	
	Log4j lj = new Log4j();  
	
	public boolean mail(String to_input,String from_input,String message_input) {
	      // Recipient's email ID needs to be mentioned.
	      String to = to_input;

	      // Sender's email ID needs to be mentioned
	      String from =from_input;
	      final String username = "student.counterpict@gmail.com";//change accordingly
	      final String password = "pict@123";//change accordingly

	      // Assuming you are sending email through smtp.gmail.com
	      String host = "smtp.gmail.com";

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "25");

	      // Get the Session object.
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(username, password);
		   }
	         });

	      try {
		   // Create a default MimeMessage object.
		   Message message = new MimeMessage(session);
		
		   // Set From: header field of the header.
		   message.setFrom(new InternetAddress(from));
		
		   // Set To: header field of the header.
		   message.setRecipients(Message.RecipientType.TO,
	               InternetAddress.parse(to));
		
		   // Set Subject: header field
		   message.setSubject("Testing Subject");
		
		   // Now set the actual message
		   message.setText(message_input);

		   // Send message
		   Transport.send(message);

		   return true;
	      } catch (MessagingException e) {
	         e.printStackTrace();
	         lj.errorer(" from mail section.");
	         return false;
	         
	      }
		
	   }

}
