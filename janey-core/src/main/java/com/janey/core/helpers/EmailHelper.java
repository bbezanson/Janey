package com.janey.core.helpers;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.janey.core.managers.PrefsManager;

public class EmailHelper {
	public static final String DEFAULT_MAIL_SERVER = "smtp.server.com";
	public static final String DEFAULT_MAIL_SENDER = "someone@server.com";
	private PrefsManager pm;
	
	public EmailHelper(PrefsManager pm) {
		this.pm = pm;
	}
	
	public void sendmail(String recipient, String subject, String body) throws Throwable {
		String sender = pm.get("com.janey.mail.sender", DEFAULT_MAIL_SENDER);
		sendmail(recipient, subject, body, sender);
	}
	
	public synchronized void sendmail(String recipient, String subject, String body, String replyTo) throws Throwable {
		if ((recipient != null) && (subject != null)  && (body != null)) // we have mail to send
		{
			try {
				// Get the email server and sender from preferences
				String server = pm.get("com.janey.mail.server", DEFAULT_MAIL_SERVER);
				String sender = pm.get("com.janey.mail.sender", DEFAULT_MAIL_SENDER);
								
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
