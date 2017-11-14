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
/*
		boolean readAll = false;
		int nRead = 0;
		byte[] buffer = new byte[1024];
		FileInputStream fin = null;
		List<Byte> temp = null;
		byte[] content;
*/
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
			status = 404;
			uri = "/404-1/index.html";
			path = FileSystems.getDefault().getPath("content" + uri);
		}

		// read the file
		try {
			fil = Files.readAllBytes(path);
		} catch (IOException ioEx) {
			System.out.println("ERROR: Error while loading requested file: "+uri);
		}
	
/*
		// Try to read the file and change status code if sucess
		try {
			// BufferedReader file = new BufferedReader ( new FileReader
			// ("content" + uri) );
			fin = new FileInputStream("content" + uri);
			status = 200;
		} catch (FileNotFoundException e) {

			// Change the status code if fail
			status = 404;
		}

		// Gets the bites for the file
		while (readAll == false) {
			nRead = fin.read(buffer); // Læs op til 1024 bytes fra filen
			if (nRead == -1) {
				// Nu er vi nået til end-of-file
				fin.close();
				readAll = true;
				content = temp.toArray(new Byte[temp.size()]);
			} else {

				temp = new ArrayList<Byte>();


			}
		}
*/		
		// Sends the response to the response class
		Response resp = new Response(status, ext, fil);

		return resp;
	} // handler
}
