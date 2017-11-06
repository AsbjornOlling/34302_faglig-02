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
	
	//Flags
	private static InetAddress host; //The host adress
	private static final int PORT; //The host port
	
	//The constructor handling the server connection
	public ServerConnect (String IP, int port) {

		//Defining our flags
		PORT = port;
		host = IP;
		
		//Run the method to access the server
		accessServer();
	}
	
	public static void accessServer() {
		
		//Variable for link
		Socket link = null;
		
		//Error handling for link
		try
		{
			link = new Socket(host,PORT);
		}
		
		
	}

}
