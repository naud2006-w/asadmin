/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.asadmin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.denis.command.CommandBean;

/**
 *
 * @author naumenko_ds
 */
public class Asadmin {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
//        Mainwindow mw = new Mainwindow();
//        
//        mw.show();
        
        try {
            CommandBean.runComand();
        } catch (InterruptedException ex) {
            Logger.getLogger(Asadmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
