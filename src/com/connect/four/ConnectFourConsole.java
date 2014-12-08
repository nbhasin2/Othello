/*@author Matthew Crooke
 * This game is built in the style of Othello using boardSpace and playableItem classes.
 * The object of the game is to strategically place four of our own pieces in a row.
 * 
 * 
 */
package com.connect.four;
import gameai.AIMain;
import gameai.AIStrategy;
import gamemodel.GameConsole;
import gamestate.GameSateModel;
import gamestate.GameStateRetriever;
import gamestate.GameStateWrter;
import gameui.Controller;

import java.util.ArrayList;
import java.util.Scanner;

import othello.OthelloConsole;
import shared.BoardSpace;
import shared.SharedConstants;
//import shared.SharedConstants.PlayableItem;


public class ConnectFourConsole extends GameConsole {

	private static final int DEFAULT_ROW = 5;
	private static final int MAX_AISCORE = 10000;
	private static final int MAX_HUSCORE = -10000;
	private GameBoardC4 board;
	private SharedConstants.PlayableItem currentPlayer;
	private static Scanner playerMove= new Scanner(System.in); 
	private SharedConstants.GameStatus currentState;	
	private AIMain aiPlayer;
	private ArrayList<String> directionArray;
	private GameSateModel gameStateModel;
	private ConnectFourConsole connectFourModel;
	private GameStateRetriever loadGame;
	private GameStateWrter saveGame;
	/*
	 * Constructor for connect four console game
	 */
	public ConnectFourConsole(AIStrategy AIType){
		aiPlayer = new AIMain(AIType);
		board = new GameBoardC4(null);
		connectFourModel = this;
		
		
		gameSetup();
	}
	public void playConnectFour(){
		board.printBoard();
		printCommands(false);
		gameStateModel.setCurrentBoard(board.makeDeepCopy());
		do{ 
			playerMove(currentPlayer);
			board.printBoard();		
			currentPlayer = (currentPlayer == SharedConstants.PlayableItem.BLACK) ? SharedConstants.PlayableItem.WHITE : SharedConstants.PlayableItem.BLACK;
		} while(currentState == SharedConstants.GameStatus.PLAYING);
		getWinner();
	}
	private void getWinner(){
		int[] results = evaluate();
		if(results[1] == SharedConstants.GAMENOWIN){
			System.out.println("DRAW!");
		}
		else if(results[1] == SharedConstants.GAMEBLACKWIN){
			System.out.println("Black Wins!");
		}
		else{
			System.out.println("White Wins!");
		}	
	}
	/*
	 * Method to setup the initial game with basic item on the board.
	 */
	private void gameSetup(){
		board.boardSetup();
		directionArray = setUpDirectionArray();
		currentPlayer = SharedConstants.PlayableItem.BLACK;
		currentState = SharedConstants.GameStatus.PLAYING;
		gameStateModel = new GameSateModel();
		loadGame = new GameStateRetriever();
		saveGame = new GameStateWrter(connectFourModel.getGameStateModel());
	}
	
	

