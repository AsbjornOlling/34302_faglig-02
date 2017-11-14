/*
 * Parses the text gotten from ClientConnection.java,
 * fetches a file from the content directory,
 * and returns (some kind of) outputtable object to ClientConnection.
 * 
 */

import java.io.*;

public class RequestHandler {
		
  	//Fields
  	int status;
  	String ext;

  	public static void Handler (String FirstLine) {
  		
  		//Variables
  		String uri;
  		
  		// Gets the URI for the file
  		String[] split = FirstLine.split("\\s+");
  		uri = split[1];
  		
  		// Gets the file extension for the file 
  		String[] dotArray = uri.split("[.]+");
  		ext = dotArray[dotArray.length - 1];
  		System.out.println(ext);
  		try {
  			BufferedReader fil = new BufferedReader ( new FileReader ("content" + uri) );
  			status = 200;
  		} catch (FileNotFoundException e){
  		    System.out.println(e);
  		    status = 404;
  		}
  	}
  	
}