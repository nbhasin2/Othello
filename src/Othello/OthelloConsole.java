package Othello;
import gameai.AI;
import gamemodel.GameConsoleInterface;
import gameui.Gameui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import shared.SharedConstants;


public class OthelloConsole extends GameConsoleInterface {
	
	
	private ArrayList<String> tokensChanged = null;
	private ArrayList<String> availableSolutions = null;
	private GameBoard board;
	private GameStatus currentState;
	private PlayableItem currentPlayer;
	private AI aiPlayer;
	private int countNO;
	private int  globalCounter; 
	private static Scanner playerMove= new Scanner(System.in); 
	private Gameui gameui;
	private static int gridSize = 16; // Total number of 	
	private boolean gameuiMove = false;
	private int gameuiMoveX = -1;
	private int gameuiMoveY = -1;
	private String scoreString = "";
	private int currentScore[];
	
	public OthelloConsole(String AIType){
		this(new AI(AIType));
	}

	/*
	 * Constructor for othello console game
	 */
	public OthelloConsole(AI AIType){

		super();
		gameui = new Gameui(this);
		gameui.initializeGrid();
		tokensChanged = new ArrayList<String>();
		globalCounter = 0;
		countNO = 0;
		aiPlayer = AIType;
		board = new GameBoard();
		gameSetup();
		
		
	}
	public void playOthello(){
		board.printBoard(gameui);
		do{ 
			playerMove(currentPlayer);
			board.printBoard(gameui);
			currentPlayer = (currentPlayer == PlayableItem.BLACK) ? PlayableItem.WHITE : PlayableItem.BLACK;
		} while(currentState == GameStatus.PLAYING);
		winner();
	}
	
	/*
	 * Method to setup the initial game with basic item on the board.
	 */
	private void gameSetup(){
		board.boardSetup();
		currentPlayer = PlayableItem.BLACK;//Black goes first
		currentState = GameStatus.PLAYING;
	}
	
	/*
	 * This method is used to move the player item
	 */
	private void playerMove(PlayableItem move){
		boolean isValidInput = false;
		int row=-1;
	    int col=-1;
	    int validity;
	    
	    String coor;
		do {
			
			availableSolutions = availableSolutions(move);
			//System.out.println(availableSolutions);
			validity = validMove(-1,-1,availableSolutions);
			if(validity == -1){
				//System.out.print("No avaible moves switch to other player\n");
				isValidInput = true;
				countNO++;
				if(countNO ==2) currentState = GameStatus.GAME_END;
			}
			else{
				if(move == PlayableItem.BLACK) {
					int item = 0;
					//System.out.print("Enter your move player Black\n"); 
					do{
						item++;
						if(item >= Integer.MAX_VALUE-10000){
							item = 0;
							//System.out.print(gameuiMove +"\n"); 
						}
						if(gameuiMove){
							
							System.out.println("X - " + gameuiMoveX + " -- " + "Y - " + gameuiMoveY);
							row = getGameuiMoveX();
							col = getGameuiMoveY();
						}
						else{
	//						System.out.println("Skipping --");
	//						row = playerMove.nextInt() -1;
	//						col = playerMove.nextInt() -1;
						}
					}while(!gameuiMove);
					gameuiMove = false;
						
				}
				else{
					coor = aiPlayer.makeMove(this);
					//System.out.println("White ai turn:");
					row = Integer.parseInt(coor.split(",")[0]);
					col = Integer.parseInt(coor.split(",")[1]);
				}
				availableSolutions = availableSolutions(move);
				validity = validMove(row,col,availableSolutions);
				{ 
					countNO = 0;
			        if(validity == 1){
			        	board.playField[row][col].gamePiece = move;
			        	board.currentRow = row;
			        	board.currentCol = col;
			        	tokenChange(row,col,move,availableSolutions,-1);
			        	isValidInput = true; }
			         else {
			        	System.out.println("Invalid move");
				        }
					}
				}
		} while (!isValidInput);
	}
	/**
	 * @author Zacharie
	 * 
	 * will print to the console the winner of the game and the tokens 
	 * that the black player and white player had when the game is over
	 */
	private void winner(){
		
		int[] results = evaluate();
		String scoreStr = "Black Count: "+ results[2] + " White Count: " + results[1]+"\n";
		StringBuilder sb = new StringBuilder(scoreStr);
		System.out.print(scoreStr);
		if(results[0] == 0){
			sb.insert(0, "DRAW! - ");
			System.out.println("DRAW!");
		}
		else if(results[0] < 0){
			sb.insert(0, "Black Wins! - ");
			System.out.println("Black Wins!");
		}
		else{
			sb.insert(0, "White Wins! - ");
			System.out.println("White Wins!");
		}
		setScoreString(sb.toString());
		gameui.showPopup(scoreString);
	}
	/**
	 * Will return a number depending on the number of tokens a positive number means that there are more white tokens a black number 
	 * int score[0] - who wins 
	 * int score[1] - white
	 * int score[2] - black
	 * return the "score of the board."
	 *
	 * @see gamemodel.GameConsoleInterface#evaluate()
	 */
	@Override
	public int[] evaluate(){
		int whiteToken = 0;
		int blackToken = 0;
		int score;
		int[]  results = new int[3];
		for(int row = 0; row < GameBoard.ROWS ;row++){
			for(int col = 0; col < GameBoard.COLS ;col++){
				if(board.playField[row][col].gamePiece.equals(PlayableItem.BLACK))
					blackToken++;
				else if(board.playField[row][col].gamePiece.equals(PlayableItem.WHITE))
					whiteToken++;
			}
		}
		//System.out.println(whiteToken);
		score = whiteToken - blackToken;
		
		results[0] = score;
		results[1] = whiteToken;
		results[2] = blackToken;
		
		setCurrentScore(results);
		return results;
	}
	
