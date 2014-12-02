package gamestate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameStateWrter {

	private GameSateModel gameState;
	
	public GameStateWrter(GameSateModel model) {
		
		this.gameState = model;
	}
	public void writeModel()
	{
		 try{
	         FileOutputStream writeRedoBoard= new FileOutputStream("redoBoard");
	         FileOutputStream writeUndoBoard = new FileOutputStream("undoBoard");
	         
	         ObjectOutputStream oosredoBoard = new ObjectOutputStream(writeRedoBoard);
	         ObjectOutputStream oosundoBoard = new ObjectOutputStream(writeUndoBoard);
	         
	         oosredoBoard.writeObject(gameState.getRedoBoard());
	         oosundoBoard.writeObject(gameState.getUndoBoard());
	         
	         oosredoBoard.close();
	         oosundoBoard.close();
	         
	         writeRedoBoard.close();
	         writeRedoBoard.close();
	       }catch(IOException ioe){
	            ioe.printStackTrace();
	        }
	}
}
