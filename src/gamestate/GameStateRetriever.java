package gamestate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import othello.BoardSpace;

public class GameStateRetriever {
	
	public GameStateRetriever() {
		

	}
	
	@SuppressWarnings("unchecked")
	public GameSateModel retrieveModel()
	{
	
		 ArrayList<BoardSpace[][]> redoBoard= new ArrayList<BoardSpace[][]>();
		 ArrayList<BoardSpace[][]> undoBoard= new ArrayList<BoardSpace[][]>();
	        try
	        {
	            FileInputStream fisRedoBoard = new FileInputStream("redoBoard");
	            FileInputStream fisUndoBoard = new FileInputStream("undoBoard");
	            ObjectInputStream oisRedoBoard = new ObjectInputStream(fisRedoBoard);
	            ObjectInputStream oisUndoBoard = new ObjectInputStream(fisUndoBoard);
	            
	            redoBoard =(ArrayList<BoardSpace[][]>) oisRedoBoard.readObject();
	            undoBoard =(ArrayList<BoardSpace[][]>) oisUndoBoard.readObject();
	            oisUndoBoard.close();
	            oisRedoBoard.close();
	            fisRedoBoard.close();
	            fisUndoBoard.close();
	         }catch(IOException ioe){
	             ioe.printStackTrace();
	             
	          }catch(ClassNotFoundException c){
	             System.out.println("Class not found");
	             c.printStackTrace();
	             
	          }
	        
	    GameSateModel model = new GameSateModel(redoBoard,undoBoard);

		return model;
	}
}
