/*
 * Parses the text gotten from ClientConnection.java,
 * fetches a file from the content directory,
 * and returns (some kind of) outputtable object to ClientConnection.
 * 
 */

import java.io.*;

public class RequestHandler {
		
  	//Fields
  	String status;

  	public static void Handler (String FirstLine) {
  		
  		//Variables
  		String URI;
  		
  		//Metod handling
  		String[] split = FirstLine.split("\\s+");
  		URI = split[1];
  		try {
  			BufferedReader Fil = new BufferedReader ( new FileReader ("content" + URI) );
  			System.out.println(URI);
  		} catch (FileNotFoundException e){
  		    System.out.println(e);
  		}
  	}
  	
}