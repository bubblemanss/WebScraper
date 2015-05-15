import com.jaunt.*;
import com.jaunt.component.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Scrape{

  public static void main(String[] args){
    try{
        UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
        String mangaSite = "http://mangahere.com";  
        ArrayList<String> newTitles = new ArrayList<String>(); 
        if (mangaSite.toLowerCase().contains("mangastream"))                    //visit a ur
        {
    		String searchQuery = "featurebox-caption";
            newTitles = mangaStream(userAgent, mangaSite, searchQuery);
        }else if(mangaSite.toLowerCase().contains("mangahere")){
        	String searchQuery = "popular_img";
        	newTitles = mangaHere(userAgent, mangaSite, searchQuery);
        	newTitles = spaceFixer(newTitles);        	
        }
        else if (mangaSite.toLowerCase().contains("mangafox")){
        	String searchQuery = "title nowrap";
        	newTitles = mangaFox(userAgent, mangaSite, searchQuery);
        }
        
        String sentMess = "";
       // newTitles = formatString(newTitles);
        for (String titles : newTitles){
        	sentMess += titles;
         	 printString(titles);
        }
        sendMail(sentMess);
    }
    catch(Exception es){         //if an HTTP/connection error occurs, handle JauntException.
      System.err.println(es);
    }
  }


  //Example: &&&&One Piece 90&& -> &One Piece 490&
  public static ArrayList<String> formatString(ArrayList<String> titles){
  	int i = 0; 
  	while (i < titles.size()-1) {
  		//if (titles.get(i) == "&" && (titles.get(i+1) == "&")) titles.remove(i);
  		
  		if (titles.get(i) == "&" && (titles.get(i+1) == "&")) titles.remove(i);
  		else i ++;
  		//System.out.println(i + " " + titles.get(i));
  	}
  	
  	return titles;
  }

  public static void printString(String titles){
  	if (titles == "&") System.out.println ();//Next Line
  	else System.out.print (titles + " ");//Proper Text
  }


  public static String trim(String manga){
  	String [] content = manga.split(">"); //essentially splitting by both < and > leaving only content 
  	if (content.length > 1){
  		if(isEmpty(content[1].trim())) return "&"; // Should only ever be 2 items
  		else return content[1].trim();
  	}
  	else return "&";
  }

	public static boolean isEmpty (String word){
		if(word.length() < 1) return true;
	else return false; 
	}

  public static ArrayList<String> mangaStream(UserAgent userAgent, String mangaSite, String searchQuery){
  	try
  	{
	  	userAgent.visit(mangaSite);                        //visit a url
        String html = userAgent.doc.innerHTML();
        String[] s = html.split("<");
        ArrayList<String> newTitles = new ArrayList<String>();

        for (int i = 0; i < s.length; i++) {
            if (s[i].toLowerCase().contains(searchQuery.toLowerCase())) {
            	i++; //to avoid hitting current div
                while (!s[i].toLowerCase().contains("div"))
                {	
                	newTitles.add(trim(s[i]));
                	i++;
                }

            }
        }
        return newTitles;
	}
	catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
  		System.err.println(e);
  		return null;
	}

  }


  public static ArrayList<String> mangaFox(UserAgent userAgent, String mangaSite, String searchQuery){
  	try
  	{
	  	userAgent.visit(mangaSite);                        //visit a url
        String html = userAgent.doc.innerHTML();
        String[] s = html.split("<");
        ArrayList<String> newTitles = new ArrayList<String>();

        for (int i = 0; i < s.length; i++) {
            if (s[i].toLowerCase().contains(searchQuery.toLowerCase())) {
            	i++; //to avoid hitting current div
                while (!s[i].toLowerCase().contains("releases"))
                {	
                	newTitles.add(trim(s[i]));
                	i++;
                }
                break;
            }
        }
        return newTitles;
	}
	catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
  		System.err.println(e);
  		return null;
	}

  }
	  
	  public static ArrayList<String> mangaHere(UserAgent userAgent, String mangaSite, String searchQuery){
		  try{
			  userAgent.visit(mangaSite);
			  String html = userAgent.doc.innerHTML();
			  String[] s = html.split("<");
			  ArrayList<String> newTitles = new ArrayList<String>();
			  
			  for(int i = 0; i < s.length; i++){
				  if(s[i].toLowerCase().contains(searchQuery.toLowerCase())){
					  newTitles.add(trim(s[i + 4]));
					  i = i + 4;
				  }
			  }
			  return newTitles;
		  }
		  catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
	      		System.err.println(e);
	      		return null;
	    	}
	  }
	  
	  public static ArrayList<String> spaceFixer(ArrayList<String> newTitles){
		  ArrayList<String> newerTitles = new ArrayList<String>();
		  for(String title: newTitles){
			  newerTitles.add(title.trim() + "\n");
		  }
		  return newerTitles;
	  }
	  
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
		  try{
		     Session session = Session.getDefaultInstance(prop, 
		    		 new Authenticator(){protected PasswordAuthentication getPasswordAuthentication() {
		                                return new PasswordAuthentication(user, pass);
		                             }}); 

		     Message message = new MimeMessage(session);
		     message.setRecipients(Message.RecipientType.TO, 
		                      InternetAddress.parse("ronnie.huang@hotmail.ca",false)); //set the reciever's email
		     message.setSubject("Manga Update");
		     message.setText(mess);
		     Transport.send(message);
		     System.out.println("Message sent");
		  }catch(MessagingException me){
			  me.printStackTrace();
		  }
	  }

 }
