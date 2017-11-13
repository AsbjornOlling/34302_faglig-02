/*
 * Handles connections to client.
 * Reads an HTTP request, and returns the text to main.
 * Blame Asbjørn for any and all faults.
 */

// have a socket
// wait for connections on the socket
// receive data
// close connection
// return data to main

import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;


public class ClientConnection {
	// socket fields
	ServerSocket serverSocket;
	Socket clientSocket;

	public static void main(String[] args){
		ClientConnection connection = new ClientConnection(8080);
		String request =connection.getNextRequest();
		System.out.println(request);
	} // main

	// constructor
	public ClientConnection(int port) {

		try { // open serverSocket
			serverSocket = new ServerSocket(port);
		} catch (IOException ioEx) {
			System.out.println("ERROR: Could not establish socket on port "+port);
		} 

	} // constructor

	public String getNextRequest() {
		BufferedReader input = null;
		String request = null;

		try { // wait for client to connect
			clientSocket = serverSocket.accept();
		} catch (IOException ioEx) {
			System.out.println("ERROR: Client could not connect to server.");
		}

		try { // read one line from request
			// read a line from the request
			// TODO - allow to read multiple lines
			input = new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ) );
			request = input.readLine();
		} catch (IOException ioEx ) {
			System.out.println("ERROR: Could not read line from InputStream");
		}

		try { // close the client connection
			clientSocket.close();
		} catch (IOException ioEx) {
			System.out.println("ERROR: Could not close connection to client.");
		}

		return request;
	}

} // class
