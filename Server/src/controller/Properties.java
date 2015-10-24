package controller;

import java.io.Serializable;
/**
 *Properties  class - Serializable the Observer
 * manage the Properties of the program
 */
public class Properties implements Serializable {
	
	private static final long serialVersionUID = 42L;
	int port;
	int numberOfThreads;
	String algorithemForSolution;
	String algorithemForCreate;
	
/**
 * Constructor of Properties
 */
	public Properties() {
		this.port = 5400;
		this.numberOfThreads = 10;
		this.algorithemForSolution = "A* Air Distance";
		this.algorithemForCreate = "My Maze generator";
	}
	/**
	 * get the algorithm of create
	 * @return String algorithm name
	 */
	public String getAlgorithemForCreate() {
		return algorithemForCreate;
	}

	/**
	 * set the algorithm of create
	 * @param algorithemForCreate -String name
	 */

	public void setAlgorithemForCreate(String algorithemForCreate) {
		this.algorithemForCreate = algorithemForCreate;
	}

	/**
	 * get the number of threads
	 * @return int 
	 */
	public int getNumberOfThreads() {
		return numberOfThreads;
	}

	/**
	 * set the number of threads
	 * @param int 
	 */

	public void setNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}


	/**
	 * get the algorithm for solution
	 * @return String
	 */
	public String getAlgorithemForSolution() {
		return algorithemForSolution;
	}
	/**
	 * set the algorithm for solution
	 * @return String
	 */

	public void setAlgorithemForSolution(String algorithemForSolution) {
		this.algorithemForSolution = algorithemForSolution;
	}
	
	/**
	 * return the port that the server work with him
	 * @return port (int)
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * set new port to work with him
	 * @param port - int
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	
}
