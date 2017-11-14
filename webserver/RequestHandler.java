/*
 * Parses the text gotten from ClientConnection.java,
 * fetches a file from the content directory,
 * and returns (some kind of) outputtable object to ClientConnection.
 * 
 */

import java.io.*;
import java.util.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.*;


public class RequestHandler {

	public static Response handler(String FirstLine) {

		// Variables
		String uri;
		int status = 0;

		// Gets the URI for the file
		String[] split = FirstLine.split("\\s+");
		uri = split[1];

		// redirect root to index.html
		if ( uri.equals("/") ) {
			uri = "/index.html";
		}

		// Gets the file extension for the file
		String[] dotArray = uri.split("[.]+");
		String ext = dotArray[dotArray.length - 1];
		
		// load the file, 404 if not found
		byte[] fil = null;
		Path path = FileSystems.getDefault().getPath("content" + uri);

		// if the file exists, set 200 and continue as normal
		if ( Files.exists(path) ) {
			status = 200;
		} else { // if it doesn't exist, get a random 404 page

			// chose randomly between 0-5
			Random rand = new Random();
			int pageno = rand.nextInt(6); 

			// set new variables
			status = 404;
			uri = "/404-"+pageno+"/index.html";
			ext = "html";
			path = FileSystems.getDefault().getPath("content" + uri);
		}

		// read the file
		try {
			fil = Files.readAllBytes(path);
		} catch (IOException ioEx) {
			System.out.println("ERROR: Error while loading requested file: "+uri);
		}

		// Sends the response to the response class
		Response resp = new Response(status, ext, fil);

		return resp;
	} // handler
}
