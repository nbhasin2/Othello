package GameModel;

import java.util.ArrayList;

public class DummyConsole extends GameConsoleInterface {
	private ArrayList<String> solutions;
	
	public DummyConsole(ArrayList<String> solutions) {
		this.solutions = solutions;
	}
	
	@Override
	public ArrayList<String> getAvailableSolutions(int player) {
		// TODO Auto-generated method stub
		return solutions;
	}

	@Override
	public int[] evaluate() {
		int[] results = new int[1];
		
		results[0] = 1;
		return  results;
	}

	@Override
	public void moveSet(int row, int col, int player) {
		
	}

	@Override
	public void undoMove(int row, int col, int player) {
		
	}

	@Override
	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

}
