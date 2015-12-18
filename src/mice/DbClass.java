/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mice;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComboBox;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author Victoria
 */

public class DbClass {

    private InfDB idb;
    
    public DbClass()
    {
        try
        {
            String workingDir = System.getProperty("user.dir");
            idb = new InfDB(workingDir + "\\MICEDB (1).FDB");
            System.out.println("Uppkopplingen lyckades");
        }
        catch(InfException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * method for getting a list of which hired who works with a certain projekt
     */
    public void getHiredInProject()
    {   
                String sqlFraga = "SELECT BETECKNING FROM SPELPROJEKT "
                + "WHERE SPELPROJEKT.SID LIKE (SELECT ARBETAR_I.SID FROM ARBETAR_I "
                + "WHERE ARBETAR_I.AID LIKE (SELECT ANSTALLD.AID FROM ANSTALLD "
                + "WHERE NAMN LIKE 'My%'))";     ///*searchHired*/ +
        
        try
        {
            ArrayList<HashMap<String, String>> hiredProject = idb.fetchRows(sqlFraga);
            
            for (int i = 0; i < hiredProject.size(); i++)
            {
                //cbsearchHits.addItem(hiredProject.get(i).get("BETECKNING"));
            }
            System.out.println("IT FREAKING WORKS");
        }
        catch(InfException e)
        {
            System.out.println("Något gick fel");
            System.out.println(e.getMessage());
        }
    }
}
