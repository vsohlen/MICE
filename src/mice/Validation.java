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
 * @author Victoria & Anton
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

    /**
     * Checks if the variable contains valid characters.
     * @param str
     * @return 
     */
    public static boolean containsString(String str)
    {
        Boolean match= false;
        
        try
        {
        String regex = "^[a-zA-Z0-9åäöÅÄÖ ]+$";
        if (str.matches(regex))
            {
                match = true;
            }
        else
        {
            match = false;
        }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return match;
    }
    
    /**
     * Checks that a name entered contains the valid requirements.
     * @param name
     * @return 
     */
     public static boolean checkName(String name)
    {
       boolean match;
        
        String Name = name;
        
        if (Name.length() < 1 && Name.contains("\\s+"))
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
        if (mail.contains("@") && mail.contains("."))
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
     * checks the in-phone number
     * @param number
     * @return 
    */
    public static boolean checkNumber(String number)
    {
    String regex = "^[0-9\\-]+$";
        boolean match;
        if (number.matches(regex) && !number.contains("\\s+"))
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
     * Checks that a number is from this or the previous millenia.
     * @param number
     * @return 
     */
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
    
    /**
     * Checks that the date entered is valid. Checks for leap years and different
     * nummer of days days in different months.
     * @param year
     * @param month
     * @param day
     * @return 
     */
    public static boolean checkDate (int year, int month, int day)
    {
        boolean validDate = false;
        boolean leapYear = false;
        
        if ((month >= 1 && month <= 12) && (day >= 1 && day <= 31))
        {
            if((month == 4 || month == 6 || month == 9 || month == 11) && (day <= 30))
            {
                validDate = true;
            }
            if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day <= 31))
            {
                validDate = true;
            }
            if ((month == 2) && (day < 30))
            {
                leapYear = false;
                if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0)))
                {
                    leapYear = true;
                }
                if (leapYear == true && day <= 29)
                {
                    validDate = true;
                }
                else if (leapYear == false && day <= 28)
                {
                    validDate = true;
                }
            }
        }
        return validDate;
    }
    
    
}



