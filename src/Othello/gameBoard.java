package Othello;

import gameui.Gameui;

import java.util.ArrayList;

/*
 * This class is mainly deals with initialization of the game board for othellogame.
 */
public class GameBoard {

public static final int ROWS = 8;
public static final int COLS = 8;
	
BoardSpace[][] playField;	
int currentRow,currentCol;	

/*
 * This method initializes a playing field for the user.
 */
public GameBoard(){
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

playField[(ROWS / 2) - 1][(COLS / 2) - 1].gamePiece = PlayableItem.WHITE;
playField[(ROWS / 2) - 1][COLS / 2].gamePiece = PlayableItem.BLACK; // No matter what size board is
playField[ROWS / 2][(COLS / 2) - 1].gamePiece = PlayableItem.BLACK;
playField[ROWS / 2][COLS / 2].gamePiece = PlayableItem.WHITE;
}

/*
 * This method is used to print the board for the user.
 */
public void printBoard(Gameui ui){
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
	
	ui.updateGrid(listOfItems);
}






}
