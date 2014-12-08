package gamemodel;

import shared.BoardSpace;
import shared.GameBoard;
import shared.SharedConstants;
import gamestate.GameSateModel;
import gamestate.GameStateRetriever;
import gamestate.GameStateWrter;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

import com.connect.four.ConnectFourConsole;
import com.connect.four.GameBoardC4;

public abstract class GameConsole extends Observable implements GameConsoleInterface {

	private ArrayList<String> directionArray;
	private GameSateModel gameStateModel;
	private GameStateRetriever loadGame;
	private GameStateWrter saveGame;
	public GameConsole() {
	
	}
	/**
	 * will parse the input from the scanner so that the user can not enter somthing other than and int
	 * 
	 * @param in scanner input to read what the user wants to put in the game
	 * @param needTwoInt true if the game requires both a row and column as an input
	 * @return return an int array containing the row and column that user inputs in
	 * will be a negative number if what the user is entering a none interger.
	 * depending on the letter, entered will return a specific code number
	 * 
	 */
	public int[] consoleParser(Scanner in,boolean needTwoInt){
		int[] parsed = new int[2];
		int row;
		int col;
		String line;
		if((in.hasNextInt())){
			row = in.nextInt() - 1;
			
			if(!(needTwoInt)){
				col = SharedConstants.INVALID;
			}
			else if((in.hasNextInt())){
				col = in.nextInt() - 1;
				in.nextLine();
			}
			else{
				col = SharedConstants.INVALID;
			}
			if(row < 0 || col < 0){
				row = SharedConstants.INVALID;
				col = row;
			}
		}
		else{
			line = in.nextLine();
			
			if(line.equals("R")){
				row = SharedConstants.REDO;
				col = row;
			}
			else if(line.equals("U")){
				row = SharedConstants.UNDO;
				col = row;
			}
			else if(line.equals("S")){
				row = SharedConstants.SAVE;
				col = row;
			}
			else if(line.equals("L")){
				row = SharedConstants.LOAD;
				col = row;
			}
			else if(line.equals("H")){
				row = SharedConstants.HELP;
				col = row;
			}
			else{
				row = SharedConstants.INVALID;
				col = row;
			}
		}
		parsed[0] = row;
		parsed[1] = col;
		return parsed;	
	}
	/**
	 * Sets up an array of coordinates that are used to check all for all directions of an array
	 * where (1,-1) 1 is the row modifier and -1 is the column modifier
	 * 
	 */
	public ArrayList<String> setUpDirectionArray() {
		directionArray = new ArrayList<String>();
		directionArray.add("1,1");
		directionArray.add("1,0");
		directionArray.add("1,-1");
		directionArray.add("0,-1");
		directionArray.add("-1,-1");
		directionArray.add("-1,0");
		directionArray.add("-1,1");
		directionArray.add("0,1");	
		return directionArray;
	}
	/**
	 * @see gamemodel.GameConsoleInterface#isGameOver()
	 */
	@Override
	public boolean isGameOver() {
		int results[] = evaluate();
		if(results[1] != SharedConstants.GAMENOWIN){
			return true;
		}
		else if(getAvailableSolutions(-1).size() == SharedConstants.EMPTY){
			return true;
		}
		return false;
	}
	
	public GameSateModel getGameStateModel() {
		return gameStateModel;
	}

	public void setGameStateModel(GameSateModel gameStateModel) {
		this.gameStateModel = gameStateModel;
	}
	
	/**
	 * 
	 * Will print he availabe commands that the user can enter
	 * 
	 * @param help 
	 * if help is true then it was request by help 
	 * 
	 * 
	 */
	public void  printCommands(boolean help){
		if(help){
			System.out.println("Help:");
		}
		System.out.println("Commands(Action -- Letter to enter)"
				+ "\nRedo -- 'R'"
				+ "\nUndo -- 'U'"
				+ "\nSave -- 'S'"
				+ "\nLoad -- 'L'");
		
	}
	/**
	 * prints the help message when ever a user enters and invalid entry
	 */
	public void printHelp(){
		System.out.println("Invalid entry, if you for looking for help on commands enter 'H',\nKeep in mind that the commands are case sensitive.");
	}
}
