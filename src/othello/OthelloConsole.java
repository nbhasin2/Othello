package othello;
import gameai.AIMain;
import gameai.AIStrategy;
import gamemodel.GameConsole;
import gamemodel.GameConsoleInterface;
import gamestate.GameSateModel;
import gameui.Controller;
import gameui.Gameui;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import shared.SharedConstants;


public class OthelloConsole extends  GameConsole {


	private ArrayList<String> tokensChanged = null;
	private ArrayList<String> availableSolutions = null;
	private GameBoard board;
	private SharedConstants.GameStatus currentState;
	private SharedConstants.PlayableItem currentPlayer;
	private AIMain aiPlayer;
	private int  globalCounter; 
	private static Scanner playerMove= new Scanner(System.in); 
	private boolean gameuiMove = false;
	private int gameuiMoveX = -1;
	private int gameuiMoveY = -1;
	private String scoreString = "";
	private int currentScore[];
	private boolean isConsole = true;
	private ArrayList<String> directionArray;
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private GameSateModel gameStateModel;
	
	public OthelloConsole(AIStrategy AIType){

		this(new AIMain(AIType));
	}

	/*
	 * Constructor for othello console game
	 */
	public OthelloConsole(AIMain AIType){

		super();
		gameStateModel = new GameSateModel();
		tokensChanged = new ArrayList<String>();
		globalCounter = 0;
		aiPlayer = AIType;

	}

	/**
	 * @author Nishant
	 * After the constructor is initialized we use gameOthelloInitializer
	 * is mainly used to tell which kind of gui is going to be used i.e.
	 * Swing or Console based. If the controller is empty then we use
	 * console based gui and if the controller is present we use
	 * the gui based. The controller is actually not hooked to the model
	 * but has an observer so that when the board changes it notifies 
	 * the controller to update the gui and thus Othello Console (Model)
	 * doesn't know about the controler.
	 */
	public void gameOthelloInitializer(Controller gamecon)
	{
		if(gamecon!=null)
		{
			isConsole = false;
			addObserver(gamecon);
		}
		board = new GameBoard(gamecon);

		gameSetup();
	}

	public void playOthello(){
		board.printBoard();
		gameStateModel.getUndoBoard().add(board.makeDeepCopy());
		do{ 
			System.out.println("Current Player - "+currentPlayer);
			playerMove(currentPlayer);
			evaluate();
			//Rest the redo board array because now player has made a move and previous undo no longer exists.
//			if(currentPlayer.equals(SharedConstants.PlayableItem.BLACK))
//			{
//				System.out.println("Resetting Redo Board");
//				gameStateModel.resetRedoBoard();
//			}
//			gameStateModel.getUndoBoard().add(board.makeDeepCopy());
			board.printBoard();
			currentPlayer = (currentPlayer == SharedConstants.PlayableItem.BLACK) ? SharedConstants.PlayableItem.WHITE : SharedConstants.PlayableItem.BLACK;
		} while(currentState == SharedConstants.GameStatus.PLAYING);
		winner();
	}
	
	/**
	 * @author nishantbhasin
	 * Undo player move and print board
	 */
	public void undoBoard()
	{
		gameStateModel.addCurrentBoard(board.makeDeepCopy());
		board.printBoard(gameStateModel.popUndoElement());
	}
	
	
	/**
	 * @author nishantbhasin
	 * Redo player move and print board
	 */
	public void redoBoard()
	{
		board.printBoard(gameStateModel.popRedoElement());
	}
	
