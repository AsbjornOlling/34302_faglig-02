import java.io.BufferedReader;
import java.net.Socket;

/* Handles connection to server;
 *
 * Contains methods that will send
 * specific tic-tac-toe moves.
 *
 * Parses output from server, passes back to main.
 *
 * Blame David for any and all faults.
 */

public class ServerConnection {

	// Flags
	private static InetAddress host; // The host adress
	private static final int PORT; // The host port
	public String startingPlayer; // Who starts
	public String boardState; // What does the board look like now
	public String gameState; // The line about win/lose or keep going
	public boolean serverIsActive;

	// The constructor handling the server connection
	public ServerConnect(String IP, int port) {

		// Initializing our flags
		PORT = port;
		host = IP;

		// Run the method to access the server
		accessServer();
	}

	public static void accessServer() {

		// Variable for link
		Socket link = null;

		// Error handling for link
		try {
			link = new Socket(host, PORT);

			serverIsActive = true;
			BufferedReader response = new BufferedReader(link.getInputStream());

			// First round
			startingPlayer = response.readLine();
			boardState = response.readLine();
			gameState = response.readLine();
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
		}

	}

}
