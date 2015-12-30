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
        
        if (firstName.length() < 2 && lastName.length() < 2 && firstName.contains("\\s+") && lastName.contains("\\s+"))
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
      * public void updateHiredForm()
{
	DbClass database = new DbClass();
        
		//add the hired staff to an existing cb
        ArrayList<HashMap<String, String>> allHired = database.listAllHired();
        for (int i = 0; i < allHired.size(); i++)
        {
            String aid = allHired.get(i).get("AID");
            String name = allHired.get(i).get("NAMN");
            cbListAllPosition.addItem(aid + ", " + name);
        }
		
	//Add all the employment-states to a cb
	String employment = cbHiredForm.getSelectedItem().toString();
	switch (employment)
	{
		case "Specialist" :
			break;
		case "Administratör" :
			lblAddPassword.setVisible(true);
			tfPassword.setVisible(true);
			btnConfirmNewPassword.setVisible(true);
			break;
		case "Projektledare" :
			break;
	}
}
      */
    
}



