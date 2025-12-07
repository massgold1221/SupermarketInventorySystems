package ui.components;

import javax.swing.*;

/**
 * NumericTextField: Validated text field that only accepts numeric input
 */
public class NumericTextField extends JTextField {
    private static final long serialVersionUID = 1L;
    private boolean allowDecimal;
    
    public NumericTextField(int columns, boolean allowDecimal) {
        super(columns);
        this.allowDecimal = allowDecimal;
    }
    
    @Override
    public void setText(String text) {
        if (text == null || text.isEmpty()) {
            super.setText("");
            return;
        }
        
        if (isValidNumeric(text)) {
            super.setText(text);
        }
    }
    
    /**
     * Validate numeric input
     */
    private boolean isValidNumeric(String text) {
        if (allowDecimal) {
            return text.matches("^\\d*\\.?\\d*$");
        } else {
            return text.matches("^\\d*$");
        }
    }
    
    /**
     * Get numeric value
     */
    public double getNumericValue() {
        String text = getText();
        if (text.isEmpty()) {
            return 0;
        }
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
