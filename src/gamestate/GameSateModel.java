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
	
	/**
	 * @author nishantbhasin
	 * This method is used to undo the move.
	 */
	public BoardSpace[][] popUndoElement()
	{
		if(undoBoard.size()<=0)
			return null;
		
		BoardSpace[][] undoTempElement = undoBoard.get(undoBoard.size()-1);
		BoardSpace[][] undoTempElementForRedoArrayList = undoBoard.get(undoBoard.size()-1);
		redoBoard.add(undoTempElementForRedoArrayList);
		
		undoBoard.remove(undoBoard.size()-1);
		
		return undoTempElement;
	}
	
	/**
	 * @author nishantbhasin
	 * @return
	 * This method is used to redo the move.
	 */
	public BoardSpace[][] popRedoElement()
	{
		if(redoBoard.size()<=0)
			return null;
		
		BoardSpace[][] redoTempElement = redoBoard.get(redoBoard.size()-1);
		redoBoard.remove(redoBoard.size()-1);
		
		return redoTempElement;
	}
			
	
	/**
	 * @author nishantbhasin
	 * Method clearns the array list for undoBoard
	 */
	public void resetUndoBoard()
	{
		undoBoard.clear();
	}
	
	/**
	 * @author nishantbhasin
	 * Method clears the arrays list for redoboard.
	 */
	public void resetRedoBoard()
	{
		redoBoard.clear();
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
