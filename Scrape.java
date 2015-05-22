import com.jaunt.*;
import com.jaunt.component.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.net.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Scrape {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args){
        try{
            UserAgent userAgent = new UserAgent();
            String mangaSite = "http://mangafox.com";
            ArrayList<String> newTitles = new ArrayList<String>();

            MangaStream mangaStream = new MangaStream();
            MangaHere mangaHere = new MangaHere();
            MangaFox mangaFox = new MangaFox();

            if (mangaSite.toLowerCase().contains("mangastream")){
                String searchQuery = "featurebox-caption";
                newTitles = mangaStream.getReleases(userAgent, mangaSite, searchQuery);
            }
            else if (mangaSite.toLowerCase().contains("mangahere")){
                String searchQuery = "popular_img";
                newTitles = mangaHere.getReleases(userAgent, mangaSite, searchQuery);
                newTitles = spaceFixer(newTitles);
            }
            else if (mangaSite.toLowerCase().contains("mangafox")){
                String searchQuery = "title nowrap";
                newTitles = mangaFox.getReleases(userAgent, mangaSite, searchQuery);
            }

            String sentMess = "";
            newTitles = formatString(newTitles);
            for (String titles : newTitles){
                sentMess += titles;
                 printString(titles);
            }

            // Scrape http = new Scrape();
            // http.sendGet();
//            http.sendPost();

            Email email = new Email();
            System.out.println("nig");
            email.sendMail(sentMess);
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

    public static ArrayList<String> spaceFixer(ArrayList<String> newTitles){
      ArrayList<String> newerTitles = new ArrayList<String>();
      for (String title: newTitles){
          newerTitles.add(title.trim() + "\n");
      }
      return newerTitles;
    }

    // HTTP GET request
    private void sendGet() throws Exception {

        String url = "http://localhost:9000";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    // HTTP POST request
    private void sendPost() throws Exception {

        String url = "http://localhost:9200";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

 }
