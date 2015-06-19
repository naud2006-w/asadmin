
package ru.denis.command;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.denis.component.DomainGFObject;
import ru.denis.component.JDBCConnectObject;
import ru.denis.component.SchemaUsrObject;
import ru.denis.db.DataBaseUtils;

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
    
}
