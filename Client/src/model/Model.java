package model;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;

/**
 * Model Interface - set the functional of the model side
 */

public interface Model {
	
	/**
	 * create the solution of the maze and send him to the controller
	 * @param name - the name of the maze
	 */
	void createSolution(String name);
	
	/**
	 * get the solution of the maze with specific name
	 * @param name - the name of the specific maze
	 * @return - return the solution of the maze
	 */
	Solution<Position> getSolution(String name);
	
	/**
	 * generate new maze with specific name, and size of x, y and z
	 * @param name - the name of the new maze
	 * @param x - the size of x
	 * @param y - the size of y
	 * @param z - the size of z
	 */
	void generate(String name, int x, int y, int z);
	
	/**
	 * return the maze by his name
	 * @param name - get name of maze
	 * @return
	 */
	void getMazeByName(String name);
	
	/**
	 * Cross specific maze in one of the selection, in some index
	 * @param by - the selection x,y or z
	 * @param index - index in the selection
	 * @param name - name of the maze
	 */
	void crossBy(String by, int index, String name);
	
	/**
	 * save maze that in the memory into a file
	 * @param arg - get the parameters of the command
	 */
	void saveMaze(String arg);
	
	/**
	 * load maze from file to the memory
	 * @param arg - get the parameters of the command
	 */
	void loadMaze(String arg);
	
	/**
	 * check the size of maze in the memory and send to the controller the size
	 * of the specific maze
	 * @param name - get name of maze
	 */
	void mazeSizeMemory(String name);
	
	/**
	 * check the size of file and send to the controller the size
	 * of the file that include the specific maze
	 * @param name - get name of maze
	 */
	void mazeSizeFile(String name);
	/**
	 * exit safely for the program
	 */
	void exit();
	
	/**
	 * set the x size
	 * @param -int x size
	 */
	void setxSize(int xSize);
	/**
	 * set the y size
	 * @param -int y size
	 */
	void setySize(int ySize);
	/**
	 * set the z size
	 * @param -int z size
	 */
	void setzSize(int zSize);
	/**
	 * generate new maze
	 */
	void generate();
	/**
	 * set the properties for the maze
	 * @param -Properties properties
	 */
	void setProperties(Properties properties);

	/**
	 * save data to zip
	 */
	void save();

	/**
	 * Create new connection
	 */
	void newConnection();

	/**
	 * load data from zip
	 */
	void load();
}
