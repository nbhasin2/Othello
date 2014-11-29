package shared;

/**
 * @author Nishant Bhasin
 * Shared constants class is used to store
 */
import java.util.EnumSet;

public abstract class SharedConstants {

/*
 * GENERAL CONSTANTS
 */

//General Constants
public static final String CancelButton = "Cancel";

//AI constants 
public static final String TicTacToe = "TicTacToeGame";
public static final String Othello = "OthelloGame";

//GUI Files
public static final String EmptyImage = "/GameIcons/resized-images/empty.png";
public static final String WhiteImage = "/GameIcons/resized-images/white-green.png";
public static final String BlackGreenImage = "/GameIcons/resized-images/black-green.png";
public static final String GreenYellowImage= "/GameIcons/resized-images/empty-available-green-yellow.png";


//AI Type
public static final String AIRandom = "Random";
public static final String AIMinimax = "Minimax";
public static final String AIIdiot = "Idiot";
public static final String AIPickMiddleStrategy = "AI Picks the middle move";

//Error
public static final String ErrorMessage = "Something went wrong. Error !!";


/*
 * OTHELLO and CONNECT FOUR
 * 
 */
public enum PlayableItem {
EMPTY, WHITE, BLACK
}

/*
 * Enum for state of the game. This tells whether the game is in playing mode or ended.
 */
public enum GameStatus {
PLAYING, GAME_END
}


/*
 * TIC TAC TOE
 * Constant Variables for Tic Tac Toe Console game.
 */
	
// Name-constants to represent the seeds and cell contents
public static final int EMPTY = 0;
public static final int CROSS = 1;
public static final int NOUGHT = 2;
public static final int AIPlayer = 3;

// Name-constants to represent the various states of the game
public static final int PLAYING = 0;
public static final int DRAW = 1;
public static final int CROSS_WON = 2;
public static final int NOUGHT_WON = 3;
public static final int AI_WON = 4;
public static final int PLAYER_WON = 5;

// The game board and the game status
public static final int ROWS = 3, COLS = 3; // number of rows and columns

}
