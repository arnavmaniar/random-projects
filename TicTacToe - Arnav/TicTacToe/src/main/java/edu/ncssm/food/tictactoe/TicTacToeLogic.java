package edu.ncssm.food.tictactoe;

import java.util.Arrays;

/**
 * @author arnav m
 * @date 8/20/2022
 *
 * The logic for a generic Tic Tac Toe game
 */

public class TicTacToeLogic {

    /**
     * Represents the board of Tic Tac Toe.
     * The index indicate the space
     * The value indicates who is in the space (-1 = O, 1 = X, 0 = Empty)
     */
    private int[] board;

    // 1 = X, -1 = O
    private int turn;
    private boolean gameOver;

    /**
     * Create a 3x3 board
     */
    public TicTacToeLogic(){
        this(3);
    }

    /**
     * Creates a NxN board
     * @param dimension number of rows and columns; anything < 3 is set to 3
     */
    public TicTacToeLogic(int dimension) {
        if (dimension < 3) {
            dimension = 3; // Ensure minimum dimension is 3
        }
        this.board = new int[dimension * dimension]; // Initialize the board with appropriate size
        // Initialize other variables as needed
    }


    /**
     * Resets the board to the start of the game
     */
    public void reset(){
        Arrays.fill(board, 0);
        turn = 1;
        gameOver = false;
    }

    /**
     * Attempts to make a move
     * @param space space to make the move
     * @return true if the move was made, false if invalid
     */
    public boolean makeMove( int space ){
        return false;
    }

    /**
     * Check for a winner
     * @return "X", "O", or "TIE" if the game is over, empty string otherwise
     */
    public String checkWinner(){
        int dimension = getDimension();

        for (int i = 0; i < dimension; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < dimension; j++) {
                rowSum += board[i * dimension + j];
                colSum += board[j * dimension + i];
            }
            if (Math.abs(rowSum) == dimension) {
                return rowSum > 0 ? "X" : "O";
            }
            if (Math.abs(colSum) == dimension) {
                return colSum > 0 ? "X" : "O";
            }
        }

        int diagSum = 0;
        int diagSum1 = 0;
        for (int i = 0; i < dimension; i++) {
            diagSum += board[i * dimension + i];
            diagSum1 += board[i * dimension + (dimension - i - 1)];
        }
        if (Math.abs(diagSum) == dimension) {
            return diagSum > 0 ? "X" : "O";
        }
        if (Math.abs(diagSum1) == dimension) {
            return diagSum1 > 0 ? "X" : "O";
        }

        boolean isTie = true;
        for (int i : board) {
            if (i == 0) {
                isTie = false;
                break;
            }
        }
        if (isTie) {
            return "TIE";
        }

        return "";
    }

    // Checks to see if the score makes the game over
    private String checkScore( int score ){
        int dimension = getDimension();
        if (Math.abs(score) == dimension) {
            gameOver = true;
            return score > 0 ? "X" : "O";
        }
        return "";
    }

    /**
     * Check the owner of a space
     * @param spot the space to check
     * @return the owner ("X" or "O"), empty string if not occupied or spot is invalid
     */
    public String getOwner(int spot){
        String rtn = "";

        if( spot >= 0 && spot < board.length ) {
            if( board[spot] == 1 ){
                rtn = "X";
            }
            else if( board[spot] == -1 ){
                rtn = "O";
            }
        }
        return rtn;
    }

    /**
     * Whose turn is it?
     * @return "X" or "O" depending on whose turn it currently is
     */
    public String whoseTurn(){
        String rtn = "X";
        if( turn == -1 ){
            rtn = "O";
        }
        return rtn;
    }

    /**
     * Is the game over?
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver(){
        return gameOver;
    }

    /**
     * Get the number of rows and columns currently in use
     * @return the number rows/columns in the board
     */
    public int getDimension(){
        return (int)Math.sqrt(board.length);
    }
}
