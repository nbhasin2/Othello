package tictactoe;
/**
 * @author Nishant Bhasin
 * Base console based tictactoe 
 * game class. 
 */
import gameai.AIMain;
import gameai.AIStrategy;
import gamemodel.GameConsole;
import gamestate.GameSateModel;
import gamestate.GameStateRetriever;
import gamestate.GameStateWrter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import com.connect.four.ConnectFourConsole;
import com.connect.four.gameBoard;

import shared.BoardSpace;
import shared.SharedConstants;
import shared.SharedConstants.PlayableItem;

public class TicTacToeConsole extends GameConsole implements Serializable{//implements GameConsoleInterface{

 	//private in[][]  board = new int[SharedConstants.ROWS][SharedConstants.COLS]; // game board in 2D array
	
	private GameBoardTic board;
	
	private int currentState;  
	private int currentPlayer; 
	private int AIplayer;
	private int tempCurrentPlayer;
	private int currntRow, currentCol;
	private boolean switchPlayer = false;
	private AIMain ticTacToeAI;
	private static Scanner in; 
	private String playerType;
	private boolean AImove;
	private ArrayList<String> lineArray;
	private GameSateModel gameStateModel;
	private TicTacToeConsole ticTacToeModel;
	private GameStateRetriever loadGame;
	private GameStateWrter saveGame;
	/*
	 * Constructor for ticTacToe
	 */
	public TicTacToeConsole(AIStrategy AIType){
		this(new AIMain(AIType),null);
	
	}
	/*
	 * Constructor for ticTacToe
	 */
	public TicTacToeConsole(AIStrategy AIType,Scanner passedScanner){
		this(new AIMain(AIType),passedScanner);
	}
	/*
	 * Constructor for ticTacToe
	 */
	public TicTacToeConsole(AIMain AIType,Scanner passedScan){
		ticTacToeModel = this;
		setTicTacToeAI(AIType);
		if(passedScan == null)
			in = new Scanner(System.in);
		else
			in = passedScan;
		
		System.out.println("Welcome to TicTacToc Console Game !!" +
				"\nTo start playing please enter your cell cordinate between [1-3]" +
				"\nlike 1 1, 2 2. Make sure there is space between the numbers." +
				"Enjoy your game.\n");
	//	System.out.println("What player would you like to be X or O :");
	//	Scanner user_input = new Scanner(System.in);
	//	playerType = user_input.next();
		initGame(playerType);
	}

	/*
	 * Method that is called for playing tictactoe
	 */
	public void playTicTacToe()  
	{
		System.out.println("Game Started ... ");
		board.printBoard();
		System.out.println("gsM = "+gameStateModel);
		gameStateModel.setCurrentBoard(board.makeDeepCopy());
		do {
			playerMove(currentPlayer,this.AImove);  
			updateGame(currentPlayer, currntRow, currentCol); 
			board.printBoard();
			checkGameOver();
			togglePlayer();
		} while (currentState == SharedConstants.PLAYING); // repeat if not game-over
	}

	/*
	 * changes AI vs Human
	 */
	private void togglePlayer()
	{
		if(AImove == false){
			AImove = true;
		}
		else {
			currentPlayer = tempCurrentPlayer;
			AImove = false;
		}
	}
	/*
	 * checks whether the game is over or not
	 */
	private void checkGameOver()
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
	public void toggleSwitch()	{
		if(switchPlayer == false)
			switchPlayer = true;
		else
			switchPlayer = false;
	}
	/*
	 * Instantiates the game board 
	 */
	private void initGame(String crossOrNought) {
//		if(crossOrNought.equals("X")||crossOrNought.equals("x")){
//			currentPlayer = SharedConstants.CROSS; 
//			tempCurrentPlayer = SharedConstants.CROSS;
//			AIplayer = SharedConstants.NOUGHT;
//		}
//		else if(crossOrNought.equals("O")||crossOrNought.equals("o")){
//			currentPlayer = SharedConstants.NOUGHT;
//			tempCurrentPlayer = SharedConstants.NOUGHT;
//			AIplayer = SharedConstants.CROSS;
//		}else{
			System.out.println("We assigned you X as you can only enter O or X");
			currentPlayer = SharedConstants.CROSS;
			tempCurrentPlayer = SharedConstants.CROSS;
			AIplayer = SharedConstants.NOUGHT;
//		}
		currentState = SharedConstants.PLAYING; 
		board = new GameBoardTic(null);
		board.boardSetup();
		setUpLineArray();
		gameStateModel = new GameSateModel();
		loadGame = new GameStateRetriever();
		saveGame = new GameStateWrter(ticTacToeModel.getGameStateModel());
	}

