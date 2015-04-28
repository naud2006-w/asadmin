/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.command;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.denis.asadmin.Asadmin;

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
    
    
    
    public static Path createFileComands(String txt){
        
        Path cmd = null;
        try{
            String folder = "c:/R";

            Path path = Paths.get(folder, "comand.txt");

            Files.deleteIfExists(path);
            
            cmd = Files.createFile(path);
        
            try(OutputStream bigOut = new BufferedOutputStream(Files.newOutputStream(cmd))){
                bigOut.write(txt.getBytes());
            }
            
        }catch(Exception e){
            System.out.println("ошибка!");
        }
        return cmd;
    }

    
    
    public static Path createBatFile(Path comandFile) {
        
        String defPasswordFile = "c:/default.adminPassword.properties";
        String asadminbat = "c:/glassfish4/bin/asadmin.bat";
        String portnumber = "2048";
        
        
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(asadminbat);
        sb.append(" ");
        sb.append("--port ");
        sb.append(portnumber);
        
        sb.append(" --passwordfile");
        sb.append(" ");
        sb.append(defPasswordFile);
        sb.append(" ");
        sb.append("multimode --file");
        sb.append(" ");
        sb.append(comandFile.toString());
        sb.append(" ");
        //sb.append(" >> rrr.txt");
        
        
        String folder = "c:/R";

        Path file = null;
        Path bat = null;
        try {
            file = Paths.get(folder, "run.bat");

            Files.deleteIfExists(file);
            bat = Files.createFile(file);         
            
            
            try(OutputStream bigOut = new BufferedOutputStream(Files.newOutputStream(bat))){
                bigOut.write(sb.toString().getBytes());
            }            
        
        } catch (IOException ex) {
            Logger.getLogger(CommandBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return bat;
    }
    
    public static void runComand() throws InterruptedException{
        Path comandFille = CommandBean.createFileComands(changeOption());
        //Path comandFille = CommandBean.createFileComands("sdfsdgfasdfasdfd \r\n привет строка");
        
        Path batFile = CommandBean.createBatFile(comandFille);
        
        try {
            Process p = Runtime.getRuntime().exec("cmd /c " + batFile.toString());
            //p.waitFor();
            
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
           while((s = bufferedReader.readLine()) != null) System.out.println(s);
            
           p.exitValue();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Asadmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
