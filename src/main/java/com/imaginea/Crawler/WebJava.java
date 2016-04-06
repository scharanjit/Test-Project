/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imaginea.Crawler;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author charanjiths
 */
public class WebJava {

    long NUMB = 1000;
    private Set<String> pagesVisited = new HashSet<String>(); //no duplicates
    private List<String> pagesToVisit = new LinkedList<String>(); //next node address
    private List<String> links = new LinkedList<String>(); // Just a list of URLs
    private Document htmlDocument;
    private Set finalList = new HashSet();

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:39.0) Gecko/20100101 Firefox/39.0";// identify themselves to web server

    private String nextUrl() //always return new url
    {
        String nextUrl;
        do {
            nextUrl = this.pagesToVisit.remove(0);//removes all url
        } while (this.pagesVisited.contains(nextUrl)); //checking in set
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }

    /*
    *   it will take two parameters:  url & searchString
    *   searchString
    *   it will call find method & find all the href 
        (hypertext reference of other pages
    *   it will also match the search String after calling matchWord method 
    *   this method will search all the links present in the provided url
    
     */
    public void search(String url, String searchString) {
        while (this.pagesVisited.size() < NUMB) {
            String currentUrl;
            if (this.pagesToVisit.isEmpty()) {
                currentUrl = url;
                this.pagesVisited.add(url);
            } else {
                currentUrl = this.nextUrl();
            }
            System.out.println(currentUrl);
            find(currentUrl);  //check href of all urls & sav dem to links

            boolean success = matchWord(searchString); //matching the search world..2014
            if (success) {
//                System.out.println(String.format("**Success** Word %s found at %s", searchString, currentUrl));
                break;
            }
            this.pagesToVisit.addAll(nextPath());
            
        }
        
        
//        System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
    }

    public boolean find(String currentUrl) {
        try {
            Connection connection = Jsoup.connect(currentUrl).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;

//            if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code
//                                                          // indicating that everything is great.
//            {
//                System.out.println("\n**Visiting** Received web page at " + currentUrl);
//            }
//            if(!connection.response().contentType().contains("text/html"))
//            {
//                System.out.println("**Failure** Retrieved something other than HTML");
//                return false;
//            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for (Element link : linksOnPage) {
                //finding next url present on page
                //hyperlink
                System.out.println(" "+link);
                this.links.add(link.absUrl("href")); //adding next link in a linked list
                }
            
            for(String l:links){
               if( l.contains("2014"))
                  
                        finalList.add(l.substring(0,64));
            }
            download(finalList);
            System.out.println(finalList);
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    public boolean matchWord(String searchWord) {
//         if(this.htmlDocument == null)
//        {
//            System.out.println("NO html ");
//            return false;
//        }
        try {
            System.out.println("Searching" + searchWord + "...");
            String bodyText = this.htmlDocument.body().text();
            Elements bodyTextq = this.htmlDocument.select(searchWord);
            System.out.println(bodyTextq);
            String baseUrl = this.htmlDocument.baseUri();
//        System.out.println(baseUrl);
//        System.out.println(bodyText);
            return bodyText.toLowerCase().contains(searchWord.toLowerCase());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;

    }

    public List<String> nextPath() {
        return this.links;
    }
    
    public void download(Set l){
    for(Object s :l){
        try {
            URL link = new URL(s.toString());
            
            //Code to download
		 InputStream in = new BufferedInputStream(link.openStream());
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
		 byte[] buf = new byte[1024];
		 int n = 0;
		 while (-1!=(n=in.read(buf)))
		 {
		    out.write(buf, 0, n);
		 }
		 out.close();
		 in.close();
		 byte[] response = out.toByteArray();
 String name="f.txt";
 File file= new File("/home/charanjiths/"+s.toString().substring(53, 64));
 file.createNewFile();
		 FileOutputStream fos = new FileOutputStream(file);
		 fos.write(response);
		 fos.close();
     //End download code
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(WebJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebJava.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
}
