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
	private static InetAddress host;  // The host adress
	private static final int PORT;  // The host port
	public String startingPlayer;  // Who starts 
	public String boardState;  // What does the board look like now
	public String winState;  // The line about win/lose or keep going
	
	// The constructor handling the server connection
	public ServerConnect (String IP, int port) {

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
		try
		{
			link = new Socket(host,PORT);
		}
		
		
	}

}
