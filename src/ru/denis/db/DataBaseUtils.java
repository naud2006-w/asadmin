
package ru.denis.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс для обслуживания базы данных.
 * 
 * @author Denis
 */
public class DataBaseUtils {
    
    private static DataBaseUtils  instance;
    private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver" ;
    private String url = "";
    private Connection con = null;
    
    /**
     * Конструктор.
     * 
     * @throws java.lang.Exception
     */
    public DataBaseUtils() throws Exception {
        
        
        try{                       
            this.url = "jdbc:derby:"+ getDataBaseFolder() +";create=true" ;
            
            // получаем коннект
            getConnect();            
            
            // инициализация таблицы с системными параметрами
            initSettingsProgram();          
            
            
            
        }catch(Exception e){
            throw new Exception("Ошибка при открытии программы. " + e.getMessage());
        }
        
    }
    
    
    
    
    public static synchronized DataBaseUtils getInstance() throws Exception{
        if(instance == null){
            instance = new DataBaseUtils();
        }
        
        return instance;
    }
    
    public final synchronized Connection getConnect() throws Exception{
        if(this.con == null){
            Class.forName(driver) ;
            this.con = DriverManager.getConnection(url);
        }
        
        return this.con;
    }
    
    public void closeConnect(){
        try {
            Connection conL = getConnect( );
            
            conL.close();
        } catch (Exception ex) {
            Logger.getLogger(DataBaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    
    private String getDataBaseFolder(){
        
        return "c:/dataBaseJ/asadmin";
    }
    
    private void initSettingsProgram() throws Exception{       
        
        Connection conL = null;
        String sql = "create table SYSTEM_SETTING (id int not null, name_sys varchar(100) not null, name varchar(100) not null, value varchar(255), note varchar(255), "+
                    " CONSTRAINT PK_SYS_SETTING PRIMARY KEY ( ID ) ) ";
        String checkSql = "select count(*) cnt from SYS.SYSTABLES where tabletype = 'T' and tablename = 'SYSTEM_SETTING'";
        
        Statement stmt = null;
        ResultSet res = null;
        
        try{            
            conL = getConnect();
            
           stmt = conL.createStatement() ;
           
           res = stmt.executeQuery(checkSql);
           
           
           int cnt = 0;
           if(res.next()){
               cnt = Integer.parseInt(res.getString("cnt"));
           }           
            
           if(cnt < 1){
                stmt.executeUpdate(sql);
                conL.commit();
                
                // первоначальное заполнение данными
                executeInsertQuery(" insert into SYSTEM_SETTING (id, name_sys, name, value, note) values (1, 'pathasadminbat', 'Файл asadmin.bat', 'c:\\glassfish4\\bin\\asadmin.bat', 'файл с утилитой для glassfisha')");
                executeInsertQuery(" insert into SYSTEM_SETTING (id, name_sys, name, value, note) values (2, 'defaultadminpass', 'Файл с паролем админа', 'd:\\default.adminPassword.properties', 'файл с паролем админа' )");                               
           } 
        }catch(Exception e){
            throw new Exception("Ошибка при создании таблицы для хранения настроек системы");
        }finally{
            res.close();
            stmt.close();
        }
    }    
    
    private void executeInsertQuery(String sql) throws Exception{
        Connection conL = null;
        
        try{
            
            conL = getConnect();
            
            Statement stmt = conL.createStatement() ;
            
            stmt.executeUpdate(sql);
            
            conL.commit();            
        
        }catch(Exception e){
            throw new Exception("Ошибка при выполнении запроса. " + e.getMessage());
        }    
    }    
    
    public String getSettingByName(String name) throws Exception{
        String resStr = "";
        
        
        Connection conL = null;
                
        String sql = "select value from SYSTEM_SETTING where name_sys  = " + name;
        
        Statement stmt = null;
        ResultSet res = null;
        
        try{            
            conL = getConnect();
            
            stmt = conL.createStatement() ;
           
            res = stmt.executeQuery(sql);                     
            
            if(res.next()){
                resStr = res.getString("value");
            }            
        }catch(Exception e){
            throw new Exception("Ошибка при получении настройки системы " + name);
        }finally{
            res.close();
            stmt.close();
        } 
        
        return resStr;
    }
    
}
