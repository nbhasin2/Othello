package Othello;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
private gameBoard board;
private gameStatus currentState;
private playableItem currentPlayer;
	
private static Scanner playerMove= new Scanner(System.in); 
	

public Game(){
	
	board = new gameBoard();
	gameSetup();
	board.printBoard();
	do{ 
		playerMove(currentPlayer);
		board.printBoard();
		
		currentPlayer = (currentPlayer == playableItem.BLACK) ? playableItem.WHITE : playableItem.BLACK;
	} while(currentState == gameStatus.PLAYING);
	

}


private void gameSetup() {
	board.boardSetup();
	currentPlayer = playableItem.BLACK;//Black goes first
	currentState = gameStatus.PLAYING;
}

public void playerMove(playableItem move){
	boolean isValidInput = false;
	
	do {
		if(move == playableItem.BLACK) {
			System.out.print("Enter your move player Black"); }
			else {
				System.out.print("Enter your move player White");
			}
			
			int row = playerMove.nextInt() - 1;
	        int col = playerMove.nextInt() - 1;
	      
	        if(validMove(row,col,move)){
	        	board.playField[row][col].gamePiece = move;
	        	board.currentRow = row;
	        	board.currentCol = col;
	        	isValidInput = true; }
	         else {
	        	System.out.println("Invalid move");
	        }
	
		
		
		
		
		
	} while (!isValidInput);
}

public boolean validMove(int row, int col, playableItem playerPiece){
	
	//ArrayList<int[]> solutions = availableSoltuions(playerPiece);
	ArrayList<String> tempSolution = availableSoltuions(playerPiece);
	int[] move = {row,col};
    
	String tempMove = row +"-"+col;
	System.out.println(tempSolution);

    return(tempSolution.contains(tempMove));


}


public ArrayList<String> availableSoltuions(playableItem playerPiece) {
	
	
	//ArrayList<String> freeSpaces = new ArrayList<String>();
//	System.out.println("freespaces 1" +freeSpaces);
	ArrayList<int[]> freeSpaces = new ArrayList<int[]>();
	ArrayList<String> temp = new ArrayList<String>();
	int row = gameBoard.ROWS;
	int col = gameBoard.COLS;
	int validRow = -1;
	int validCol= -1;
	boolean valid =false;
	
	for (row = 0; row < gameBoard.ROWS; ++row) {
		for (col = 0; col < gameBoard.COLS; ++col) {
			if(board.playField[row][col].gamePiece.equals(playerPiece))
			{
				/**
				 *    0|1|2 //if the row is == 0 it should not check spots 0,1,2
				 *    7|s|3 //if the col is == 0 it should not check spots 0,6,7
				 *    6|5|4 //if the row is == max it should not check spots 4,5,6
				 *    		//if the col is == max it should not check spots 2,3,4			
				 *    where s is the currently selected space on the board
				 */
				for(int c = 0; c < 8; c++)
				{
					
					if(c == 0 && !(row == 0 || col ==0))
					{
						valid = isValid(row-1,col-1,c,playerPiece);
						validRow = row-2;
						validCol = col-2;
					}
					else if(c == 1 && !(row == 0))
					{
						valid = isValid(row-1,col,c,playerPiece);
						validRow = row-2;
						validCol = col;
					}
					else if(c == 2 && !(row == 0 || col == gameBoard.COLS))
					{
						valid = isValid(row-1,col+1,c,playerPiece);
						validRow = row-2;
						validCol = col+2;
					}
					else if(c == 3 && !(col == gameBoard.COLS))
					{
						valid = isValid(row,col+1,c,playerPiece);
						validRow = row;
						validCol = col+2;
					}
					else if(c == 4 && !(row == gameBoard.ROWS || col == gameBoard.COLS))
					{
						valid = isValid(row+1,col+1,c,playerPiece);
						validRow = row+2;
						validCol = col+2;
					}
					else if(c == 5 && !(row == gameBoard.ROWS))
					{
						valid = isValid(row+1,col,c,playerPiece);
						validRow = row+2;
						validCol = col;
					}
					else if(c == 6 && !(row == gameBoard.ROWS || col == 0))
					{
						valid = isValid(row+1,col-1,c,playerPiece);
						validRow = row+2;
						validCol = col-2;
					}
					else if(c == 7 && !(col == 0))
					{
						valid = isValid(row,col-1,c,playerPiece);
						validRow = row;
						validCol = col-2;
					}
					if(valid)
					{
						int[] newAdd = {validRow,validCol};
						System.out.println("Valid Row - "+ validRow + " Valid col - " + validCol);
						freeSpaces.add(newAdd);
						temp.add(validRow +"-" + validCol);
					}
					
					
				}
			}
		}
	}
	
	return temp;
}
public boolean isValid(int row,int col,int c,playableItem playerPiece)
{
	if(board.playField[row][col].gamePiece.equals(playableItem.EMPTY))
	{
		return false;
	}
	else if(board.playField[row][col].gamePiece.equals(playerPiece))
	{
		
		return false;
	}
	else
	{
		if(c == 0 && !(row == 0 || col ==0))
		{
			if(!(board.playField[row-1][col-1].gamePiece.equals(playableItem.EMPTY)))
			{
				return isValid(row-1,col-1,c,playerPiece);
			}
			else
			{
				return true;
			}
		}
		else if(c == 1 && !(row == 0))
		{
			if(!(board.playField[row-1][col].gamePiece.equals(playableItem.EMPTY)))
			{
				return isValid(row-1,col,c,playerPiece);
			}
			else
			{
				return true;
			}
		}
		else if(c == 2 && !(row == 0 || col == gameBoard.COLS))
		{
			if(!(board.playField[row-1][col+1].gamePiece.equals(playableItem.EMPTY)))
			{
				return isValid(row-1,col+1,c,playerPiece);
			}
			else
			{
				return true;
			}
		}
		else if(c == 3 && !(col == gameBoard.COLS))
		{
			if(!(board.playField[row][col+1].gamePiece.equals(playableItem.EMPTY)))
			{
				return isValid(row,col+1,c,playerPiece);
			}
			else
			{
				return true;
			}
		}
		else if(c == 4 && !(row == gameBoard.ROWS || col == gameBoard.COLS))
		{
			if(!(board.playField[row+1][col+1].gamePiece.equals(playableItem.EMPTY)))
			{
				return isValid(row+1,col+1,c,playerPiece);
			}
			else
			{
				return true;
			}
		}
		else if(c == 5 && !(row == gameBoard.ROWS))
		{
			if(!(board.playField[row+1][col].gamePiece.equals(playableItem.EMPTY)))
			{
				return isValid(row+1,col,c,playerPiece);
			}
			else
			{
				return true;
			}
		}
		else if(c == 6 && !(row == gameBoard.ROWS || col == 0))
		{
			if(!(board.playField[row+1][col-1].gamePiece.equals(playableItem.EMPTY)))
			{
				return isValid(row+1,col-1,c,playerPiece);
			}
			else
			{
				return true;
			}
		}
		else if(c == 7 && !(col == 0))
		{
			if(!(board.playField[row][col-1].gamePiece.equals(playableItem.EMPTY)))
			{
				return isValid(row,col-1,c,playerPiece);
			}
			else
			{
				return true;
			}
		}
		else
		{
			return false;
		}
	}
}




public static void main(String[] args) {
	new Game();
}
}
