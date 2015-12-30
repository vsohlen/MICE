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

        try
        {
            ArrayList<HashMap<String, String>> leadsProjects = idb.fetchRows(sqlFraga);
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
    
    public void deleteHired(String deleteAID)
    {
       String sqlFraga = "delete from anstalld "
               + "where aid = " + deleteAID + ";";
       
        try 
        {
            idb.delete(sqlFraga);
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
        }
    
    }
    
    
    
    /**
     * Updates all info about the hired person at once. No field is aloud to be left empty
     *                      //Validate so that no field can be empty in the vadlidation-class
     * @param setName
     * @param setPhone
     * @param setMail
     * @param AID
     */
    public void changeHired(String setName, String setPhone, String setMail, int AID)
    {
        String sqlFraga = "update ANSTALLD " +
                          "set Namn = '" + setName + "', Telefon = '" + setPhone + "', Mail = '" + setMail + "'" +
                          "where AID = " + AID;
        try 
        {
            idb.update(sqlFraga);
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public ArrayList<HashMap<String, String>> listAllHired()
    {
        String sqlFraga = "select * from ANSTALLD";
        
        try
        {
           ArrayList<HashMap<String, String>> allHired = idb.fetchRows(sqlFraga);
           return allHired;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     Lists all the specialists.
     */
    public ArrayList<HashMap<String, String>> listAllSpecialists()
    {
        String sqlFraga = "select anstalld.aid, anstalld.namn "
                + "from ANSTALLD JOIN SPECIALIST on SPECIALIST.AID = ANSTALLD.AID";
        
        try
        {
           ArrayList<HashMap<String, String>> allHired = idb.fetchRows(sqlFraga);
           return allHired;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public void updateSpecialistProject(String specialist, String project)
    {
        String sqlFraga ="insert into ARBETAR_I " 
               + "(AID, SID) "
               + "values((select anstalld.AID from anstalld "
               + "where namn = '" + specialist + "'), (select spelprojekt.sid from spelprojekt "
               + "where spelprojekt.BETECKNING = '" + project + "'))";
        
        try
	{
            idb.insert(sqlFraga);
	}
	catch (InfException e)
	{
		System.out.println(e.getMessage());
	}
    }
    
     public void deleteSpecialistProject(String specialist, String project)
    {
        String sqlFraga ="delete from ARBETAR_I " 
               + "where AID = (select anstalld.AID from anstalld "
               + "where namn = '" + specialist + "') and SID = (select spelprojekt.sid from spelprojekt "
               + "where spelprojekt.BETECKNING = '" + project + "')";
        
        try
	{
            idb.delete(sqlFraga);
	}
	catch (InfException e)
	{
		System.out.println(e.getMessage());
	}
    }
    
    public ArrayList<HashMap<String, String>> listAllProjects()			
{
	String sqlFraga = "select * from SPELPROJEKT";
	
	try
	{
		ArrayList<HashMap<String, String>> allProjects = idb.fetchRows(sqlFraga);
		return allProjects;
	}
	catch (InfException e)
	{
		System.out.println(e.getMessage());
		return null;
	}
}


    
    public HashMap<String, String> listHired(int aid)
    {
        String sqlFraga = "select * from ANSTALLD where aid = " + aid + ";";
        try
        {
            HashMap<String, String> aboutHired= idb.fetchRow(sqlFraga);
            return aboutHired; 
            
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
*Generates a new AID to be used when adding a new hired
     * @return 
*/
    public int generateAID ()
    {
    try 
        {
            int newAID = Integer.parseInt(idb.getAutoIncrement("ANSTALLD", "AID"));
            return newAID;
        }
    catch (InfException e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }
    
    /**
* Adds a new hired in the database
     * @param newAID
     * @param name
     * @param phone
     * @param mail
     * @param username
*/
    public void addHired(int newAID, String name, String phone, String mail, String username)
    {
	String sqlFraga = "insert into ANSTALLD " + 
			  "(AID, NAMN, TELEFON, MAIL, ANVNAMN) " + 
			  "values (" + newAID + ", '" + name + "', '" + phone + "', '" + mail + "', '" + username + "')";
					  
	try 
	{
		idb.insert(sqlFraga);
	}
	catch(InfException e)
	{
		System.out.println(e.getMessage());
	}
    }

    /**
    * Lists all leaders from the database
     * @return 
    */
    public ArrayList<HashMap<String, String>> listAllLeaders()			//DbClass
    {
	String sqlFraga = "select * from ANSTALLD " +
			  "JOIN PROJEKTLEDARE on ANSTALLD.AID = PROJEKTLEDARE.AID";
	
	try
	{
		ArrayList<HashMap<String, String>> allLeaders = idb.fetchRows(sqlFraga);
		return allLeaders;
	}
	catch (InfException e)
	{
		System.out.println(e.getMessage());
		return null;
	}
    }
    
    /**
    * updates the hired to the database
     * @param aid
     * @param game
    */
    public void updateHired(int aid, int gameId)			//DbClass
    {
	String sqlFraga = "update SPELPROJEKT " + 
                          "set aid = " + aid +  
                          " where SID = " + gameId + ";";
	try 
	{
		idb.update(sqlFraga);
	}
	catch(InfException e)
	{
		System.out.println(e.getMessage());
	}
    }
    
    /**
    *Lists a leader from the database
     * @param aid
     * @return 
    */
    public HashMap<String, String> listALeader(int aid)			//DbClass
    {
	String sqlFraga = "select * from ANSTALLD " +
					  "JOIN PROJEKTLEDARE on PROJEKTLEDARE.AID = ANSTALLD.AID " + 
					  "where anstalld.aid =" + aid + ";";
	
	try 
	{
		HashMap<String, String> aHired = idb.fetchRow(sqlFraga);
		return aHired;
	}
	catch (InfException e)
	{
		System.out.println(e.getMessage());
		return null;
	}
    }
    
    
                    
}
    
