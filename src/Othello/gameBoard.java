package othello;

import gameui.Controller;
import gameui.Gameui;
import shared.SharedConstants;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/*
 * This class is mainly deals with initialization of the game board for othellogame.
 */
public class GameBoard  extends Observable{

	public static final int ROWS = 8;
	public static final int COLS = 8;
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	private BoardSpace[][] playField;	
	

	private int currentRow,currentCol;	
	

	/*
	 * This method initializes a playing field for the user.
	 */
	public GameBoard(Object obj){
		if(obj!=null)
			addObserver((Observer) obj);
		
		playField = new BoardSpace[ROWS][COLS];	//New board that is 4x4 
	
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				playField[i][j] = new BoardSpace(i, j); //4x4 gameboard where every space is a new boardSpace object 
			}
		}
	}
	
	/*
	 * This method iterates over rows and columns to setup the board with empty tiles in the beginning.
	 */
	public void boardSetup(){
	
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				playField[i][j].clear(); //Initialize every space to empty
			}
		}
	
		playField[(ROWS / 2) - 1][(COLS / 2) - 1].gamePiece = SharedConstants.PlayableItem.WHITE;
		playField[(ROWS / 2) - 1][COLS / 2].gamePiece = SharedConstants.PlayableItem.BLACK; // No matter what size board is
		playField[ROWS / 2][(COLS / 2) - 1].gamePiece = SharedConstants.PlayableItem.BLACK;
		playField[ROWS / 2][COLS / 2].gamePiece = SharedConstants.PlayableItem.WHITE;
	}
	
	/*
	 * This method is used to print the board for the user.
	 */
	public void printBoard(){
		ArrayList<String> listOfItems = new ArrayList<>();
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				playField[i][j].putItem();
				listOfItems.add(playField[i][j].gamePiece+"");
				if(j < COLS ) System.out.print("|");
			}
		System.out.println();
		if(i < ROWS  )
			System.out.println("----------------");
		}
		setChanged();
		notifyObservers(listOfItems);	
	}
	
	
	/*
	 * This method is used to print the board for the user.
	 */
	public void printBoard(BoardSpace[][] bspace){
		ArrayList<String> listOfItems = new ArrayList<>();
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				bspace[i][j].putItem();
				listOfItems.add(bspace[i][j].gamePiece+"");
				if(j < COLS ) System.out.print("|");
			}
		System.out.println();
		if(i < ROWS  )
			System.out.println("----------------");
		}
		setChanged();
		notifyObservers(listOfItems);	
	}
	
	public BoardSpace[][] getPlayField() {
		return playField;
	}

	public void setPlayField(BoardSpace[][] playField) {
		this.playField = playField;
	}
	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public int getCurrentCol() {
		return currentCol;
	}

	public void setCurrentCol(int currentCol) {
		this.currentCol = currentCol;
	}


}
