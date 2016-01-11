
package mice;

import java.util.ArrayList;
import java.util.HashMap;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 * The constructor of the database-class. Creates a connection to the SQL-database
 * @author Victoria & Anton
 */

public class DbClass {

    private InfDB idb;

    /**
     * The constructor. Connects to the database.
     */
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
     * Method for getting a list of which hired who works with a certain project.
     * @param searchHired
     * @return     */
    public ArrayList<HashMap<String, String>> listHiredInProject(String searchHired)
    {   
        String sqlFraga = "SELECT * FROM SPELPROJEKT"
                         +  " join ARBETAR_I on SPELPROJEKT.SID = ARBETAR_I.SID"
                         +  " join ANSTALLD on ARBETAR_I.AID = ANSTALLD.AID"
                         +  " where ANSTALLD.NAMN like '" + searchHired + "%'";
        
        try
        {
            ArrayList<HashMap<String, String>> hiredProject = idb.fetchRows(sqlFraga);
            return hiredProject;
        }
        catch(InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Lists the projects that a project leader is the leader of.
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
    
    public ArrayList<HashMap<String, String>> listWorksInProjects(String sid)
    {
        String sqlFraga = "select ANSTALLD.NAMN FROM ANSTALLD " +
                          "JOIN ARBETAR_I on ANSTALLD.AID = ARBETAR_I.AID " +
                          "where ARBETAR_I.SID =  " + sid + "" ;

        try
        {
            ArrayList<HashMap<String, String>> worksInProjects = idb.fetchRows(sqlFraga);
            return worksInProjects;
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
    
    /**
     * Deletes a hired from the database where the selected AID is the 
     * parameter.
     * @param deleteAID 
     */
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
     * Updates all info about the hired person at once. 
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
    
    /**
     * Changes a specific administrators aid and/or password where the aid
     * is the aid of the selected administrator.
     * @param AID
     * @param password 
     */
    public void changeAdmin (int AID, String password)
    {
        String sqlFraga = "update ADMINISTRATOR " +
                          "set aid = " + AID + ", losenord = '" + password + "' " +
                          "where aid = " + AID + ";";
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
     * Updates all info about a game at once. The parameters are all the values
     * that are being updated.
     */
    public void changeGame (String setName, String setStartDate, String setRelease, int sid)
    {
        String sqlFraga = "update SPELPROJEKT " + 
                          "set BETECKNING = '" + setName + "', STARTDATUM = '" + setStartDate + "', RELEASEDATUM = '" + setRelease + "' " +
                          "where SID = " + sid + ";";
        
        try
        {
            idb.update(sqlFraga);
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Lists all hired persons and returns them in the form of an 
     * ArrayList<HashMap<String, String>>.
     * @return 
     */
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
     Lists all the specialists and returns them in the form of an 
     * ArrayList<HashMap<String, String>>.
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
    
    /**
     * Checks if a specialist exists with the help of the specialists aid.
     * @param aid
     * @return 
     */
    public boolean doesSpecialistExist(int aid)
    {
        String sqlFraga = "Select * from SPECIALIST where AID = " + aid;
        
        try 
        {
             HashMap<String, String> specialist = idb.fetchRow(sqlFraga);
             if(specialist != null)
             {
                 return true;
             }
             else
             {
                return false;
             }
        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
            return false;
        }       
    }
    
    /**
     * Adds a project to a specialist with the help of the specialist aid
     * and the projects sid.
     * @param aid
     * @param sid
     * @return 
     */
    public boolean updateSpecialistProject(int aid, int sid)
    {
        String sqlFraga = "insert into ARBETAR_I " +
                          "(AID, SID) " +
                          "values(" + aid + "," + sid + ");";
        
        try
	{
            idb.insert(sqlFraga);
            return true;
	}
	catch (InfException e)
	{
            System.out.println(e.getMessage());
            return false;
	}
    }
    
    /**
     * Removes a project to a specialist with the help of the specialist aid
     * and the projects sid.
     * @param aid
     * @param sid 
     */
     public void deleteSpecialistProject(int aid, int sid)
    {
        String sqlFraga ="delete from ARBETAR_I " 
               + "where AID = (" + aid + ") and SID = (" + sid + ")";
        
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
      * Lists all projects and returns them in the form of an 
      * ArrayList<HashMap<String, String>>.
      * @return 
      */
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


    /**
     * Lists all info about a hire with the help of the hires aid 
     * and returns it in a HashMap<String, String>.
     * @param aid
     * @return 
     */
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
     * Lists all info about a project with the help of the projects sid 
     * and returns it in a HaspMap<String, String>.
     */
    public HashMap<String, String> listProject (int sid)
    {
        String sqlFraga = "select * from SPELPROJEKT where sid = " + sid + ";";
        try 
        {
            HashMap<String, String> aboutProject = idb.fetchRow(sqlFraga);
            return aboutProject;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
    *Generates a new AID to be used when adding a new hired.
    *@return 
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
    * Adds a new hired in the database. The parameters are the values entered
    * in to the database.
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
    * Lists all leaders from the database and returns them in the form of an 
    * ArrayList<HashMap<String, String>>.
    * @return 
    */
    public ArrayList<HashMap<String, String>> listAllLeaders()			
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
    * Updates the hired to the database.                                                                   UPDATES LEADER?
    * @param aid
    * @param gameId
    */
    public void updateHired(int aid, int gameId)			
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
    *Lists a leader from the database.
     * @param aid
     * @return 
    */
    public HashMap<String, String> listALeader(int aid)	
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
    
    /**
     * Adds a hire to specialist.
     * @param aid 
     */
    public void insertToSpecialist (int aid)
    {
        String sqlFraga = "insert into specialist " +
                          "values (" + aid + ");";
        
        try 
        {
            idb.insert(sqlFraga);
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Adds a hire to project leader.
     * @param aid 
     */
    public void insertToProjectleader (int aid)
    {
        String sqlFraga = "insert into PROJEKTLEDARE " +
                          "values (" + aid + ");";
        
        try 
        {
            idb.insert(sqlFraga);
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * List the leader of a project and return it. Parameter is the games SID.
     * @param gameID
     * @return 
     */
    public String listLeaderOnProject (int gameID)
    {
        String sqlFraga = "select namn from ANSTALLD " +
                          "join PROJEKTLEDARE on ANSTALLD.AID = PROJEKTLEDARE.AID " +
                          "join SPELPROJEKT on PROJEKTLEDARE.AID = SPELPROJEKT.AID " +
                          "where sid = " + gameID + ";";
        try
        {
            
            String LeaderInProject = idb.fetchSingle(sqlFraga);
            return LeaderInProject;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        
    }
    
   
    
    /**
     * List all the platforms for a game where the parameter is the name of the
     * game and returns them in the form of an ArrayList<HashMap<String, String>>.
     * @param searchWord
     * @return 
     */
    public ArrayList<HashMap<String, String>> listPlatformsForGames (String searchWord)
    {
        String sqlFraga = "select BENAMNING from PLATTFORM " +
                          "join INNEFATTAR on PLATTFORM.PID = INNEFATTAR.PID " +
                          "join SPELPROJEKT on INNEFATTAR.SID = SPELPROJEKT.SID " +
                          "where SPELPROJEKT.BETECKNING like '" + searchWord + "%';";
        
        try
        {
            ArrayList<HashMap<String, String>> platformsForGames = idb.fetchRows(sqlFraga);
            return platformsForGames;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Gets all the info from platforms and returns them in the form of an 
     * ArrayList<HashMap<String, String>>.
     * @return 
     */
    public ArrayList<HashMap<String, String>> listPlatforms ()
    {
        String sqlFraga = "select * from PLATTFORM";
        
        try 
        {
            ArrayList<HashMap<String, String>> platforms = idb.fetchRows(sqlFraga);
            return platforms;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * List all games that have been developed/are developed for the chosen
     * platform.
     * @param pid
     * @return 
     */
    public ArrayList<HashMap<String, String>> listGamesForPlatform(int pid)
    {
        String sqlFraga = "select beteckning from SPELPROJEKT " +
                          "join INNEFATTAR on SPELPROJEKT.SID = INNEFATTAR.SID " +
                          "join PLATTFORM on INNEFATTAR.PID = PLATTFORM.PID " +
                          "where PLATTFORM.PID = " + pid + ";";
        
        try 
        {
            ArrayList<HashMap<String, String>> gamePlatform = idb.fetchRows(sqlFraga);
            return gamePlatform;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Adds a game to a specific platform. 
     * @param sid
     * @param pid 
     */
    public void insertGamePlatform(int sid, int pid)
    {
        String sqlFraga = "insert into INNEFATTAR " +
                          "values (" + sid + ", " + pid + ")";
        
        try
        {
            idb.insert(sqlFraga);
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Checks if a specific game is connected with a specific platform.
     * @param sid
     * @param pid
     * @return 
     */
    public boolean doesExistInInnefattar (int sid, int pid)
    {
        String sqlFraga = "select * from INNEFATTAR " +
                          "where sid = " + sid + " and pid = " + pid + ";";
        boolean match = false;
        try
        {
            String gamePlatform = idb.fetchSingle(sqlFraga);
            if (gamePlatform != null)
            {
                match = true;
            }
            return match;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return match;
        }
    }
    
    /**Generates a new SID to be used when adding a new project.
     * @return 
    */
    public int generateSID ()
    {
    try 
        {
            int newSID = Integer.parseInt(idb.getAutoIncrement("SPELPROJEKT", "SID"));
            return newSID;
        }
    catch (InfException e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }
    
     public void addProject(int newSID, String beteckning, String startdatum, String releasedatum, int leaderAID)
    {
	String sqlFraga = "insert into  SPELPROJEKT" + 
			  "(SID, BETECKNING, STARTDATUM, RELEASEDATUM, AID) " + 
			  "values (" + newSID + ", '" + beteckning + "', '" + startdatum + "', '" + releasedatum + "'," + leaderAID + ")";
					  
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
 * Lists all the project names and returns them in the form of an 
 * ArrayList<HashMap<String, String>>.
 * @return 
 */
 public ArrayList<HashMap<String, String>> listAllProjectNames()			
{
	String sqlFraga = "select * from SPELPROJEKT";
	
	try
	{
		ArrayList<HashMap<String, String>> allProjectNames = idb.fetchRows(sqlFraga);
		return allProjectNames;
	}
	catch (InfException e)
	{
		System.out.println(e.getMessage());
		return null;
	}
}



/**
 * Lists all the released games and returns them in the form of an 
 * ArrayList<HashMap<String, String>>.
 * @return 
 */
  public ArrayList<HashMap<String, String>> listReleasedGames()
    {
        String sqlFraga = "select sid, beteckning from SPELPROJEKT\n" +
                          "where releasedatum < CURRENT_DATE;";
        
        try
        {
            ArrayList<HashMap<String, String>> ReleasedGames = idb.fetchRows(sqlFraga);
            return ReleasedGames;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    
    }
    /**
     * Lists all games under development and returns them in the form of an 
     * ArrayList<HashMap<String, String>>.
     * @return 
     */
    public ArrayList<HashMap<String, String>> listUnderDevelopment()
    {
        String sqlFraga = "select sid, beteckning from SPELPROJEKT\n" +
                          "where releasedatum > CURRENT_DATE;";
        
        try
        {
            ArrayList<HashMap<String, String>> ReleasedGames = idb.fetchRows(sqlFraga);
            return ReleasedGames;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    
    }
    
    /**
     * Lists all the competences a specific hire has with the hires AID and 
     * returns them in the form of an ArrayList<HashMap<String, String>>.
     * @param aid
     * @return 
     */
    public ArrayList<HashMap<String, String>> getSpecialistCompetence(String aid)
    {
        String sqlFraga = "select * from HAR_KOMPETENS " +
                          "where aid = '" + aid + "';";
        
        try
        {
            ArrayList<HashMap<String, String>> specialistCompetences = idb.fetchRows(sqlFraga);
            return specialistCompetences;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Gets a competence name with the help of the chosen competences kid.
     * @param kid
     * @return 
     */
    public String getCompetenceName(String kid)
    {
        String sqlFraga = "select BENAMNING from KOMPETENSDOMAN " +
                          "where kid = '" + kid + "';";
        
        try
        {
            String getCompetenceName = idb.fetchSingle(sqlFraga);
            return getCompetenceName;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Gets the AID of a specialist from the specialists name.
     * @param name
     * @return 
     */
    public String getAidFromSpecialist(String name)
    {
        String aid;
        
        String sqlFraga = "select specialist.aid from SPECIALIST " +
                          "join anstalld on anstalld.aid = SPECIALIST.AID " +
                          "where NAMN = '" + name + "'";
        
        try
        {
            aid = idb.fetchSingle(sqlFraga);
            return aid;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        
    }
    
    /**
     * Gets the sid of a a project from the name, where the name is the given parameter.
     * @param beteckning
     * @return 
     */
      public String getSidFromBeteckning(String beteckning)
    {
        String sid;
        String sqlFraga = "select sid from spelprojekt " +
                          "where BETECKNING = '" + beteckning + "'";
        
        try
        {
            sid = idb.fetchSingle(sqlFraga);
            return sid;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        
    }
    
     /**
      * Selects the info from administrator.
      * @param AID
      * @return 
      */
    public ArrayList<HashMap<String, String>> listAdmin(int AID)
    {
        String sqlFraga = "select * from ADMINISTRATOR " +
                          "JOIN ANSTALLD on ADMINISTRATOR.AID = ANSTALLD.AID " +
                          "where ANSTALLD.AID = " + AID + ";";
        
        ArrayList<HashMap<String,String>> allAdmin = new ArrayList<HashMap<String, String>>();
        
        try
        {
            allAdmin = idb.fetchRows(sqlFraga);
            return allAdmin;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return allAdmin;
        }
    }
    
    /**
     * Lists all administrators. Returns them in a String.
     * @return 
     */
    public HashMap<String, String> getOneAdminAID(int aid)
    {
        String sqlFraga = "select anstalld.aid, ADMINISTRATOR.LOSENORD from ADMINISTRATOR " +
                           "join anstalld on anstalld.aid = administrator.AID " +
                           "where anstalld.aid = '"+aid+"'";
        
        HashMap<String, String> admin = new HashMap<>();
        
        try
        {
            admin = idb.fetchRow(sqlFraga);
            return admin;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return admin;
        }
    }
    
    /**
     * Lists all the platform names and returns them in the form of an 
     * ArrayList<HashMap<String, String>>.
     * @return 
     */
    public ArrayList<HashMap<String, String>> listPlatformNames ()
    {
        String sqlFraga = "select BENAMNING from PLATTFORM";
        
        try 
        {
            ArrayList<HashMap<String, String>> platformNames = idb.fetchRows(sqlFraga);
            return platformNames;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Lists all competence names and returns them in the form of an 
     * ArrayList<HashMap<String, String>>.
     * @return 
     */
   public ArrayList<HashMap<String, String>> getCompetenceNames()
    {
        String sqlFraga = "select BENAMNING from  KOMPETENSDOMAN";  
    
        try
        {
            ArrayList<HashMap<String, String>> competenceNames = idb.fetchRows(sqlFraga);
            return competenceNames;
        }
        catch(InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
   /**
    * Gets the competence level from a specific specialist.
    * @param aid
    * @param kid
    * @param pid
    * @return 
    */
    public String getCompetenceLevel(String aid, String kid, String pid)
    {
    String sqlFraga = "select KOMPETENSNIVA from HAR_KOMPETENS "
            + "where aid = " + aid + " and kid = " + kid + " and pid = " + pid + ";";
    
    try
        {
           String specialistCompetenceLevel = idb.fetchSingle(sqlFraga);
           return specialistCompetenceLevel;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Adds or subtracts one value of the "kompetensnivå" value.
     * @param aid
     * @param kid
     * @param pid
     * @param newValue 
     */
    public void updateCompetenceLevel(String aid, String kid, String pid, int newValue)
    {
         String sqlFraga = "update HAR_KOMPETENS set kompetensniva = " + newValue + " " +
                            "where aid = " + aid + " and kid = " + kid + " and pid = " + pid + "";
         try
	{
            idb.update(sqlFraga);    
	}
	catch (InfException e)
	{
            System.out.println(e.getMessage());
	}
    }
    
    /**
     * Removes a certain specialists whole competence where the kid is the one
     * chosen in a combo box.
     * @param aid
     * @param kid
     */
    public void removeCompetenceLevel(String aid, String kid)
    {
         String sqlFraga = ";";
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
     * Adds a competence to a certain specialist where the kid and pid is 
     * the one chosen in a combo box.
     * @param aid
     * @param kid
     * @param pid
     */
    public void addCompetenceLevel(String aid, String kid, String pid)
    {

         String sqlFraga = "insert into HAR_KOMPETENS (aid, kid, pid, KOMPETENSNIVA) " +
                            "values( " + aid + ", " + kid + ", " + pid +", '1');";
        try
        {
            idb.insert(sqlFraga);    
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
     /**
     * Removes a competence to a certain specialist where the kid and pid is 
     * the one chosen in a combo box.
     * @param aid
     * @param kid
     * @param pid
     */
    public void removeCompetenceLevel(String aid, String kid, String pid)
    {

         String sqlFraga = "delete from HAR_KOMPETENS " +
                            "where aid = " + aid + " and kid = " + kid + " and pid = " + pid + ";";
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
     * Get a specific competences kid from the competence name.
     * @param name
     * @return 
     */
    public String getKidFromCompetenceName(String name)
    {
      String sqlFraga = "select KID from KOMPETENSDOMAN " +
                        "where BENAMNING = '" + name + "'";
        try
        {
            String competenceKid = idb.fetchSingle(sqlFraga);
            return competenceKid;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * Get a specific platforms pid from the platform name.
     * @param plattform
     * @return 
     */
     public String getPidFromPlattformName(String plattform)
    {
        String sqlFraga = "select PID from PLATTFORM " +
                        "where BENAMNING = '" + plattform + "'";
        try
        {
            String competencePid = idb.fetchSingle(sqlFraga);
            return competencePid;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
     
    /**
     * Get the specific platform name from the platform pid.
     * @param pid
     * @return 
     */ 
    public String getPlatformFromPid(String pid)
    {
        String sqlFraga = "select BENAMNING from PLATTFORM " +
                        "where pid = '" + pid + "'";
        try
        {
            String competenceKid = idb.fetchSingle(sqlFraga);
            return competenceKid;
        }
        catch (InfException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
    


