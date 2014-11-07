package testing;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Before;
import org.junit.Test;

import Shared.SharedConstants;
import TicTacToeConsole.TicTacToeConsole;

public class TicTacToeConsoleTest {
	
	TicTacToeConsole ticTacGame;
	
	@Before 
	public void setUp()
	{

		ByteArrayInputStream in = new ByteArrayInputStream("X".getBytes());
		System.setIn(in);
		ticTacGame = new TicTacToeConsole(SharedConstants.AIRandom);		
		

	}
	@Test
	public void testGetAvailableSolutions() {
		assertEquals(9,ticTacGame.getAvailableSolutions(0).size());
	}

	@Test
	public void testEvaluate() {
		fail("Not yet implemented");
	}

	@Test
	public void testMoveSet() {
		fail("Not yet implemented");
	}

	@Test
	public void testUndoMove() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsGameOver() {
		fail("Not yet implemented");
	}

	@Test
	public void testEvaluateLine() {
		fail("Not yet implemented");
	}

}
