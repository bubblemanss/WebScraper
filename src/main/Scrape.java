import com.jaunt.*;
import com.jaunt.component.*;
import java.io.*;
import java.util.*;

public class Scrape{
  public static void main(String[] args){
    try{
        UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
        String mangaSite = "http://mangastream.com";  
        ArrayList<String> newTitles = new ArrayList<String>(); 
        if (mangaSite.toLowerCase().contains("mangastream"))                    //visit a ur
        {
    		String searchQuery = "featurebox-caption";
            newTitles = mangaStream(userAgent, mangaSite, searchQuery);
        }
        for (String titles : newTitles){
            System.out.println(titles);
        }
    }
    catch(Exception es){         //if an HTTP/connection error occurs, handle JauntException.
      System.err.println(es);
    }
  }

	  public static String trim(String manga){
	  	String [] content = manga.split(">"); //essentially splitting by both < and > leaving only content 
	  	if (content.length > 1)
	  	return content[1]; // Should only ever be 2 items
	  	else return "";
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
 }