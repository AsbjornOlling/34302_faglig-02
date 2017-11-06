/* Handles connection to server;
 *
 * Contains methods that will send
 * specific tic-tac-toe moves.
 *
 * Parses output from server, passes back to main.
 *
 * Blame David for any and all faults.
 */

import java.io.BufferedReader;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerConnection {

	// Flags
	private final String HOST; // The host adress
	private final int PORT; // The host port
	public String startingPlayer; // Who starts
	public String boardState; // What does the board look like now
	public String gameState; // The line about win/lose or keep going
	public boolean serverIsActive;

	// The constructor handling the server connection
	public ServerConnection(String host, int port) {

		// Initializing our flags
		this.HOST = host;
		this.PORT = port;

		// Run the method to access the server
		accessServer();
	}

	public void accessServer() {

		// Variable for link
		Socket link = null;

		// Error handling for link
		try {
			link = new Socket(HOST, PORT);

			serverIsActive = true;
			BufferedReader response = new BufferedReader(new InputStreamReader(link.getInputStream()));

			// First round
			startingPlayer = response.readLine();
			boardState = response.readLine();
			gameState = response.readLine();
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
		}

	} // accessServer

} // class
