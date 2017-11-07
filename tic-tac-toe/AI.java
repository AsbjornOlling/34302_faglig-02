/*
 * Asbjørns failsome attempt at AI
 */

public class AI {
	private static final int MAX_DEPTH = 1;
	private static final char PLAYER = 'X';
	private static final char OPPONENT = 'O';
	private static final boolean DEBUG = true;

	public static void main(String[] args) {
		char[] aBoard = {'.','.','.',
										 '.','X','.',
										 '.','X','O'};
		System.out.println(makeMove(aBoard)+"");
		//System.out.println(checkForWin(aBoard, OPPONENT));
	}

	public static int makeMove(char[] board) {
		int bestMoveScore = -999; // easy to beat
		int bestMove = 999; // obviousy wrong

		// go through every possible move
		for (int i = 0; i < board.length; i++) {
			if (board[i] == '.') {

				if (DEBUG) System.out.println("Evaluating position "+i+" for Player "+PLAYER);

				// make modified board
				char[] newBoard = board;
				newBoard[i] = PLAYER;

				// evaluate this position
				// if there wasn't an immediate win,
				// start recursive lookahead
				int thisScore = 0;
				if (checkForWin(board,PLAYER)) {
					thisScore += 10;
				} else { // start recursive lookahead
					if (DEBUG) System.out.println("Starting recursive lookahead on positition "+i);
					thisScore = evaluateBoard(newBoard,OPPONENT,0);
					if (DEBUG) System.out.println("Evaluated position "+i+" to a total of "+thisScore+" points.");
				}

				// is this the best move so far?
				if (thisScore > bestMoveScore) {
					bestMove = i;
				}
			}	// fi
		} // loop
		return bestMove;
	} // makeMove

	public static int evaluateBoard(char[] passedBoard, char currentPlayer, int depth) {
		int returnValue = 0;
		char otherPlayer = ' ';

		if ( currentPlayer == 'X' ) {
			otherPlayer = 'O';
		} else if ( currentPlayer == 'O' ) {
			otherPlayer = 'X';
		}

		for (int i = 0; i < passedBoard.length; i++) {
			if (passedBoard[i] == '.') {

				if (DEBUG) System.out.println("Depth = "+depth+" Evaluating position "+i+" for Player "+currentPlayer);

				// make new modified board
				char[] newBoard = passedBoard;
				newBoard[i] = currentPlayer;

				// check for wins
				if ( checkForWin(newBoard,PLAYER) ) {
					returnValue += 10;
				} else if ( checkForWin(newBoard,OPPONENT) ) {
					returnValue -= 10;
				} else if ( depth < MAX_DEPTH ){
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
			if (DEBUG) System.out.println("Found win for "+player);
			return true;
		} else  {
			return false;
		}
	} // checkForWin

} // class
