import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;

public static class Email {

    public static void sendMail(String mess){
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        final String user = System.getenv("SCRAPER_EMAIL"); //grab the gmail account from environment variables
        final String pass = System.getenv("SCRAPER_PW");

        System.out.println (user + pass);
        Properties prop = System.getProperties();
        prop.setProperty("mail.smtp.host", "smtp.gmail.com"); //i believe this sets the host
        prop.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.port", "465"); // this sets the port
        prop.setProperty("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.auth", "true"); //turn on the auth to login to gmail


        try {
            Session session = Session.getDefaultInstance(prop,
                                                         new Authenticator(){
                                                             protected PasswordAuthentication getPasswordAuthentication() {
                                                                 return new PasswordAuthentication(user, pass);
                                                             }
                                                         });

            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("malcom309@gmail.com", false)); //set the reciever's email
            message.setSubject("Manga Update");
            message.setText(mess);
            Transport.send(message);
            System.out.println("Message sent");
        }
        catch(MessagingException me) {
            me.printStackTrace();
        }
    }

}