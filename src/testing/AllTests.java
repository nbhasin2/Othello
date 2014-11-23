package testing;

import org.junit.runner.RunWith;
import org.junit.runner.JUnitCore;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({AITest.class,
	TicTacToeConsoleTest.class,
	OthelloConsoleTest.class,
	Connect4Test.class})
public class AllTests {
       
}