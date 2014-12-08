package play;
/**
 * @author Nishant Bhasin
 * Base launcher class for playing games.
 */
import gameai.*;

import java.util.Scanner;

import com.connect.four.ConnectFourConsole;

import othello.OthelloConsole;
import tictactoe.TicTacToeConsole;


public class Launcher {
	
	private static final String TICTACTOE = "TicTacTo";
	private static final String CONNECTFOUR = "Connect4";
	private static final String OTHELLO = "Othell";
	private static final String QUIT= "Qui";
	
	
	
	private Scanner scan;
	private AIStrategy playaiType;
	private String playgame;
	public Launcher(){
		
	}
	public void newGame(){
		scan = new Scanner(System.in);
		playgame = getGameType();
		if(!(playgame.equals(QUIT))) 
			playaiType = getAIType(); 
		else
			playaiType = null;
		playGame(playgame,playaiType);
	}
	public void playGame(String game,AIStrategy aiType)
	{
		String thisGame = game;
		AIStrategy thisAIType  = aiType;
		if(thisGame.equals("")) System.out.println("Oops something went wrong");
		else if(thisGame.equals(QUIT)){
			System.out.println("GoodBye");
			System.exit(0);
		}
		else if(aiType == null)	{
			System.out.println("Okay choose the game again");
			return;
		}
		else if(thisGame.equals(TICTACTOE)) {
			TicTacToeConsole ticTacToeGame = new TicTacToeConsole(thisAIType);
			ticTacToeGame.playTicTacToe();
			return;
		}
		else if(thisGame.equals(OTHELLO)){
			OthelloConsole othello = new OthelloConsole(thisAIType);
			othello.gameOthelloInitializer(null);
			othello.playOthello();
		}
		else if(thisGame.equals(CONNECTFOUR)){
			ConnectFourConsole cFour = new ConnectFourConsole(thisAIType);
			cFour.playConnectFour();
		}
	}
	public String getGameType()
	{
		int ans;
		boolean quit;
		do{
			quit = false;
			System.out.print("Enter the game you want to play,\n"
					+ " 1- Tic Tac Toe\n"
					+ " 2- Othello\n"
					+ " 3- Connect Four\n"
					+ " 4- Quit\n"); 
			if(scan.hasNextInt()){
				ans = scan.nextInt();
			}
			else{
				ans = 0;
			}
			if(ans == 0){
				String dummy = scan.nextLine();
				System.out.println(dummy+ " is not an integer therefore it is not an option");
			}
			else if(ans == 1){
				return TICTACTOE;
			}
			else if(ans == 2){
				return OTHELLO;
			}
			else if(ans ==3){
				return CONNECTFOUR;
			}
			else if(ans ==4){
				quit = true;
				return QUIT;
			}
			else{
				System.out.print("Not an option\n");
			}
		}while(!quit);
		return "";
	}
	public AIStrategy getAIType(){
		boolean cancel;
		int ans = -1;
		do{
			cancel = false;
			System.out.print("Enter the type of AI you want to play against,\n"
					+ " 1- Random\n"
					+ " 2- Minimax\n"
					+ " 3- Idiot\n"
					+ " 4- Picks the middle the solutions\n"
					+ " 5- Cancel\n"); 	
			if(scan.hasNextInt()){
				ans = scan.nextInt();
			}
			else{
				ans = 0;
			}
			if(ans == 0){
				String dummy = scan.nextLine();
				System.out.println(dummy+" is not an integer therefore it is not an option");
			}
			else if(ans == 1){
				return new AIRandom();
			}
			else if(ans == 2){
				return new AIMinimax();
			}
			else if(ans == 3){
				return new AIIdiot();
			}
			else if(ans ==4){
				return new AIPickMiddleMove();
			}
			else if(ans ==5){
				cancel = true;
			}
			else{
				System.out.print("Not an option\n");
			}
		}while(!cancel);
		return null;
	}
	public static void main(String[] args)   {
		Launcher launch = new Launcher();
		while(true){
			launch.newGame();
		}
	}
}