	/*
	 * This method is used to checking whether the player move is valid or not.
	 */
	private int validMove(int row, int col, ArrayList<String> validCoordinatesAndDirection){
		ArrayList<String> splitStr = new ArrayList<>();
		ArrayList<String> validCoordinatesOnly = new ArrayList<String>();
		int resultValidMove = -1;
		if(validCoordinatesAndDirection.size() == 0){
			return resultValidMove;
		}
		//System.out.println(validCoordinatesAndDirection);
	
		for(String s : validCoordinatesAndDirection){
			validCoordinatesOnly.add(s.split(",")[0]+","+s.split(",")[1]);
		}
		//System.out.println(validCoordinatesOnly);
		String playerMove = row+","+col;
		
		resultValidMove = validCoordinatesOnly.contains(playerMove) ? 1 : 0;
		//System.out.println(playerMove + "--" + validCoordinatesOnly.contains(playerMove)+"--"+resultValidMove);
		return resultValidMove;
		
	}
	/**
	 * @author Zacharie
	 * depending on the value of the direction, it will go through the and reverse all the tokens
	 * until it reachs a token of a thoken that has the same color othe player 
	 * 
	 * @param row the head of the line to be reversed
	 * @param col 
	 * @param dir cardinal direction of where the line will be changed 
	 * @param player the color that the tokens will be flipped to
	 * @param level the depth of the AI, used for the undoMove method
	 * if level is -1 then the move was made by a player
	 */
	private void tokenChangeWithDirection(int row,int col,int dir,PlayableItem player,int level){
		int r;
		int c;
		
		if(dir == 0){
			r= 1;
			c= 1;
		}
		else if(dir == 1 ){
			r=1;
			c=0;
		}
		else if(dir == 2 ){
			r=1; 
			c=-1;
		}
		else if(dir == 3 ){
			r=0;
			c=-1;
		}
		else if(dir == 4 ){
			r=-1;
			c=-1;
		}
		else if(dir == 5 ){
			r=-1;
			c=0;
		}
		else if(dir == 6){
			r=-1;
			c=+1;
		}
		else if(dir == 7){
			r=0;
			c=1;
		}
		else{
			return;
		}
		while(!((board.playField[row+r][col+c].gamePiece.equals(player)))){
			if(level > 0){
				tokensChanged.add((row+r)+","+(col+c)+","+level);
			}
			board.playField[row+r][col+c].gamePiece = player;
			//board.printBoard(gameui);
			row +=r;
			col +=c;
		}
	}
	/**
	 * @author Zacharie
	 * iterates through the avaible moves, 
	 * when it finds it's move in the array list of moves it will parse the direction of that move
	 * the direction points to where the line should be reversed,
	 * it is possible that a move has many lines to be reversed
	 * 
	 * @param row the value of where the a token has been added
	 * @param col 
	 * @param player the color of the token being placed
	 * @param changeSolution the array list of all the players available moves
	 * @param level the level of the AI used to save the tokens to be changed
	 */
	private void tokenChange(int row,int col,PlayableItem player,ArrayList<String> changeSolution,int level){
		int dir = -1;
		String sRow = "" + row;
		String sCol = "" + col;
		
		for(String solution: changeSolution){
			if(sRow.equals(solution.split(",")[0]) && sCol.equals(solution.split(",")[1])){
				dir = Integer.parseInt(solution.split(",")[2]);
				tokenChangeWithDirection(row,col,dir,player,level);
				//System.out.println("Tokens: " +tokensChanged);
				//board.printBoard(gameui);
			}
		}
		
	}
	
