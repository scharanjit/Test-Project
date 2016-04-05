/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imaginea.Crawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author charanjiths
 */
public class WebJava {
     private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();

    private String nextUrl()
    {
        String nextUrl;
        do
        {
            nextUrl = this.pagesToVisit.remove(0);
        } while(this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }
    
    public void search(String url, String searchWord)
  {
      while(this.pagesVisited.size() <100)
      {
          String currentUrl;
          WebJavaClient leg = new WebJavaClient();
          if(this.pagesToVisit.isEmpty())
          {
              currentUrl = url;
              this.pagesVisited.add(url);
          }
          else
          {
              currentUrl = this.nextUrl();
          }
          leg.find(currentUrl);
          
          boolean success = leg.matchWord(searchWord);
          if(success)
          {
              System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
              break;
          }
          this.pagesToVisit.addAll(leg.nextPath());
      }
      System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
  }
    
}
