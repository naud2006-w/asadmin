/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import ru.denis.db.DataBaseUtils;

/**
 *
 * @author naumenko_ds
 */
public class DomainGFCBoxModel extends DefaultComboBoxModel {
    
    public void setDataSource(){
        
        // очистим список
        removeAllElements();       
        
        
        DataBaseUtils dbu;
        try {
            dbu = DataBaseUtils.getInstance();
            
            List<DomainGFObject> listelement = dbu.getListDomainGFObject();
        
            for(DomainGFObject itemi : listelement){
                addElement(itemi);
            }           
            
        } catch (Exception ex) {
            Logger.getLogger(JDBCConnectCBoxModel.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
