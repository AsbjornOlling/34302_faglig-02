/*
 * Parses the text gotten from ClientConnection.java,
 * fetches a file from the content directory,
 * and returns (some kind of) outputtable object to ClientConnection.
 * 
 */

public class RequestHandler {
		
  	//Fields
  	String status;

  	public static Handler (String URI) {
  		String[] split = URI.split("\\s+");
  		System.out.println(split[1]);
 	}
  	
}