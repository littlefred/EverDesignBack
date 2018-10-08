package co.simplon.tools;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
// @PropertySource("classpath:foo.properties")
public class SendEmail {
	private String userSMTP = DataBaseInitialization.getUserSMTPValue(); // user SMTP
	private String passwordSMTP = DataBaseInitialization.getPasswordSMTPValue(); // password SMTP
	private String urlFront = DataBaseInitialization.getUrlFrontValue(); // beginning path of callback button in mail
	private int keyEncryptId = DataBaseInitialization.getKeyEncryptValue(); // number to code the userId in sending and reception mail
	//@Value("${smtp.user}")
	//private String userSMTP;
	//@Value("${smtp.password}")
	//private String passwordSMTP;
	//@Value("${url.front}")
	//private String urlFront;
	//@Value("${id.keyEncrypt}")
	//private int keyEncryptId;
	
	/**
	 * method to generate an encrypted id
	 * @param id
	 * @return
	 */
	public Long generateID(Long id) {
		return id + (id*this.keyEncryptId);
	}
	
	/**
	 * method to define the subject of mail since type of mail title
	 * @param title
	 * @return
	 */
	private String defineSubjectMail(Title title) {
		String result = "";
		if (title == Title.INSCRIPTION) {
			result = "Inscription sur EverDesign";
		}
		return result;
	}
	
	/**
	 * method to define the title of mail content since type of mail title
	 * @param title
	 * @return
	 */
	private String defineTitle(Title title) {
		String result = "";
		if (title == Title.INSCRIPTION) {
			result = "Bienvenue";
		}
		return result;
	}
	
	/**
	 * method to define the content of mail since type of mail title
	 * @param title
	 * @return
	 */
	private String defineBody(Title title) {
		String result = "";
		if (title == Title.INSCRIPTION) {
			result = "Nous sommes heureux de vous compter parmis nos futurs clients.<br>";
			result += "Pour finaliser votre inscription, merci de cliquer sur le bouton ci-dessous.<br>";
		}
		return result;
	}
	
	/**
	 * method to define the link of button of mail since type of mail title
	 * @param title
	 * @return
	 */
	private String defineLink(Title title, Long userId, String userMail) {
		String result = "";
		userId = generateID(userId);
		if (title == Title.INSCRIPTION) {
			result = this.urlFront + "/account?ID=" + userId + "&login=" + userMail + "&callBack=confirmAccount";
		}
		return result;
	}
	
	/**
	 * method to define the title button callBack of mail since type of mail title
	 * @param title
	 * @return
	 */
	private String defineButton(Title title) {
		String result = "";
		if (title == Title.INSCRIPTION) {
			result = "Valider mon compte";
		}
		return result;
	}
	
	/**
	 * method to define the footer content of mail since type of mail title
	 * @param title
	 * @return
	 */
	private String defineFooter(Title title) {
		String result = "";
		if (title == Title.INSCRIPTION) {
			result = "";
		}
		return result;
	}
	
	/**
	 * html template of mail define since type of title mail
	 * @param title
	 * @return
	 */
	private String templateInit(Title title, Long userId, String userMail) {
		String header ="<center><img src='http://localhost:4200/assets/imagesSites/logo.png' alt='logo du site EverDesign' style='height:30px;width:auto;'/></center>";
		String bodyLineTitle = "<h2>" + defineTitle(title) + "</h2>";
		String bodyLineText = "<p>" + defineBody(title) + "</p>";
		String styleButton = "height:30px;background:#2da4c4;color:#FAD796;margin:10px;padding:10px;text-decoration:none;Font-size:1.2em;font-style:bolder;border-radius:10px 10px;";
		String bodyLineButton = "<a href='" + defineLink(title, userId, userMail) + "' style='" + styleButton + "'>" + defineButton(title) + "</a>";
		String bodyLineFooter = "<p>" + defineFooter(title) + "</p>";
		String styleTable = "width:80%;margin:auto;text-align:center;border:solid 1px black;";
		String styleHeader = "height:30px;background:black;";
		String styleFooter = "height:15px;background:black;";
		String template = "<!DOCTYPE html><html><body><table style='" + styleTable + "'>";
		template += "<tr><td colspan=3 style='" + styleHeader + "'>" + header + "</td></tr>";
		template += "<tr><td colspan=3>" + bodyLineTitle + "</td></tr>";
		template += "<tr><td colspan=3>" + bodyLineText + "</td></tr>";
		template += "<tr><td></td><td style='height:70px;'>" + bodyLineButton + "</td><td></td></tr>";
		template += "<tr><td colspan=3 style='" + styleFooter + "'>" + bodyLineFooter + "</td></tr>";
		template += "</table></body></html>";
		return template;
	}
	
	/**
	 * method to prepare mail with different contents since type of mail title
	 * @param title
	 * @return
	 */
	private MimeMultipart basicTemplateMail(Title title, Long userId, String userMail) {
		MimeMultipart bodyTemplate = new MimeMultipart("related");
		MimeBodyPart content = new MimeBodyPart();
		String template = templateInit(title, userId, userMail);
		try {
		    content.setText(template, "UTF-8", "html");
		    bodyTemplate.addBodyPart(content);
		} catch (MessagingException e) {
		    e.printStackTrace();
		}
		return bodyTemplate;
	}

	/**
	 * method to open a session on SMTP server
	 * @return
	 */
	private Session getSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		props.put("mail.smtp.port", "587"); // TLS Port
		props.put("mail.smtp.auth", "true"); // enable authentication
		props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS
		// create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userSMTP, passwordSMTP);
			}
		};
		return Session.getInstance(props, auth);
	}

	/**
	 * method to prepare the headers of mail
	 * @param msg
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	private MimeMessage configMessage(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
		// set message headers
		msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		msg.addHeader("format", "flowed");
		msg.addHeader("Content-Transfer-Encoding", "8bit");
		msg.setFrom(new InternetAddress("no_reply@EverDesign.fr", "NoReply-EverDesign"));
		// msg.setReplyTo(InternetAddress.parse("no_reply@EverDesign.fr", false));
		return msg;
	}

	/**
	 * method to send a mail
	 * @param to
	 * @param title
	 */
	public boolean sendSimpleMessage(String to, Title title, Long userId) {
		try {
			Session session = getSession();
			MimeMessage msg = new MimeMessage(session);
			msg = configMessage(msg);
			msg.setContent(basicTemplateMail(title, userId, to));
			msg.setSubject(defineSubjectMail(title), "UTF-8");
			// msg.setText(body, "UTF-8", "html");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
			System.out.println("Message is ready");
			Transport.send(msg);
			System.out.println("EMail Sent Successfully!!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
