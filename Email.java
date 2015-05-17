import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Email {

    public static void sendMail(String mess){
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        Properties prop = System.getProperties();
        prop.setProperty("mail.smtp.host", "smtp.gmail.com"); //i believe this sets the host
        prop.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.port", "465"); // this sets the port
        prop.setProperty("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.auth", "true"); //turn on the auth to login to gmail
        final String user = "updater.manga@gmail.com"; //made that gmail account
        final String pass = "shittypassword";

        try {
            Session session = Session.getDefaultInstance(prop,
                                                         new Authenticator(){
                                                             protected PasswordAuthentication getPasswordAuthentication() {
                                                                 return new PasswordAuthentication(user, pass);
                                                             }
                                                         });

            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ronnie.huang@hotmail.ca", false)); //set the reciever's email
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