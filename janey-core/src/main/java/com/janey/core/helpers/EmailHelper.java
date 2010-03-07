package com.janey.core.helpers;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailHelper {
	private Properties props;
	
	public EmailHelper(Properties props) {
		this.props = props;
	}
	
	public void sendmail(String recipient, String subject, String body) throws Throwable {
		String sender = props.getProperty("com.janey.mail.sender");
		sendmail(recipient, subject, body, sender);
	}
	
	public synchronized void sendmail(String recipient, String subject, String body, String replyTo) throws Throwable {
		if ((recipient != null) && (subject != null)  && (body != null)) // we have mail to send
		{
			try {
				// Get the email server and sender from preferences
				String server = props.getProperty("com.janey.mail.server");
				String sender = props.getProperty("com.janey.mail.sender");
								
				// Get system properties
				Properties props = System.getProperties();
				
				// Specify the desired SMTP server and settings
				props.put("mail.smtp.host", server);
				props.put("mail.smtp.port", "25");
				props.put("mail.smtp.auth", "true");
				
				// create a new Session object
				Session session = Session.getInstance(props,null);
				
				// create a new MimeMessage object (using the Session created above)
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(sender, "Janey"));
				message.setReplyTo(new InternetAddress[] {new InternetAddress(replyTo) });
				message.setRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress(recipient) });
				message.setSubject(subject);
				message.setContent(body, "text/plain");
				
				//Transport.send(message);
				// open a transport and send the message
				Transport tr = session.getTransport("smtp");
				tr.connect(server, sender, "password");
				tr.sendMessage(message, message.getAllRecipients());
				tr.close();
			} catch (Throwable t) {
				throw t;
			}
		}
	}

}
