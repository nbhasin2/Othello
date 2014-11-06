package GameModel;
/**
 * @author Zacharie
 * The general game interface that AI works with, all the methods here are what each game needs to implement
 */
import java.util.ArrayList;

import Othello.gameBoard;

public abstract class GameConsoleInterface {

	
	public abstract ArrayList<String> getAvailableSolutions(int player);
	public abstract int[] evaluate();
	public abstract void moveSet(int row, int col,int player);
	public abstract void undoMove(int row,int col,int player);
	
	public abstract boolean isGameOver();
}
