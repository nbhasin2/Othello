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

import java.util.ArrayList;
import java.util.Scanner;

import shared.SharedConstants;
//import shared.SharedConstants.PlayableItem;


public class ConnectFourConsole extends GameConsole {

	private gameBoard board;
	private SharedConstants.PlayableItem currentPlayer;
	private static Scanner playerMove= new Scanner(System.in); 
	private SharedConstants.GameStatus currentState;	
	private AIMain aiPlayer;
	private ArrayList<String> directionArray;
	private GameSateModel gameStateModel;
	/*
	 * Constructor for connect four console game
	 */
	public ConnectFourConsole(AIStrategy AIType){
		aiPlayer = new AIMain(AIType);
		board = new gameBoard();
		gameSetup();
	}
	public void playConnectFour(){
		board.printBoard();
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
		if(results[1] == 0){
			System.out.println("DRAW!");
		}
		else if(results[1] == 2){
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
	}
	public void undoBoard()
	{
		
		gameStateModel.addCurrentBoardToRedo(board.makeDeepCopy());
		
		gameStateModel.setCurrentBoard(board.makeDeepCopy());
		board.printBoard();
	}
	
	public void redoBoard()
	{
		gameStateModel.addCurrentBoardToUndo(board.makeDeepCopy());
		
		gameStateModel.setCurrentBoard(board.makeDeepCopy());
		board.printBoard();
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
				if(col == -2){
					System.out.println("Redo");
					redoBoard();
				}
				else if(col == -3){
					System.out.println("Undo");
					undoBoard();
				}
				if(validMove(col)){
					
					gameStateModel.getUndoBoard().add(board.makeDeepCopy());
				}
			}
			else {
				System.out.print("AI " + aiPlayer.getAIType() + " move \n");
				AICoordinates = aiPlayer.makeMove(this);
				col = Integer.parseInt(AICoordinates.split(",")[1]);
			}
	        if(validMove(col)){
	        		int row = 5;
	        		int substituteLevel = (currentPlayer.equals(SharedConstants.PlayableItem.BLACK) ? -2:-1);
	        		moveSet(row,col,substituteLevel);
		        	isValidInput = true;
	        }
	        else if(col < -1){
	        	System.out.println("Action");
	        }
	        else
				System.out.println("Invalid move"); 	
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
		if (col > gameBoard.COLS || col < 0 ){
			return false;
		}
		else{
			int row = 5;
			while(row >=0 ){
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
		for (int col = 0; col < gameBoard.COLS; ++col) {
			if(validMove(col)){
				moves.add("5,"+col); 
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
		for(int row = 0; row<gameBoard.ROWS;row++){
			for(int col = 0;col<gameBoard.COLS;col++){
				int tempScore = evaluateToken(row,col);
				if(tempScore >= 10000){ 
					results[1] = 1;
				}
				else if(tempScore <= -10000 ){
					results[1] = 2;
				}
				score += tempScore;
				//System.out.println(score);
			}
		}
		results[0]= score;
		return results;
	}
	/*Looks at all directions and evaluates the lines direction 
	 * based on function called evaluate line
	 * 
	 * 
	 */
	private int evaluateToken(int row,int col){
		int score = 0;
		for(int dir = 0; dir <8;dir++){
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
		int maxRow = gameBoard.ROWS -1;
		int maxCol = gameBoard.COLS -1;
		int boundRow = row-r;
		int boundCol = col-c;
	
		if(!((boundRow <0 || boundRow >maxRow)||(boundCol <0|| boundCol >maxCol)))
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
		if(combo >= 3){
			return score;
		}
		else{
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
		while(!(board.getPlayField()[row][col].getGamePiece().equals(SharedConstants.PlayableItem.EMPTY)&& row >= 0)){
			row--;
		}
		SharedConstants.PlayableItem move;
		move = (Math.abs(player)%2 == 0 ? SharedConstants.PlayableItem.BLACK : SharedConstants.PlayableItem.WHITE);
		board.getPlayField()[row][col].setGamePiece(move);
		return true;
	}
	
	/*
	 * @see gamemodel.GameConsoleInterface#undoMove(int, int, int)
	 */
	@Override
	public boolean undoMove(int row, int col, int player) {
		if((row >= gameBoard.ROWS || row < 0)||(col >= gameBoard.COLS || col <0 )){
			return false;
		}
		while(!(board.getPlayField()[row][col].getGamePiece().equals(SharedConstants.PlayableItem.EMPTY)) && row >= 0 ){
			row--;
			if(row == -1)
				break;
		}

		if(row == 5)
		{
			return false;
		}
	board.getPlayField()[row+1][col].setGamePiece(SharedConstants.PlayableItem.EMPTY);
		return true;
	}
	
}