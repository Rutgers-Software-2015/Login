package Login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import Shared.Communicator.DatabaseCommunicator;

public class LoginAuthenticator extends DatabaseCommunicator{

	/**
	 * This class is used to authenticate login data
	 * 
	 * @author Samuel Baysting
	 * 
	 */
	
	private String username = null;
	private String password = null;
	
	/**
	 * Create new instance of login authentication with a username and password
	 * This constructor is used best with getAuth()
	 * 
	 * @param username
	 * @param password
	 * 
	 */
	
	public LoginAuthenticator(String username, String password)
	{
		super();
		this.username = username;
		this.password = SHA_256_Hash(password);
		System.out.println("USERNAME INPUT: "+this.username);
		System.out.println("PASSWORD INPUT: "+this.password);
		System.out.println("EXIT STATUS FOR OFFLINE FILE WRITE: "+writeOfflineFiles());
		System.out.println("");
	}
	
	/**
	 * Creates a blank instance of login authentication
	 * This constructor is best used with writing offline data files
	 * 
	 */
	
	public LoginAuthenticator()
	{
		super();
		System.out.println("EXIT STATUS FOR OFFLINE FILE WRITE: "+writeOfflineFiles());
		System.out.println("");
	}
	
	/**
	 * Verifies login information given by user
	 * 
	 * @return 0 - valid
	 * @return 1 - invalid
	 * 
	 */
	
	public int getAuth()
	{
		connect("admin","gradMay17");
		
		System.out.println("");
		System.out.println("Running login authentication...");

		int authStatus = -1;
		
		if(getConnectionStatus()==0){
			System.out.println("Connection valid! Running online authentication...");
			authStatus = getDatabaseAuth();
		}
		else{
			System.out.println("No connection to database. Running offline authentication...");
			authStatus = getOfflineAuth();
		}
		
		if(authStatus == 0){
			System.out.println("Authentication successful! Welcome "+username+"!");
		}
		else{
			System.out.println("Authentication failed. Please try again");
		}
		System.out.println("");
		disconnect();
		return authStatus;
		
	}
	
	/**
	 * Used for online login authentication
	 * 
	 * @return 0 - valid
	 * @return 1 - invalid
	 * 
	 */
	
	public int getDatabaseAuth()
	{
		ResultSet rs = tell("SELECT username,password FROM MAINDB.EmployeeList;");
		LinkedList<String> usernames = new LinkedList<String>();
		LinkedList<String> passwords = new LinkedList<String>();
		
		if(rs != null){
			try{
				
				rs.first();
				
				do{
					usernames.add(rs.getString(1));
					passwords.add(rs.getString(2));
					
				}while(rs.next());
				
				for(int i = 0; i < usernames.size(); i++){
					if(usernames.get(i).equals(username) && passwords.get(i).equals(password)){
						return 0;
					}
				}
				
				
				
			}catch(Exception e){
				e.printStackTrace(System.out);
				return 1;
			}
		}
		
		return 1;
	}
	
	/**
	 * Used for offline login authentication
	 * 
	 * @return 0 - valid
	 * @return 1 - invalid
	 * 
	 */
	
