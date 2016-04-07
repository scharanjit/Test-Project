/*
 * File Name: WebJava.java
 * To KNow user agent of ur browser visit  :https://user-agents.me/test/what-is-my-user-agent
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
    private Set<String> urlNavigated = new HashSet<String>();       //no duplicates
    private List<String> urlRemaining = new LinkedList<String>();   //next node address
    private List<String> links = new LinkedList<String>();          // Just a list of URLs
    private Document htmlDocument;
    private Set finalList = new HashSet();                          // final list carrying searchWord urls           

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:39.0) Gecko/20100101 Firefox/39.0";// identify themselves to web server

    /*
    *   it will take two parameters:  url & searchString
    *   searchString
    *   it will call find method & find all the href 
        (hypertext reference of other pages
    *   it will also match the search String after calling matchWord method 
    *   this method will search all the links present in the provided url
    
     */
    public void search(String url, String searchString) {
        try {
            while (this.urlNavigated.size() < NUMB) {
                String currentUrl;
                if (this.urlRemaining.isEmpty()) {
                    currentUrl = url;
                    this.urlNavigated.add(url);
                } else {
                    currentUrl = this.nextUrl();
                }
                find(currentUrl, searchString);  //check href of all urls & sav dem to links

                boolean success = matchWord(searchString); //matching the search world..2014
                if (success) {
                    break;
                }
                this.urlRemaining.addAll(nextPath());

            }
        } catch (Exception e) {
        }

    }

    /*
    *   It will build a Connection using user agent of availble browser
    *   This method finds the href (hypertext reference) from the availble links
    *   after successful match found,it will call download method
     */
    public boolean find(String currentUrl, String searchString) {
        try {
            Connection connection = Jsoup.connect(currentUrl).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;

            Elements linksOnPage = htmlDocument.select("a[href]");
            for (Element link : linksOnPage) {
                //finding next url present on page
                //hyperlink
                this.links.add(link.absUrl("href")); //adding next link in a linked list
            }

            for (String l : links) {
                if (l.contains(searchString)) {
                    finalList.add(l.substring(0, 64));
                }
            }
            download(finalList); //download the files containing searchString
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    /*
    *   This method takes single parameter search word 
    *   And match it to the available list of urls
    *   It will return boolean output  true or false
     */

    public boolean matchWord(String searchWord) {
        try {
            String bodyText = this.htmlDocument.body().text();
            return bodyText.toLowerCase().contains(searchWord.toLowerCase());
        } catch (Exception ex) {
        }
        return false;

    }

    /*
    This method will always return the next node in the Linkedlist
     */
    public List<String> nextPath() {
        return this.links;
    }

    /*
    * This method will download all the files matching with search Word criteria
    *
     */
    public void download(Set l) {
        for (Object s : l) {
            try {
                URL link = new URL(s.toString());
                InputStream in = new BufferedInputStream(link.openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while (-1 != (n = in.read(buf))) {
                    out.write(buf, 0, n);
                }
                out.close();
                in.close();
                byte[] response = out.toByteArray();
                File file = new File("/home/charanjiths/" + s.toString().substring(53, 64));
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(response);
                fos.close();

            } catch (MalformedURLException ex) {
            } catch (IOException ex) {
            } catch (Exception e) {
            }
        }
    }

    private String nextUrl() //always return new url
    {
        String nextUrl;
        do {
            nextUrl = this.urlRemaining.remove(0);//removes all url
        } while (this.urlNavigated.contains(nextUrl)); //checking in set
        this.urlNavigated.add(nextUrl);
        return nextUrl;
    }
}
