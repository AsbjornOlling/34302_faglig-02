/*
 * This is the main class of the program.
 * This file contains the main method, 
 * and handles communication between ClientConnection 
 * and RequestHandler.
 *
 */

import java.io.*;
import java.net.*;

public class WebServer {

	public static void main(String[] args) {
	
	ClientConnection connection = new ClientConnection(8080);
	
	// receive requests as long as server is active
	
		while (connection.serverActive == true) {	
			
			String returnValue = connection.getNextRequest();  
		
			RequestHandler.Handler(returnValue);  // = throw value in David's face
		
		}
	
	System.out.print("CONNECTION TERMINATED.\r\n");	
	// connection closed
		
	}
}