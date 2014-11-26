package gamestate;

import java.util.ArrayList;
import com.connect.four.gameBoard;

import othello.BoardSpace;


public class GameSateModel {

	private int row = gameBoard.ROWS;
	private int col = gameBoard.COLS;
	private ArrayList<BoardSpace[][]> redoBoard;
	private ArrayList<BoardSpace[][]> undoBoard;
	
	public GameSateModel()
	{
		redoBoard = new ArrayList<BoardSpace[][]>();
		undoBoard = new ArrayList<BoardSpace[][]>();
	}

	/**
	 * @return the redoBoard
	 */
	public ArrayList<BoardSpace[][]> getRedoBoard() {
		return redoBoard;
	}

	/**
	 * @param redoBoard the redoBoard to set
	 */
	public void setRedoBoard(ArrayList<BoardSpace[][]> redoBoard) {
		this.redoBoard = redoBoard;
	}

	/**
	 * @return the undoBoard
	 */
	public ArrayList<BoardSpace[][]> getUndoBoard() {
		return undoBoard;
	}

	/**
	 * @param undoBoard the undoBoard to set
	 */
	public void setUndoBoard(ArrayList<BoardSpace[][]> undoBoard) {
		this.undoBoard = undoBoard;
	}
	
	
	
}
