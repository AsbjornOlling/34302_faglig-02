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
	static ServerSocket serverSocket;
	static Socket clientSocket;

	public static void main(String[] args){
		getNextRequest();
	} // main

	public static String getNextRequest() {

		BufferedReader input = null;
		String request = null;

		try { // TODO: make this block *wayyy* narrower
			// listen on port 80
			serverSocket = new ServerSocket(8080);
			// connect to client - hangs here
			clientSocket = serverSocket.accept();

			// read data:
			input = new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ) );

			// print data - for debug
			request = input.readLine();

			// close the sockets
			clientSocket.close();
			serverSocket.close();
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
		}

		return request;
	}

} // class
