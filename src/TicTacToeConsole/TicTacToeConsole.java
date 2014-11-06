package TicTacToeConsole;
/**
 * @author Nishant Bhasin
 * Base console based tictactoe 
 * game class. 
 */
import java.util.ArrayList;
import java.util.Scanner;

import sun.security.mscapi.PRNG;
import GameAI.AI;
import GameModel.GameConsoleInterface;
import Othello.gameBoard;
import Shared.SharedConstants;

public class TicTacToeConsole extends GameConsoleInterface{

	private int[][] board = new int[SharedConstants.ROWS][SharedConstants.COLS]; // game board in 2D array
	private int currentState;  
	private int currentPlayer; 
	private int AIplayer;
	private int tempCurrentPlayer;
	private int currntRow, currentCol;
	private boolean switchPlayer = false;
	private AI ticTacToeAI;
	public static Scanner in; 
	private String playerType;
	private boolean AImove;

	/*
	 * Constructor for ticTacToe
	 */
	public TicTacToeConsole(String AIType)
	{
		setTicTacToeAIRandom(new AI(AIType));
		in = new Scanner(System.in);

	}

	/*
	 * Method that is called for playing tictactoe
	 */
	public void playTicTacToe()  
	{
		
		System.out.println("Welcome to TicTacToc Console Game !!" +
				"\nTo start playing please enter your cell cordinate between [1-3]" +
				"\nlike 1 1, 2 2. Make sure there is space between the numbers." +
				"Enjoy your game.\n");
		System.out.println("What player would you like to be X or O :");
		Scanner user_input = new Scanner(System.in);
		playerType = user_input.next();
		
		initGame(playerType);
		System.out.println("Game Started ... ");
		printBoard();
		do {
			playerMove(currentPlayer,this.AImove);  
			updateGame(currentPlayer, currntRow, currentCol); 
			printBoard();
			checkGameOver();
			togglePlayer();
		} while (currentState == SharedConstants.PLAYING); // repeat if not game-over
	}

	/*
	 * changes AI vs Human
	 */
	public void togglePlayer()
	{
		if(AImove == false)
		{
			AImove = true;
		}
		else 
		{
			currentPlayer = tempCurrentPlayer;
			AImove = false;
		}
	}

	/*
	 * checks whether the game is over or not
	 */
	public void checkGameOver()
	{
		if (currentState == SharedConstants.PLAYER_WON) {
			System.out.println("You Won !! Hope you enjoyed.");
		} else if (currentState == SharedConstants.AI_WON) {
			System.out.println("Sorry, AI won :(");
		} else if (currentState == SharedConstants.DRAW) {
			System.out.println("Match Draw!!");
		}
	}
	
	/*
	 * player switch for multiplayer
	 */
	public void toggleSwitch()
	{
		if(switchPlayer == false)
			switchPlayer = true;
		else
			switchPlayer = false;
	}


	/*
	 * Instantiates the game board 
	 */
	public void initGame(String crossOrNought) {
		if(crossOrNought.equals("X")||crossOrNought.equals("x"))
		{
			currentPlayer = SharedConstants.CROSS; 
			tempCurrentPlayer = SharedConstants.CROSS;
			AIplayer = SharedConstants.NOUGHT;
		}
		else if(crossOrNought.equals("O")||crossOrNought.equals("o"))
		{
			currentPlayer = SharedConstants.NOUGHT;
			tempCurrentPlayer = SharedConstants.NOUGHT;
			AIplayer = SharedConstants.CROSS;
		}else
		{
			System.out.println("We assigned you X as you can only enter O or X");
			currentPlayer = SharedConstants.CROSS;
			tempCurrentPlayer = SharedConstants.CROSS;
			AIplayer = SharedConstants.NOUGHT;
		}
		currentState = SharedConstants.PLAYING; 
		for (int row = 0; row < SharedConstants.ROWS; ++row) {
			for (int col = 0; col < SharedConstants.COLS; ++col) {
				board[row][col] = SharedConstants.EMPTY;  

			}
		}
	}

