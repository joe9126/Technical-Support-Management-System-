// File Name SendEmail.java
// File Name SendEmail.java

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class SendEmail extends Home {
    Properties emailProperties;   MimeBodyPart messageBodyPart; MimeBodyPart attachmentPart, signaturePart;
	Session mailSession; String fromUser; ImageIcon mailsignature;
	MimeMessage emailMessage;  String emailPort; String emailHost; 
  String fromUserEmailPassword; String grouprecipientmail;     Multipart multipart;
    byte[] ibytes=null;    
	public void setMailServerProperties() {
        try {
            String getmailsettingsql="SELECT * FROM NOTIFICATIONS";
            PreparedStatement pstret2=(com.mysql.jdbc.PreparedStatement)connectDb.prepareStatement(getmailsettingsql);
            ResultSet stret2=pstret2.executeQuery();
            while(stret2.next()){
              fromUser=stret2.getString(1); System.out.println("Sender mail :"+stret2.getString(1));
              fromUserEmailPassword=stret2.getString(2);
             grouprecipientmail=stret2.getString(3);
               emailHost=stret2.getString(4);
                 emailPort=stret2.getString(5);
                 
             ibytes =stret2.getBytes(6);

/*Image imagelog = getToolkit().createImage(ibytes);
  mailsignature=new ImageIcon( imagelog.getScaledInstance(600, 200, Image.SCALE_DEFAULT)); */   
            }
          
            emailProperties = System.getProperties();
            emailProperties.put("mail.smtp.port", emailPort);
            emailProperties.put("mail.smtp.auth", "true");
            emailProperties.put("mail.smtp.starttls.enable", "true");
        } catch (SQLException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

    /*            
 public void createEmailMessage(  ) throws AddressException,MessagingException {  
      String[] toEmails = { "joeasewe@gmail.com","lwachira@symphony.co.ke" };
 	String emailSubject = "Java Email TEst";
 	 String emailBody = "This is an email sent by JavaMail api.";
 */
 public void createEmailMessage(String recipientEmail, String[] ccEmails,String emailSubject, String emailBody, String attachment, String filename ) throws AddressException,MessagingException {
                mailSession = Session.getDefaultInstance(emailProperties, null);
		emailMessage = new MimeMessage(mailSession);
                 multipart=new MimeMultipart();
                 messageBodyPart= new MimeBodyPart();
                 attachmentPart=new MimeBodyPart();
                 signaturePart=new MimeBodyPart();

               
InternetAddress[] myToList = InternetAddress.parse(recipientEmail);
emailMessage.setRecipients(Message.RecipientType.TO,myToList);

/*InternetAddress[] myBccList = InternetAddress.parse("Usha.B@xyz.com");
InternetAddress[] myCcList = InternetAddress.parse("NEHA.SIVA@xyz.com");*/

//message.setFrom(new InternetAddress(objEmail.getFrom()));

for (int i = 0; i < ccEmails.length; i++) {
    System.out.println("CC "+i+" is "+ccEmails[i]);
	emailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(ccEmails[i]));
}

emailMessage.setSubject(emailSubject);
		//emailMessage.setContent(emailBody, "text/html");//for a html email
///emailMessage.setText(emailBody);// for a text email
System.out.println("Attchment URL is: "+attachment);
System.out.println("Message body is\n"+emailBody); 

  messageBodyPart.setText(emailBody); 
    DataSource source1 =new InputStreamDataSource("image/plain","signature.png",new ByteArrayInputStream(ibytes));
signaturePart.setDataHandler(new DataHandler(source1));
signaturePart.setFileName("email_signature"+".png");
 multipart.addBodyPart(signaturePart);
   
if(attachment!=null){
  DataSource source =new FileDataSource(attachment);
   attachmentPart.setDataHandler(new DataHandler(source));
  attachmentPart.setFileName(filename+".pdf");  
  multipart.addBodyPart(attachmentPart);
  emailMessage.setContent(multipart); 
  
} else{
  multipart.addBodyPart(messageBodyPart); 
  emailMessage.setContent(multipart); 
}          
 

 }

public void sendEmail(  ) throws AddressException, MessagingException {	
// public void sendEmail(  ) throws AddressException, MessagingException {

      //String fromUser = "jasewe@symphony.co.ke";//just the id alone without @gmail.com
   //   String emailHost = "smtp.gmail.com";
     // String fromUserEmailPassword = "28788529@Owaga";

        try (Transport transport = mailSession.getTransport("smtp")) {
            transport.connect(emailHost, fromUser, fromUserEmailPassword);
            transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        }
System.out.println("Email sent successfully.");
                JOptionPane.showMessageDialog(null, "Notification sent to client and technician successfully!", "Alert",JOptionPane.INFORMATION_MESSAGE);
	}
    
  /*    
  public static void main(String args[]) throws AddressException,MessagingException {

		SendEmail javaEmail = new SendEmail();
		javaEmail.setMailServerProperties();
		javaEmail.createEmailMessage();
		javaEmail.sendEmail();
	}
 */
 
}

 class InputStreamDataSource implements DataSource  {
 String contentType, name1; 
InputStream inputStream1;
Home access=new Home(); byte[] ibytes;  
    public InputStreamDataSource(String contenttype, String name, InputStream inputStream){
 this.inputStream1 = inputStream;  
 this.contentType= contenttype ;
 this.name1=name;
 
 //fileData= IOUtils.toByArray(inputStream);
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
    try { int f=0;
        String getmailsettingsql="SELECT SIGNATURE FROM NOTIFICATIONS";
        PreparedStatement pstret2=(com.mysql.jdbc.PreparedStatement)access.connectDb.prepareStatement(getmailsettingsql);
        ResultSet stret2=pstret2.executeQuery();
        while(stret2.next()){ ibytes =stret2.getBytes(1);
        f++; }
        if(f>0){
        System.out.println("signature found");
        }
      } 
    catch (SQLException ex) {
        Logger.getLogger(InputStreamDataSource.class.getName()).log(Level.SEVERE, null, ex);
    }
     return new ByteArrayInputStream(ibytes);
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
      throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String getName() {
       return name1;
    }
    
}