	/*
	 * Method to setup the initial game with basic item on the board.
	 */
	private void gameSetup(){
		board.boardSetup();

		directionArray = setUpDirectionArray();

		currentPlayer = SharedConstants.PlayableItem.BLACK;//Black goes first
		currentState = SharedConstants.GameStatus.PLAYING;
	}
	/*
	 * This method is used to move the player item
	 */
	private void playerMove(SharedConstants.PlayableItem move){
		boolean isValidInput = false;
		int row=-1;
		int col=-1;
		String coor;
		do {

			if(move == SharedConstants.PlayableItem.BLACK) {
				if(!isConsole)
				{
					int item = 0;
					do{
						item++;
						if(item >= Integer.MAX_VALUE-10000){
							item = 0;
						}
						if(gameuiMove){

							System.out.println("X - " + gameuiMoveX + " -- " + "Y - " + gameuiMoveY);
							row = getGameuiMoveX();
							col = getGameuiMoveY();
						}
					}while(!gameuiMove);
					gameuiMove = false;
				}else{
					System.out.println("Black player move:");
					int[] parsed = consoleParser(playerMove,true);
					row = parsed[0];
					col = parsed[1];
				}
				gameStateModel.getUndoBoard().add(board.makeDeepCopy());
			}
			else{

				System.out.println(aiPlayer.getAIType() + " player move:");
				coor = aiPlayer.makeMove(this);
				//System.out.println("White ai turn:");
				row = Integer.parseInt(coor.split(",")[0]);
				col = Integer.parseInt(coor.split(",")[1]);
				
			}
			int substituteLevel = (move == SharedConstants.PlayableItem.BLACK ? -2:-1);

			if(moveSet(row,col,substituteLevel)){
				isValidInput = true; 
			}
			else if(row == -1 && col == -1){
				System.out.println("No moves available");
				isValidInput = true;
			}	
			else{
				System.out.println("Invalid move");  
			}
		} while (!isValidInput);
		if(isGameOver()){
			currentState = SharedConstants.GameStatus.GAME_END;
		}
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
		setChanged();
		notifyObservers(sb.toString());
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
				if(board.getPlayField()[row][col].gamePiece.equals(SharedConstants.PlayableItem.BLACK))
					blackToken++;
				else if(board.getPlayField()[row][col].gamePiece.equals(SharedConstants.PlayableItem.WHITE))
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
		new ArrayList<>();
		ArrayList<String> validCoordinatesOnly = new ArrayList<String>();
		int resultValidMove = -1;
		if(validCoordinatesAndDirection.size() == 0){
			return resultValidMove;
		}

		for(String s : validCoordinatesAndDirection){
			validCoordinatesOnly.add(s.split(",")[0]+","+s.split(",")[1]);
		}
		String playerMove = row+","+col;

		resultValidMove = validCoordinatesOnly.contains(playerMove) ? 1 : 0;
		return resultValidMove;

	}
	/**
	 * @author Zacharie
	 * depending on the value of the direction, it will go through the and reverse all the tokens
	 * until it reachs a token of a thoken that has the same color othe player 
	 * 
	 * row the head of the line to be reversed
	 * col 
	 * dir cardinal direction of where the line will be changed 
	 * player the color that the tokens will be flipped to
	 * level the depth of the AI, used for the undoMove method
	 * if level is -1 then the move was made by a player
	 */
	private void tokenChangeWithDirection(int row,int col,int dir, SharedConstants.PlayableItem player,int level){
		int r;
		int c;
		String rowAndColMods = directionArray.get(dir);
		r = Integer.parseInt(rowAndColMods.split(",")[0]);
		c = Integer.parseInt(rowAndColMods.split(",")[1]);
		while(!((board.getPlayField()[row+r][col+c].gamePiece.equals(player)))){
			if(level > 0){
				tokensChanged.add((row+r)+","+(col+c)+","+level);
			}
			board.getPlayField()[row+r][col+c].gamePiece = player;
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
	 * row the value of where the a token has been added
	 * col 
	 * player the color of the token being placed
	 * changeSolution the array list of all the players available moves
	 * level the level of the AI used to save the tokens to be changed
	 */
	private void tokenChange(int row,int col,SharedConstants.PlayableItem player,ArrayList<String> changeSolution,int level){
		int dir = -1;
		String sRow = "" + row;
		String sCol = "" + col;

		for(String solution: changeSolution){
			if(sRow.equals(solution.split(",")[0]) && sCol.equals(solution.split(",")[1])){
				dir = Integer.parseInt(solution.split(",")[2]);
				tokenChangeWithDirection(row,col,dir,player,level);

			}
		}

	}

	/**
	 * @author Zacharie
	 * 
	 * @param 
	 * playerPiece is the color of the player that wants to check for his available moves
	 * @return 
	 * an arrayList - String of all the valid moves for the player of playerPiece color
	 * the format of each string is has follows "row,col,dir"
	 * where row is the row value of the avaible move
	 * where col is the col value of the avaible move
	 * where dir is the directions where the its source for reversing the line is coming from 
	 * it is possible for having strings with the same row and col but the direction will be different
	 */
	private ArrayList<String> availableSolutions(SharedConstants.PlayableItem playerPiece) {
		ArrayList<String> temp = new ArrayList<String>();
		int maxRow = GameBoard.ROWS -1;
		int maxCol = GameBoard.COLS -1;	
		int validRow = -1;
		int validCol= -1;
		boolean valid =false;
		for (int row = 0; row < maxRow+1; ++row) {
			for (int col = 0; col < maxCol+1; ++col) {
				if(board.getPlayField()[row][col].gamePiece.equals(playerPiece)){

					/**
					 *    0|1|2 //if the row is == 0 it should not check spots 0,1,2
					 *    7|s|3 //if the col is == 0 it should not check spots 0,6,7
					 *    6|5|4 //if the row is == max it should not check spots 4,5,6
					 *    		//if the col is == max it should not check spots 2,3,4			
					 *    where s is the currently selected space on the board
					 */
					for(int dir = 0; dir < 8; dir++){
						valid =false;
						globalCounter = 1;
						String rowAndColMods = directionArray.get(dir);
						int r = Integer.parseInt(rowAndColMods.split(",")[0]);
						int c = Integer.parseInt(rowAndColMods.split(",")[1]);

						int boundRow =row-r;
						int boundCol = col-c;
						if(!((boundRow <0 || boundRow >maxRow)||(boundCol <0|| boundCol >maxCol)))
						{
							valid = isValid(boundRow,boundCol,dir,playerPiece);
							validRow = row-globalCounter*r;
							validCol = col-globalCounter*c;
							if(valid){
								temp.add(validRow +","+ validCol + "," + dir);
							}
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
	 * @see #availableSolutions(SharedConstants.PlayableItem)
	 * row of the adjacent space of the source token from availableSoltutions;
	 * col "   "     "      "     "  "    "      "     "        "
	 * c if the cardinal direction of where the line is pointing from the source token
	 * if the color of the player piece
	 * @return a boolean if the line is reversible
	 */
	private boolean isValid(int row,int col,int dir,SharedConstants.PlayableItem playerPiece){
		int maxRow = GameBoard.ROWS -1;
		int maxCol = GameBoard.COLS -1;

		globalCounter++;
		if(board.getPlayField()[row][col].gamePiece.equals(SharedConstants.PlayableItem.EMPTY)){
			return false;
		}
		else if(board.getPlayField()[row][col].gamePiece.equals(playerPiece)){
			return false;
		}
		else{
			String rowAndColMods = directionArray.get(dir);
			int r = Integer.parseInt(rowAndColMods.split(",")[0]);
			int c = Integer.parseInt(rowAndColMods.split(",")[1]);
			int boundRow =row-r;
			int boundCol = col-c;
			if(!((boundRow <0 || boundRow >maxRow)||(boundCol <0|| boundCol >maxCol)))
			{
				if(!(board.getPlayField()[boundRow][boundCol].gamePiece.equals(SharedConstants.PlayableItem.EMPTY))){
					return isValid(boundRow,boundCol,dir,playerPiece);
				}
				else{
					return true;
				}
			}
			else
			{
				return false;
			}

		}
	}

	/**
	 * @see gamemodel.GameConsoleInterface#getAvailableSolutions(int)
	 */
	@Override
	public ArrayList<String> getAvailableSolutions(int player) {
		SharedConstants.PlayableItem move;
		move = (player == 0 ? SharedConstants.PlayableItem.BLACK : SharedConstants.PlayableItem.WHITE);
		availableSolutions = availableSolutions(move);
		return availableSolutions;
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
		if((row >= GameBoard.ROWS || row < 0)||(col >= GameBoard.COLS || col <0 )){
			return false;
		}
		SharedConstants.PlayableItem move;
		move = (Math.abs(level)%2 == 0 ? SharedConstants.PlayableItem.BLACK : SharedConstants.PlayableItem.WHITE);
		ArrayList<String> solution = availableSolutions(move);
		if(validMove(row,col,solution) != 1){
			return false;
		}
		else{
			board.getPlayField()[row][col].gamePiece = move;
			board.setCurrentRow(row);
			board.setCurrentCol(col);
			tokenChange(row,col,move,solution,level);

			return true;
		}
	}	
	/**
	 * @see gamemodel.GameConsoleInterface#undoMove(int, int, int)
	 */
	@Override
	public boolean undoMove(int row, int col, int level) {
		if((row >= GameBoard.ROWS || row < 0)||(col >= GameBoard.COLS || col <0 )){
			return false;
		}
		else if(board.getPlayField()[row][col].gamePiece == SharedConstants.PlayableItem.EMPTY){
			return false;
		}
		ArrayList<String> tempChange = new ArrayList<String>((ArrayList<String>)tokensChanged.clone());
		if(tempChange.size() == 0){
			return false;
		}
		SharedConstants.PlayableItem move;
		move = (level%2 == 0 ? SharedConstants.PlayableItem.BLACK : SharedConstants.PlayableItem.WHITE);
		board.getPlayField()[row][col].gamePiece = SharedConstants.PlayableItem.EMPTY;
		//board.printBoard(gameui);


		for(String token:tempChange){
			int currRow = Integer.parseInt(token.split(",")[0]);
			int currCol = Integer.parseInt(token.split(",")[1]);
			int currLevel = Integer.parseInt(token.split(",")[2]);
			if(currLevel == level){
				if(move == SharedConstants.PlayableItem.BLACK){
					board.getPlayField()[currRow][currCol].gamePiece = SharedConstants.PlayableItem.WHITE;
				}
				else{
					board.getPlayField()[currRow][currCol].gamePiece = SharedConstants.PlayableItem.BLACK;
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
		int totalSol = availableSolutions(SharedConstants.PlayableItem.BLACK).size() + availableSolutions(SharedConstants.PlayableItem.WHITE).size(); 
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
	public AIMain getAiPlayer() {
		return aiPlayer;
	}

	/**
	 * @param aiPlayer the aiPlayer to set
	 */
	public void setAiPlayer(AIMain aiPlayer) {
		this.aiPlayer = aiPlayer;
	}
}
