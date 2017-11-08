/*
 * Asbjørns failsome attempt at AI
 */

import java.util.ArrayList;

public class AI {
	private final int MAX_DEPTH = 4;
	private final char PLAYER;
	private final char OPPONENT;
	private static final boolean DEBUG = false;

	public static void main(String[] args) {
		/* works fine from here (last i checked)
		char[] aBoard = {'X','.','X',
										 'X','O','O',
										 'O','X','O'};
		AI robot = new AI('X');
		System.out.println(robot.makeMove(aBoard));
		//*/
	}


	public AI(char playerSymbol) {
		// set player symbols for the game
		this.PLAYER = playerSymbol;
		if (this.PLAYER == 'X') this.OPPONENT = 'O';
		else if (this.PLAYER == 'O') this.OPPONENT = 'X';
		else {
			this.OPPONENT = ' '; // TODO is this line even necessary?
			throw new IllegalArgumentException("Bad playerSymbol passed to AI constructor:" + playerSymbol);
		}
	} // constructor

	// returns a best move in range [1;9[
	public int makeMove(char[] board) {
		return evaluateBoard(board, PLAYER, 0)[1];
	} // makeMove

	
	// recursive minmax algorithm for finding the best move
	// returns array {bestScore,bestMove}
	private int[] evaluateBoard(char[] passedBoard, char currentPlayer, int depth) {
		// init ALL THE VARS
		int bestScore = 0;
		int bestMove = 0;

		String indent = "";
		if (DEBUG) { // figure out indentation level for debuggin
			for (int j = 0; j < depth; j++ ) indent+="   ";
		}

		// figure out who the other player is
		char otherPlayer = ' ';
		if ( currentPlayer == 'X' ) {
			otherPlayer = 'O';
		} else if ( currentPlayer == 'O' ) {
			otherPlayer = 'X';
		}

		// find the empty spaces on the board
// NOTE - this list is testedly ok
		ArrayList<Integer> validMoves = new ArrayList<Integer>();
		for (int i = 0; i < passedBoard.length; i++) {
			if (passedBoard[i] == '.') {
				validMoves.add(i);
			}
		} // loop

		// return a bogus value if there are no empty spots
		// TODO is this dumb? 
		if (validMoves.size() == 0) {
			if (DEBUG) System.out.println(indent+"Board filled.");
			int[] aTempValue = new int[2];
			aTempValue[1] = 9999;
			return aTempValue; 
		}	

		//for (int i = 0; i < validMoves.size(); i++) {
		// go through valid moves
// NOTE - this loop only tests actual valid moves
		for (int i : validMoves) {

			// print indented debug line
			if (DEBUG) {
				System.out.println(indent+"Depth = "+depth+" Evaluating position "+i+" for Player "+currentPlayer);
			}

			// make new based on the move
			char[] newBoard = passedBoard.clone();
			newBoard[i] = currentPlayer;
			

			// check for wins, generate score for this move
			int thisScore = 0;
			if ( checkForWin(newBoard,PLAYER) ) {
				thisScore += 10;
			} else if ( checkForWin(newBoard,OPPONENT) ) {
				thisScore -= 10;
			} else if ( depth < MAX_DEPTH ){ // look ahead if the game hasn't ended from the move
				thisScore += evaluateBoard(newBoard, otherPlayer, depth + 1)[0];
			}

			// check if this move is best score (relative to whose turn it is)
			if ( currentPlayer == PLAYER && thisScore > bestScore ) {
				bestScore = thisScore;
				bestMove = i;
			} else if ( currentPlayer == OPPONENT && thisScore < bestScore ) {
				bestScore = thisScore;
				bestMove = i;
			}
		} // loop

		// assemble returnvalue
		int [] returnValue = {bestScore, bestMove + 1};
		System.out.println("RETURNING: "+returnValue[0]+' '+returnValue[1]);
// NOTE - this is returning {0,1} a lot of the time
		return returnValue;
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
