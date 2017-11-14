/*
 * Parses the text gotten from ClientConnection.java,
 * fetches a file from the content directory,
 * and returns (some kind of) outputtable object to ClientConnection.
 * 
 */

import java.io.*;

public class RequestHandler {

  	public static void Handler (String FirstLine) {
  		
  		// Variables
  		String uri;
  		int status;
  		
  		// Gets the URI for the file
  		String[] split = FirstLine.split("\\s+");
  		uri = split[1];
  		
  		// Gets the file extension for the file 
  		String[] dotArray = uri.split("[.]+");
  		String ext = dotArray[dotArray.length - 1];
  		
  		// Try to read the file and change status code if sucess
  		try {
  			BufferedReader fil = new BufferedReader ( new FileReader ("content" + uri) );
  			status = 200;
  		} catch (FileNotFoundException e){
  		    
  			// Change the status code if fail
  			status = 404;
  		}
  		
  		// Sends the response to the response class
  		Response.Response(status; ext; int Size = 0; byte[] content);
  	}
  	
}