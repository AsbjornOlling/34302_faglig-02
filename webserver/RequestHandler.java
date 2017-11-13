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
  		String uri;
  		
  		//Metod handling
  		String[] split = FirstLine.split("\\s+");
  		uri = split[1];
  		
  		String[] dotArray = uri.split("[.]+");
  		String ext = dotArray[dotArray.length - 1];
  		System.out.println(ext);
  		try {
  			BufferedReader fil = new BufferedReader ( new FileReader ("content" + uri) );
  			System.out.println(uri);
  		} catch (FileNotFoundException e){
  		    System.out.println(e);
  		}
  	}
  	
}