package gamemodel;
/**
 * @author Zacharie
 * The general game interface that AI works with, all the methods here are what each game needs to implement
 */
import java.util.ArrayList;

import othello.GameBoard;

public abstract class GameConsoleInterface {

	/**
	 * @author Zacharie
	 * @param player an integer that defines the player that needs to see move
	 * usually 0 for the human 1 for the AI
	 * @return  A list of moves that are available for the player to make, the 
	 * list holds string in the following format "row,col,dir"(dir is used for Othello)
	 * 
	 */
	public abstract ArrayList<String> getAvailableSolutions(int player);
	/**
	 * @author Zacharie
	 * @returns and integer array in which the first element is a score that defines the state
	 * of the board. The score is used by the AI to determine what wether a certain desicions 
	 * is a good move
	 */
	public abstract int[] evaluate();
	/**
	 *@author Zacharie 
	 * @param row a value that is used to know where the token is placed
	 * @param col a value that is used to know where the token is placed
	 * @param player the player that is making the move, 
	 * usually even means it is a human uneven means it is the AI
	 * @return will return false if the row or col are out bounds or if the row or col tried
	 * and illegal move
	 * will be true otherwise
	 */
	public abstract boolean moveSet(int row, int col,int player);
	/**
	 *@author Zacharie 
	 *	removes a token and resets the board to how it was before the move was set
	 *	
	 * @param row a value that is used to know where the token is removed
	 * @param col a value that is used to know where the token is removed
	 * @param player the player that is removing the move, 
	 * usually: even means it is a human uneven means it is the AI
	 * @return will return false if the row or col are out bounds or if the row or col tried
	 * to remove a non-existing move
	 * will be true otherwise
	 */
	public abstract boolean undoMove(int row,int col,int player);
	
	/**
	 * @author Zacharie
	 * @return a true if the game is over, false if the game is not over
	 */
	public abstract boolean isGameOver();
}
