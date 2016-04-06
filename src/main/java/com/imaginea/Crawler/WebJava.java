/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imaginea.Crawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

            find(currentUrl);  //check href of all urls & sav dem to links

            boolean success = matchWord(searchString); //matching the search world..2014
            if (success) {
                System.out.println(String.format("**Success** Word %s found at %s", searchString, currentUrl));
                break;
            }
            this.pagesToVisit.addAll(nextPath());
        }
        System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
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
//                System.out.println(" "+link);
                this.links.add(link.absUrl("href")); //adding next link in a linked list
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();// We were not successful in our HTTP request
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
}
