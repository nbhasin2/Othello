package GameModel;

import java.util.ArrayList;

public abstract class GameConsoleInterface {

	
	
	public abstract ArrayList<String> getAvailableSolutions();
	public abstract  int evaluate();
	public abstract void moveSet(int row, int col,int player);
	public abstract void undoMove(int row,int col,int player);
	public abstract boolean gameOver();
}
