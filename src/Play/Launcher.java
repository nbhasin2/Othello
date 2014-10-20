package Play;

import java.util.Scanner;

import Othello.Game;
import TicTacToeConsole.TicTacToeConsole;


public class Launcher {

	public static void main(String[] args) {
		boolean quit;
		Scanner scan = new Scanner(System.in);
		int ans;
		do{
		
		quit = false;
		
		System.out.print("Enter the game you want to play,\n"
				+ " 1- Tic Tac Toe\n"
				+ " 2- Othello\n"
				+ " 3- quit\n"); 
		ans = scan.nextInt();
		
		if(ans == 1)
		{
			TicTacToeConsole ticTacToeGame = new TicTacToeConsole();
			ticTacToeGame.playTicTacToe();
		}
		else if(ans == 2)
		{
			Game othello = new Game();
		}
		else if(ans ==3)
		{
			System.out.print("Goodbye! \n");
			quit = true;
		}
		else
		{
			System.out.print("Not an option\n");
		}
		}while(!quit);
	}
}