	/*This function will handle the different moves between
	 * players and the AI player.
	 * 
	 */
	public void playerMove(SharedConstants.PlayableItem playitem){
		boolean isValidInput = false;
		int col;
		String AICoordinates;
		do {
			if(currentPlayer == SharedConstants.PlayableItem.BLACK) {
				System.out.print("What column do you wish to put your piece in player Black?\n"); 
				int[] parsed = consoleParser(playerMove,false);
				col = parsed[0];
				if(col == SharedConstants.REDO){
					System.out.println("Redo");
					redoBoard();
				}
				else if(col == SharedConstants.UNDO){
					System.out.println("Undo");
					undoBoard();
				}
				else if(col == SharedConstants.SAVE){
					System.out.println("Save");
					saveBoard();
				}
				else if(col == SharedConstants.LOAD){
					System.out.println("Load");
					loadBoard();
				}
				else if(col == SharedConstants.HELP){
					printCommands(true);
				}
			}
			else {
				System.out.print("AI " + aiPlayer.getAIType() + " move \n");
				AICoordinates = aiPlayer.makeMove(this);
				col = Integer.parseInt(AICoordinates.split(",")[1]);
			}
	        if(validMove(col)){
	        		int row = DEFAULT_ROW;
	        		int substituteLevel = (currentPlayer.equals(SharedConstants.PlayableItem.BLACK) ? SharedConstants.SUBBLACK:SharedConstants.SUBWHITE);
	        		moveSet(row,col,substituteLevel);
		        	isValidInput = true;
	        }
	        else if(col < SharedConstants.INVALID){
	        	System.out.println("Action");
	        }
	        else
	        	printHelp();
	        
		}   while(!isValidInput);	
		if(isGameOver()){
			currentState = SharedConstants.GameStatus.GAME_END;
		}
	}
	/*
	 * Checks for valid moves, if the column number
	 * selected is within the gameboard, as well as
	 * check if the column is filled with pieces.
	 */
	public boolean validMove(int col){
		if (col >= board.getCOLS() || col < SharedConstants.MINCOLS ){
			return false;
		}
		else{
			int row = DEFAULT_ROW;
			while(row >= SharedConstants.MINROWS ){
				if(board.getPlayField()[row][col].getGamePiece().equals(SharedConstants.PlayableItem.EMPTY))
					return true;
				row--;
			}
			return false;
		}
	}
	/*An available solution for connect four is any column that is not currently full (row = 0)
	 * Valid move is called to make sure the column can hold more game pieces
	 * 
	 * @see gamemodel.GameConsoleInterface#getAvailableSolutions(int)
	 */
	@Override
	public ArrayList<String> getAvailableSolutions(int player) {
		ArrayList<String> moves = new ArrayList<String>();
		for (int col = 0; col < board.getCOLS(); ++col) {
			if(validMove(col)){
				moves.add(DEFAULT_ROW+ ","+col); 
			}			
		}		
		return moves;
	}
	
	/*
	 * @see gamemodel.GameConsoleInterface#evaluate()
	 */
	@Override
	public int[] evaluate() {
		int score = 0;
		int[] results = new int[2];
		results[1] = 0;
		for(int row = 0; row<board.getROWS();row++){
			for(int col = 0;col<board.getCOLS();col++){
				int tempScore = evaluateToken(row,col);
				if(tempScore >= MAX_AISCORE){ 
					results[1] = SharedConstants.GAMEWHITEWIN;
				}
				else if(tempScore <= MAX_HUSCORE ){
					results[1] = SharedConstants.GAMEBLACKWIN;
				}
				score += tempScore;
				//System.out.println(score);
			}
		}
		results[0]= score;
		return results;
	}
	/*
	 * Looks at all directions and evaluates the lines direction 
	 * based on function called evaluate line
	 * 
	 */
	private int evaluateToken(int row,int col){
		int score = 0;
		for(int dir = 0; dir <SharedConstants.ORDINALMAX;dir++){
			String rowAndColMods = directionArray.get(dir);
			int r = Integer.parseInt(rowAndColMods.split(",")[0]);
			int c = Integer.parseInt(rowAndColMods.split(",")[1]);
			if(!(board.getPlayField()[row][col].getGamePiece().equals(SharedConstants.PlayableItem.EMPTY))){
				int tempScore = 10*evalutateLine(row,col,r,c,0,board.getPlayField()[row][col].getGamePiece());
				int modifier = (board.getPlayField()[row][col].getGamePiece().equals(SharedConstants.PlayableItem.BLACK)? -1:1);
				score += tempScore*modifier;
			}
			else{
				score = 0;
			}
		}
		return score;
	}
	/*This function will evaluate the worthiness of the direction lines
	 * around the current gamepiece. If there are pieces of the same colour
	 * adjacent to the initial space, the score will be multiplied. A higher score
	 * means the move is valued more in terms of selecting best move. 
	 */
	private int evalutateLine(int row,int col,int r,int c,int combo,SharedConstants.PlayableItem player){
		int score = 1;
		int MAXCOMBO = 2;
		int maxRow = board.getROWS() -1;
		int maxCol = board.getCOLS() -1;
		int boundRow = row-r;
		int boundCol = col-c;
		if(combo > MAXCOMBO){
			return score;
		}
		else{
			if(!((boundRow <SharedConstants.MINROWS || boundRow >maxRow)||(boundCol <SharedConstants.MINCOLS|| boundCol >maxCol)))
			{
				if(board.getPlayField()[boundRow][boundCol].getGamePiece().equals(player)){
					score = 10;
				}
				else if(board.getPlayField()[boundRow][boundCol].getGamePiece().equals(SharedConstants.PlayableItem.EMPTY)){
					score = 1;
				}
				else{
					score = 0;
				}
			}
			else{
					score = 0;
			}
		
			return score*evalutateLine(boundRow,boundCol,r,c,combo+1,player);			
		}
	}
	/*
	 * @see gamemodel.GameConsoleInterface#moveSet(int, int, int)
	 */
	@Override
	public boolean moveSet(int row, int col, int player) {
		if(!validMove(col))
			return false;
		while(!(board.getPlayField()[row][col].getGamePiece().equals(SharedConstants.PlayableItem.EMPTY)&& row >= SharedConstants.MINROWS)){
			row--;
		}
		SharedConstants.PlayableItem move;
		move = (Math.abs(player)%2 == 0 ? SharedConstants.PlayableItem.BLACK : SharedConstants.PlayableItem.WHITE);
		if(player == SharedConstants.SUBBLACK){
			gameStateModel.getUndoBoard().add(board.makeDeepCopy());
		}
		board.getPlayField()[row][col].setGamePiece(move);
		
		return true;
	}
	
