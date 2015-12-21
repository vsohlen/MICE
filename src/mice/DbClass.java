/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mice;

import java.util.ArrayList;
import java.util.HashMap;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 * The constructor of the database-class. Creates a connection to the SQL-database
 * @author Victoria
 */

public class DbClass {

    private InfDB idb;
    
    public DbClass()
    {
        try
        {
            String workingDir = System.getProperty("user.dir")+ "\\MICEDB.FDB";
            idb = new InfDB(workingDir);
            System.out.println("Uppkopplingen lyckades");
        }
        catch(InfException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * method for getting a list of which hired who works with a certain projekt
     * @param searchHired
     * @return     */
    public ArrayList<String> listHiredInProject(String searchHired)
    {   
        String sqlFraga = "SELECT BETECKNING FROM SPELPROJEKT"
                         +  " join ARBETAR_I on SPELPROJEKT.SID = ARBETAR_I.SID"
                         +  " join ANSTALLD on ARBETAR_I.AID = ANSTALLD.AID"
                         +  " where ANSTALLD.NAMN like '" + searchHired + "%'";
        
        ArrayList<String> listHired = new ArrayList<>(); 
      
        try
        {
        ArrayList<HashMap<String, String>> hiredProject = idb.fetchRows(sqlFraga);

        for (int i = 0; i < hiredProject.size(); i++)
            {
              listHired.add(hiredProject.get(i).get("BETECKNING"));
            }
        }
        catch(InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        return listHired;
    }
    
    /**
     * lists the projects as a projectleader leads.
     * @param searchProject
     * @return 
     */
    public ArrayList<String> listProjects(String searchProject)
    {
            String sqlFraga = "select beteckning, startdatum, releasedatum from SPELPROJEKT " +
                              "join ANSTALLD on ANSTALLD.AID = SPELPROJEKT.AID " +
                              "where ANSTALLD.NAMN like '" + searchProject + "%'";
            
            ArrayList listProjects = new ArrayList<>();
            
            try
            {
                ArrayList<HashMap<String, String>> leadsProjects = idb.fetchRows(sqlFraga);
                for(int i = 0; i < leadsProjects.size(); i++)
                {
                    listProjects.add(leadsProjects.get(i).get("BETECKNING"));
                    listProjects.add(leadsProjects.get(i).get("STARTDATUM"));
                    listProjects.add(leadsProjects.get(i).get("RELEASEDATUM"));
                }
            }
            catch(InfException e)
            {
                System.out.println(e.getMessage());
                return null;
            }
            return listProjects;
    }
            
}
    
