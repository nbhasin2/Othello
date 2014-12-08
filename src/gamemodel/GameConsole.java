package gamemodel;

import shared.SharedConstants;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

public abstract class GameConsole extends Observable implements GameConsoleInterface {

	private ArrayList<String> directionArray;
	public GameConsole() {
	
	}
	/**
	 * will parse the input from the scanner so that the user can not enter somthing other than and int
	 * 
	 * @param in scanner input to read what the user wants to put in the game
	 * @param needTwoInt true if the game requires both a row and column as an input
	 * @return return an int array containing the row and column that user inputs in
	 * will be -1 if what the user tried to enter is not an int;
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
	
}
