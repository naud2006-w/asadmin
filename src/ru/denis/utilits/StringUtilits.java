/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.utilits;

/**
 *
 * @author naumenko_ds
 */
public class StringUtilits {
    
    public static boolean isEmpty(String str){
        
        if(str == null || str.length() == 0 || "".equals(str)){
            return true;
        }else{
            return false;
        }        
    } 
    
}
