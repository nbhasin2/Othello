package Othello;

/*
 * This class is mainly deals with initialization of the game board for othellogame.
 */
public class gameBoard {

public static final int ROWS = 4;
public static final int COLS = 4;
	
boardSpace[][] playField;	
int currentRow,currentCol;	

/*
 * This method initializes a playing field for the user.
 */
public gameBoard(){
	playField = new boardSpace[ROWS][COLS];	//New board that is 4x4 

	for(int i = 0; i < ROWS; i++){
		for(int j = 0; j < COLS; j++){
			playField[i][j] = new boardSpace(i, j); //4x4 gameboard where every space is a new boardSpace object 
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

playField[(ROWS / 2) - 1][(COLS / 2) - 1].gamePiece = playableItem.WHITE;
playField[(ROWS / 2) - 1][COLS / 2].gamePiece = playableItem.BLACK; // No matter what size board is
playField[ROWS / 2][(COLS / 2) - 1].gamePiece = playableItem.BLACK;
playField[ROWS / 2][COLS / 2].gamePiece = playableItem.WHITE;
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
		System.out.println("----------------");
	}
}






}
