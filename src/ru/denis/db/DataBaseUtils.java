
package ru.denis.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.denis.command.LoggerBean;
import ru.denis.component.ConnectJDBCObject;
import ru.denis.component.DomainGFObject;
import ru.denis.component.SchemaUsrObject;

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
            
            // таблица с конектами
            initJDBCConnectTable();
            
            // таблица с настрйкой домена
            initDomainGFTable();
            
            // таблица со схемами
            initSchemaNameTable(); 
            
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
    
    private void initJDBCConnectTable() throws Exception{
        Connection conL = null;
        String sql = "create table JDBC_LINK (name varchar(50)  not null, link varchar(100) not null, "+
                    " CONSTRAINT PK_JDBC_LINK PRIMARY KEY ( NAME ) ) ";
        String checkSql = "select count(*) cnt from SYS.SYSTABLES where tabletype = 'T' and tablename = 'JDBC_LINK'";
        
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
                executeInsertQuery(" insert into JDBC_LINK(name, link) values ('ЛПР db23', 'jdbc:oracle:thin:@192.168.3.23:1521:db23')");
                executeInsertQuery(" insert into JDBC_LINK(name, link) values ('ЛПР xe', 'jdbc:oracle:thin:@192.168.3.23:11521:xe')");
                executeInsertQuery(" insert into JDBC_LINK(name, link) values ('Мурманск xe', 'jdbc:oracle:thin:@192.168.3.27:1521:xe')");
                executeInsertQuery(" insert into JDBC_LINK(name, link) values ('Калмыкия xe', 'jdbc:oracle:thin:@192.168.3.156:1521:xe')");
                executeInsertQuery(" insert into JDBC_LINK(name, link) values ('Новгород xe', 'jdbc:oracle:thin:@192.168.3.29:1521:xe')");
                executeInsertQuery(" insert into JDBC_LINK(name, link) values ('ssh Краснодар localhost:21521:krasdb', 'jdbc:oracle:thin:@localhost:21521:krasdb')");
                executeInsertQuery(" insert into JDBC_LINK(name, link) values ('ssh Мурманск localhost:21521:murdb', 'jdbc:oracle:thin:@localhost:21521:murdb')");
           } 
        }catch(Exception e){
            throw new Exception("Ошибка при создании таблицы для хранения серверов.");
        }finally{
            res.close();
            stmt.close();
        }        
    }
    
    private void initDomainGFTable() throws Exception{
        Connection conL = null;
        String sql = "create table DOMAIN_GF (name varchar(50)  not null, port int not null, "+
                    " CONSTRAINT PK_DOMAIN_GF PRIMARY KEY ( NAME ) ) ";
        String checkSql = "select count(*) cnt from SYS.SYSTABLES where tabletype = 'T' and tablename = 'DOMAIN_GF'";
        
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
                executeInsertQuery(" insert into DOMAIN_GF(name, port) values ('domain2', 20048)");
                executeInsertQuery(" insert into DOMAIN_GF(name, port) values ('domain1', 4848)");                
           } 
        }catch(Exception e){
            throw new Exception("Ошибка при создании таблицы для хранения доменов GF.");
        }finally{
            res.close();
            stmt.close();
        }
    }
    
    private void initSchemaNameTable() throws Exception{
        Connection conL = null;
        String sql = "create table SCHEMA_BD (name varchar(50)  not null, usr varchar(50) not null, pwd varchar(50) not null, "+
                    " CONSTRAINT PK_SCHEMA_BD PRIMARY KEY ( NAME ) ) ";
        String checkSql = "select count(*) cnt from SYS.SYSTABLES where tabletype = 'T' and tablename = 'SCHEMA_BD'";
        
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
                executeInsertQuery(" insert into SCHEMA_BD(name, usr, pwd) values ('kalmykiya_prod', 'kalmykiya_prod', 'kalmykiya_prod')");
                executeInsertQuery(" insert into SCHEMA_BD(name, usr, pwd) values ('kalmykiya_prod0213', 'kalmykiya_prod0213', 'kalmykiya_prod0213')");
                executeInsertQuery(" insert into SCHEMA_BD(name, usr, pwd) values ('krasnodar_prod', 'krasnodar_prod', 'krasnodar_prod')");
                executeInsertQuery(" insert into SCHEMA_BD(name, usr, pwd) values ('krasnodar_prod0505', 'krasnodar_prod0505', 'krasnodar_prod0505')");
                executeInsertQuery(" insert into SCHEMA_BD(name, usr, pwd) values ('candidate_test', 'candidate_test', 'candidate_test')");
                executeInsertQuery(" insert into SCHEMA_BD(name, usr, pwd) values ('novgorod_prod', 'novgorod_prod', 'novgorod_prod')");
                executeInsertQuery(" insert into SCHEMA_BD(name, usr, pwd) values ('release_test', 'release_test', 'release_test')");
           } 
        }catch(Exception e){
            throw new Exception("Ошибка при создании таблицы для хранения информации о схемах пользователя.");
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
    
    
    
    
    //**** для комбобокса со списком подключений
    public List<ConnectJDBCObject> getListObjectConnect(){
        List<ConnectJDBCObject> res = new ArrayList<>();
        
        Connection conL = null;
                
        String sql = "select name, link from JDBC_LINK order by name DESC ";
        
        Statement stmt = null;
        ResultSet resSql = null;
        
        try{
            conL = getConnect();

            stmt = conL.createStatement() ;

            resSql = stmt.executeQuery(sql);                     

            while(resSql.next()){
                ConnectJDBCObject jo = new ConnectJDBCObject(resSql.getString("name"), resSql.getString("link")); 
                
                res.add(jo);
            }         

        }catch(Exception e){
            // пишем в лог
            LoggerBean.writeLog(e.getMessage());
        }finally{            
            try {
                resSql.close();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataBaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }        
        
        return res;
    } 

    public List<SchemaUsrObject> getListSchemaUsrObject() {
        List<SchemaUsrObject> res = new ArrayList<>();
        
        Connection conL = null;
                
        String sql = "select name, usr, pwd from SCHEMA_BD order by name DESC ";
        
        Statement stmt = null;
        ResultSet resSql = null;
        
        try{
            conL = getConnect();

            stmt = conL.createStatement() ;

            resSql = stmt.executeQuery(sql);                     

            while(resSql.next()){
                SchemaUsrObject jo = new SchemaUsrObject(resSql.getString("name"), resSql.getString("usr"), resSql.getString("pwd")); 
                
                res.add(jo);
            }         

        }catch(Exception e){
            // пишем в лог
            LoggerBean.writeLog(e.getMessage());
        }finally{            
            try {
                resSql.close();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataBaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }        
        
        return res;
    }

    public List<DomainGFObject> getListDomainGFObject() {
        List<DomainGFObject> res = new ArrayList<>();
        
        Connection conL = null;
                
        String sql = "select name, port from DOMAIN_GF order by name DESC ";
        
        Statement stmt = null;
        ResultSet resSql = null;
        
        try{
            conL = getConnect();

            stmt = conL.createStatement() ;

            resSql = stmt.executeQuery(sql);                     

            while(resSql.next()){
                DomainGFObject jo = new DomainGFObject(resSql.getString("name"), resSql.getInt("port")); 
                
                res.add(jo);
            }         

        }catch(Exception e){
            // пишем в лог
            LoggerBean.writeLog(e.getMessage());
        }finally{            
            try {
                resSql.close();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataBaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }        
        
        return res;
    }
    
}
