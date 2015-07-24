
package ru.denis.command;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.denis.component.DomainGFObject;
import ru.denis.component.JDBCConnectObject;
import ru.denis.component.SchemaUsrObject;
import ru.denis.db.DataBaseUtils;
import ru.denis.utilits.AppConstants;
import ru.denis.utilits.StringUtilits;

/**
 * Вспомогательный класс для обслуживания команд к компонентам.
 * 
 * @author naumenko_ds
 */
public class ComponentCommBean {
    
    public List<DomainGFObject> fillGFdomains(){
        
        List<DomainGFObject> listelement = null;
        
        DataBaseUtils dbu;
        try {
            dbu = DataBaseUtils.getInstance();                    
            
            listelement = dbu.getListDomainGFObject();
            
            if(listelement == null){
                listelement = new ArrayList<>();
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ComponentCommBean.class.getName()).log(Level.SEVERE, null, ex);
        }       
        
        return listelement;
    }
    
    public List<SchemaUsrObject> fillDBlist(){
        
        List<SchemaUsrObject> listelement = null;
        
        DataBaseUtils dbu;
        try {
            dbu = DataBaseUtils.getInstance();                    
            
            listelement = dbu.getListSchemaUsrObject();
            
            if(listelement == null){
                listelement = new ArrayList<>();
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ComponentCommBean.class.getName()).log(Level.SEVERE, null, ex);
        }       
        
        return listelement;
    }
    
    public List<JDBCConnectObject> fillJDBCLink(){
        
        List<JDBCConnectObject> listelement = null;
        
        DataBaseUtils dbu;
        try {
            dbu = DataBaseUtils.getInstance();                    
            
            listelement = dbu.getListObjectConnect();
            
            if(listelement == null){
                listelement = new ArrayList<>();
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ComponentCommBean.class.getName()).log(Level.SEVERE, null, ex);
        }       
        
        return listelement;
    }
    
    
    public String getGfRootFolder(){        
        String res = "";
        
        DataBaseUtils dbu;
        try {
            dbu = DataBaseUtils.getInstance();                    
            
            res = dbu.getSettingByName(AppConstants.sn_pathglassfish);          
            
        } catch (Exception ex) {
            Logger.getLogger(ComponentCommBean.class.getName()).log(Level.SEVERE, null, ex);
        }       
        
        return res;
    }
    
    public String getFileAdminPWD(){
        String res = "";
        
        DataBaseUtils dbu;
        try {
            dbu = DataBaseUtils.getInstance();                    
            
            res = dbu.getSettingByName(AppConstants.sn_defadmpasswordfile);          
            
        } catch (Exception ex) {
            Logger.getLogger(ComponentCommBean.class.getName()).log(Level.SEVERE, null, ex);
        }       
        
        return res;
    }
    
    public String getAppNameGkh(){
        String res = "";
        
        DataBaseUtils dbu;
        try {
            dbu = DataBaseUtils.getInstance();                    
            
            res = dbu.getSettingByName(AppConstants.sn_app_name_gkh);          
            
        } catch (Exception ex) {
            Logger.getLogger(ComponentCommBean.class.getName()).log(Level.SEVERE, null, ex);
        }       
        
        return res;
    }
    
    public void saveCommonSetting(File gfFolder, File adminPWDfile, String appNameGkh){
        
        String upGfFolder = "";
        String upAdminPwd = "";
        String upAppNameGkh = "";
        
        if(gfFolder != null){
            upGfFolder = String.format("update SYSTEM_SETTING s set s.value = '%s' where s.name_sys = '%s'", 
                    gfFolder.toString(),
                    AppConstants.sn_pathglassfish
                );
        }
        
        if(adminPWDfile != null){
            upAdminPwd = String.format("update SYSTEM_SETTING s set s.value = '%s' where s.name_sys = '%s'",
                adminPWDfile,
                AppConstants.sn_defadmpasswordfile);
        }
        
        if(!StringUtilits.isEmpty(appNameGkh)){
            upAppNameGkh = String.format("update SYSTEM_SETTING s set s.value = '%s' where s.name_sys = '%s'",
                appNameGkh,
                AppConstants.sn_app_name_gkh);
        }
        
        DataBaseUtils dbu;
        try {
            dbu = DataBaseUtils.getInstance();                    
            
            if(!StringUtilits.isEmpty(upGfFolder)){
                dbu.executeInsertDelUpdateQuery(upGfFolder); 
            }
            
            if(!StringUtilits.isEmpty(upAdminPwd)){
                dbu.executeInsertDelUpdateQuery(upAdminPwd); 
            }
            
            if(!StringUtilits.isEmpty(upAdminPwd)){
                dbu.executeInsertDelUpdateQuery(upAppNameGkh); 
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ComponentCommBean.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
