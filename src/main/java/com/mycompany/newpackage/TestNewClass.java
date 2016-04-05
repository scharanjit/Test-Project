/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.newpackage;

import java.awt.Graphics;

/**
 *
 * @author charanjiths
 */
public class TestNewClass {
    public static void main(String[] args) {
        System.out.println("Draw paint");
    }

    private static void paint(Graphics g) {
       
        char []ch={'p','r','a','m','a','t','i'};
        g.drawChars(ch, 0, 5, 25, 25);
    }
    
}
