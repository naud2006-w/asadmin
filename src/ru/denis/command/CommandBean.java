/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.command;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author naumenko_ds
 */
public class CommandBean {

    public static String changeOption(){
        
        StringBuilder sb = new StringBuilder();
        
        List<String> optionShemaList = new ArrayList<>();
        List<String> optionDbList = new ArrayList<>();
        
        String domainName = getDomainName();
        String curShemaName = "";
        String curDbName = "";
        
        for(String shi : optionShemaList){
            sb.append("delete-jvm-options -Dgkhconf.jvm.dbschema=");
            sb.append(shi);
            sb.append("\r\n");
        }
        
        sb.append("create-jvm-options -Dgkhconf.jvm.dbschema=");
        sb.append(curShemaName);
        sb.append("\r\n");
        
        for(String dbi : optionDbList){
            sb.append("delete-jvm-options -Dgkhconf.jvm.dbaddress=");
            sb.append(dbi);
            sb.append("\r\n");
        }
        
        // проставляем текущие опции
        
        sb.append("create-jvm-options -Dgkhconf.jvm.dbaddress=");
        sb.append(curDbName);
        sb.append("\r\n");
        
        
        //  jdbc\\:oracle\\:thin\\:@192\\.168\\.3\\.23\\:1521\\:db23

        sb.append("stop-domain ");
        sb.append(domainName); 
        sb.append("\r\n");
        sb.append("start-domain ");
        sb.append("\r\n");         
        
        
        return sb.toString();
    }
    
    
    private static String getDomainName(){
    
        return "domain2";
    }
    
    //private 
    
}
