package gamestate;

import java.util.ArrayList;
import com.connect.four.boardSpace;
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

	public BoardSpace[][] undoBoardMove()
	{
		BoardSpace[][] tempSpace = null;
		if(undoBoard.size()>0)
		{
			tempSpace = undoBoard.get(undoBoard.size()-1);
			redoBoard.add(tempSpace);
			undoBoard.remove(undoBoard.size()-1);
		}
		
		return tempSpace;
	}
	
	
	public BoardSpace[][] redoBoardMove()
	{
		BoardSpace[][] tempSpace = null;
		if(redoBoard.size()>0)
		{
			tempSpace = redoBoard.get(redoBoard.size()-1);
			undoBoard.add(tempSpace);
			redoBoard.remove(redoBoard.size()-1);
		}
		
		return tempSpace;
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
