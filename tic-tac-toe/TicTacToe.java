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

// UI flow for human player mode is not perfect
// see especially end game scenarios
// TODO fix ^

import java.util.Scanner;

public class TicTacToe {
	public static char[] boardState = {'.','.','.','.','.','.','.','.','.'};
	private static int playerMove;
	private static char playerSymbol;

	// hardcoded variable for now
	// allows the ai to continuously play new games against lars
	// log level is lower if continuous
	// TODO: add timer, print lines  with wins/loss ratio
	private static final boolean CONTINUOUS = false;

	// TODO maybe just make these variables instead of fields
	private static ServerConnection LarsServer;
	private static Scanner console = new Scanner(System.in);
	private static AI robot; // for some reason this can't be declared inside a conditional

	public static void main(String[] args) {
		System.out.println("SHALL WE PLAY A GAME?");

		// ai or human play?
		String AIresponse = null;
		do {
			System.out.print("\nPlay yourself, or let the AI win over Lars?\n(SELF / AI): ");
			AIresponse = console.nextLine().toUpperCase();
		} while ( !(AIresponse.equals("AI") || AIresponse.equals("SELF")) );

		// instantiate appropriate object
		LarsServer = new ServerConnection("itkomsrv.fotonik.dtu.dk",1102);

		// insert extra server move, if server starts
		playerSymbol = parsePlayerSymbol();
		if ( playerSymbol == 'O' ) {
			System.out.println("Staalhagen has the starting turn. Watch out!");
		} else if ( !(playerSymbol == 'X') ) {
			throw new IllegalArgumentException("parseStartingPlayer() method returned funny value.");
		}

		// instantiate AI
		if (AIresponse.equals("AI")) {
			robot = new AI(playerSymbol);
		}

		// print initial board, before start
		parseBoardState();
		tempBoardDrawer();

		// loop of player and server moves
		while ( LarsServer.serverIsActive ) {


			// TODO this block has redundant code - could be leaner
			// get a move from the player or AI
			if (AIresponse.equals("SELF")) {
				System.out.println("It's your turn, place a "+playerSymbol+".");
				System.out.print("Choose a space to put it, numbers 0 - 9: "); 
				playerMove = console.nextInt();

				// if the given move was invalid
				while ( boardState[playerMove - 1] != '.' || !(playerMove >= 1 && playerMove <=9) ) {
					System.out.println(playerMove + " is an invalid move, try again.");
					System.out.println("Choose a space to put it, numbers 0 - 9");
					playerMove = console.nextInt();
				}
			} else if (AIresponse.equals("AI")) {
				// get and set the move
				playerMove = robot.makeMove(boardState);

				if ( !CONTINUOUS ) {
					// show board after AI move
					System.out.println("AI MOVES: "+playerMove);
				}
			}

			// put player move into board state and print
			boardState[playerMove - 1] = playerSymbol;
			tempBoardDrawer();

			// upload the move, and read the new state
			LarsServer.sendPlayerMove(playerMove);

			// now we have lars' move
			System.out.println("LARS MOVES:");
			parseBoardState();
			tempBoardDrawer();
			

			///////////////////////
			//IN CASE OF GAMEOVER//
			//		BREAK LOOP     //
			///////////////////////
			if (! LarsServer.gameState.equals("YOUR TURN")) {
				System.out.println(LarsServer.gameState);
				tempBoardDrawer();

				// if continuous flag is set, set up new game
				// TODO: also-reconstruct ai
				if ( CONTINUOUS ) {
					LarsServer = new ServerConnection("itkomsrv.fotonik.dtu.dk",1102); // TODO put these value into fields or vars or SOMETHING
					playerSymbol = parsePlayerSymbol();
					parseBoardState();
				} else {
					break;
				}
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

		// SOME error handlign
		if (boardLine.length() != 18) {
			throw new IllegalArgumentException("Read bad boardLine from server: "+boardLine);
		}

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
