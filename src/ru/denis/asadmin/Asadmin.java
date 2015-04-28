/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.asadmin;

import java.io.IOException;
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
        
        
        //Mainwindow mw = new Mainwindow();
        
        //mw.show();
        
        
        Path comandFille = CommandBean.createFileComands("sdfsdgfasdfasdfd \r\n привет строка");
        
        Path batFile = CommandBean.createBatFile(comandFille);
        
        try {
            Runtime.getRuntime().exec(new String[] {"notepad.exe", batFile.toString()});
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Asadmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
