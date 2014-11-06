package GameAI;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import GameModel.GameConsoleInterface;
import Shared.SharedConstants;
import TicTacToeConsole.TicTacToeConsole;

public class AITest {
	private AI random; 
	private AI minimax;
	@Before
	public void setUp(){
		random = new AI(SharedConstants.AIRandom);
		minimax = new AI(SharedConstants.AIMinimax);
		 
	}
	@Test
	public void testMakeMove() {
		
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
