package ui.components;

import javax.swing.*;
import java.util.Vector;

/**
 * CustomTableModel: Enhanced JTable model with sorting capability
 */
public class CustomTableModel extends javax.swing.table.DefaultTableModel {
    private static final long serialVersionUID = 1L;
    
    private int sortColumn = -1;
    private boolean sortAscending = true;
    
    public CustomTableModel(Vector<Vector<Object>> data, Vector<String> columnNames) {
        super(data, columnNames);
    }
    
    /**
     * Sort table by column
     */
    public void sortByColumn(int column) {
        if (sortColumn == column) {
            sortAscending = !sortAscending;
        } else {
            sortColumn = column;
            sortAscending = true;
        }
        
        // Trigger table refresh
        fireTableDataChanged();
    }
    
    /**
     * Get sort column
     */
    public int getSortColumn() {
        return sortColumn;
    }
    
    /**
     * Check if sort is ascending
     */
    public boolean isSortAscending() {
        return sortAscending;
    }
}
