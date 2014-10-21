package artificialIntelegence;
import java.util.Random;
/**
 *File 		RandomStrategy
 *@author Zacharie Gauthier
 * Description:	This is the random strategy implementation for the AI
 * This strategy will return a random avaible move;
 */

public class RandomStrategy implements AIStrategy{
	
	private int[] solution; 
	private int row;
	private int col;
	private Random generator;
	private long seed;
	
	/**
	 * creates a random generator and then will go through the avaible solutions and picks one at random
	 * and then sets the row and the column of that solutions 
	 * @param game source where we will look for avaible moves
	 */
	public void makeMove(Game g) {
	
		newRandom();
		this.solution = g.getAvailableSolutions()[generator.nextInt((g.getAvailableSolutions().length-1))];
		this.row = this.solution[0];
		this.col = this.solution[1];
		
	}
	/**
	 * 
	 * set the generator to a random seed 
	 * 
	 */
	private void newRandom()
	{
		this.seed = System.nanoTime();
		this.generator = new Random(seed);
		
	}
	/**
	 * return the row that the Strategy picked from makeMove()
	 * @return int return this row;
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * return the column that the Strategy picked from makeMove()
	 * @return int return this column;
	 */
	public int getCol() {
		// TODO Auto-generated method stub
		return this.col;
	}


}