	public void undoBoard()
	{
		
		gameStateModel.addCurrentBoardToRedo(board.makeDeepCopy());
		
		gameStateModel.setCurrentBoard(board.makeDeepCopy());
		board.setPlayField(gameStateModel.popUndoElement());
		board.printBoard();
	}
	
	public void redoBoard()
	{
		gameStateModel.addCurrentBoardToUndo(board.makeDeepCopy());
		
		gameStateModel.setCurrentBoard(board.makeDeepCopy());
		board.setPlayField(gameStateModel.popRedoElement());
		board.printBoard();
	}
	
	
	
	/**
	 * Method used to set up the lineArray variable to help the method {@link #evaluateLine(int)}
	 */
	private void setUpLineArray() {
		lineArray = new ArrayList<String>();
		lineArray.add("0,0,0,0,1,2");
		lineArray.add("1,0,0,0,1,2");
		lineArray.add("2,0,0,0,1,2");
		lineArray.add("0,0,1,2,0,0");
		lineArray.add("0,1,1,2,0,0");
		lineArray.add("0,2,1,2,0,0");
		lineArray.add("0,0,1,2,1,2");
		lineArray.add("2,0,-1,-2,1,2");
	}
	/*
	 * Checks player and AI move
	 */
	private void playerMove(int checkplayerMove, boolean AImove)   {
		boolean validInput = false;  
		int row;  
		int col;
		String AICoordinates;
		do {
			if ((checkplayerMove == SharedConstants.CROSS || checkplayerMove == SharedConstants.NOUGHT) && AImove==false) {
				System.out.print("Player " + playerType + " enter your move :");
				int[] parsed  = consoleParser(in,true);
				row =parsed[0];
				col =parsed[1];
				if(col == -2){
					System.out.println("Redo");
					redoBoard();
				}
				else if(col == -3){
					System.out.println("Undo");
					undoBoard();
				}
				else if(col == -4){
					System.out.println("Save");
					saveMove();
				}
				else if(col == -5){
					System.out.println("Load");
					loadMove();
				}
				if(validMove(row,col,getAvailableSolutions(0)) == 1){
					gameStateModel.getUndoBoard().add(board.makeDeepCopy());
				}
				
			}
			else {
				System.out.print("AI " + ticTacToeAI.getAIType() + " move \n");
				AICoordinates = ticTacToeAI.makeMove(this);
				row = Integer.parseInt(AICoordinates.split(",")[0]);
				col = Integer.parseInt(AICoordinates.split(",")[1]);	
			}
			
			int substituteLevel = (this.AImove) ? -1:-2;
			
			if (moveSet(row,col,substituteLevel)) {
				validInput = true;  
			} else {
				System.out.println("This move at (" + (row + 1) + "," + (col + 1)
						+ ") is not valid. Try again...");
			}
		} while (!validInput);  
	}
	
