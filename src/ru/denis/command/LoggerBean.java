/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.command;


import javax.swing.JTextArea;

/**
 *
 * @author naumenko_ds
 */
public class LoggerBean {
    
    public static void writeLog(String txt){
        System.out.println(txt);
    }
    
    
    public static void writeLogText(JTextArea ta, String text){
        ta.append(text);
        ta.append("\r\n");
        
        // прокручиваем текст к низу
        ta.setCaretPosition(ta.getText().length());
    }
}
