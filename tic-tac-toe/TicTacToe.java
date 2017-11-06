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

public class TicTacToe {
	// private static char[] boardState = new char[9];
	private static char[] boardState = {'.','.','.','.','.','.','.','.','.'};
	private static ServerConnection LarsServer;

	public static void main(String[] args) {

		// instantiate connection object
		LarsServer = new ServerConnection("itkomsrv.fotonik.dtu.dk",1102);

		// insert extra server move, if serverstarts
		// TODO - maybe don't call this function twice
		if ( parseStartingPlayer() == "SERVER" ) {
			System.out.println("Staalhagen has the starting turn. Watch out!");
			boardState = parseBoardState();
			boardState = parseBoardState();
			tempBoardDrawer();
		} else if ( parseStartingPlayer() == "PLAYER" ) {
			// do nothing, just start the while loop
			// where the player is first anyway
		} else {
			throw new IllegalArgumentException("parseStartingPlayer() method returned funnay value.");
		}

		/*
		for (int i = 0; i < boardState.length; i++ ) {
			System.out.println(boardState[i]);
		} //*/

		// loop of server and player moves
		while ( LarsServer.serverIsActive ) {

			// ** PLAYER MOVES
			// move = self.getUserInput() - få et gyldigt move fra bruger
			// BoardDrawer.drawState(boardState); - tegn vores move
			// LarsServer.playerMove(the move) - send et gyldigt move
			// if LarsServer.getWinState() == win || lose || tie  
			//   - hvis gjorde noget, så slut
			// else - ellers, får serveren lov
			//   LarsServer.getBoardState();
			//   BoardDrawer.drawState(boardState);
			//
			// if LarsServer.getWinState() == win || lose || tie  
			//   - hvis gjorde noget, så slut
			// ellers, loop igen.
			
		} // while loop
	} // main


	// TODO maybe make this be about "player symbol" instead
	// parse starting player line from field in connection object
	private static String parseStartingPlayer() {
		String startingPlayerLine = LarsServer.startingPlayer;
		char playerSymbol = startingPlayerLine.charAt(startingPlayerLine.length()-1);

		String startingPlayerString = null;
		if ( playerSymbol == 'O' ) {
			startingPlayerString = "SERVER";
		} else if ( playerSymbol == 'X' ) {
			startingPlayerString = "PLAYER";
		} else {
			startingPlayerString = null;
			throw new IllegalArgumentException("Illegal line fetched from connection object: "+startingPlayerLine);
		} 
		return startingPlayerString;
	} // parseStartingPlayer


	// TODO - maybe make this leaner:
	// * do less checks
	// * write directly to field
	//
	// takes the line given by the server, and turns it into an array
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