	/*
	 * @see gamemodel.GameConsoleInterface#undoMove(int, int, int)
	 */
	@Override
	public boolean undoMove(int row, int col, int player) {
		if((row >= board.getROWS() || row < SharedConstants.MINROWS)||(col >= board.getCOLS() || col <SharedConstants.MINCOLS )){
			return false;
		}
		while(!(board.getPlayField()[row][col].getGamePiece().equals(SharedConstants.PlayableItem.EMPTY)) && row >= SharedConstants.MINROWS ){
			row--;
			if(row == SharedConstants.INVALID)
				break;
		}

		if(row == DEFAULT_ROW){
			return false;
		}
		board.getPlayField()[row+1][col].setGamePiece(SharedConstants.PlayableItem.EMPTY);
		
		return true;
	}
	
	public GameSateModel getGameStateModel() {
		return gameStateModel;
	}

	public void setGameStateModel(GameSateModel gameStateModel) {
		this.gameStateModel = gameStateModel;
	}
	
	
	public GameBoardC4 getBoard() {
		return board;
	}
	public void setBoard(GameBoardC4 board) {
		this.board = board;
	}
	/**
	 * @see OthelloConsole#undoBoard();
	 */
	public void undoBoard(){
		
		gameStateModel.addCurrentBoardToRedo(board.makeDeepCopy());
		
		gameStateModel.setCurrentBoard(board.makeDeepCopy());
		board.setPlayField(gameStateModel.popUndoElement());
		board.printBoard();
	}
	/**
	 * @see OthelloConsole#redoBoard();
	 */
	public void redoBoard()
	{
		gameStateModel.addCurrentBoardToUndo(board.makeDeepCopy());
		
		gameStateModel.setCurrentBoard(board.makeDeepCopy());
		board.setPlayField(gameStateModel.popRedoElement());
		board.printBoard();
	}
	/**
	 * @see Controller#saveMove();
	 */
	public void saveBoard(){
		gameStateModel.setCurrentBoard(board.getPlayField());
		this.saveGame.writeModel();
	}
	/**
	 * @see Controller#undoMove()
	 */
	public void loadBoard(){
		
		ArrayList<BoardSpace[][]> redoBoard = new ArrayList<BoardSpace[][]>();
		ArrayList<BoardSpace[][]> undoBoard = new ArrayList<BoardSpace[][]>();
		BoardSpace[][] currentBoard;
		loadGame.retrieveModel();
		
		
		redoBoard = loadGame.getMyModel().getRedoBoard();
		undoBoard = loadGame.getMyModel().getUndoBoard();
		currentBoard = loadGame.getMyModel().getCurrentBoard();
		connectFourModel.getGameStateModel().setUndoBoard(undoBoard);
		connectFourModel.getGameStateModel().setRedoBoard(redoBoard);
		connectFourModel.getBoard().setPlayField(currentBoard);
		
		connectFourModel.getBoard().printBoard();		
	}
}