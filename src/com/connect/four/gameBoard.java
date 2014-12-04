package com.connect.four;

import shared.BoardSpace;
import shared.SharedConstants;
/*
 * This class is mainly deals with initialization of the game board for othellogame.
 */
public class gameBoard {

	public static final int ROWS = 6;
	public static final int COLS = 7;
		
	private BoardSpace[][] playField;	
	private int currentRow,currentCol;	
	
	/*
	 * This method initializes a playing field for the user.
	 */
	public gameBoard(){
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
	}
	
	/*
	 * This method is used to print the board for the user.
	 */
	public void printBoard(){
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				playField[i][j].putItem();
				if(j < COLS ) System.out.print("|");
			}
		System.out.println();
		if(i < ROWS  )
			System.out.println("----------------------------");
		}
	}
	
	/**
	 * @author nishantbhasin
	 * @return
	 * This method is used to create deep copy of the board.
	 */
	public BoardSpace[][] makeDeepCopy()
	{
		BoardSpace[][] bsDeepCopy = new BoardSpace[ROWS][COLS];	//New board that is 4x4 
	
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				bsDeepCopy[i][j] = new BoardSpace(i, j); //4x4 gameboard where every space is a new boardSpace object 
			}
		}
		
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				SharedConstants.PlayableItem gamePiece = playField[i][j].getGamePiece();
				bsDeepCopy[i][j].setGamePiece(gamePiece);
			}
		}
			
		return bsDeepCopy;
	}

	
	/**
	 * @author nishantbhasin
	 * @return
	 * This method is used to create deep copy of the board.
	 */
	public void assignDeepCopy(BoardSpace[][] copy)
	{
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				SharedConstants.PlayableItem gamePiece = copy[i][j].getGamePiece();
				playField[i][j].setGamePiece(gamePiece);
			}
		}		
	
	}
	public BoardSpace[][] getPlayField() {
		return playField;
	}

	public void setPlayField(BoardSpace[][] playField) {
		if(playField == null){
			return;
		}
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
