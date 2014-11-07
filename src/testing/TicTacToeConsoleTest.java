package testing;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import Shared.SharedConstants;
import TicTacToeConsole.TicTacToeConsole;

public class TicTacToeConsoleTest {
	
	private TicTacToeConsole ticTacGame;
	private String data;
	private InputStream stdin;
	private int testRow;
	private int testCol;
	private String testCoor;
	@Before 
	public void setUp()
	{
		data = "X\r";
		stdin = System.in;
		try{
			System.setIn(new ByteArrayInputStream(data.getBytes()));
		
			Scanner scanner = new Scanner(System.in);
			ticTacGame = new TicTacToeConsole(SharedConstants.AIRandom);		
		}
		finally{
			System.setIn(stdin);
		}	
	}
	@Test
	public void testGetAvailableSolutions() {
		
		ticTacGame.moveSet(0, 0, 0);
		assertFalse(ticTacGame.getAvailableSolutions(0).contains("0,0"));
		ticTacGame.moveSet(1, 2, 1);
		assertFalse((ticTacGame.getAvailableSolutions(0).contains("0,0") || ticTacGame.getAvailableSolutions(0).contains("1,2")));
		testCoor = ticTacGame.getAvailableSolutions(0).get(0);
		testRow = Integer.parseInt(testCoor.split(",")[0]);
		testCol = Integer.parseInt(testCoor.split(",")[1]);
		assertTrue(ticTacGame.moveSet(testRow, testCol, 0));
	}

	@Test
	public void testEvaluate() {
		assertEquals(8,ticTacGame.evaluate()[0]);//score for empty board
		ticTacGame.moveSet(0, 0, 0);
		ticTacGame.moveSet(0, 1, 1);
		ticTacGame.moveSet(0, 2, 0);
		assertEquals(-28,ticTacGame.evaluate()[0]);
		ticTacGame.moveSet(1, 1, 1);
		assertEquals(91,ticTacGame.evaluate()[0]);
	}

	@Test
	public void testMoveSet() {
		assertFalse(ticTacGame.moveSet(5, 0,0));
		assertFalse(ticTacGame.moveSet(-1, 0,0));
		assertFalse(ticTacGame.moveSet(0, 5,0));
		assertFalse(ticTacGame.moveSet(0, -1,0));
		assertFalse(ticTacGame.moveSet(5,-1,0));
		assertEquals(9,ticTacGame.getAvailableSolutions(0).size());
		assertTrue(ticTacGame.moveSet(1,1,0));
		assertTrue(ticTacGame.moveSet(2, 2, 1));
		assertFalse(ticTacGame.moveSet(1, 1, 0));
		
	}

	@Test
	public void testUndoMove() {
		ticTacGame.moveSet(1,1,0);
		assertNotEquals(9,ticTacGame.getAvailableSolutions(0).size());
		assertFalse(ticTacGame.undoMove(6, 0,0));
		assertFalse(ticTacGame.undoMove(-2, 0,0));
		assertFalse(ticTacGame.undoMove(0, 4,0));
		assertFalse(ticTacGame.undoMove(0, -2,0));
		assertFalse(ticTacGame.undoMove(-7,7,0));
		assertFalse(ticTacGame.undoMove(0,0,0));
		assertTrue(ticTacGame.undoMove(1, 1, 0));
		assertEquals(9,ticTacGame.getAvailableSolutions(0).size());
		
	}

	@Test
	public void testIsGameOver() {
		assertFalse(ticTacGame.isGameOver());
		ticTacGame.moveSet(0, 0, 0);
		ticTacGame.moveSet(0, 1, 0);
		ticTacGame.moveSet(0, 2, 0);
		assertTrue(ticTacGame.isGameOver());
		ticTacGame.undoMove(0, 0, 0);
		ticTacGame.undoMove(0, 1, 0);
		ticTacGame.undoMove(0, 2, 0);
		assertFalse(ticTacGame.isGameOver());
		ticTacGame.moveSet(0, 0, 1);
		ticTacGame.moveSet(1, 1, 1);
		ticTacGame.moveSet(2, 2, 1);
		assertTrue(ticTacGame.isGameOver());
		ticTacGame.undoMove(0, 0, 1);
		ticTacGame.undoMove(1, 1, 1);
		ticTacGame.undoMove(2, 2, 1);
		assertFalse(ticTacGame.isGameOver());
		ticTacGame.moveSet(0, 0, 1);
		ticTacGame.moveSet(1, 0, 0);
		ticTacGame.moveSet(2, 0, 1);
		assertFalse(ticTacGame.isGameOver());
		ticTacGame.undoMove(0, 0, 1);
		ticTacGame.undoMove(1, 0, 0);
		ticTacGame.undoMove(2, 0, 1);
		assertFalse(ticTacGame.isGameOver());
	}


}
