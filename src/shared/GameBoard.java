package shared;

import gameui.Controller;
import gameui.Gameui;
import shared.*;
import tictactoe.BoardSpaceTic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;



/*
 * This class is mainly deals with initialization of the game board for othellogame.
 */
public class GameBoard  extends Observable{

	protected int ROWS;
	
	protected int COLS;
	
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	protected BoardSpace[][] playField;	
	

	private int currentRow,currentCol;	
	

	/*
	 * This method initializes a playing field for the user.
	 */
	public GameBoard(Object obj,int maxRow,int maxCol){
		
		ROWS = maxRow;
		COLS = maxCol;
		if(obj!=null)
			addObserver((Observer) obj);
		
	}
	
	/*
	 * This method iterates over rows and columns to setup the board with empty tiles in the beginning.
	 */
	public void boardSetup(){
		playField = new BoardSpace[ROWS][COLS];
		
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				playField[i][j] = new BoardSpace(i, j); //4x4 gameboard where every space is a new boardSpace object 
			}
		}
		
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				playField[i][j].clear(); //Initialize every space to empty
			}
		}
	
		playField[(ROWS / 2) - 1][(COLS / 2) - 1].setGamePiece(SharedConstants.PlayableItem.WHITE);
		playField[(ROWS / 2) - 1][COLS / 2].setGamePiece(SharedConstants.PlayableItem.BLACK); // No matter what size board is
		playField[ROWS / 2][(COLS / 2) - 1].setGamePiece(SharedConstants.PlayableItem.BLACK);
		playField[ROWS / 2][COLS / 2].setGamePiece(SharedConstants.PlayableItem.WHITE);
	
		
	}
	
	/*
	 * This method is used to print the board for the user.
	 */
	public void printBoard(){
		ArrayList<String> listOfItems = new ArrayList<>();
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				playField[i][j].putItem();
				listOfItems.add(playField[i][j].getGamePiece()+"");
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
	public void printBoard(BoardSpace[][] boardToPrint){
		if(boardToPrint != null){
		ArrayList<String> listOfItems = new ArrayList<>();
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				if(boardToPrint!=null)
				{
					boardToPrint[i][j].putItem();
					listOfItems.add(boardToPrint[i][j].getGamePiece()+"");
				}
				if(j < COLS ) System.out.print("|");
			}
		System.out.println();
		if(i < ROWS  )
			System.out.println("----------------");
		}
		
		assignDeepCopy(boardToPrint);
		setChanged();
		notifyObservers(listOfItems);	
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
	 *
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
		if(playField != null)
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
	public int getROWS() {
		return ROWS;
	}

	public void setROWS(int rOWS) {
		ROWS = rOWS;
	}

	public int getCOLS() {
		return COLS;
	}

	public void setCOLS(int cOLS) {
		COLS = cOLS;
	}



}
