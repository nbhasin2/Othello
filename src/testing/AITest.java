package testing;

import static org.junit.Assert.*;
import gameai.AI;
import gamemodel.DummyConsole;
import gamemodel.GameConsoleInterface;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import shared.SharedConstants;
import tictactoe.TicTacToeConsole;

public class AITest {
	private AI random; 
	private AI minimax;
	private int rowTest;
	private int colTest;
	ArrayList<String> solutions;
	GameConsoleInterface gameTest;
	@Before
	public void setUp(){
		solutions = new ArrayList<String>();
		random = new AI(SharedConstants.AIRandom);
		minimax = new AI(SharedConstants.AIMinimax);
		solutions.add("3,3");
		solutions.add("1,2");
		solutions.add("1,1");
		gameTest = new DummyConsole(solutions);
		
	}
	@Test
	public void testMakeMove() {
		String answer = random.makeMove(gameTest);
		assertTrue(answer.equals("3,3") || answer.equals("1,2") ||answer.equals("1,1"));
		answer = minimax.makeMove(gameTest);
		assertTrue(answer.equals("3,3,1") || answer.equals("1,2,1") ||answer.equals("1,1,1"));

	}
	@Test
	public void testGetAIType() {
		assertEquals(SharedConstants.AIRandom,random.getAIType());
		assertEquals(SharedConstants.AIMinimax,minimax.getAIType());
	}
	@Test
	public void testSetAIType() {
		random.setAIType(SharedConstants.AIMinimax);//set the AI of random to minimax
		minimax.setAIType(SharedConstants.AIRandom);//set the AI of minimax to random
		assertEquals(SharedConstants.AIMinimax,random.getAIType());
		assertEquals(SharedConstants.AIRandom,minimax.getAIType());
	}

}
