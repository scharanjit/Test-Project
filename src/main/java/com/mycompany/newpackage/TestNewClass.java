/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.newpackage;

import org.apache.commons.logging.*;

/**
 *
 * @author charanjiths
 */
public class TestNewClass {
private static final Log LOGGER = LogFactory.getLog(TestNewClass.class.getName());
    public static void main(String[] args) {
       
       
        
        System.out.println("Draw paint");
        try {
            int i = 5 / 0;
        } catch (Exception e) {
            LOGGER.info("dds");
            System.out.println("Caught");
        } finally {
            paint();
        }
        
        
        
    }

    private static void paint() {

        char[] ch = {'p', 'r', 'a', 'm', 'a', 't', 'i'};
        for (char c : ch) {
            System.out.print(c);
        }
        System.out.println("");
        for(char c:ch){
            System.out.print(c+' ');
        }
    }

}
