package Play;
/**
 * @author Nishant Bhasin
 * Base launcher class for playing games.
 */
import java.util.Scanner;

import Othello.OthelloConsole;
import Shared.SharedConstants;
import TicTacToeConsole.TicTacToeConsole;


public class Launcher {

	private Scanner scan;
	
	public Launcher(){
		scan = new Scanner(System.in);
		String game = getGameType();
		String aiType = getAIType(); 
		playGame(game,aiType);
		
	}
	public void playGame(String game,String aiType)
	{
		String thisGame = game;
		if(thisGame.equals("")||aiType.equals("")) System.out.println("Oops something went wrong");
		else if(thisGame.equals("Quit")){
			System.out.println("GoodBye");
			System.exit(0);
		}
		else if(aiType.equals("Cancel"))
		{
			System.out.println("Okay choose the game again");
			return;
		}
		else if(thisGame.equals("TicTacToe")) 
		{
			TicTacToeConsole ticTacToeGame = new TicTacToeConsole();
			ticTacToeGame.playTicTacToe();
			return;
		}
		else if(thisGame.equals("Othello"))
		{
			OthelloConsole othello = new OthelloConsole();
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
					+ " 3- quit\n"); 
		
				
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
				//TicTacToeConsole ticTacToeGame = new TicTacToeConsole();
				//ticTacToeGame.playTicTacToe();
				return "TicTacToe";
			}
			else if(ans == 2){
				//OthelloConsole othello = new OthelloConsole();
				return "Othello";
			}
			else if(ans ==3){
				//System.out.print("Goodbye! \n");
				quit = true;
				return "Quit";
			}
			else{
				System.out.print("Not an option\n");
			}
		}while(!quit);
		return "";
	}
	public String getAIType(){
		boolean cancel;
		int ans = -1;
		do{
			cancel = false;
			System.out.print("Enter the type of AI you want to play against,\n"
					+ " 1- Random\n"
					+ " 2- Minimax\n"
					+ " 3- Cancel\n"); 	
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
				//TicTacToeConsole ticTacToeGame = new TicTacToeConsole();
				//ticTacToeGame.playTicTacToe();
				return SharedConstants.AIRandom;
			}
			else if(ans == 2){
				//OthelloConsole othello = new OthelloConsole();
				return SharedConstants.AIMinimax;
			}
			else if(ans ==3){
				cancel = true;
				return "Canceled";
			}
			else{
				System.out.print("Not an option\n");
				
			}
		}while(!cancel);
		return "";
	}
	
	public static void main(String[] args)   {
		while(true){
			Launcher launch = new Launcher();
		}
	}
	
}
