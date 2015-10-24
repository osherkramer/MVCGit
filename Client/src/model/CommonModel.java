package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;
/**
 * CommonModel implements Model interface
 * abstract class of the model
 */

public abstract class CommonModel extends Observable implements Model {
	String ip;
	int port;
	Socket theServer;
	PrintWriter outToServer;
	BufferedReader inFromServer;
	HashMap<Maze3d, Solution<Position>> hashSolution;
	HashMap<String, Maze3d> hashMaze;
	/**
	 * constructor for common model
	 */
	public CommonModel(String ip, int port) {
		this.ip = ip;
		this.port = port;
		try {
			theServer = new Socket(ip,port);
		} catch (UnknownHostException e) {
			setChanged();
			notifyObservers(e.getMessage());
		} catch (IOException e) {
			setChanged();
			notifyObservers(e.getMessage());
		}
		
		try {
			outToServer = new PrintWriter(theServer.getOutputStream());
		} catch (IOException e) {
			setChanged();
			notifyObservers(e.getMessage());
		}
		try {
			inFromServer = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
		} catch (IOException e) {
			setChanged();
			notifyObservers(e.getMessage());
		}
		
		hashSolution = new HashMap<Maze3d,Solution<Position>>();
		hashMaze = new HashMap<String,Maze3d>();
	}
	
	@Override
	public void newConnection(){
		try {
			theServer = new Socket(ip,port);
		} catch (UnknownHostException e) {
			setChanged();
			notifyObservers("Error create connection");
			return;
		} catch (IOException e) {
			setChanged();
			notifyObservers("Error create connection");
			return;
		}
		
		try {
			outToServer = new PrintWriter(theServer.getOutputStream());
		} catch (IOException e) {
			setChanged();
			notifyObservers("Error create connection");
			return;
		}
		try {
			inFromServer = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
		} catch (IOException e) {
			setChanged();
			notifyObservers("Error create connection");
			return;
		}
		
		setChanged();
		notifyObservers("Connection up");
	}

	@Override	
	public Solution<Position> getSolution(String name){
		return hashSolution.get(hashMaze.get(name));
	}
	@Override
	public abstract void generate(String name, int x, int y, int z);


	@Override
	public abstract void getMazeByName(String name);
	
	@Override
	public abstract void createSolution(String name);
	
	@Override
	public abstract void crossBy(String by, int index, String name);
	
	@Override
	public abstract void saveMaze(String arg);
	
	@Override
	public abstract void loadMaze(String arg);
	
	@Override
	public abstract void mazeSizeMemory(String name);

	@Override
	public abstract void mazeSizeFile(String name);
	
	@Override
	public abstract void exit();
	
	@Override
	public abstract void setProperties(Properties properties);
	
	@Override
	public abstract void setxSize(int xSize);

	@Override
	public abstract void setySize(int ySize);

	@Override
	public abstract void setzSize(int zSize);
	
	@Override
	public abstract void generate();
}