	/**
	 * @author Zacharie
	 * 
	 * @param playerPiece is the color of the player that wants to check for his available moves
	 * @return an arrayList<String> of all the valid moves for the player of playerPiece color
	 * the format of each string is has follows "row,col,dir"
	 * where row is the row value of the avaible move
	 * where col is the col value of the avaible move
	 * where dir is the directions where the its source for reversing the line is coming from 
	 * it is possible for having strings with the same row and col but the direction will be different
	 */
	private ArrayList<String> availableSolutions(PlayableItem playerPiece) {
		ArrayList<String> temp = new ArrayList<String>();
		int maxRow = GameBoard.ROWS -1;
		int maxCol = GameBoard.COLS -1;	
		int validRow = -1;
		int validCol= -1;
		boolean valid =false;
		for (int row = 0; row < maxRow+1; ++row) {
			for (int col = 0; col < maxCol+1; ++col) {
				if(board.playField[row][col].gamePiece.equals(playerPiece)){
					
					/**
					 *    0|1|2 //if the row is == 0 it should not check spots 0,1,2
					 *    7|s|3 //if the col is == 0 it should not check spots 0,6,7
					 *    6|5|4 //if the row is == max it should not check spots 4,5,6
					 *    		//if the col is == max it should not check spots 2,3,4			
					 *    where s is the currently selected space on the board
					 */
					for(int c = 0; c < 8; c++){
						valid =false;
						globalCounter = 1;
						if(c == 0 && !(row <= 0 || col <=0)){
							valid = isValid(row-1,col-1,c,playerPiece);
							validRow = row-globalCounter;
							validCol = col-globalCounter;
						}
						else if(c == 1 && !(row <= 0)){
							valid = isValid(row-1,col,c,playerPiece);
							validRow = row-globalCounter;
							validCol = col;
						}
						else if(c == 2 && !(row <= 0 || col >= maxCol)){
							valid = isValid(row-1,col+1,c,playerPiece);
							validRow = row-globalCounter;
							validCol = col+globalCounter;
						}
						else if(c == 3 && !(col >= maxCol)){
							valid = isValid(row,col+1,c,playerPiece);
							validRow = row;
							validCol = col+globalCounter;
						}
						else if(c == 4 && !(row >= maxRow || col >= maxCol)){
							valid = isValid(row+1,col+1,c,playerPiece);
							validRow = row+globalCounter;
							validCol = col+globalCounter;
						}
						else if(c == 5 && !(row >= maxRow)){
							valid = isValid(row+1,col,c,playerPiece);
							validRow = row+globalCounter;
							validCol = col;
						}
						else if(c == 6 && !(row >= maxRow || col <= 0)){
							valid = isValid(row+1,col-1,c,playerPiece);
							validRow = row+globalCounter;
							validCol = col-globalCounter;
						}
						else if(c == 7 && !(col <= 0)){
							valid = isValid(row,col-1,c,playerPiece);
							validRow = row;
							validCol = col-globalCounter;
						}
						if(valid){
							//System.out.println("Root Row-" + row + " Root Col-" + col + " Valid Row - " + validRow + " Valid Col - " + validCol+" Direction-" + c);
							temp.add(validRow +","+ validCol + "," + c);
						}
					}
				}
			}
		}
		return temp;
	}
	
