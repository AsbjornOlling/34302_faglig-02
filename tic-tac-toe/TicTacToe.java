/*
 * This is the main class to be compiled and run.
 *
 * Passes data between SeverConnection and BoardDrawer.
 * (maybe also an AI player)
 *
 * Also handles processing of userinput.
 *
 * Blame Asbjørn for any and all faults.
 */

// fra david
// String boardState
// String startingPlayer
// String gameState

// predominant bug:
// clearly blank spaces are often invalid
// when checking move validity


import java.util.Scanner;

public class TicTacToe {
	private static char[] boardState = {'.','.','.','.','.','.','.','.','.'};
	private static char playerSymbol;
	private static ServerConnection LarsServer;
	private static Scanner console = new Scanner(System.in);

	public static void main(String[] args) {

		// instantiate connection object
		LarsServer = new ServerConnection("itkomsrv.fotonik.dtu.dk",1102);

		// insert extra server move, if serverstarts
		playerSymbol = parsePlayerSymbol();
		if ( playerSymbol == 'O' ) {
			System.out.println("Staalhagen has the starting turn. Watch out!");
			parseBoardState();
			tempBoardDrawer();
		} else if ( playerSymbol == 'X' ) {
			// do nothing, just start the while loop,
			// where the player is first anyway.
		} else {
			throw new IllegalArgumentException("parseStartingPlayer() method returned funny value.");
		}

		// loop of player and server moves
		while ( LarsServer.serverIsActive ) {

			// get a valid move from the player
			System.out.println("Now it's your turn, place a "+playerSymbol+".");
			System.out.print("Choose a space to put it, numbers 0 - 9: ");
			int playerMove = console.nextInt();
			while ( boardState[playerMove - 1] != '.' ) {
				tempBoardDrawer();
				System.out.println("That was an invalid move, try again.");
				System.out.println("Choose a space to put it, numbers 0 - 9");
				playerMove = console.nextInt();
			}

			// upload the move, and get new state
			LarsServer.sendPlayerMove(playerMove);
			parseBoardState();

			// check for victory
			if (! LarsServer.gameState.equals("YOUR TURN")) {
				tempBoardDrawer();
				System.out.println(LarsServer.gameState);
				break;
			}
			tempBoardDrawer();
			
		} // while loop
	} // main


	// parse starting player line from field in connection object
	private static char parsePlayerSymbol() {
		char symbol = LarsServer.startingPlayer.charAt( LarsServer.startingPlayer.length() - 1);
		if (! (symbol == 'X' || symbol== 'O') ) {
			throw new IllegalArgumentException("parsePlayerSymbol() method received a funny value.");
		}
		return symbol;
	} // parseStartingPlayer


	// takes the string field in ServerConnection, and puts it into a neat array
	// has already replaced a version that does more error checking
	// because fuck error checking
	private static void parseBoardState() {
		String boardLine = LarsServer.boardState;
		// loop through last 9 chars in the line from server
		for (int i = 0; i < boardLine.length() - 9; i++ ) {
			char boardLineChar = boardLine.charAt(i+9);
			boardState[i] = boardLineChar;
		} // loop
	} // parseBoardState */


	// temporary drawing mechanism
	private static void tempBoardDrawer() {
		for (int i = 0; i < boardState.length; i++ ) {
			System.out.print(boardState[i]);
			if ((i+1) % 3 == 0) {
				System.out.print("\n");
			}
		} // loop
	} // Board Drawer

} // class
