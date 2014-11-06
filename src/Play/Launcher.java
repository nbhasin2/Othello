package Play;
/**
 * @author Nishant Bhasin
 * Base launcher class for playing games.
 */
import java.util.Scanner;

import Othello.OthelloConsole;
import TicTacToeConsole.TicTacToeConsole;


public class Launcher {

	public static void main(String[] args)   {
		boolean quit;
		Scanner scan = new Scanner(System.in);
		int ans;
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
				TicTacToeConsole ticTacToeGame = new TicTacToeConsole();
				ticTacToeGame.playTicTacToe();
			}
			else if(ans == 2){
				OthelloConsole othello = new OthelloConsole();
			}
			else if(ans ==3){
				System.out.print("Goodbye! \n");
				quit = true;
			}
			else{
				System.out.print("Not an option\n");
			}
		}while(!quit);
	}
}
