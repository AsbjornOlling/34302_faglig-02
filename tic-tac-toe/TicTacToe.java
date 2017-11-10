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

// TODO: 
// UI flow for human player mode is not perfect
// see especially end game scenarios

// still *occasionally* makes an illegal move
// like ever 100 games or so - why is this?
// bug is most easily provokable in continous mode


import java.util.Scanner;

public class TicTacToe {
	private static char[] blankBoard = {'.','.','.','.','.','.','.','.','.'};
	public static char[] boardState = blankBoard.clone();

	private static int playerMove;
	private static char playerSymbol;

	private static int noOfWins;
	private static int noOfTies;
	private static int noOfLosses;

	// whether AI is chosen
	private static boolean AI_ON;

	// CONINOUS allows the ai to continuously play new games against lars
	// log level is lower if continuous - maybe this should be set-able in UI
	private static final boolean CONTINUOUS = true;

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
		// set field if chosen
		if ( AIresponse.equals("AI") ) {
			AI_ON = true;
		} 

		// intantiate objects, fill out fields
		startNewGame();

		// insert extra server move, if server starts
		playerSymbol = parsePlayerSymbol();
		if ( playerSymbol == 'O' ) {
			System.out.println("Staalhagen has the starting turn. Watch out!");
		} else if ( !(playerSymbol == 'X') ) {
			throw new IllegalArgumentException("parseStartingPlayer() method returned funny value.");
		}

		// print initial board, before start
		parseBoardState();
		tempBoardDrawer();

		// loop of player and server moves
		while ( LarsServer.serverIsActive ) {

			// TODO this block has redundant code - could be leaner
			// get a move from the player or AI
			if (!AI_ON) {
				System.out.println("It's your turn, place a "+playerSymbol+".");
				System.out.print("Choose a space to put it, numbers 0 - 9: "); 
				playerMove = console.nextInt();

				// if the given move was invalid
				while ( boardState[playerMove - 1] != '.' || !(playerMove >= 1 && playerMove <=9) ) {
					System.out.println(playerMove + " is an invalid move, try again.");
					System.out.println("Choose a space to put it, numbers 0 - 9");
					playerMove = console.nextInt();
				}
			} else if (AI_ON) {
				// get and set the move
				playerMove = robot.makeMove(boardState);

				if ( !CONTINUOUS ) {
					// show board after AI move set
					System.out.println("AI MOVES: "+playerMove);
				}
			}

			// put player move into board state and print
			boardState[playerMove - 1] = playerSymbol;
			if (!CONTINUOUS) tempBoardDrawer();

			// upload the move, and read the new state
			LarsServer.sendPlayerMove(playerMove);
			parseBoardState();

			// now we have lars' move
			if (!CONTINUOUS) {
				System.out.println("LARS MOVES: ");
				tempBoardDrawer();
			}


			///////////////////////
			//IN CASE OF GAMEOVER//
			//		BREAK LOOP     //
			///////////////////////
			if ( !LarsServer.gameState.equals("YOUR TURN")) {
				// TODO: odd bug - on player win, gameState line is actually boardState
				// if continuous flag is set, set up new game
				if ( CONTINUOUS ) {
					if ( LarsServer.gameState.equals("SERVER WINS") ) {
						noOfLosses++;
					} else if ( LarsServer.gameState.equals("NOBODY WINS") ) {
						noOfTies++;
					} else { // TODO: this is not very sure -make it more robust
						noOfWins++;
					}
					System.out.println("Wins: "+noOfWins+" Losses: "+noOfLosses+" Ties: "+noOfTies);
					startNewGame();

				} else {
					System.out.println(LarsServer.gameState);
					tempBoardDrawer();
					break; // exit loop
				} 
			} // consider it done.

		} // while loop
		// TODO do something if server loses connection
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
		// this is where ILLEGALMOVE is caught
		if (boardLine.length() != 18) {
			throw new IllegalArgumentException("Read bad boardLine from server: "+boardLine);
		}

		// loop through last 9 chars in the line from server
		for (int i = 0; i < boardLine.length() - 9; i++ ) {
			char boardLineChar = boardLine.charAt(i+9);
			boardState[i] = boardLineChar;
		} // loop
	} // parseBoardState */


	private static void startNewGame() {
		LarsServer = new ServerConnection("itkomsrv.fotonik.dtu.dk",1102); // TODO put these value into fields or vars or SOMETHING
		playerSymbol = parsePlayerSymbol();
		parseBoardState();
		boardState = blankBoard.clone();
		// instantiate AI
		if ( AI_ON ) {
			robot = new AI(playerSymbol);
		}
	} // startNewGame


	// temporary drawing mechanism
	private static void tempBoardDrawer() {
		// System.out.print("\n");
		for (int i = 0; i < boardState.length; i++ ) {
			System.out.print(boardState[i]);
			if ((i+1) % 3 == 0) {
				System.out.print("\n");
			}
		} // loop
	} // BoardDrawer

} // class