	/*
	 * Checks player and AI move
	 */
	public void playerMove(int checkplayerMove, boolean AImove)   {
		boolean validInput = false;  
		int row;  
		int col;
		ArrayList<String> solutions;
		String AICoordinates;
		int validity;
		do {
			solutions = availableSolutions();
			
			if ((checkplayerMove == SharedConstants.CROSS || checkplayerMove == SharedConstants.NOUGHT) && AImove==false) {
				System.out.print("Player " + playerType + " enter your move :");
				row = in.nextInt() - 1;  
				col = in.nextInt() - 1;
			} 
			else {
				System.out.print("AI " + ticTacToeAI.getAIType() + " move \n");
				AICoordinates = ticTacToeAI.makeMove(this);
				row = Integer.parseInt(AICoordinates.split(",")[0]);
				col = Integer.parseInt(AICoordinates.split(",")[1]);	
			}
			validity = validMove(row,col,solutions);

			if (validity == 1) {
				currntRow = row;
				currentCol = col;
				if(this.AImove)
				{
					checkplayerMove = AIplayer;
				}
				board[currntRow][currentCol] = checkplayerMove;  
				validInput = true;  
			} else {
				System.out.println("This move at (" + (row + 1) + "," + (col + 1)
						+ ") is not valid. Try again...");
			}
		} while (!validInput);  
	}
	
	public int validMove(int row, int col, ArrayList<String> validCoordinates){
		int resultValidMove= -1;
		String playerMove = row+","+col;
		resultValidMove = validCoordinates.contains(playerMove) ? 1:0;
		return resultValidMove;
		
	}
	public ArrayList<String> availableSolutions(){
		ArrayList<String> freeSpaces = new ArrayList<String>();
		int row = SharedConstants.ROWS;
		int col = SharedConstants.COLS;
		for (row = 0; row < SharedConstants.ROWS; ++row){
			for (col = 0; col < SharedConstants.COLS; ++col){
				if(board[row][col] == SharedConstants.EMPTY){
					freeSpaces.add(row+","+col);
				}
			}
		}
		return freeSpaces;
	}

	/*
	 * Updates the game if there is a win
	 * draw or lose situation
	 */
	public void updateGame(int playerType, int currentRow, int currentCol) {
		if (hasWon(playerType, currentRow, currentCol)) {  // check if winning move
			if((playerType == SharedConstants.CROSS || playerType == SharedConstants.NOUGHT )&& AImove==false)
			{
				currentState = SharedConstants.PLAYER_WON;
			}else if ((playerType == SharedConstants.CROSS || playerType == SharedConstants.NOUGHT )&& AImove==true)
			{
				currentState = SharedConstants.AI_WON;
			}
		} else if (isDraw()) {  
			currentState = SharedConstants.DRAW;
		}
		evaluate();
	}

	/*
	 * Check whether its a draw and all.
	 */
	public boolean isDraw() {
		for (int row = 0; row < SharedConstants.ROWS; ++row) {
			for (int col = 0; col < SharedConstants.COLS; ++col) {
				if (board[row][col] == SharedConstants.EMPTY) {
					return false; 
				}
			}
		}
		return true;  
	}

	/*
	 * check who won whether AI or The player
	 */
	public boolean hasWon(int playerType, int currentRow, int currentCol) {
		if(AImove){
			playerType = AIplayer;
		}
		boolean checkWon = board[currentRow][0] == playerType         
				&& board[currentRow][1] == playerType
				&& board[currentRow][2] == playerType
				|| board[0][currentCol] == playerType      
				&& board[1][currentCol] == playerType
				&& board[2][currentCol] == playerType
				|| currentRow == currentCol            
				&& board[0][0] == playerType
				&& board[1][1] == playerType
				&& board[2][2] == playerType
				|| currentRow + currentCol == 2  
				&& board[0][2] == playerType
				&& board[1][1] == playerType
				&& board[2][0] == playerType;
		if(checkWon){
			System.out.println("Won player - " + playerType);
		}
		return checkWon;
	}
	
