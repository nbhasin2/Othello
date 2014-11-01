package Othello;
import gameui.Gameui;

import java.util.ArrayList;
import java.util.Scanner;

import GameAI.AI;
import Shared.SharedConstants;

public class OthelloConsole {
private gameBoard board;
private gameStatus currentState;
private playableItem currentPlayer;
private AI aiPlayer;
private int countNO;
private int  globalCounter; 
private static Scanner playerMove= new Scanner(System.in); 
private Gameui gameui;
private static int gridSize = 16; // Total number of 	
private boolean gameuiMove = false;
private int gameuiMoveX = -1;
private int gameuiMoveY = -1;

/*
 * Constructor for othello console game
 */
public OthelloConsole(){
	gameui = new Gameui(this);
	gameui.initializeGrid(16);
	globalCounter = 0;
	countNO = 0;
	aiPlayer = new AI(SharedConstants.Othello,SharedConstants.AIRandom);
	board = new gameBoard();
	gameSetup();
	board.printBoard(gameui);
	do{ 
		
		playerMove(currentPlayer);
		board.printBoard(gameui);
		
		currentPlayer = (currentPlayer == playableItem.BLACK) ? playableItem.WHITE : playableItem.BLACK;
	} while(currentState == gameStatus.PLAYING);
	
}

/*
 * Method to setup the initial game with basic item on the board.
 */
private void gameSetup() {
	board.boardSetup();
	currentPlayer = playableItem.BLACK;//Black goes first
	
	currentState = gameStatus.PLAYING;
}

/*
 * This method is used to move the player item
 */
public void playerMove(playableItem move){
	boolean isValidInput = false;
	int row = -1;
    int col = -1;
    int validity;
    
    String coor;
    ArrayList<String> availableSolutions;
	do {
		
		availableSolutions = availableSoltuions(move);
		validity = validMove(-1,-1,availableSolutions);
		if(validity == -1)
		{
			System.out.print("No avaible moves switch to other player\n");
			isValidInput = true;
			countNO++;
			if(countNO ==2) currentState = gameStatus.GAME_END;
		}
		else
		{
			if(move == playableItem.BLACK) {
				
				System.out.print("Enter your move player Black\n"); 
				do{
					if(gameuiMove)
					{
						System.out.println("X - " + gameuiMoveX + " -- " + "Y - " + gameuiMoveY);
						row = getGameuiMoveX();
						col = getGameuiMoveY();
					}
					else
					{
//						System.out.println("Skipping --");
//						row = playerMove.nextInt() -1;
//						col = playerMove.nextInt() -1;
					}
				}while(!gameuiMove);
				gameuiMove = false;
					
			}
			else
			{
				
				coor = aiPlayer.othelloRandom(availableSolutions);
				System.out.println("White ai turn");
				row = Integer.parseInt(coor.split("-")[0]);
				col = Integer.parseInt(coor.split("-")[1]);
			}

			validity = validMove(row,col,availableSolutions);
			
			{ 
				countNO = 0;
		        if(validity == 1){
		        	board.playField[row][col].gamePiece = move;
		        	board.currentRow = row;
		        	board.currentCol = col;
		        	tokenChange(row,col,move,availableSolutions);
		        	isValidInput = true; }
		         else {
		        	System.out.println("Invalid move");
		        }
			}
		}
	} while (!isValidInput);
	
}

/*
 * The method checks who wins the game.
 */
public void winner()
{
	int blackToken = 0;
	int whiteToken = 0;
	for(int row = 0; row < gameBoard.ROWS ;row++)
	{
		for(int col = 0; col < gameBoard.COLS ;col++)
		{
			if(board.playField[row][col].gamePiece.equals(playableItem.BLACK))
				blackToken++;
			else if(board.playField[row][col].gamePiece.equals(playableItem.WHITE))
				whiteToken++;
		}
	}
	if(blackToken == whiteToken )
	{
		System.out.println("DRAW!");
	}
	else if(blackToken > whiteToken)
	{
		System.out.println("Black Wins!");
	}
	else
	{
		System.out.println("White Wins!");
	}

}

/*
 * This method is used to checking whether the player move is valid or not.
 */
public int validMove(int row, int col, ArrayList<String> validCoordinatesAndDirection){
	
	ArrayList<String> validCoordinatesOnly = new ArrayList<String>();
	int resultValidMove = -1;
	if(validCoordinatesAndDirection.size() == 0)
	{
		return resultValidMove;
	}
	//System.out.println(validCoordinatesAndDirection);
	
	for(String s : validCoordinatesAndDirection)
	{
		validCoordinatesOnly.add(s.split("-")[0]+"-"+s.split("-")[1]);
	}
	//System.out.println(validCoordinatesOnly);
	String playerMove = row+"-"+col;
	
	resultValidMove = validCoordinatesOnly.contains(playerMove) ? 1 : 0;
	//System.out.println(playerMove + "--" + validCoordinatesOnly.contains(playerMove)+"--"+resultValidMove);
    return resultValidMove;

}

public void tokenChange(int row,int col,playableItem player,ArrayList<String> changeSolution)
{
	int dir = -1;
	int r;
	int c;
	String sRow = "" + row;
	String sCol = "" + col;
	for(String solution: changeSolution)
	{
		if(sRow.equals(solution.split("-")[0]) && sCol.equals(solution.split("-")[1]))
		{
			dir = Integer.parseInt(solution.split("-")[2]);
		}
	}
	
	if(dir == 0)
	{
		r= 1;
		c= 1;
	}
	else if(dir == 1 )
	{
		r=1;
		c=0;
	}
	else if(dir == 2 )
	{
		r=1; 
		c=-1;
	}
	else if(dir == 3 )
	{
		r=0;
		c=-1;
	}
	else if(dir == 4 )
	{
		r=-1;
		c=-1;
	}
	else if(dir == 5 )
	{
		r=-1;
		c=0;
	}
	else if(dir == 6)
	{
		r=-1;
		c=+1;
	}
	else if(dir == 7)
	{
		r=0;
		c=1;
	}
	else
	{
		return;
	}
	while(!((board.playField[row+r][col+c].gamePiece.equals(player))))
	{
		
		board.playField[row+r][col+c].gamePiece = player;
		row +=r;
		col +=c;
	}

	
}


/*
 * The method checks total number of available moves a player / AI has.
 */
public ArrayList<String> availableSoltuions(playableItem playerPiece) {
	
	
//	System.out.println("freespaces 1" +freeSpaces);
	ArrayList<int[]> freeSpaces = new ArrayList<int[]>();
	ArrayList<String> temp = new ArrayList<String>();
	int maxRow = gameBoard.ROWS -1;
	int maxCol = gameBoard.COLS -1;
	
	
	int validRow = -1;
	int validCol= -1;
	boolean valid =false;

	
	
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
					globalCounter = 2;
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

/*
 * The method checks whether the move is valid along with the direction of the move.
 */
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
				//globalCounter++;

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

				//globalCounter++;

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
				//globalCounter++;
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
				//globalCounter++;
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
				//globalCounter++;
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
				//globalCounter++;
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
				//globalCounter++;
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
				//globalCounter++;

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

public Gameui getGameui() {
	return gameui;
}

public void setGameui(Gameui gameui) {
	this.gameui = gameui;
}

public boolean isGameuiMove() {
	return gameuiMove;
}

public void setGameuiMove(boolean gameuiMove) {
	this.gameuiMove = gameuiMove;
}

public int getGameuiMoveX() {
	return gameuiMoveX;
}

public void setGameuiMoveX(int gameuiMoveX) {
	this.gameuiMoveX = gameuiMoveX;
}

public int getGameuiMoveY() {
	return gameuiMoveY;
}

public void setGameuiMoveY(int gameuiMoveY) {
	this.gameuiMoveY = gameuiMoveY;
}




}
