/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mice;
import java.awt.Color;
import javax.swing.JTextField; 
/**
 *
 * @author Victoria
 */
public class Validation {
    
    /**
     * checks if the textField contains a string of text or not
     * @param textField
     * @return 
     */
    public static boolean textBoxTextIsRequired (JTextField textField)
    {
        if (textField.getText().isEmpty())
        {
            textField.requestFocus();
            textField.setBackground(Color.red);
            return false;
        }
        else
        {
            textField.setBackground(Color.white);
            return true;
        }
    }

    public static boolean containsString(String str)
    {
        String regex = "^[a-zåäöA-ZÅÄÖ]+$";
        Boolean match;
        if (str.matches(regex))
            {
                match = true;
            }
        else
        {
            match = false;
        }
        return match;
    }
    
}



