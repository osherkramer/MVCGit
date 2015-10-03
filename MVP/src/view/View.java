package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import presenter.Command;

/**
 * View Interface
 * set the functional of the view side
 */

public interface View {
	/**
	 * start the view side
	 */
	void start();
	
	/**
	 * display the message that get from 
	 * @param message - get string to view for the client
	 */
	void displayMessage(String message);
	
	void displayMaze(Maze3d maze);
	
	/**
	 * set the HashMap that mapped between strings and commands
	 * @param hashCommand
	 */
	void setCommands(HashMap<String,Command> hashCommand);
	
	/**
	 * exit from the view - close threads and the view
	 */
	void exit();
	
	void notifyMessage(String[] str);
	
	void notifyMessage(String str);
}
