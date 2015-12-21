/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mice;

/**
 *
 * @author Victoria
 */
public class Validation {
    
    
    
public static String containsString(String str)
{
    MainPage mp = new MainPage();
    String regex = "^[a-zåäöA-ZÅÄÖ]+$";
    String match;
    if (str.matches(regex))
        {
            match = "Sökningen körs";
        }
    else
    {
        mp.setErrorMessage("Sökordet måste vara ett namn");
        match = "Sökordet fungerar inte";
    }
    return match;
}
    
}



