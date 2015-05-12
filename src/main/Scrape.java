import com.jaunt.*;
import com.jaunt.component.*;
import java.io.*;
import java.util.*;
import java.lang.*;

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
        else if (mangaSite.toLowerCase().contains("mangafox")){
        	String searchQuery = "title nowrap";
        	newTitles = mangaFox(userAgent, mangaSite, searchQuery);
        }
       // newTitles = formatString(newTitles);
        for (String titles : newTitles){
         	 printString(titles);
        }
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

 }