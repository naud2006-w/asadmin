
package ru.denis.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.denis.command.CommandBean;
import ru.denis.command.LoggerBean;
import ru.denis.component.JDBCConnectObject;
import ru.denis.component.DomainGFObject;
import ru.denis.component.SchemaUsrObject;
import ru.denis.utilits.AppConstants;

/**
 * ����� ��� ������������ ���� ������.
 * 
 * @author Denis
 */
public class DataBaseUtils {
    
    private static DataBaseUtils  instance;
    private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver" ;
    private String url = "";
    private Connection con = null;
    
    
    
    /**
     * �����������.
     * 
     * @throws java.lang.Exception
     */
    public DataBaseUtils() throws Exception {
        
        
        try{            
            this.url = "jdbc:derby:"+ getDataBaseFolder() +";create=true" ;
            
            // �������� �������
            getConnect();            
            
            // ������������� ������� � ���������� �����������
            initSettingsProgram();          
            
            // ������� � ���������
            initJDBCConnectTable();
            
            // ������� � ��������� ������
            initDomainGFTable();
            
            // ������� �� �������
            initSchemaNameTable(); 
            
        }catch(Exception e){
            throw new Exception("������ ��� �������� ���������. " + e.getMessage());
        }
        
    }
    
    
    private String getDataBaseFolder(){
        
        //C:\Users\naumenko_ds\AppData\Local\asadminj
        
        Path dbfolder = Paths.get(CommandBean.getAppFolder().toString(), "db");                               
                
        return dbfolder.toString();
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
                
                // �������������� ���������� �������
                executeInsertDelUpdateQuery(" insert into SYSTEM_SETTING (id, name_sys, name, value, note) values (1, '"+ AppConstants.sn_pathglassfish + "', '" + AppConstants.pathglassfish +"', 'c:\\glassfish4', '������� ������� Glassfish')");
                executeInsertDelUpdateQuery(" insert into SYSTEM_SETTING (id, name_sys, name, value, note) values (2, '"+ AppConstants.sn_defadmpasswordfile + "', '" + AppConstants.defadmpasswordfile +"', 'd:\\default.adminPassword.properties', '���� � ������� ������' )");                               
                executeInsertDelUpdateQuery(" insert into SYSTEM_SETTING (id, name_sys, name, value, note) values (3, '"+ AppConstants.sn_app_name_gkh + "', '" + AppConstants.app_name_gkh +"', 'web', '��������� ��� �������� � �������' )");                               
           } 
        }catch(Exception e){
            throw new Exception("������ ��� �������� ������� ��� �������� �������� �������");
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
                
                // �������������� ���������� �������                
                executeInsertDelUpdateQuery(" insert into JDBC_LINK(name, link) values ('��� db23', 'jdbc:oracle:thin:@192.168.3.23:1521:db23')");
                executeInsertDelUpdateQuery(" insert into JDBC_LINK(name, link) values ('��� xe', 'jdbc:oracle:thin:@192.168.3.23:11521:xe')");
                executeInsertDelUpdateQuery(" insert into JDBC_LINK(name, link) values ('�������� xe', 'jdbc:oracle:thin:@192.168.3.27:1521:xe')");
                executeInsertDelUpdateQuery(" insert into JDBC_LINK(name, link) values ('�������� xe', 'jdbc:oracle:thin:@192.168.3.156:1521:xe')");
                executeInsertDelUpdateQuery(" insert into JDBC_LINK(name, link) values ('�������� xe', 'jdbc:oracle:thin:@192.168.3.29:1521:xe')");
                executeInsertDelUpdateQuery(" insert into JDBC_LINK(name, link) values ('ssh ��������� localhost:21521:krasdb', 'jdbc:oracle:thin:@localhost:21521:krasdb')");
                executeInsertDelUpdateQuery(" insert into JDBC_LINK(name, link) values ('ssh �������� localhost:21521:murdb', 'jdbc:oracle:thin:@localhost:21521:murdb')");
                executeInsertDelUpdateQuery(" insert into JDBC_LINK(name, link) values ('��� 3.30', 'jdbc:oracle:thin:@192.168.3.30:1521:murdb')");
           } 
        }catch(Exception e){
            throw new Exception("������ ��� �������� ������� ��� �������� ��������.");
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
                
                // �������������� ���������� �������                
                executeInsertDelUpdateQuery(" insert into DOMAIN_GF(name, port) values ('domain2', 20048)");
                executeInsertDelUpdateQuery(" insert into DOMAIN_GF(name, port) values ('domain1', 4848)");                
           } 
        }catch(Exception e){
            throw new Exception("������ ��� �������� ������� ��� �������� ������� GF.");
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
                
                // �������������� ���������� �������                
                executeInsertDelUpdateQuery(" insert into SCHEMA_BD(name, usr, pwd) values ('kalmykiya_prod', 'kalmykiya_prod', 'kalmykiya_prod')");
                executeInsertDelUpdateQuery(" insert into SCHEMA_BD(name, usr, pwd) values ('kalmykiya_prod0213', 'kalmykiya_prod0213', 'kalmykiya_prod0213')");
                executeInsertDelUpdateQuery(" insert into SCHEMA_BD(name, usr, pwd) values ('krasnodar_prod', 'krasnodar_prod', 'krasnodar_prod')");
                executeInsertDelUpdateQuery(" insert into SCHEMA_BD(name, usr, pwd) values ('krasnodar_prod0505', 'krasnodar_prod0505', 'krasnodar_prod0505')");
                executeInsertDelUpdateQuery(" insert into SCHEMA_BD(name, usr, pwd) values ('candidate_test', 'candidate_test', 'candidate_test')");
                executeInsertDelUpdateQuery(" insert into SCHEMA_BD(name, usr, pwd) values ('novgorod_prod', 'novgorod_prod', 'novgorod_prod')");
                executeInsertDelUpdateQuery(" insert into SCHEMA_BD(name, usr, pwd) values ('release_test', 'release_test', 'release_test')");
                executeInsertDelUpdateQuery(" insert into SCHEMA_BD(name, usr, pwd) values ('murmansk_prod0430', 'murmansk_prod0430', 'murmansk_prod0430')");
           } 
        }catch(Exception e){
            throw new Exception("������ ��� �������� ������� ��� �������� ���������� � ������ ������������.");
        }finally{
            res.close();
            stmt.close();
        }
    }
    
    public void executeInsertDelUpdateQuery(String sql) throws Exception{
        Connection conL = null;
        
        try{
            
            conL = getConnect();
            
            Statement stmt = conL.createStatement() ;
            
            stmt.executeUpdate(sql);
            
            conL.commit();            
        
        }catch(Exception e){
            throw new Exception("������ ��� ���������� �������. " + e.getMessage());
        }    
    }    
    
    
    public String getSettingByName(String name) throws Exception{
        String resStr = "";
        
        
        Connection conL = null;
                
        String sql = "select value from SYSTEM_SETTING where name_sys  = '" + name + "'";
        
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
            throw new Exception("������ ��� ��������� ��������� ������� " + name);
        }finally{
            res.close();
            stmt.close();
        } 
        
        return resStr;
    }   
   
    
    //**** ��� ���������� �� ������� �����������
    public List<JDBCConnectObject> getListObjectConnect(){
        List<JDBCConnectObject> res = new ArrayList<>();
        
        Connection conL = null;
                
        String sql = "select name, link from JDBC_LINK order by name DESC ";
        
        Statement stmt = null;
        ResultSet resSql = null;
        
        try{
            conL = getConnect();

            stmt = conL.createStatement() ;

            resSql = stmt.executeQuery(sql);                     

            while(resSql.next()){
                JDBCConnectObject jo = new JDBCConnectObject(resSql.getString("name"), resSql.getString("link")); 
                
                res.add(jo);
            }         

        }catch(Exception e){
            // ����� � ���
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
            // ����� � ���
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
            // ����� � ���
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
    
    public SchemaUsrObject getSchemaUsrObjectByName(String name) throws Exception {
        SchemaUsrObject res = null;
        
        Connection conL = null;
                
        String sql = "select id, name, usr, pwd from SCHEMA_BD where name = " + name;
        
        Statement stmt = null;
        ResultSet resSql = null;
        
        try{
            conL = getConnect();

            stmt = conL.createStatement() ;

            resSql = stmt.executeQuery(sql); 
            
            if(resSql.getFetchSize() < 1) throw new Exception("������ ������ ��������� ������������ �� ����� " + name);

            while(resSql.next()){
                SchemaUsrObject jo = new SchemaUsrObject(resSql.getString("name"), resSql.getString("usr"), resSql.getString("pwd"));                 
            }         

        }catch(Exception e){
            throw e;
            // ����� � ���
            //LoggerBean.writeLog(e.getMessage());
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