	/**
	 * @author Zacharie
	 * 
	 * a recursive function that will go in a line on the board to find out if the line is 
	 * reversible by the players moves
	 * 
	 * @see #availableSolutions(PlayableItem)
	 * @param row of the adjacent space of the source token from availableSoltutions;
	 * @param col "   "     "      "     "  "    "      "     "        "
	 * @param c if the cardinal direction of where the line is pointing from the source token
	 * @param if the color of the player piece
	 * @return a boolean if the line is reversible
	 */
	private boolean isValid(int row,int col,int c,PlayableItem playerPiece){
		int maxRow = GameBoard.ROWS -1;
		int maxCol = GameBoard.COLS -1;
		
		globalCounter++;
		if(board.playField[row][col].gamePiece.equals(PlayableItem.EMPTY)){
			return false;
		}
		else if(board.playField[row][col].gamePiece.equals(playerPiece)){
			return false;
		}
		else{
			if(c == 0 && !(row <= 0 || col <=0)){
				if(!(board.playField[row-1][col-1].gamePiece.equals(PlayableItem.EMPTY))){
					return isValid(row-1,col-1,c,playerPiece);
				}
				else{
					return true;
				}
			}
			else if(c == 1 && !(row <= 0)){
				if(!(board.playField[row-1][col].gamePiece.equals(PlayableItem.EMPTY)))
				{
					return isValid(row-1,col,c,playerPiece);
				}
				else{
					return true;
				}
			}
			else if(c == 2 && !(row <= 0 || col >= maxCol)){
				if(!(board.playField[row-1][col+1].gamePiece.equals(PlayableItem.EMPTY))){
					return isValid(row-1,col+1,c,playerPiece);
				}
				else{
					return true;
				}
			}
			else if(c == 3 && !(col >= maxCol)){
				if(!(board.playField[row][col+1].gamePiece.equals(PlayableItem.EMPTY))){
					return isValid(row,col+1,c,playerPiece);
				}
				else{
					return true;
				}
			}
			else if(c == 4 && !(row >= maxRow || col >= maxCol)){
				if(!(board.playField[row+1][col+1].gamePiece.equals(PlayableItem.EMPTY))){
					return isValid(row+1,col+1,c,playerPiece);
				}
				else{
					return true;
				}
			}
			else if(c == 5 && !(row >= maxRow)){
				if(!(board.playField[row+1][col].gamePiece.equals(PlayableItem.EMPTY))){
					return isValid(row+1,col,c,playerPiece);
				}
				else{
					return true;
				}
			}
			else if(c == 6 && !(row >= maxRow || col <= 0)){
				if(!(board.playField[row+1][col-1].gamePiece.equals(PlayableItem.EMPTY))){
					return isValid(row+1,col-1,c,playerPiece);
				}
				else{
					return true;
				}
			}
			else if(c == 7 && !(col <= 0)){
				if(!(board.playField[row][col-1].gamePiece.equals(PlayableItem.EMPTY))){
					return isValid(row,col-1,c,playerPiece);
				}
				else{
					return true;
				}
			}
			else{
				return false;
			}
		}
	}
	
	
	/**
	 * @see gamemodel.GameConsoleInterface#getAvailableSolutions(int)
	 */
	@Override
	public ArrayList<String> getAvailableSolutions(int player) {
		PlayableItem move;
		move = (player == 0 ? PlayableItem.BLACK : PlayableItem.WHITE);
		availableSolutions = availableSolutions(move);
		return availableSolutions;
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
	/**
	 * @see gamemodel.GameConsoleInterface#moveSet(int, int, int)
	 */
	@Override
	public boolean moveSet(int row, int col, int level) {
		if((row >= board.ROWS || row < 0)||(col >= board.COLS || col <0 )){
			return false;
		}
		PlayableItem move;
		move = (level%2 == 0 ? PlayableItem.BLACK : PlayableItem.WHITE);
		ArrayList<String> solution = availableSolutions(move);
		if(validMove(row,col,solution) != 1){
			return false;
		}
		board.playField[row][col].gamePiece = move;
		//board.printBoard(gameui);
		board.currentRow = row;
    	board.currentCol = col;
    	tokenChange(row,col,move,solution,level);
    	//board.printBoard(gameui);
    	//System.out.println("\n");
    	return true;
	}	
	/**
	 * @see gamemodel.GameConsoleInterface#undoMove(int, int, int)
	 */
	@Override
	public boolean undoMove(int row, int col, int level) {
		if((row >= board.ROWS || row < 0)||(col >= board.COLS || col <0 )){
			return false;
		}
		else if(board.playField[row][col].gamePiece == PlayableItem.EMPTY){
			return false;
		}
		ArrayList<String> tempChange = new ArrayList<String>(((ArrayList<String>)tokensChanged.clone()));
		if(tempChange.size() == 0){
			return false;
		}
		PlayableItem move;
		move = (level%2 == 0 ? PlayableItem.BLACK : PlayableItem.WHITE);
		board.playField[row][col].gamePiece = PlayableItem.EMPTY;
		//board.printBoard(gameui);
		
		
		for(String token:tempChange){
			int currRow = Integer.parseInt(token.split(",")[0]);
	    	int currCol = Integer.parseInt(token.split(",")[1]);
	    	int currLevel = Integer.parseInt(token.split(",")[2]);
	    	if(currLevel == level){
	    		if(move == PlayableItem.BLACK){
		    		board.playField[currRow][currCol].gamePiece = PlayableItem.WHITE;
		    	}
		    	else{
		    		board.playField[currRow][currCol].gamePiece = PlayableItem.BLACK;
		    	}
	    		//board.printBoard(gameui);
		    	tokensChanged.remove(token);
		    }
		}
		return true;
	}
	/**
	 * @see gamemodel.GameConsoleInterface#isGameOver()
	 */
	@Override
	public boolean isGameOver() {
		
		int totalSol = availableSolutions(PlayableItem.BLACK).size() + availableSolutions(PlayableItem.WHITE).size(); 
		if(totalSol == 0)
			return true;
		else
			return false;
	}

	public String getScoreString() {
		return scoreString;
	}

	public void setScoreString(String scoreString) {
		this.scoreString = scoreString;
	}

	/**
	 * @return the currentScore
	 */
	public int[] getCurrentScore() {
		return currentScore;
	}

	/**
	 * @param currentScore the currentScore to set
	 */
	public void setCurrentScore(int currentScore[]) {
		this.currentScore = currentScore;
	}

	/**
	 * @return the aiPlayer
	 */
	public AI getAiPlayer() {
		return aiPlayer;
	}

	/**
	 * @param aiPlayer the aiPlayer to set
	 */
	public void setAiPlayer(AI aiPlayer) {
		this.aiPlayer = aiPlayer;
	}
}
