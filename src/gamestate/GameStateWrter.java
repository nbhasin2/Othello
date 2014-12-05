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
	         FileOutputStream writeCurrentBoard = new FileOutputStream("currentBoard");
	         
	         ObjectOutputStream oosredoBoard = new ObjectOutputStream(writeRedoBoard);
	         ObjectOutputStream oosundoBoard = new ObjectOutputStream(writeUndoBoard);
	         ObjectOutputStream ooscurrentBoard = new ObjectOutputStream(writeCurrentBoard);
	    
	         oosredoBoard.writeObject(gameState.getRedoBoard());
	         oosundoBoard.writeObject(gameState.getUndoBoard());
	         ooscurrentBoard.writeObject(gameState.getCurrentBoard());
	         
	         oosredoBoard.close();
	         oosundoBoard.close();
	         ooscurrentBoard.close();
	         
	         writeRedoBoard.close();
	         writeUndoBoard.close();
	         writeCurrentBoard.close();
	       }catch(IOException ioe){
	            ioe.printStackTrace();
	        }
	}
}
