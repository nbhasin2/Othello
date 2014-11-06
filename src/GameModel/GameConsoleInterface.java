package GameModel;

import java.util.ArrayList;

import Othello.gameBoard;

public abstract class GameConsoleInterface {

	
	
	public abstract ArrayList<String> getAvailableSolutions(int player);
	public abstract  int evaluate();
	public abstract void moveSet(int row, int col,int player);
	public abstract void undoMove(int row,int col,int player);
	
	public abstract boolean isGameOver();
}
