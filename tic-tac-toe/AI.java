/*
 * Asbjørns failsome attempt at AI
 */

public class AI {
	private static final int MAX_DEPTH = 3;
	private static final char PLAYER = 'X';
	private static final char OPPONENT = 'O';

	public static void main(String[] args) {
		char[] aBoard = {'.','.','o',
										 '.','.','o',
										 'o','o','.'};
		System.out.println(makeMove(aBoard)+"");
	}

	public static int makeMove(char[] board) {
		int bestMoveScore = -1000; // easy to beat
		int bestMove = 999; // obviousy wrong
		for (int i = 0; i < board.length; i++) {
			if (board[i] == '.') {
				// make modified board
				char[] newBoard = board;
				newBoard[i] = PLAYER;

				// check for win and run recursive method
				int thisScore = 0;
				if (checkForWin(board,PLAYER)) {
					thisScore += 10;
				}
				if ( thisScore + evaluateBoard(newBoard,OPPONENT,0) > bestMoveScore){
					bestMove = i;
				}
			}		
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
			return true;
		} else  {
			return false;
		}
	} // checkForWin

} // class
