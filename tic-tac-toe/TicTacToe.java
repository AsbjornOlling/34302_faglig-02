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
	private char[] boardState;

	public static void main(String[] args) {

		// instantiate connection object
		ServerConnection LarsServer = new ServerConnection("itkomsrv.fotonik.dtu.dk",1102);

		if ( parseStartingPlayer() == "SERVER" ) {
			System.out.println("Staalhagen has the starting turn. Watch out.");
			this.boardState = parseBoardState();
		}

		while ( LarsServer.active ) {

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
		char playerSymbol = startingPlayerLine[startingPlayerLine.length()-1];
		if ( playerSymbol == 'O' ) {
			String startingPlayerString = "SERVER";
		} else if ( playerSymbol == 'X' ) {
			String startingPlayerString = "PLAYER";
		} else {
			throw new IllegalArgumentException("Line fetched from connection object: "+startingPlayerLine);
		}

		return startingPlayerString;
	} // parseStartingPlayer

	// TODO write this board-state parser
	// reads server output from field in connection object
	private static char[] parseBoardState() {
		String boardLine = LarsServer.boardState;
		return boardArray;
	} //

} // class
