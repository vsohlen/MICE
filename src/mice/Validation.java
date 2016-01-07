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
    
     public static boolean checkName(String name1, String name2)
    {
       boolean match;
        
        String firstName = name1;
        String lastName = name2;
        
        if (firstName.length() < 1 && lastName.length() < 1 && firstName.contains("\\s+") && lastName.contains("\\s+"))
        {  
            match = false;
        }
        else
        {
            
            match = true;
        }
        
        return match;
  
    }
     /**
      * checks the mail if its valid
      * @param mail
      * @return 
      */  
     public static boolean checkMail(String mail)
    {
      //String regex = "^[a-zåäöA-ZÅÄÖ0-9.-]+@[a-zåäöA-ZÅÄÖ0-9.-]+\\.[A-Z]{2,6}+$";
        boolean match;
        if (mail.contains("@"))
            {
                match = true;
            }
        else
        {
            match = false;
        }
        return match;
    }
    /**
     * checks the in-phonenumber
     * @param number
     * @return 
    */
    public static boolean checkNumber(String number)
    {
    String regex = "^[0-9]+$";
        boolean match;
        if (number.matches(regex) && !number.contains("\\s+") && number.length() > 4)
            {
                match = true;
            }
        else
        {
            match = false;
        }
        return match;
    }
    
    public static boolean checkYear(String number)
    {
    
        try
        {
            boolean match;
            

            //Checks if the number is a number.
            boolean isNumber = checkNumber(number);

            if (isNumber == true && number.length() == 4)
            {
                match = true;
            }
            else
            {
                match = false;
            }

            return match;
        }
        
        catch(Exception e)
            {
                System.out.println(e.getMessage());
                return false;
            }
    }
    
    
}



