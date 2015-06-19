package ru.denis.component.winsettings;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import ru.denis.component.JDBCConnectObject;

/**
 *
 * @author naumenko_ds
 */
public class JDBCConnectTableModel implements TableModel{

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private List<JDBCConnectObject> listObj ;
    
    public JDBCConnectTableModel(List<JDBCConnectObject> listObj) {
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
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Нименование";
            case 1:
                return "Cсылка";
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
        
        JDBCConnectObject obj = listObj.get(rowIndex);
            switch (columnIndex) {
            case 0:
                return obj.getName();
            case 1:
                return obj.getLink();
            }
            
            return "";         
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // пусто так как нередактируется        
    }

    
    
}
