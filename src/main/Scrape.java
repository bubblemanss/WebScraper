import com.jaunt.*;
import com.jaunt.component.*;
import java.io.*;
import java.util.*;

public class Scrape{
  public static void main(String[] args){
    try{
        UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
        userAgent.visit("http://mangastream.com");                        //visit a url
        String html = userAgent.doc.innerHTML();
        String[] s = html.split("<");
        String searchQuery = "featurebox-caption";
        ArrayList<String> newTitles = new ArrayList<String>();

        for (int i = 0; i < s.length; i++) {
            if (s[i].toLowerCase().contains(searchQuery.toLowerCase())) {
                newTitles.add(s[i+1] + s[i+4]);
            }
        }
        for (String titles : newTitles){
            System.out.println(titles);
        }
    }
    catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
      System.err.println(e);
    }
  }
}