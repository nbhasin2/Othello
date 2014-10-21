package artificialIntelegence;
/**
 * 
 * 
 * @author Zacharie
 * MinMax strategy is not complete
 */
public  class MinMaxStrategy implements AIStrategy{
	
	private int row;
	private int col;
	private int minMax;
	
	public void makeMove(Game g) {
	
		
	}
	public int[] miniMax(Game g,boolean player)
	{
		
		Game tempGame = g;
		int[][] tempAvailableSoltions = tempGame.getAvailableSolutions();
		int bestScore = tempGame.getMaxScore();
		int currentScore;
		int bestRow = -1;
		int bestCol = -1;
		
		if(tempAvailableSoltions.length == 0)
		{
			bestScore = tempGame.evaluate();
		}
		if(player)
		{
			for(int[] move : tempAvailableSoltions )
			{
				
			}
		}
		return new int[] {bestScore,bestRow,bestCol};
	}

	public int getRow() {
		
		return this.row;
	}

	public int getCol() {
		
		return this.col;
	}

}
