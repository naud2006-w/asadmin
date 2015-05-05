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
import ru.denis.asadmin.Mainwindow;
import ru.denis.component.ConnectJDBCObject;
import ru.denis.component.SchemaUsrObject;
import ru.denis.db.DataBaseUtils;
import ru.denis.utilits.AppConst;
import static ru.denis.utilits.StringUtilits.isEmpty;

/**
 *
 * @author naumenko_ds
 */
public class CommandBean {  
        
    private static String getAsadminBat() throws Exception{
        
        DataBaseUtils db = DataBaseUtils.getInstance();
        
        String setn = db.getSettingByName("pathasadminbat");
        
        if(isEmpty(setn)){
            throw new Exception("не заполенена настройка: " + AppConst.asadminbat);
        }
        
        return setn;
    }
    
    private static String getDefAdmPasswordFile() throws Exception{
        
        DataBaseUtils db = DataBaseUtils.getInstance();
        
        String setn = db.getSettingByName("defaultadminpass");
        
        if(isEmpty(setn)){
            throw new Exception("не заполенена настройка: " + AppConst.defadmpasswordfile);
        }
        
        return setn;
    }
    
    private static String getWorkFolder(){
        
        String wf = Paths.get("c://").toString();
        
        return wf;
    }
    
    private static Path createFileComands(String txt){
        
        Path cmd = null;
        try{
            String folder = getWorkFolder();

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
    
    private static Path createBatFile(Path comandFile, String portNumber) throws Exception {
        
        String defPasswordFile = getDefAdmPasswordFile();
        String asadminbat = getAsadminBat();        
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(asadminbat);        
        sb.append(" --port ");
        sb.append(portNumber);        
        sb.append(" --passwordfile ");        
        sb.append(defPasswordFile);        
        sb.append(" multimode --file ");        
        sb.append(comandFile.toString());
        sb.append(" ");        
        
        
        String folder = getWorkFolder();

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
        
    private static List<String> getOptionShemaList() throws Exception{
        List<String> res = new ArrayList<>();        
        
        List<SchemaUsrObject> listObj = DataBaseUtils.getInstance().getListSchemaUsrObject();        
        
        for(SchemaUsrObject itemi : listObj){
            res.add(itemi.getUsr());
        }
        
        return res;
    }
    
    private static List<String> getOptionDbList() throws Exception{
        List<String> res = new ArrayList<>();        
        
        List<ConnectJDBCObject> listObj = DataBaseUtils.getInstance().getListObjectConnect();
        
        for(ConnectJDBCObject itemi : listObj){
            res.add(itemi.getLink());
        }
        
        return res;
    }
    
    
    
    
    public static void runComand(String comand, Mainwindow window) throws Exception{
        
        if(isEmpty(comand)){
            
            LoggerBean.writeLog("Команда пустая!!!!");
            
            return;
        }
        
        String domainName = window.getDomainName();
        String portNumber = window.getDomainPort().toString();
        
        // если команда есть пробуем ее зарпустить        
        Path comandFille = CommandBean.createFileComands(comand);        
        
        Path batFile = CommandBean.createBatFile(comandFille, portNumber);
        
        try {
            Process p = Runtime.getRuntime().exec("cmd /c " + batFile.toString());           
            
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
            while((s = bufferedReader.readLine()) != null) System.out.println(s);
            
            p.exitValue();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Asadmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        LoggerBean.writeLog("команда НЕ пустая");        
    }
    
    public static String createCommand(String listelement, Mainwindow window) throws Exception{
        
        String rez = "";
        
        String[] command = AppConst.listCommand;
        
        if(listelement.equals(command[0])){
            rez = changeOption0(window);
        }else if(listelement.equals(command[1])){
            rez = stopServer1(window);
        }else if(listelement.equals(command[2])){
            rez = startServer2(window);
        }else if(listelement.equals(command[3])){
            rez = reStartServer3(window);
        }else if(listelement.equals(command[4])){
            rez = optionsServer4(window);
        }      
        
        
        return rez;
    }
    
    /**
     * 0 элемент
     * @return 
     */
    private static String changeOption0(Mainwindow window) throws Exception{
        
        StringBuilder sb = new StringBuilder();
        
        String domainName = window.getDomainName();
        List<String> optionSchemaList = getOptionShemaList();
        List<String> optionDbList = getOptionDbList();
        
        
        String curShemaName = window.getCurShemaName();
        String curDbName = window.getCurDbLink();
        
        for(String shi : optionSchemaList){
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
        

        sb.append("stop-domain ");
        sb.append(domainName); 
        sb.append("\r\n");
        sb.append("start-domain ");
        sb.append(domainName); 
        sb.append("\r\n");         
        
        
        return sb.toString();
    }
    
    private static String stopServer1(Mainwindow window){
        
        return "stop-domain " + window.getDomainName();
    }
    
    private static String startServer2(Mainwindow window){
        
        return "start-domain " + window.getDomainName();
    }
    
    private static String reStartServer3(Mainwindow window){
        return "restart-domain " + window.getDomainName();
    }
    
    private static String optionsServer4(Mainwindow window){
        return "list-jvm-options";
    }
}
