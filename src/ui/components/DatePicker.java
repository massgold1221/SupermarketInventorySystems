package ui.components;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * DatePicker: Custom date picker component
 */
public class DatePicker extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JSpinner dateSpinner;
    private SpinnerDateModel spinnerModel;
    
    public DatePicker() {
        setLayout(new FlowLayout());
        
        spinnerModel = new SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        dateSpinner = new JSpinner(spinnerModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);
        
        add(dateSpinner);
    }
    
    /**
     * Get selected date
     */
    public LocalDate getSelectedDate() {
        java.util.Date date = (java.util.Date) dateSpinner.getValue();
        return new java.sql.Date(date.getTime()).toLocalDate();
    }
    
    /**
     * Set selected date
     */
    public void setSelectedDate(LocalDate date) {
        dateSpinner.setValue(java.sql.Date.valueOf(date));
    }
}
