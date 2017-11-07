/*
 * Asbjørns failsome attempt at AI
 */

public class AI {
	private static final int MAX_DEPTH = 3;
	public static void main(String[] args) {
	}

	public static int evaluateBoard(char[] passedBoard, char player, int depth) {
		int returnValue = 0;
		char otherPlayer = null;

		if ( player == 'X' ) {
			otherPlayer = 'O';
		} else if ( player == 'O' ) {
			otherPlayer = 'X';
		}

		for (int i = 0; i < passedBoard.length; i++) {
			if (passedBoard[i] == '.') {

				// make new modified board
				char[] newBoard = passedBoard;
				newBoard[i] = player;

				// check for wins
				if ( checkForWin(newBoard,player) ) {
					returnValue += 10;
				} else if ( checkForWin(newBoard,otherPlayer) ) {
					returnValue -= 10;
				} else if ( depth >= MAX_DEPTH ){
					depth++;
					returnValue += evaluateBoard(newBoard,otherPlayer,depth);
				}
			}
		} // loop

		return returnValue;
	}

	public static boolean checkForWin(char[] board, char player){

		if (
		// horizontal wins
		(board[0] == player && board[1] == player && board[2] == player) ||
		(board[3] == player && board[4] == player && board[5] == player) ||
		(board[6] == player && board[7] == player && board[8] == player) ||
		// vertical wins
		(board[0] == player && board[3] == player && board[6] == player) ||
		(board[1] == player && board[4] == player && board[7] == player) ||
		(board[2] == player && board[5] == player && board[8] == player) ||
		// diagonal wins
		(board[0] == player && board[4] == player && board[8] == player) ||
		(board[2] == player && board[4] == player && board[6] == player) 
	 	) {
			return true;
		} else  {
			return false;
		}
	} // checkForWin

} // class
