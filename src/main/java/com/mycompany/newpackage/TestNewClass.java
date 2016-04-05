/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.newpackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

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
