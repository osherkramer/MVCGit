package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;

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
	/**
	 * display the maze
	 * @param maze - get Maze3d to view for the client
	 */
	void displayMaze(Maze3d maze);
	/**
	 * display the solution
	 * @param sol - get Maze3d to view for the client
	 */
	void displaySolution(Solution<Position> sol);
	
	/**
	 * set the HashMap that mapped between strings and commands
	 * @param hashCommand
	 */
	void setCommands(HashMap<String,Command> hashCommand);
	
	/**
	 * exit from the view - close threads and the view
	 */
	void exit();
	/**
	 * notify message by String[] 
	 * @param str
	 */
	void notifyMessage(String[] str);
	/**
	 * notify message by String 
	 * @param str
	 */
	void notifyMessage(String str);
	/**
	 * notify message by properties 
	 * @param properties
	 */
	void notifyMessage(Properties properties);
}
