package testing;

import static org.junit.Assert.*;
import gameai.*;
import org.junit.Before;
import org.junit.Test;

import othello.OthelloConsole;
public class OthelloConsoleTest {
	
	private OthelloConsole othGame;
	private int testRow;
	private int testCol;
	private String testCoor;
	@Before 
	public void setUp() {
		othGame = new OthelloConsole(new AIMinimax());
		othGame.gameOthelloInitializer(null);
	}
	/**
	 * 
	 * test the get avainble solution and that make a move by picking a move from that method 
	 * is a valid
	 * 
	 * will also test for when one player has no moves available to make 
	 */
	@Test
	public void testGetAvailableSolutions() {
		
		testCoor = othGame.getAvailableSolutions(0).get(0);
		testRow = Integer.parseInt(testCoor.split(",")[0]);
		testCol = Integer.parseInt(testCoor.split(",")[1]);
		assertTrue(othGame.moveSet(testRow, testCol, 0));
		
		
	}
	
	/**
	 * tests the evaulate method of connect 4 test results should 
	 * be depended on what moves were made, and having negative score for a human player
	 * and positive score on a aiplayer
	 */
	@Test
	public void testEvaluate() {
		assertEquals(0,othGame.evaluate()[0]);//score for the intitial board
		assertEquals(2,othGame.evaluate()[1]);//number of whitetokens
		assertEquals(2,othGame.evaluate()[2]);//number of blacktokens
		//now we will make moves to the board to change the score;
		//first move by black player
		othGame.moveSet(3, 2, 0);
		assertEquals(-3,othGame.evaluate()[0]);//score should be in favaor of black(negative score)
		assertEquals(1,othGame.evaluate()[1]);//number of whitetokens
		assertEquals(4,othGame.evaluate()[2]);//number of blacktokens
		//second move by white player
		othGame.moveSet(2, 2, 1);
		assertEquals(0,othGame.evaluate()[0]);//score should be in equal 
		assertEquals(3,othGame.evaluate()[1]);//number of whitetokens
		assertEquals(3,othGame.evaluate()[2]);//number of blacktokens
		//third move by black player
		othGame.moveSet(2, 3, 0);
		assertEquals(-3,othGame.evaluate()[0]);//score should be in favaor of black(negative score)
		assertEquals(2,othGame.evaluate()[1]);//number of whitetokens
		assertEquals(5,othGame.evaluate()[2]);//number of blacktokens
		//fourth move by white player
		othGame.moveSet(4, 2, 1);
		assertEquals(2,othGame.evaluate()[0]);//score should be in favaor of white(positive score)
		assertEquals(5,othGame.evaluate()[1]);//number of whitetokens
		assertEquals(3,othGame.evaluate()[2]);//number of blacktokens
	}
	/**
	 * tests the functionality of moveset
	 * first it will test for the move that  are out of range
	 * then it will test for moves that should not be valid
	 * then it will test for moves that should be valid
	 * then it will test for moves that should no longer be valid
	 */
	@Test
	public void testMoveSet() {
		//moves that are out of range
		assertFalse(othGame.moveSet(34, 0,0));
		assertFalse(othGame.moveSet(-1, 0,0));
		assertFalse(othGame.moveSet(0, 10,0));
		assertFalse(othGame.moveSet(0, -1,0));
		assertFalse(othGame.moveSet(23,-32,0));
		//moves that should not be valid 
		assertFalse(othGame.moveSet(1,1,0));
		assertFalse(othGame.moveSet(2, 2, 1));
		//moves that should be valid
		assertTrue(othGame.moveSet(2, 3, 0));
		assertTrue(othGame.moveSet(2, 4, 1));
		assertTrue(othGame.moveSet(4, 5, 0));
		assertTrue(othGame.moveSet(4, 2, 1));
		//now all the moves should no longer be valid
		assertFalse(othGame.moveSet(2, 3, 0));
		assertFalse(othGame.moveSet(2, 4, 1));
		assertFalse(othGame.moveSet(4, 5, 0));
		assertFalse(othGame.moveSet(4, 2, 1));
		
		
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
		assertFalse(othGame.undoMove(12, 0, 0));
		assertFalse(othGame.undoMove(-28, 0, 0));
		assertFalse(othGame.undoMove(-1, 8, 0));
		assertFalse(othGame.undoMove(0,-38, 0));
		assertFalse(othGame.undoMove(-7,10, 0));
		//Testing out the undo of places that are empty been placed 
		assertFalse(othGame.undoMove(0, 0, 0));
		assertFalse(othGame.undoMove(0, 7, 0));
		assertFalse(othGame.undoMove(7, 7, 0));
		assertFalse(othGame.undoMove(7, 0, 0));
		assertFalse(othGame.undoMove(4, 6, 0));
		//Testing out the undo of the initial tokens 
		assertFalse(othGame.undoMove(3, 3, 0));
		assertFalse(othGame.undoMove(4, 3, 0));
		assertFalse(othGame.undoMove(4, 4, 0));
		assertFalse(othGame.undoMove(3, 4, 0));
		//Testing out the undo of moves that have been set 
		testCoor = othGame.getAvailableSolutions(0).get(0);
		testRow = Integer.parseInt(testCoor.split(",")[0]);
		testCol = Integer.parseInt(testCoor.split(",")[1]);
		othGame.moveSet(testRow, testCol, 4);
		assertTrue(othGame.undoMove(testRow, testCol, 4));
		assertFalse(othGame.undoMove(testRow, testCol, 4));
	}
	/**
	 * test to aussure that the game is not over from the start
	 * then will test to see if the human player won
	 * then will test to see if the ai won
	 * then will test for a finished game based on turn by turn
	 */
	@Test
	public void testIsGameOver() {
		assertFalse(othGame.isGameOver());// the game should not be over initially
		// making move only from the black player to see if the game is over in a black dominated board
		// by allowing only moves from the black player(every level is even);
		othGame.moveSet(3, 2, 4);
		othGame.moveSet(5, 4, 2);
		assertTrue(othGame.isGameOver());
		othGame.undoMove(5, 4, 2);
		othGame.undoMove(3, 2, 4);
		//board should be reinitialized
		assertFalse(othGame.isGameOver());
		//now to test for white dominated board;
		othGame.moveSet(3, 5, 3);
		othGame.moveSet(5, 3, 1);
		assertTrue(othGame.isGameOver());
		othGame.undoMove(5, 3, 1);
		othGame.undoMove(3, 5, 3);
		//board should be reinitialized
		assertFalse(othGame.isGameOver());
		//now to test when the board for a real game, the moves selected were taken from a game against an AI
		//int moveSet(row,col,level) where when level == 0 it is black token and level == 1 is white token
		othGame.moveSet(5, 4, 0);
		othGame.moveSet(5, 3, 1);
		//
		othGame.moveSet(4, 2, 0);
		othGame.moveSet(5, 5, 1);
		//
		othGame.moveSet(6, 4, 0);
		othGame.moveSet(5, 2, 1);
		//
		othGame.moveSet(6, 2, 0);
		othGame.moveSet(3, 2, 1);
		//
		othGame.moveSet(5, 6, 0);
		othGame.moveSet(4, 5, 1);
		//
		othGame.moveSet(3, 5, 0);
		othGame.moveSet(4, 1, 1);
		//
		othGame.moveSet(3, 1, 0);
		othGame.moveSet(4, 6, 1);
		//
		othGame.moveSet(4, 0, 0);
		othGame.moveSet(6, 5, 1);
		//
		othGame.moveSet(6, 6, 0);
		othGame.moveSet(6, 7, 1);
		//
		othGame.moveSet(3, 6, 0);
		othGame.moveSet(5, 7, 1);
		//
		othGame.moveSet(6, 3, 0);
		othGame.moveSet(7, 7, 1);
		//
		othGame.moveSet(7, 6, 0);
		othGame.moveSet(7, 5, 1);
		//
		othGame.moveSet(7, 4, 0);
		othGame.moveSet(4, 7, 1);
		//
		othGame.moveSet(3, 7, 0);
		othGame.moveSet(5, 1, 1);
		//
		othGame.moveSet(6, 1, 0);
		othGame.moveSet(7, 1, 1);
		//
		othGame.moveSet(7, 2, 0);
		othGame.moveSet(7, 3, 1);
		//
		//Black player has no available moves othGame.moveSet(7, 2, 0);
		othGame.moveSet(2, 2, 1);
		//
		othGame.moveSet(2, 3, 0);
		othGame.moveSet(7, 0, 1);
		//
		othGame.moveSet(6, 0, 0);
		othGame.moveSet(5, 0, 1);
		//
		othGame.moveSet(2, 1, 0);
		othGame.moveSet(3, 0, 1);
		//
		//no moves othGame.moveSet(7, 2, 0);
		othGame.moveSet(2, 0, 1);
		//
		//no moves othGame.moveSet(7, 2, 0);
		othGame.moveSet(1, 0, 1);
		//
		//no moves othGame.moveSet(7, 2, 0);
		othGame.moveSet(2, 7, 1);
		//
		othGame.moveSet(2, 6, 0);
		othGame.moveSet(1, 6, 1);
		//
		othGame.moveSet(1, 7, 0);
		othGame.moveSet(0, 7, 1);
		//
		othGame.moveSet(0, 6, 0);
		othGame.moveSet(0, 5, 1);
		//
		//no moves othGame.moveSet(2, 6, 0);
		othGame.moveSet(1, 5, 1);
		//
		//no moves othGame.moveSet(2, 6, 0);
		othGame.moveSet(2, 5, 1);
		//
		othGame.moveSet(2, 4, 0);
		othGame.moveSet(1, 4, 1);
		//
		othGame.moveSet(2, 4, 0);
		othGame.moveSet(1, 4, 1);
		//
		othGame.moveSet(1, 3, 0);
		othGame.moveSet(0, 2, 1);
		//
		othGame.moveSet(0, 4, 0);
		othGame.moveSet(0, 3, 1);
		//
		//no moves othGame.moveSet(2, 4, 0);
		othGame.moveSet(1, 2, 1);
		
		//game should now be over
		assertTrue(othGame.isGameOver());
		
		
	}
}