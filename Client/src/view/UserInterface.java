package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
/**
 * set the functional of user interface
 */
public interface UserInterface {
    
	/**
	 * set the view
	 * @param view
	 */
	void setView(View view);
	/**
	 * set for false to stop thread
	 * @param boolean
	 */
	void setRunning(boolean running);
	/**
	 * start the thread
	 */
	void start();
	/**
	 * display m=the message
	 * @param message
	 */
	void displayMessage(String message);
	/**
	 * set commands into hash map
	 * @param hashCommand
	 */
	void setCommands(HashMap<String, Command> hashCommand);
	/**
	 * display the maze
	 * @param Maze3d
	 */
	void displayMaze(Maze3d maze);
	/**
	 * display the solution
	 * @param sol
	 */
    void displaySolution(Solution<Position> sol);
}
