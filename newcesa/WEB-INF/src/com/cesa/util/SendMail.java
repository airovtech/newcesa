package com.cesa.util;

import java.io.*;
import java.util.*;
import java.util.Date;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import org.apache.log4j.Logger;
import com.cesa.common.*;

public class SendMail
{
	private static Logger log = Logger.getLogger(SendMail.class);

    private String smtpHost = null;
	private String mailHost = null;
	private String smtpEhlo = null;
	private String smtpPort = null;
	private String smtpId = null;
	private String smtpPwd = null;
	

    public SendMail() {

		smtpHost = SiteContext.getInstance().get("mail.smtpHost");
		mailHost = SiteContext.getInstance().get("mail.mailHost");
		smtpEhlo = SiteContext.getInstance().get("mail.smtpEhlo");
		smtpPort = SiteContext.getInstance().get("mail.smtpPort");
		smtpId = SiteContext.getInstance().get("mail.smtpId");
		smtpPwd = SiteContext.getInstance().get("mail.smtpPwd");

		if(smtpHost==null) smtpHost = "211.191.168.119";
		if(mailHost==null) mailHost = "211.191.168.119";
		if(smtpEhlo==null) smtpEhlo = "true";
		if(smtpPort==null) smtpPort = "25";
		if(smtpId==null) smtpId = "stardust96";
		if(smtpPwd==null) smtpPwd = "1234";

		log.debug("smtpHost : "+smtpHost);
		log.debug("mailHost : "+mailHost);
		log.debug("smtpEhlo : "+smtpEhlo);
		log.debug("smtpPort : "+smtpPort);
		log.debug("smtpId : "+smtpId);
		log.debug("smtpPwd : "+smtpPwd);
    
	}
    
	/**
	 * 단체 메일 발송
	 *
	 * @param sender 보낸 사람 이름
	 * @param from 보낸 사람 E-mail 주소
	 * @param to 받는 사람 메일 주소 배열
	 * @param subject 제목
	 * @param text 본문
	 * @param filepath 첨부파일 파일명
	 * @return 성공여부
	 */
    public boolean send(String sender, String from, String to, String subject, String text, String filePath) throws Exception
	{
		// 받는 사람이 없는 경우 정상으로 반환
		if(to==null || to.length()==0)
			return true;

		// JavaMail Session Create
		java.util.Properties properties=System.getProperties();
		properties.put("mail.host", mailHost);
		properties.put("mail.smtp.ehlo",smtpEhlo);
		properties.put("mail.smtp.host", smtpHost);
		properties.put("mail.smtp.port", smtpPort);
		properties.put("mail.smtp.auth", "false");

		log.debug("mail.host : "+mailHost);
		log.debug("mail.host.ehlo : "+smtpEhlo);
		log.debug("mail.host.host : "+smtpHost);
		log.debug("mail.host.port : "+smtpPort);
		log.debug("mail.host.auth : "+"false");


		Authenticator auth = new SMTPAuthenticator(smtpId, smtpPwd); 
		Session session=Session.getInstance(properties, auth);

		//Session session=Session.getDefaultInstance(properties, null);
		MimeMessage msg=new MimeMessage(session);
		
		long fileSize=0;
		long msgSize = text.length();
		
		boolean isMultipart = false;

		// text conversion         
		text = toEnglish(text);

		if(filePath != null){

			String fileName = getNetFileName(filePath);
			if(fileName != null){
				isMultipart = true;

				MimeBodyPart mbp1 = new MimeBodyPart(); 
				
				DataHandler dht=new DataHandler(new ByteArrayDataSource(text, "text/html"));
				mbp1.setDataHandler(dht);
				
				MimeBodyPart mbp2 = new MimeBodyPart(); 
				String pfile=new String();

				FileDataSource fds = new FileDataSource(filePath); 
				mbp2.setDataHandler(new DataHandler(fds)); 
				mbp2.setFileName(toEnglish(fileName)); 
				
				Multipart mp = new MimeMultipart(); 
				mp.addBodyPart(mbp1); 
				mp.addBodyPart(mbp2); 
			
				msg.setContent(mp); 
				fileSize=getFileSize(filePath);
			}
		}

		if(!isMultipart){
			try{
				DataHandler dht=new DataHandler(new ByteArrayDataSource(text, "text/html"));
				msg.setDataHandler(dht);
			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
		
		if(fileSize > 0) msgSize += fileSize;

		// set 'From' Address
		if(from != null){
			String newFrom = from.trim();
			msg.setFrom(new InternetAddress(newFrom, sender)); 
		}
		// set 'To' address
		if(to!=null){
			Address [] toAddress=InternetAddress.parse(to.trim());
			msg.setRecipients(Message.RecipientType.TO, toAddress);
		}
		
		// set 'Subject'
		if(subject != null){
			msg.setSubject(subject);
			msg.setSentDate(new Date()); 
		}

		// Connect Transport
		Transport transport=session.getTransport("smtp");
		transport.connect(smtpHost,"","");

		// Send Message and close
		transport.send(msg);
		transport.close();

		return true;
    }
    
    
    public long getFileSize(String filePath)
    {
        try{
            File f = new File(filePath);
            return f.length();
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    
    public String getNetFileName(String filePath)
    {
        try{
            File f = new File(filePath);
            return f.getName();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static String toEnglish(String s) 
    {
        try {
            if(s != null){
                return (new String(s.getBytes("EUC-KR"), "ISO-8859-1"));
            }
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }    
    
    public static String toKorean(String s){
        
        try{
            if(s != null){
                return (new String(s.getBytes("ISO-8859-1"), "EUC-KR"));
            }
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

	public final class SMTPAuthenticator extends javax.mail.Authenticator {

		private String id;
		private String pw;

		public SMTPAuthenticator(String id, String pw) {
			this.id = id;
			this.pw = pw;
		}

		protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
			return new javax.mail.PasswordAuthentication(id, pw);
		}

	}
}

