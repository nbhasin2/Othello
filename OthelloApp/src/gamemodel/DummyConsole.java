package gamemodel;

import java.util.ArrayList;

public class DummyConsole implements GameConsoleInterface {
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
	public boolean moveSet(int row, int col, int player) {
		return false;
	}

	@Override
	public boolean undoMove(int row, int col, int player) {
		return false;
	}

	@Override
	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

}
