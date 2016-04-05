/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testproject;

/**
 *
 * @author charanjiths
 */
public class TestClass {
    public static void meth(){
        System.out.println("in Meth");
    }
    
    public static void meth2(){
        System.out.println("in meth2");
    }
    public static void main(String[] args) {
        System.out.println("com.mycompany.testproject.TestClass.main()");
        TestClass.meth();
        TestClass.meth2();
        
    }
    
}