	/*
	 * Checks if the move in row and col is valid and is in valid coordinates;
	 */
	private int validMove(int row, int col, ArrayList<String> validCoordinates){
		int resultValidMove= -1;
		String playerMove = row+","+col;
		resultValidMove = validCoordinates.contains(playerMove) ? 1:0;
		return resultValidMove;
	}
	private ArrayList<String> availableSolutions(){
		ArrayList<String> freeSpaces = new ArrayList<String>();
		int row;
		int col;
		for (row = 0; row <board.getROWS(); ++row){
			for (col = 0; col < board.getCOLS(); ++col){
				if(board.getPlayField()[row][col].getGamePiece().equals(PlayableItem.EMPTY)){
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
	private void updateGame(int playerType, int currentRow, int currentCol) {
		if(isGameOver()){
			int[] results = evaluate();
			
			if(results[1] == 1)
				currentState = SharedConstants.PLAYER_WON;
			else if(results[1] == 2)
				currentState = SharedConstants.AI_WON;
			else
				currentState = SharedConstants.DRAW;
		}
	}
	/**
	 * @see gamemodel.GameConsoleInterface#evaluate()
	 */
	public int[] evaluate(){
		int score = 0;
		int[] results = new int[2];
		results[1] = 0;
		for(int line = 0; line<8;line++){
			int tempScore = evaluateLine(line);
			if(tempScore == 1000)
				results[1] = 2;
			else if(tempScore == -1000)
				results[1] = 1;
			score +=tempScore;
		}
		results[0]= score;
		return results;
	}
	/**
	 * @author Zacharie
	 * @param line the lie that is being look at, under is the map for each line respectively
	 *   
	 *    147|15|168 
	 *    ----------
	 *    24|2578|26
	 *    -----------
	 *    348|35|367
	 *    
	 *    this map shows which number the line it is looking at 
	 *    for example line 1 will check all the places there is a 1;	
	 *    so line 1 is (1,1)(1,2)(1,3) where(row,col)
	 *    	
	 *@return and integer value of the line that was just observed under are the value of those scores
 	 *	1 for an empty line, 
 	 *	0 for a shared line(where there are both human and AI)
 	 *	10 or -10 for a line with either 1 token of AI(+) or 1 token of Human(-)
 	 *	100 or -100 for a line with either 2 token of AI(+) or 1 token of Human(-)
 	 *	1000 or -1000 for a line with either 3 token of AI(+) or 1 token of Human(-)				
	 */
	private int evaluateLine(int line){
		int score = 0;
		String[] lineMod = lineArray.get(line).split(",");
		int row = Integer.parseInt(lineMod[0]);
		int col = Integer.parseInt(lineMod[1]);
		int r1 = Integer.parseInt(lineMod[2]);
		int r2 = Integer.parseInt(lineMod[3]);
		int c1 = Integer.parseInt(lineMod[4]);
		int c2 = Integer.parseInt(lineMod[5]);
		
		PlayableItem aiPiece = (AIplayer == SharedConstants.NOUGHT) ? PlayableItem.WHITE:PlayableItem.BLACK;
		
		
		
		// this means that the beginning of the line is empty
		if(board.getPlayField()[row][col].getGamePiece().equals(PlayableItem.EMPTY)){
			if(board.getPlayField()[row+r1][col+c1].getGamePiece().equals(PlayableItem.EMPTY)){
				if(board.getPlayField()[row+r2][col+c2].getGamePiece().equals(PlayableItem.EMPTY)){
					score = 1;
				}
				else{
					score = (board.getPlayField()[row+r2][col+c2].getGamePiece().equals(aiPiece) ? 10 :-10);
				}
			}
			else{
				if(board.getPlayField()[row+r2][col+c2].getGamePiece().equals(PlayableItem.EMPTY)){
					score = (board.getPlayField()[row+r1][col+c1].getGamePiece().equals(aiPiece) ? 10:-10);
				}
				else if(board.getPlayField()[row+r1][col+c1] == board.getPlayField()[row+r2][col+c2]){
					score = (board.getPlayField()[row+r1][col+c1].getGamePiece().equals(aiPiece) ? 100:-100);
				}
				else{
					score = 0;
				}
			}
		}
		else{
			if(board.getPlayField()[row][col].getGamePiece() == board.getPlayField()[row+r1][col+c1].getGamePiece()){
					if(board.getPlayField()[row+r1][col+c1].getGamePiece() == board.getPlayField()[row+r2][col+c2].getGamePiece()){
						score = (board.getPlayField()[row][col].getGamePiece().equals(aiPiece) ? 1000:-1000);
					}
					else if(board.getPlayField()[row+r2][col+c2].getGamePiece().equals(PlayableItem.EMPTY)){
							score = (board.getPlayField()[row][col].getGamePiece().equals(aiPiece) ? 100:-100);
					}
					else{
							score = 0;
					}		
			}
			else if(board.getPlayField()[row+r1][col+c1].getGamePiece().equals(PlayableItem.EMPTY)){	
					if(board.getPlayField()[row][col].getGamePiece()  == board.getPlayField()[row+r2][col+c2].getGamePiece()){
						score = (board.getPlayField()[row][col].getGamePiece().equals(aiPiece) ? 100:-100);
					}
					else if(board.getPlayField()[row+r2][col+c2].getGamePiece().equals(PlayableItem.EMPTY)){
							score = (board.getPlayField()[row][col].getGamePiece().equals(aiPiece) ? 10:-10);
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
/*	private void printBoard() {
		for (int row = 0; row < SharedConstants.ROWS; ++row) {
			for (int col = 0; col < SharedConstants.COLS; ++col) {
				switch (board.getPlayField()[row][col].getGamePiece()) {
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
	}*/
	public AIMain getTicTacToeAI() {
		return ticTacToeAI;
	}
	public void setTicTacToeAI(AIMain ticTacToeAI) {
		this.ticTacToeAI = ticTacToeAI;
	}
	/**
	 * @see gamemodel.GameConsoleInterface#getAvailableSolutions int
	 * 
	 */
	@Override
	public ArrayList<String> getAvailableSolutions(int player) {	
		return availableSolutions();
	}
	/**
	 * @see gamemodel.GameConsoleInterface#moveSet(int, int, int)
	 */
	@Override
	public boolean moveSet(int row, int col, int level) {
		currntRow = row;
		currentCol = col;
		if((currntRow >= board.getROWS() || currntRow < 0)||( currentCol >= board.getCOLS() || currentCol <0 )){
			return false;
		}
		else if(!(validMove(currntRow,currentCol,availableSolutions()) == 1))
			return false;
		PlayableItem checkplayerMove = Math.abs(level)%2 == 0 ? PlayableItem.BLACK: PlayableItem.WHITE;
		board.getPlayField()[currntRow][currentCol].setGamePiece(checkplayerMove);
		return true;
	}
	/**
	 * @see gamemodel.GameConsoleInterface#undoMove(int, int, int)
	 */ 
	@Override
	public boolean undoMove(int row, int col, int level) {
		currntRow = row;
		currentCol = col;
		if((currntRow >= board.getROWS() || currntRow < 0)||( currentCol >= board.getCOLS() || currentCol <0 ))
			return false;
		else if(board.getPlayField()[currntRow][currentCol].getGamePiece().equals(PlayableItem.EMPTY)){
				return false;
		}
		board.getPlayField()[currntRow][currentCol].setGamePiece(PlayableItem.EMPTY);
		return true;
	}

	
	
	public TicTacToeConsole getTicTacToeModel() {
		return ticTacToeModel;
	}
	public void setTicTacToeModel(TicTacToeConsole ticTacToeModel) {
		this.ticTacToeModel = ticTacToeModel;
	}

	public void saveMove()
	{
		gameStateModel.setCurrentBoard(board.getPlayField());
		this.saveGame.writeModel();
	}
	public GameSateModel getGameStateModel() {
		return gameStateModel;
	}
	public void setGameStateModel(GameSateModel gameStateModel) {
		this.gameStateModel = gameStateModel;
	}

	public GameBoardTic getBoard() {
		return board;
	}
	public void setBoard(GameBoardTic board) {
		this.board = board;
	}
	public void loadMove()
	{
			ArrayList<BoardSpace[][]> redoBoard = new ArrayList<BoardSpace[][]>();
			ArrayList<BoardSpace[][]> undoBoard = new ArrayList<BoardSpace[][]>();
			BoardSpace[][] currentBoard;
			loadGame.retrieveModel();
			
			
			redoBoard = loadGame.getMyModel().getRedoBoard();
			undoBoard = loadGame.getMyModel().getUndoBoard();
			currentBoard = loadGame.getMyModel().getCurrentBoard();
			ticTacToeModel.getGameStateModel().setUndoBoard(undoBoard);
			ticTacToeModel.getGameStateModel().setRedoBoard(redoBoard);
			ticTacToeModel.getBoard().setPlayField(currentBoard);
			
			ticTacToeModel.getBoard().printBoard();
	}

	
	
	


}