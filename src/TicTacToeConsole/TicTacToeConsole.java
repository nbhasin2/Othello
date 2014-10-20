package TicTacToeConsole;

import java.util.Scanner;

import sun.net.www.content.audio.aiff;

import GameAI.AI;
import Shared.SharedConstants;

public class TicTacToeConsole {

	private int[][] board = new int[SharedConstants.ROWS][SharedConstants.COLS]; // game board in 2D array
	private int currentState;  
	private int currentPlayer; 
	private int AIplayer;
	private int tempCurrentPlayer;
	private int currntRow, currentCol; // current seed's row and column
	private boolean switchPlayer = false;
	private AI ticTacToeAIRandom;
	public static Scanner in; // the input Scanner
	private String playerType;
	private boolean AImove;

	/*
	 * Constructor for ticTacToe
	 */
	public TicTacToeConsole()
	{
		setTicTacToeAIRandom(new AI(SharedConstants.TicTacToe, SharedConstants.AIRandom));
		in = new Scanner(System.in);

	}

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
		do {
			playerMove(currentPlayer,this.AImove);  
			updateGame(currentPlayer, currntRow, currentCol); 
			printBoard();
			checkGameOver();
			togglePlayer();
		} while (currentState == SharedConstants.PLAYING); // repeat if not game-over
	}

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
	
	public void toggleSwitch()
	{
		if(switchPlayer == false)
			switchPlayer = true;
		else
			switchPlayer = false;
	}


	/** Initialize the game-board contents and the current states */
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

	public void playerMove(int theSeed, boolean AImove) {
		boolean validInput = false;  // for input validation
		int row;  // array index starts at 0 instead of 1
		int col;
		String AICoordinates;
		do {
			if ((theSeed == SharedConstants.CROSS || theSeed == SharedConstants.NOUGHT) && AImove==false) {
				System.out.print("Player " + playerType + " enter your move :");
				row = in.nextInt() - 1;  
				col = in.nextInt() - 1;
			} else {
				System.out.print("AI " + ticTacToeAIRandom.getAIType() + " move \n");
				AICoordinates = ticTacToeAIRandom.ticTacToeRandom(board);
				row = Integer.parseInt(AICoordinates.split("-")[0]);
				col = Integer.parseInt(AICoordinates.split("-")[1]);	
			}

			if (row >= 0 && row < SharedConstants.ROWS && col >= 0 && col < SharedConstants.COLS && board[row][col] == SharedConstants.EMPTY) {
				currntRow = row;
				currentCol = col;
				if(this.AImove)
				{
					theSeed = AIplayer;
				}
				board[currntRow][currentCol] = theSeed;  // update game-board content
				validInput = true;  // input okay, exit loop
			} else {
				System.out.println("This move at (" + (row + 1) + "," + (col + 1)
						+ ") is not valid. Try again...");
			}
		} while (!validInput);  
	}

	public void updateGame(int theSeed, int currentRow, int currentCol) {
		if (hasWon(theSeed, currentRow, currentCol)) {  // check if winning move
			if((theSeed == SharedConstants.CROSS || theSeed == SharedConstants.NOUGHT )&& AImove==false)
			{
				currentState = SharedConstants.PLAYER_WON;
			}else if ((theSeed == SharedConstants.CROSS || theSeed == SharedConstants.NOUGHT )&& AImove==true)
			{
				currentState = SharedConstants.AI_WON;
			}
		} else if (isDraw()) {  // check for draw
			currentState = SharedConstants.DRAW;
		}
	}

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
	public boolean hasWon(int theSeed, int currentRow, int currentCol) {
		if(AImove)
		{
			theSeed = AIplayer;
		}
		boolean checkWon = board[currentRow][0] == theSeed         
				&& board[currentRow][1] == theSeed
				&& board[currentRow][2] == theSeed
				|| board[0][currentCol] == theSeed      
				&& board[1][currentCol] == theSeed
				&& board[2][currentCol] == theSeed
				|| currentRow == currentCol            
				&& board[0][0] == theSeed
				&& board[1][1] == theSeed
				&& board[2][2] == theSeed
				|| currentRow + currentCol == 2  
				&& board[0][2] == theSeed
				&& board[1][1] == theSeed
				&& board[2][0] == theSeed;
		if(checkWon)
		{
			System.out.println("Won player - " + theSeed);
		}
		return checkWon;
	}

	/** Print the game board */
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
		return ticTacToeAIRandom;
	}

	public void setTicTacToeAIRandom(AI ticTacToeAIRandom) {
		this.ticTacToeAIRandom = ticTacToeAIRandom;
	}
}