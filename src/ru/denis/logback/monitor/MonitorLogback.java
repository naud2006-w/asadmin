/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.logback.monitor;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import ru.denis.asadmin.MainWindow;

/**
 *
 * @author naumenko_ds
 */
public class MonitorLogback {
    
    public void startMonotor(String domainName, MainWindow window){
        
        
        try{
            
            Path logFile = getLogFile(domainName);
            
            
            
            
        }catch(Exception e){
            window.getServerLogComponent().append("Ошибка получения информации из лога сервера");
            window.getServerLogComponent().append(e.getMessage());
        }
    
    }
    
    private Path getLogFile(String domainName) throws Exception{
        // получаем из системной настройки - путь до файла asadmin из него выделяем 
        // нужную папку
        
        String pathFolderDomain = "c:\\glassfish4\\glassfish\\domains" ;
        
        Path temp = Paths.get(pathFolderDomain);
        
        
        
        Path logFile = Paths.get(temp.toString(), domainName, "logs", "logback.log");
        
        if(!Files.exists(logFile)){
            throw new Exception("Ошибка поиска файла с логом сервера!");
        }        
        
        return logFile;
    }
    
}