	public int[] evaluate(){
		
		int score = 0;
		int[] results = new int[1];
		for(int line = 1; line<=8;line++)
		{
			score += evaluateLine(line);
			//System.out.println(score);
		}
		//System.out.println(score);
		
		results[0]= score;
		
		return results;
	}
	
	
	public int evaluateLine(int line){
		int score = 0;
		int row = 0;
		int col = 0;
		int c1 = 0;
		int r1 = 0;
		int r2 = 0;
		int c2 = 0;
		
		/**
		 *    147|15|168 
		 *    ----------
		 *    24|2578|26
		 *    -----------
		 *    348|35|367
		 *    
		 *    this map shows which number the line it is looking at 
		 *    for example line 1 will check all the places there is a 1;	
		 *    this is the set up for the score calculator;	
		 */
		if(line == 1){
			c1 = 1;
			c2 = 2;
			
		}
		else if(line == 2){
			row = 1;
			c1 = 1;
			c2 = 2;
		}
		else if(line == 3){
			row = 2;
			c1 = 1;
			c2 = 2;
		}
		else if(line == 4){
			r1 = 1;
			r2 = 2;
		}
		else if(line == 5){
			col = 1;
			r1 = 1;
			r2 = 2;
		}
		else if(line == 6){
			col = 2;
			r1 = 1;
			r2 = 2;
		}
		else if(line == 7){
			r1 = 1;
			r2 = 2;
			c1 = 1;
			c2 = 2;
		}
		else if(line == 8){
			row = 2;
			r1 = -1;
			r2 = -2;
			c1 = 1;
			c2 = 2;
		}	
		// this means that the beginning of the line is empty
		if(board[row][col] == SharedConstants.EMPTY){
			if(board[row+r1][col+c1] == SharedConstants.EMPTY){
				if(board[row+r2][col+c2] == SharedConstants.EMPTY){
					score = 1;
				}
				else{
					score = (board[row+r2][col+c2] == AIplayer ? 10 :-10);
				}
			}
			else{
				if(board[row+r2][col+c2] == SharedConstants.EMPTY){
					score = (board[row+r1][col+c1]== AIplayer ? 10:-10);
				}
				else if(board[row+r1][col+c1] == board[row+r2][col+c2]){
					score = (board[row+r1][col+c1]== AIplayer ? 100:-100);
				}
				else{
					score = 0;
				}
			}
		}
		else{
			if(board[row][col] == board[row+r1][col+c1]){
					if(board[row+r1][col+c1] == board[row+r2][col+c2]){
						score = (board[row][col]== AIplayer ? 1000:-1000);
					}
					else if(board[row+r2][col+c2] == SharedConstants.EMPTY){
							score = (board[row][col]== AIplayer ? 100:-100);
					}
					else{
							score = 0;
					}		
			}
			else if(board[row+r1][col+c1] == SharedConstants.EMPTY){	
					if(board[row][col]  == board[row+r2][col+c2]){
						score = (board[row][col]== AIplayer ? 100:-100);
					}
					else if(board[row+r2][col+c2] == SharedConstants.EMPTY){
							score = (board[row][col]== AIplayer ? 10:-10);
					}
					else{
							score = 0;
					}
			}
			else{
				score = 0;
			}	
		}		
		return score;
	}
	
	/*
	 * Prints game board
	 */
	public void printBoard() {
		for (int row = 0; row < SharedConstants.ROWS; ++row) {
			for (int col = 0; col < SharedConstants.COLS; ++col) {
				switch (board[row][col]) {
				case SharedConstants.EMPTY:  System.out.print("   "); break;
				case SharedConstants.NOUGHT: System.out.print(" O "); break;
				case SharedConstants.CROSS:  System.out.print(" X "); break;
				}
				if (col != SharedConstants.COLS - 1) {
					System.out.print("|");   
				}
			}
			System.out.println();
			if (row != SharedConstants.ROWS - 1) {
				System.out.println("-----------"); 
			}
		}
		System.out.println();
	}
	public AI getTicTacToeAIRandom() {
		return ticTacToeAI;
	}
	public void setTicTacToeAIRandom(AI ticTacToeAIRandom) {
		this.ticTacToeAI = ticTacToeAIRandom;
	}
	@Override
	public ArrayList<String> getAvailableSolutions(int player) {
		
		return availableSolutions();
	}

	

	@Override
	public void moveSet(int row, int col, int level) {
		
		currntRow = row;
		currentCol = col;
		int checkplayerMove = (level%2 == 0 ? currentPlayer : AIplayer);
		board[currntRow][currentCol] = checkplayerMove;  
	}

	@Override
	public void undoMove(int row, int col, int level) {
		
		board[row][col] = SharedConstants.EMPTY;
	}

	
	

	@Override
	public boolean isGameOver() {
		int score = 0;
		for(int line = 1; line<=8;line++)
		{
			score = evaluateLine(line);
			if(score == 1000 || score == -1000)
				return true;
		}
		//System.out.println(score);
		
		
		return false;
	}
	
	
}