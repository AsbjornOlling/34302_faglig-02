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

public class TicTacToe {
	// hardcoded server values
	private final String hostname = "itkomsrv.fotonik.dtu.dk";
	private final int port = 1102;

	public static void main(String[] args) {
		// call davids constructor
		// errors because it doesn't exist yet
		ServerConnection LarsServer = new ServerConnection(hostname,port);

		while (LarsServer.active){
			if ( LarsServer.startingPlayer == "SERVER" ) {
				boardState = LarsServer.getBoardState();
			}
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

		}



	} // main

} // class
