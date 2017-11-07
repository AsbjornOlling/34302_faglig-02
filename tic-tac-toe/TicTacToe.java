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
	private static int playerMove;
	private static char playerSymbol;
	private static ServerConnection LarsServer;
	private static Scanner console = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("SHALL WE PLAY A GAME?");

		// instantiate connection object
		LarsServer = new ServerConnection("itkomsrv.fotonik.dtu.dk",1102);

		// ai or human play?
		String AIresponse = null;
		do {
			System.out.print("\nPlay yourself, or let the AI win over Lars?\n(SELF / AI): ");
			AIresponse = console.nextLine().toUpperCase();
		} while ( !(AIresponse.equals("AI") || AIresponse.equals("SELF")) );


		// insert extra server move, if serverstarts
		playerSymbol = parsePlayerSymbol();
		if ( playerSymbol == 'O' ) {
			System.out.println("Staalhagen has the starting turn. Watch out!");
			parseBoardState();
			tempBoardDrawer();
		} else if ( !(playerSymbol == 'X') ) {
			throw new IllegalArgumentException("parseStartingPlayer() method returned funny value.");
		}


		// loop of player and server moves
		while ( LarsServer.serverIsActive ) {

			// get a move from the player or AI
			if (AIresponse.equals("SELF")) {
				playerMove = console.nextInt();
				System.out.println("It's your turn again, place a "+playerSymbol+".");
				System.out.print("Choose a space to put it, numbers 0 - 9: "); 
			} else if (AIresponse.equals("AI")) {
				playerMove = AI.makeMove(boardState); // TODO make AI read directly from boardState field instead of passing it
				boardState[playerMove - 1] = playerSymbol;
				System.out.println("AI MOVES:");
				tempBoardDrawer();
			}

			// ask player if the submitted move is correct
			// hopefully this should only ever happen for human players
			// but is useful to have outside above statement for debugging AI
			while ( boardState[playerMove - 1] != '.' ) {
				tempBoardDrawer();
				System.out.println(playerMove + " is an invalid move, try again.");
				System.out.println("Choose a space to put it, numbers 0 - 9");
				playerMove = console.nextInt();
			}

			// upload the move, and read the new state
			LarsServer.sendPlayerMove(playerMove);
			parseBoardState();

			//////////////////////
			//IN CASE OF VICTORY//
			//		BREAK LOOP    //
			//////////////////////
			if (! LarsServer.gameState.equals("YOUR TURN")) {
				tempBoardDrawer();
				System.out.println(LarsServer.gameState);
				break;
			} else {
				System.out.println("LARS MOVES:");
				tempBoardDrawer();
			}

			
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
		// System.out.print("\n");
		for (int i = 0; i < boardState.length; i++ ) {
			System.out.print(boardState[i]);
			if ((i+1) % 3 == 0) {
				System.out.print("\n");
			}
		} // loop
	} // Board Drawer

} // class
