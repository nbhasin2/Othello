package testing;

import static org.junit.Assert.*;
import gameai.*;
import gamemodel.DummyConsole;
import gamemodel.GameConsoleInterface;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import shared.SharedConstants;

public class AITest {
	private AIMain random; 
	private AIMain minimax;
	private AIMain PickMiddleMove;
	
	ArrayList<String> solutions;
	GameConsoleInterface gameTest;
	@Before
	public void setUp(){
		solutions = new ArrayList<String>();
		random = new AIMain(new AIRandom());
		minimax = new AIMain(new AIMinimax());
		PickMiddleMove = new AIMain(new AIPickMiddleMove());
		solutions.add("3,3");
		solutions.add("1,2");
		solutions.add("1,1");
		gameTest = new DummyConsole(solutions);
		
	}
	
	/**
	 * tests the ai the make move of the ai it should be a string, 
	 * depending on the ai the returned string will be different this checks to make sure the strategy 
	 * is working appropriately 
	 */
	@Test
	public void testMakeMove() {
		String answer = random.makeMove(gameTest);
		assertTrue(answer.equals("3,3") || answer.equals("1,2") ||answer.equals("1,1"));
		answer = minimax.makeMove(gameTest);
		assertTrue(answer.equals("3,3,1"));
		answer = PickMiddleMove.makeMove(gameTest);
		assertTrue(answer.equals("1,2"));
	
	}
	/**
	 *  test to assure that we get the right AItype and that toString() method works well
	 */
	@Test
	public void testGetAIType() {
		assertEquals(SharedConstants.AIRandom,random.getAIType()+"");
		assertEquals(SharedConstants.AIMinimax,minimax.getAIType()+"");
		assertEquals(SharedConstants.AIPickMiddleStrategy,PickMiddleMove.getAIType()+"");
	
	}
	/**
	 * checks to see that an ai being set is the right ai
	 * 
	 */
	@Test
	public void testSetAIType() {
		random.setAIType(new AIMinimax());//set the AI of random to minimax
		minimax.setAIType(new AIRandom());//set the AI of minimax to random
		PickMiddleMove.setAIType(new AIPickMiddleMove());
		
		assertEquals(SharedConstants.AIMinimax,random.getAIType()+"");
		assertEquals(SharedConstants.AIRandom,minimax.getAIType()+"");
		assertEquals(SharedConstants.AIPickMiddleStrategy,PickMiddleMove.getAIType()+"");
	}

}
