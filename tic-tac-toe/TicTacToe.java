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
//

// import java.net.InetAdress;

public class TicTacToe {
	private static char[] boardState = new char[9];
	private static ServerConnection LarsServer;

	public static void main(String[] args) {

		// instantiate connection object
		LarsServer = new ServerConnection("itkomsrv.fotonik.dtu.dk",1102);

		// insert extra server move, if serverstarts
		// TODO - maybe don't call this function twice
		if ( parseStartingPlayer() == "SERVER" ) {
			System.out.println("Staalhagen has the starting turn. Watch out!");
			// boardState = parseBoardState();
		} else if ( parseStartingPlayer() == "PLAYER" ) {
			// do nothing, just start the while loop
			// where the player is first anyway
		} else {
			throw new IllegalArgumentException("parseStartingPlayer() method returned funnay value.");
		}

		// loop of server and player moves
		while ( LarsServer.serverIsActive ) {

			// GAMEPLAN
			// if LarsServer.startingPlayer() == "SERVER"
			// 	boardState = LarsServer.getBoardState();
			// 
		// while (SERVER ACTIVE):
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

	/*
	// TODO write this board-state parser
	// reads server output from field in connection object
	private static char[] parseBoardState() {
		String boardLine = LarsServer.boardState;

		// loop through last 9 chars in the line from server
		for (int i = 9; i < boardLine.length() - 1; i++ ) {
			char boardLineChar = boardLine[i];
			if (boardLineChar != boardState[i] && boardState)
		}
		return boardArray;
	} // parseBoardState */

} // class
