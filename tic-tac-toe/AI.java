/*
 * Asbjørns failsome attempt at AI
 */

import java.util.ArrayList;

public class AI {
	private static final int MAX_DEPTH = 2;
	private final char PLAYER;
	private final char OPPONENT;
	private static final boolean DEBUG = false;

	public static void main(String[] args) {
		/*
		char[] aBoard = {'X','X','.',
										 '.','O','.',
										 'O','X','O'};


		System.out.println(makeMove()+"");
		//System.out.println(checkForWin(aBoard, OPPONENT));
		//*/
	}

	public AI(char playerSymbol) {
		// set player symbols for the game
		this.PLAYER = playerSymbol;
		if (this.PLAYER == 'X') this.OPPONENT = 'O';
		else if (this.PLAYER == 'O') this.OPPONENT = 'X';
		else {
			this.OPPONENT = ' ';
			throw new IllegalArgumentException("Bad playerSymbol passed to AI constructor:" + playerSymbol);
		}
	} // constructor

	// returns a best move in range [1;9[
	public int makeMove() {
		char[] board = TicTacToe.boardState;
		int bestMoveScore = -999; // easy to beat
		int bestMove = 999; // obviousy wrong
		
		// go through every possible move
		for (int i = 0; i < board.length; i++) {
			if (board[i] == '.') {

				if (DEBUG) System.out.println("Evaluating position "+i+" for Player "+PLAYER);

				// make modified board
				char[] newBoard = board.clone();
				newBoard[i] = PLAYER;

				// evaluate this position
				// if there wasn't an immediate win,
				// start recursive lookahead
				int thisScore = 0;
				if (checkForWin(board,PLAYER)) {
					thisScore += 10;
				} else { // start recursive lookahead
					if (DEBUG) System.out.println("Starting recursive lookahead on positition "+i);
					thisScore = evaluateBoard(newBoard,OPPONENT,1);
					if (DEBUG) System.out.println("Evaluated position "+i+" to a total of "+thisScore+" points.");
				}

				// is this the best move so far?
				if (thisScore > bestMoveScore) {
					bestMove = i;
					bestMoveScore = thisScore;
				}

				if (DEBUG) System.out.print("\n");
			}	// fi
		} // loop
		if (DEBUG) System.out.println("Determinted position "+bestMove+" to be the best move.");
		return bestMove + 1; // +1 because lars' board is not index 0
	} // makeMove


	// TODO - try and put all of makeMove's functionality into one function
	// 		find return highest / lowest return move (depending on player)
	//		
	// ? initally called with the real board
	// ? returns a field and a value?
	private int evaluateBoard(char[] passedBoard, char currentPlayer, int depth) {
		// init ALL THE VARS
		int bestScore = 0;
		int bestMove = 0;

		String indent = " ";
		if (DEBUG) { // figure out indentation level for debuggin
			for (int j = 0; j < depth; j++ ) indent+=" ";
		}

		// figure out who the other player is
		char otherPlayer = ' ';
		if ( currentPlayer == 'X' ) {
			otherPlayer = 'O';
		} else if ( currentPlayer == 'O' ) {
			otherPlayer = 'X';
		}

		// find the empty spaces on the board
		ArrayList<Integer> validMoves = new ArrayList<Integer>();
		for (int i = 0; i < passedBoard.length; i++) {
			if (passedBoard[i] == '.') {
				validMoves.add(i);
			}
		} // loop

		// return 0 if there are no empty spots
		if (validMoves.size() == 0) {
			if (DEBUG) System.out.println(indent+"Board filled.");
			return 0;
		}	

		// TODO - keep going until highest / lowest value found
		for (int i = 0; i < validMoves.size(); i++) {

				// print indented debug line
				if (DEBUG) {
					System.out.println(indent+"Depth = "+depth+" Evaluating position "+i+" for Player "+currentPlayer);
				}

				// make new based on the move
				char[] newBoard = passedBoard.clone();
				newBoard[i] = currentPlayer;

				// check for wins, generate score
				int thisScore = 0;
				if ( checkForWin(newBoard,PLAYER) ) {
					thisScore += 10;
				} else if ( checkForWin(newBoard,OPPONENT) ) {
					thisScore -= 10;
				} else if ( depth < MAX_DEPTH ){
					depth++;
					thisScore += evaluateBoard(newBoard,otherPlayer,depth);
				}

				// check if this is the best score, relative to whose turn it is
				if ( currentPlayer == PLAYER && thisScore > bestScore ) {
					bestScore = thisScore;
				} else if ( currentPlayer == OPPONENT && thisScore < bestScore ) {
					bestScore = thisScore;
				}
		} // loop

		return bestScore;
	} // evaluateBoard

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
