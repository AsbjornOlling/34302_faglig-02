/*
 * Handles connections to client.
 * Reads an HTTP request, and returns the text to main.
 * Blame Asbjørn for any and all faults.
 */

import java.io.*;
import java.util.ArrayList;
import java.net.Socket;
import java.net.ServerSocket;
import java.nio.file.*;

public class ClientConnection {
	boolean serverActive = true; // what even do with this
	final boolean DEBUG = true;
	// socket fields
	ServerSocket serverSocket;
	Socket clientSocket;

	/*
	// TEMPORARY CODEBLOCK FOR DEBUGGIN
	public static void main(String[] args){
		// make connection, get request
		ClientConnection connection = new ClientConnection(8080);
		ArrayList<String> request = connection.getNextRequest();
		for (String line : request) System.out.println(line);

		// make bogus html file
		byte[] tempfile = null;
		Path path = FileSystems.getDefault().getPath("content", "dtulogo.gif");
		try {
			tempfile = Files.readAllBytes(path);
		} catch (IOException ioEx) {
			System.out.println("ERROR: Could not read bytes from temp file.");
		}

		// make bogus response object
		Response res = new Response(200, "gif", tempfile);
		// send it back
		connection.sendResponse(res);
	} // main */


	// constructor
	public ClientConnection(int port) {
		try { // open serverSocket
			serverSocket = new ServerSocket(port);
		} catch (IOException ioEx) {
			System.out.println("ERROR: Could not establish socket on port "+port);
		} 
	} // constructor


	// wait for the next request
	// and put it into an arrayList
	public ArrayList<String> getNextRequest() {
		BufferedReader input = null;
		ArrayList<String> request = new ArrayList<String>();

		// wait for client to connect
		try { 
			clientSocket = serverSocket.accept();
		} catch (IOException ioEx) {
			System.out.println("ERROR: Client could not connect to server.");
		}

		// read the request
		try {
			String line;
			input = new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ) );
			while ( !(line = input.readLine()).isEmpty() ) {
				request.add(line);
			}
		} catch (IOException ioEx ) {
			System.out.println("ERROR: Could not read line from InputStream");
		}

		return request;
	} //getNextRequest


	// send a bytearray back
	public void sendResponse(Response response) {
		// open output stream
		BufferedOutputStream output = null;
		try { 
			output = new BufferedOutputStream(clientSocket.getOutputStream());
		} catch (IOException ioEx) {
			System.out.println("ERROR: Could not open outputstream to browser.");
		}

		// write some shit to browser
		try { 
			output.write( response.HTTP_STATUS.getBytes() );
			if ( DEBUG ) System.out.print(response.HTTP_STATUS);

			output.write( response.HTTP_DATE.getBytes() );
			if ( DEBUG ) System.out.print(response.HTTP_DATE);

			output.write( response.HTTP_CONTENTTYPE.getBytes() );
			if ( DEBUG ) System.out.print(response.HTTP_CONTENTTYPE);

			output.write( response.HTTP_CONTENTLENGTH.getBytes() );
			if ( DEBUG ) System.out.print(response.HTTP_CONTENTLENGTH);
			
			output.write( "\r\n".getBytes() );
			if ( DEBUG ) System.out.println();

			output.write( response.FILE_CONTENTS );
			// if ( DEBUG ) System.out.print( new String(response.FILE_CONTENTS) );

			output.flush();
		} catch (IOException ioEx) {
			System.out.println("ERROR: Could not write data to outputstream.");
		}

		// close the client connection
		try {
			clientSocket.close();
		} catch (IOException ioEx) {
			System.out.println("ERROR: Could not close connection to client.");
		} //*/

	} // sendResponse

} // class
