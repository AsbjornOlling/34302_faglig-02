/*
 * This is the main class of the program.
 * This file contains the main method, 
 * and handles communication between ClientConnection 
 * and RequestHandler.
 *
 */

import java.io.*;
import java.util.*;
import java.net.*;

public class WebServer {

	public static void main(String[] args) {
	
	ClientConnection connection = new ClientConnection(8080);
	
	// receive requests as long as server is active
	// this is permanently true for now	
	while (connection.serverActive == true) {	
		
		// open socket and wait for a request to arrive
		ArrayList<String> request = connection.getNextRequest();  
	
		// call requesthandler with just first line of the request
		// put response into object
		Response response = RequestHandler.handler(request.get(0));

		// send the response object out to server, and close client socket
		connection.sendResponse(response);
	}
	
	System.out.print("CONNECTION TERMINATED.\r\n");	
	// connection closed
		
	} // main
} // class
