package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gameai.AIMinimax;

import org.junit.Before;
import org.junit.Test;

import shared.SharedConstants;

import com.connect.four.ConnectFourConsole;

public class Connect4Test {
	private ConnectFourConsole connect4;
	private int testRow;
	private int testCol;
	private String testCoor;
	private static int DEFAULT_ROW =5;
	
	@Before 
	public void setUp() {
		connect4 = new ConnectFourConsole(new AIMinimax());
	}
	/**
	 * 
	 * test the get avainble solution and that make a move by picking a move from that method 
	 * is a valid
	 */
	@Test
	public void testGetAvailableSolutions() {
		
		testCoor = connect4.getAvailableSolutions(0).get(0);
		testRow = Integer.parseInt(testCoor.split(",")[0]);
		testCol = Integer.parseInt(testCoor.split(",")[1]);
		assertTrue(connect4.moveSet(testRow, testCol, 0));
		
	}
	/**
	 * tests the evaulate method of connect 4 test results should 
	 * be depended on what moves were made, and having negative score for a human player
	 * and positive score on a aiplayer
	 */
	@Test
	public void testEvaluate() {
		//testing for an empty board;
		assertEquals(0,connect4.evaluate()[0]);
		assertEquals(0,connect4.evaluate()[1]);
		connect4.moveSet(DEFAULT_ROW, 0, 0);
		//score should be in favour of black(negative score)
		assertEquals(-30,connect4.evaluate()[0]);
		//score should be in equal 
		connect4.moveSet(DEFAULT_ROW, 6, 1);
		assertEquals(0,connect4.evaluate()[0]);
		assertEquals(0,connect4.evaluate()[1]);
		//score should be in favaor of black(negative score)
		connect4.moveSet(DEFAULT_ROW, 1, 0);
		assertEquals(-120,connect4.evaluate()[0]);
	
		connect4.moveSet(DEFAULT_ROW, 5, 1);
		connect4.moveSet(DEFAULT_ROW, 4, 1);
		//score should be in favaor of white(positive score)
		assertEquals(1020,connect4.evaluate()[0]);
		
	}
	/**
	 * tests the functionality of moveset
	 * first it will test for the move that  are out of range
	 * then it will test for moves that should be valid
	 * then it will test for moves that should no longer be valid
	 */
	@Test
	public void testMoveSet() {
		//moves that are out of range
		assertFalse(connect4.moveSet(DEFAULT_ROW, 10,0));
		assertFalse(connect4.moveSet(DEFAULT_ROW, -1,0));
		assertFalse(connect4.moveSet(DEFAULT_ROW,-32,0));
		assertFalse(connect4.moveSet(DEFAULT_ROW, 8, 0));
		//moves that should be valid
		assertTrue(connect4.moveSet(DEFAULT_ROW, 3, 0));
		assertTrue(connect4.moveSet(DEFAULT_ROW, 4, 1));
		assertTrue(connect4.moveSet(DEFAULT_ROW, 5, 0));
		assertTrue(connect4.moveSet(DEFAULT_ROW, 2, 1));
		//now all the moves should no longer be valid
		assertTrue(connect4.moveSet(DEFAULT_ROW, 3, 0));
		assertTrue(connect4.moveSet(DEFAULT_ROW, 4, 1));
		assertTrue(connect4.moveSet(DEFAULT_ROW, 5, 0));
		assertTrue(connect4.moveSet(DEFAULT_ROW, 2, 1));
		setUp();
		connect4.moveSet(DEFAULT_ROW, 0, 0);
		connect4.moveSet(DEFAULT_ROW, 0, 1);
		connect4.moveSet(DEFAULT_ROW, 0, 0);
		connect4.moveSet(DEFAULT_ROW, 0, 1);
		connect4.moveSet(DEFAULT_ROW, 0, 0);
		connect4.moveSet(DEFAULT_ROW, 0, 1);
		assertFalse(connect4.moveSet(DEFAULT_ROW, 0, 0));
		assertFalse(connect4.moveSet(DEFAULT_ROW, 0, 1));
	}
	/**
	 * Test to try to undo moves
	 * first will test to undo moves that are out of range
	 * then it will test for moves that are already empty
	 * then it will test to aussure that a move made can be removed
	 * then it will test to ausser that removed moves can not be removed again(because they are empty)
	 */
	@Test
	public void testUndoMove() {
		//Testing out of range rows and cols
		assertFalse(connect4.undoMove(DEFAULT_ROW, 0, 0));
		assertFalse(connect4.undoMove(DEFAULT_ROW, 0, 0));
		assertFalse(connect4.undoMove(DEFAULT_ROW, 8, 0));
		assertFalse(connect4.undoMove(DEFAULT_ROW,-38, 0));
		assertFalse(connect4.undoMove(DEFAULT_ROW,10, 0));
		//Testing out the undo of places that are already empty been placed 
		assertFalse(connect4.undoMove(DEFAULT_ROW, 1, 0));
		assertFalse(connect4.undoMove(DEFAULT_ROW, 7, 0));
		assertFalse(connect4.undoMove(DEFAULT_ROW, 0, 0));
		//Testing out the undo of moves that have been set 
		testCoor = connect4.getAvailableSolutions(0).get(0);
		testRow = Integer.parseInt(testCoor.split(",")[0]);
		testCol = Integer.parseInt(testCoor.split(",")[1]);
		connect4.moveSet(testRow, testCol, 4);
		assertTrue(connect4.undoMove(testRow, testCol, 4));
		testCoor = connect4.getAvailableSolutions(0).get(2);
		testRow = Integer.parseInt(testCoor.split(",")[0]);
		testCol = Integer.parseInt(testCoor.split(",")[1]);
		connect4.moveSet(testRow, testCol, 2);
		assertTrue(connect4.undoMove(DEFAULT_ROW, testCol, 2));
		//should not be allowed to remove moves that have been already removed
		assertFalse(connect4.undoMove(testRow, testCol, 4));
		assertFalse(connect4.undoMove(testRow, testCol, 4));
	}
	/**
	 * test to aussure that the game is not over from the start
	 * then will test to see if the human player won
	 * then will test to see if the ai won
	 * then will test for a draw
	 */
	@Test
	public void testIsGameOver() {
		//testing for a scenario where the human player is the winner
		assertFalse(connect4.isGameOver());
		connect4.moveSet(DEFAULT_ROW, 0, 0);
		connect4.moveSet(DEFAULT_ROW, 0, 0);
		connect4.moveSet(DEFAULT_ROW, 0, 0);
		connect4.moveSet(DEFAULT_ROW, 0, 0);
		assertTrue(connect4.isGameOver());
		//test the evaluates results class when black(human) is winner
		assertEquals(2,connect4.evaluate()[1]);
		//testing for a scenario where the aiplayer is the winner
		setUp();
		assertFalse(connect4.isGameOver());
		connect4.moveSet(DEFAULT_ROW, 0, 1);
		connect4.moveSet(DEFAULT_ROW, 0, 1);
		connect4.moveSet(DEFAULT_ROW, 0, 1);
		connect4.moveSet(DEFAULT_ROW, 0, 1);
		assertTrue(connect4.isGameOver());
		//test the evaluates results class when white(aiplayer) is winner
		assertEquals(1,connect4.evaluate()[1]);
		//testing for a draw
		setUp();
		assertFalse(connect4.isGameOver());
		//a loop that will make the game a case of a draw
		for(int i = 0;i<6;i++){
			int hu= i%2;
			int ai= (i+1)%2;
			connect4.moveSet(DEFAULT_ROW, 0, hu);
			connect4.moveSet(DEFAULT_ROW, 1, hu);
			connect4.moveSet(DEFAULT_ROW, 2, hu);
			connect4.moveSet(DEFAULT_ROW, 3, ai);
			connect4.moveSet(DEFAULT_ROW, 4, ai);
			connect4.moveSet(DEFAULT_ROW, 5, ai);
			connect4.moveSet(DEFAULT_ROW, 6, hu);
		}
		assertTrue(connect4.isGameOver());
		//test the evaluates results class when black is winner
		assertEquals(SharedConstants.GAMENOWIN,connect4.evaluate()[1]);
		
		connect4.moveSet(DEFAULT_ROW, 0, 0);
		connect4.moveSet(DEFAULT_ROW, 0, 0);
		connect4.moveSet(DEFAULT_ROW, 0, 0);
		connect4.moveSet(DEFAULT_ROW, 0, 1);
		//
		connect4.moveSet(DEFAULT_ROW, 1, 1);
		connect4.moveSet(DEFAULT_ROW, 1, 1);
		//
		connect4.moveSet(DEFAULT_ROW, 2, 1);
		connect4.moveSet(DEFAULT_ROW, 2, 0);
		connect4.moveSet(DEFAULT_ROW, 2, 1);
		connect4.moveSet(DEFAULT_ROW, 2, 1);
		//
		connect4.moveSet(DEFAULT_ROW, 3, 0);
		//
		connect4.moveSet(DEFAULT_ROW, 4, 0);
		connect4.moveSet(DEFAULT_ROW, 4, 1);
		//
		connect4.moveSet(DEFAULT_ROW, 5, 0);
		//
		connect4.moveSet(DEFAULT_ROW, 6, 0);
		assertTrue(connect4.isGameOver());
		assertEquals(SharedConstants.GAMEBLACKWIN,connect4.evaluate()[1]);
		
		
	}
}
