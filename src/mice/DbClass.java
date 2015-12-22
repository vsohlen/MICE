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
    public ArrayList<HashMap<String, String>> listProjects(String searchProject)
    {
            String sqlFraga = "select beteckning, startdatum, releasedatum from SPELPROJEKT " +
                              "join ANSTALLD on ANSTALLD.AID = SPELPROJEKT.AID " +
                              "where ANSTALLD.NAMN like '" + searchProject + "%'";
            
            ArrayList listProjects = new ArrayList<>();
            
            try
            {
                ArrayList<HashMap<String, String>> leadsProjects = idb.fetchRows(sqlFraga);
                /*for(int i = 0; i < leadsProjects.size(); i++)
                {
                    listProjects.add(leadsProjects.get(i).get("BETECKNING"));
                    listProjects.add(leadsProjects.get(i).get("STARTDATUM"));
                    listProjects.add(leadsProjects.get(i).get("RELEASEDATUM"));
                }*/
                return leadsProjects;
            }
            catch(InfException e)
            {
                System.out.println(e.getMessage());
                return null;
            }
    }
    
        /**
     * Checks if a username and password matches any values in the database.
     * If yes, return TRUE, if no, return FALSE.
     * @param username
     * @param password
     * @return 
     */
    public boolean adminLoginCheck(String username, String password)
    {
        //Initiates the uname and pass variables.
        String uname = "";
        String pass = "";
        
        //Stores a username from the DB where the username matches into a String.
     String sqlUsername = "Select ANVNAMN from ANSTALLD"
             + " where ANVNAMN = '" + username + "'";
     
     //Stores a password from the DB where username matches in to a String.
     //Will be used to check towards the parameter password.
     String sqlPass = "select LOSENORD from ADMINISTRATOR"
             + " where AID = (select AID from ANSTALLD"
             + " where ANVNAMN = '" + username + "')";
     
     try
     {
     uname = idb.fetchSingle(sqlUsername);
     pass = idb.fetchSingle(sqlPass);
     }
     catch(InfException e)
     {
         System.out.println(e.getMessage());
     }
     
     boolean response = false;
     
     if(username.equals(uname) && password.equals(pass))
     {
        response = true;
     }
    
     return response;
    }
}
    
