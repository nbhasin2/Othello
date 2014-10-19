package Othello;
import java.util.ArrayList;
import java.util.Scanner;
import GameAI.AI;

public class Game {
private gameBoard board;
private gameStatus currentState;
private playableItem currentPlayer;
private AI aiPlayer;

private int  globalCounter; 
private static Scanner playerMove= new Scanner(System.in); 
	

public Game(){

	globalCounter = 0;
	
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
	int row;
    int col;
    String coor;
    ArrayList<String> availableSolutions;
	do {
		if(move == playableItem.BLACK) {
			availableSolutions = availableSoltuions(move);
			System.out.print("Enter your move player Black"); 
			row = playerMove.nextInt() - 1;
			col = playerMove.nextInt() - 1;
		}
		else
		{
			availableSolutions = availableSoltuions(move);
			coor = aiPlayer.othelloRandom(availableSolutions);
			
			row = Integer.parseInt(coor.split("-")[0]);
			col = Integer.parseInt(coor.split("-")[1]);
		}
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

public int validMove(int row, int col, playableItem playerPiece){
	
	ArrayList<String> validCoordinatesAndDirection = availableSoltuions(playerPiece);
	ArrayList<String> validCoordinatesOnly = new ArrayList<String>();
	int resultValidMove = -1;
	System.out.println(validCoordinatesAndDirection);
	for(String s : validCoordinatesAndDirection)
	{
		validCoordinatesOnly.add(s.split("-")[0]+"-"+s.split("-")[1]);
	}
	String playerMove = row +"-"+col;
	resultValidMove = validCoordinatesOnly.contains(playerMove) ? 1 : 0;
	
    return resultValidMove;

}

public void tokenChange(int row,int col,ArrayList<String> changeSolution)
{
	
}



public ArrayList<String> availableSoltuions(playableItem playerPiece) {
	
	
//	System.out.println("freespaces 1" +freeSpaces);
	ArrayList<int[]> freeSpaces = new ArrayList<int[]>();
	ArrayList<String> temp = new ArrayList<String>();
	int maxRow = gameBoard.ROWS -1;
	int maxCol = gameBoard.COLS -1;
	
	
	int validRow = -1;
	int validCol= -1;
	boolean valid =false;

	globalCounter = 0;
	
	for (int row = 0; row < maxRow+1; ++row) {
		for (int col = 0; col < maxCol; ++col) {
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
					
					if(c == 0 && !(row <= 0 || col <=0))
					{
						valid = isValid(row-1,col-1,c,playerPiece);
						validRow = row-globalCounter;
						validCol = col-globalCounter;
					}
					else if(c == 1 && !(row <= 0))
					{
						valid = isValid(row-1,col,c,playerPiece);
						validRow = row-globalCounter;
						validCol = col;
					}
					else if(c == 2 && !(row <= 0 || col >= maxCol))
					{
						valid = isValid(row-1,col+1,c,playerPiece);
						validRow = row-globalCounter;
						validCol = col+globalCounter;
					}
					else if(c == 3 && !(col >= maxCol))
					{
						valid = isValid(row,col+1,c,playerPiece);
						validRow = row;
						validCol = col+globalCounter;
					}
					else if(c == 4 && !(row >= maxRow || col >= maxCol))
					{
						valid = isValid(row+1,col+1,c,playerPiece);
						validRow = row+globalCounter;
						validCol = col+globalCounter;
					}
					else if(c == 5 && !(row >= maxRow))
					{
						valid = isValid(row+1,col,c,playerPiece);
						validRow = row+globalCounter;
						validCol = col;
					}
					else if(c == 6 && !(row >= maxRow || col <= 0))
					{
						valid = isValid(row+1,col-1,c,playerPiece);
						validRow = row+globalCounter;
						validCol = col-globalCounter;
					}
					else if(c == 7 && !(col <= 0))
					{
						valid = isValid(row,col-1,c,playerPiece);
						validRow = row;
						validCol = col-globalCounter;
					}
					if(valid)
					{
						int[] newAdd = {validRow,validCol};
						//System.out.println("Valid Row - "+ validRow + " Valid col - " + validCol);
						freeSpaces.add(newAdd);
						temp.add(validRow +"-"+ validCol + "-" + c);
					}
					
					
				}
			}
		}
	}
	
	return temp;
}
public boolean isValid(int row,int col,int c,playableItem playerPiece)
{
	int maxRow = gameBoard.ROWS -1;
	int maxCol = gameBoard.COLS -1;
	
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
		if(c == 0 && !(row <= 0 || col <=0))
		{
			if(!(board.playField[row-1][col-1].gamePiece.equals(playableItem.EMPTY)))
			{
				globalCounter++;
				return isValid(row-1,col-1,c,playerPiece);
			}
			else
			{
				return true;
			}
		}
		else if(c == 1 && !(row <= 0))
		{
			if(!(board.playField[row-1][col].gamePiece.equals(playableItem.EMPTY)))
			{
				globalCounter++;
				return isValid(row-1,col,c,playerPiece);
			}
			else
			{
				return true;
			}
		}
		else if(c == 2 && !(row <= 0 || col >= maxCol))
		{
			if(!(board.playField[row-1][col+1].gamePiece.equals(playableItem.EMPTY)))
			{
				globalCounter++;
				return isValid(row-1,col+1,c,playerPiece);
			}
			else
			{
				return true;
			}
		}
		else if(c == 3 && !(col >= maxCol))
		{
			if(!(board.playField[row][col+1].gamePiece.equals(playableItem.EMPTY)))
			{
				globalCounter++;
				return isValid(row,col+1,c,playerPiece);
			}
			else
			{
				return true;
			}
		}
		else if(c == 4 && !(row >= maxRow || col >= maxCol))
		{
			if(!(board.playField[row+1][col+1].gamePiece.equals(playableItem.EMPTY)))
			{
				globalCounter++;
				return isValid(row+1,col+1,c,playerPiece);
			}
			else
			{
				return true;
			}
		}
		else if(c == 5 && !(row >= maxRow))
		{
			if(!(board.playField[row+1][col].gamePiece.equals(playableItem.EMPTY)))
			{
				globalCounter++;
				return isValid(row+1,col,c,playerPiece);
			}
			else
			{
				return true;
			}
		}
		else if(c == 6 && !(row >= maxRow || col <= 0))
		{
			if(!(board.playField[row+1][col-1].gamePiece.equals(playableItem.EMPTY)))
			{
				globalCounter++;
				return isValid(row+1,col-1,c,playerPiece);
			}
			else
			{
				return true;
			}
		}
		else if(c == 7 && !(col <= 0))
		{
			if(!(board.playField[row][col-1].gamePiece.equals(playableItem.EMPTY)))
			{
				globalCounter++;
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
