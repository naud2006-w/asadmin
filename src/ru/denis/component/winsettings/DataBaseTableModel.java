/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.component.winsettings;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import ru.denis.component.SchemaUsrObject;

/**
 *
 * @author naumenko_ds
 */
public class DataBaseTableModel implements TableModel{
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private List<SchemaUsrObject> listObj ;
    
    public DataBaseTableModel(List<SchemaUsrObject> listObj) {
        this.listObj = listObj;
    }
    
    
    
    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }
    
    @Override
    public int getRowCount() {
        return listObj.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Нименование";
            case 1:
                return "Пользователь";
            case 2:
                return "Пароль";
            }
            return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        SchemaUsrObject obj = listObj.get(rowIndex);
            switch (columnIndex) {
            case 0:
                return obj.getName();
            case 1:
                return obj.getUsr();
            case 2:
                return obj.getPwd();
            }
            
            return "";         
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // пусто так как нередактируется
    }
}