	public int getOfflineAuth()
	{
		LinkedList<String> usernames = new LinkedList<String>();
		LinkedList<String> passwords = new LinkedList<String>();
		
		try {
			File user = new File(System.getProperty("user.dir")+"/src/Login/OfflineUsernames.txt");
			File pass = new File(System.getProperty("user.dir")+"/src/Login/OfflinePasswords.txt");
			FileReader userReader = new FileReader(user);
			FileReader passReader = new FileReader(pass);
			BufferedReader userBuffered = new BufferedReader(userReader);
			BufferedReader passBuffered = new BufferedReader(passReader);

			String line = null;
			while ((line = userBuffered.readLine()) != null) {
				usernames.add(line);
			}
			line = null;
			while ((line = passBuffered.readLine()) != null) {
				passwords.add(line);
			}
			
			userReader.close();
			passReader.close();
			
			for(int i = 0; i < usernames.size(); i++){
				if(usernames.get(i).equals(username) && passwords.get(i).equals(password)){
					return 0;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace(System.out);
			return 1;
		}
		
		return 1;
	}
	
	/**
	 * Returns the actor class associated with a given username
	 * 
	 * @return actor if username is valid
	 * @return null if username is invalid
	 * 
	 */
	
	public String getActorClass()
	{
		connect("admin","gradMay17");
		
		try{
			if(getConnectionStatus()==0){
				System.out.println("Connection valid! Retrieving actor class for "+username+" from database...");
				ResultSet rs = tell("SELECT position FROM MAINDB.EmployeeList WHERE username=\""+username+"\";");
				if(rs == null){
					disconnect();
					return null;
				}
			
				rs.first();
			
				String actor = rs.getString(1);
				System.out.println("ACTOR RETRIEVED: "+actor);
				disconnect();
				return actor;
			}
			else{
				System.out.println("Connection failed! Retrieving actor class for "+username+" from offline file...");
				LinkedList<String> usernames = new LinkedList<String>();
				LinkedList<String> actorClasses = new LinkedList<String>();
				
				File user = new File(System.getProperty("user.dir")+"/src/Login/OfflineUsernames.txt");
				File actors = new File(System.getProperty("user.dir")+"/src/Login/OfflinePositions.txt");
				FileReader userReader = new FileReader(user);
				FileReader actorsReader = new FileReader(actors);
				BufferedReader userBuffered = new BufferedReader(userReader);
				BufferedReader actorsBuffered = new BufferedReader(actorsReader);

				String line = null;
				while ((line = userBuffered.readLine()) != null) {
					usernames.add(line);
				}
				line = null;
				while ((line = actorsBuffered.readLine()) != null) {
					actorClasses.add(line);
				}
					
				userReader.close();
				actorsReader.close();
				userBuffered.close();
				actorsBuffered.close();
				
				for(int i = 0;i < usernames.size();i++){
					if(usernames.get(i).equals(username)){
						System.out.println("ACTOR RETRIEVED: "+actorClasses.get(i));
						return actorClasses.get(i);
					}
				}
				
				disconnect();
				return null;
			}
			
		}catch (SQLException ex){
		    //Error handler
			disconnect();
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return null;
		}
		catch(Exception e){
			disconnect();
			e.printStackTrace(System.out);
			return null;
		}
	}
	
	/**
	 * Returns the employee ID associated with a given username
	 * 
	 * @return positive integer if username is valid
	 * @return -1 if username is invalid
	 * 
	 */
	
	public int getEmployeeID()
	{
		connect("admin","gradMay17");
		
		try{
			if(getConnectionStatus()==0){
				System.out.println("Connection valid! Retrieving employee ID for "+username+" from database...");
				ResultSet rs = tell("SELECT id FROM MAINDB.EmployeeList WHERE username=\""+username+"\";");
				if(rs == null){
					disconnect();
					return -1;
				}
			
				rs.first();
			
				int ID = Integer.parseInt(rs.getString(1));
				System.out.println("ID RETRIEVED: "+ID);
				disconnect();
				return ID;
			}
			else{
				System.out.println("Connection failed! Retrieving employee ID for "+username+" from offline file...");
				LinkedList<String> usernames = new LinkedList<String>();
				LinkedList<String> actorClasses = new LinkedList<String>();
				
				File user = new File(System.getProperty("user.dir")+"/src/Login/OfflineUsernames.txt");
				File actors = new File(System.getProperty("user.dir")+"/src/Login/OfflineIDs.txt");
				FileReader userReader = new FileReader(user);
				FileReader actorsReader = new FileReader(actors);
				BufferedReader userBuffered = new BufferedReader(userReader);
				BufferedReader actorsBuffered = new BufferedReader(actorsReader);

				String line = null;
				while ((line = userBuffered.readLine()) != null) {
					usernames.add(line);
				}
				line = null;
				while ((line = actorsBuffered.readLine()) != null) {
					actorClasses.add(line);
				}
					
				userReader.close();
				actorsReader.close();
				userBuffered.close();
				actorsBuffered.close();
				
				for(int i = 0;i < usernames.size();i++){
					if(usernames.get(i).equals(username)){
						System.out.println("ID RETRIEVED: "+actorClasses.get(i));
						return Integer.parseInt(actorClasses.get(i));
					}
				}
				disconnect();
				return -1;
			}
			
		}catch (SQLException ex){
		    //Error handler
			disconnect();
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return -1;
		}
		catch(Exception e){
			disconnect();
			e.printStackTrace(System.out);
			return -1;
		}
	}
	
	/**
	 * Used to construct offline username and password files to use in the case
	 * of no database connection
	 * 
	 * @return 0 - successful file write
	 * @return 1 - unsuccessful file write
	 * 
	 */
	
	public int writeOfflineFiles()
	{
		connect("admin","gradMay17");
		int exitcode = 1;
		
		if(getConnectionStatus()==0){
			ResultSet rs = tell("SELECT username,password,id,position FROM MAINDB.EmployeeList;");
			LinkedList<String> usernames = new LinkedList<String>();
			LinkedList<String> passwords = new LinkedList<String>();
			LinkedList<String> employeeIDs = new LinkedList<String>();
			LinkedList<String> actorClasses = new LinkedList<String>();
			
			if(rs != null){
				try{
					
					rs.first();
					
					do{
						usernames.add(rs.getString(1));
						passwords.add(rs.getString(2));
						employeeIDs.add(rs.getString(3));
						actorClasses.add(rs.getString(4));
						
						
					}while(rs.next());
					
					PrintStream user = new PrintStream(System.getProperty("user.dir")+"/src/Login/OfflineUsernames.txt");
					PrintStream pass = new PrintStream(System.getProperty("user.dir")+"/src/Login/OfflinePasswords.txt");
					PrintStream ids = new PrintStream(System.getProperty("user.dir")+"/src/Login/OfflineIDs.txt");
					PrintStream actors = new PrintStream(System.getProperty("user.dir")+"/src/Login/OfflinePositions.txt");
					
					for(int i = 0; i < usernames.size(); i++){
						user.println(usernames.get(i));
						pass.println(passwords.get(i));
						ids.println(employeeIDs.get(i));
						actors.println(actorClasses.get(i));
					}
					
					user.close();
					pass.close();
					ids.close();
					actors.close();
					
					
				}catch (SQLException ex){
				    //Error handler
				    System.out.println("SQLException: " + ex.getMessage());
				    System.out.println("SQLState: " + ex.getSQLState());
				    System.out.println("VendorError: " + ex.getErrorCode());
				    disconnect();
				    return 1;
				}
				catch(Exception e){
					e.printStackTrace(System.out);
					disconnect();
					return 1;
				}
			}
			
			
			exitcode = 0;
		}
		
		disconnect();
		return exitcode;
	}
	
}
