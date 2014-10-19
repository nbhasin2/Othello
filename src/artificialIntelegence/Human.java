package artificialIntelegence;
import java.util.Scanner;
/**
 * 
 * @author Zacharie
 *
 */

public class Human extends Player {
	private static Scanner input = new Scanner(System.in);

	
	
	public Human(String name,Game game){
		super(name,game);
	}
	/**
	 * Playturn() for a human player
	 * will ask the player to make a move by first entering the row then the collumn 
	 * it will call the game to see if the chosen move is a valid one if not it will have to ask 
	 * the player to try again.
	 * If it is valid then the move will be made
	 */
	@Override
	public void playTurn() {
		int row; 
		int col;
		do
		{
			row = input.nextInt() - 1;
			col = input.nextInt() - 1;
			
		}while(!(getGame().isValid(row,col)));
		
		getGame().makeMove(row,col);
		
		
	}
	

}
