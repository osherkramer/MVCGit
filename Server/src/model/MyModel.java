package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import algorithms.demo.MazeDomain;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Solution;
import algorithms.search.State;
import controller.Controller;
import controller.Properties;

/**
 * MyModel class extends CommonModel
 * manage the size of the model in the server
 */


public class MyModel extends CommonModel {
	Controller controller;
	ExecutorService threadpool;
	int numberOfThreads;
	String algorithemForSolution;
	String algorithemForCreate;
	Properties properties;
	
	/**
	 * Default Constructor of MyModel
	 */
	public MyModel(Properties properties) {
		super();
		this.properties = properties;
		setProperties(properties);
		threadpool = Executors.newFixedThreadPool(numberOfThreads);
		load();
	}
	
	/**
	 * set the properties of the server
	 * @param properties - get properties to set them
	 */
	private void setProperties(Properties properties) {
		this.numberOfThreads = properties.getNumberOfThreads();
		algorithemForCreate = properties.getAlgorithemForCreate();
		algorithemForSolution = properties.getAlgorithemForSolution();
	}

	@Override
	public Maze3d generate(String str) {
		String[] parm = str.split(" ");
		String name = parm[2];
		int x = Integer.parseInt(parm[3]);
		int y = Integer.parseInt(parm[4]);
		int z = Integer.parseInt(parm[5]);
		if(hashMaze.get(name)!=null)
			return hashMaze.get(name);
		Callable<Maze3d> callable = new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				Maze3d maze;
				if(algorithemForCreate.equals("My Maze generator"))
					maze = new MyMaze3dGenerator().generate(x, y, z);
				else
					maze = new SimpleMaze3dGenerator().generate(x, y, z);
				return maze;
			}
		};
		
		
		
		Future<Maze3d> maze3d = threadpool.submit(callable);
		try {
			hashMaze.put(name, maze3d.get());
			return maze3d.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return null;		
	}

	@Override
	public Solution<Position> createSolution(String str) {
		String[] parm=str.split(" ");
		boolean isDefault;
		boolean notStartPositon;
		
		String name = parm[0];
		
		Solution<Position> solution = hashSolution.get(name);
		
		if(solution != null){
			if(parm.length==4){
				int x = Integer.parseInt(parm[1]);
				int y = Integer.parseInt(parm[2]);
				int z = Integer.parseInt(parm[3]);
				State<Position> newStart = new State<Position>(new Position(x,y,z));
				if(solution.indexOf(newStart) == solution.toString().split(" ").length-1)
					return solution;
			}
			else
				return solution;
		}
		
		if(parm.length == 2)
			isDefault = false;
		else
			isDefault = true;

		if(parm.length == 4){
			notStartPositon = true;
		}
		else
			notStartPositon = false;
		
		Maze3d maze = hashMaze.get(name);
		Callable<Solution<Position>> callable = new Callable<Solution<Position>>() {

			@Override
			public Solution<Position> call(){
				Solution<Position> solution = null;
				if((isDefault && algorithemForSolution.equals("BFS")) || (!isDefault && parm[1].equalsIgnoreCase("bfs"))){
					if(maze != null){
						BFS<Position> bfs = new BFS<Position>();
						MazeDomain md = new MazeDomain(maze);
						if(notStartPositon){
							int x = Integer.parseInt(parm[1]);
							int y = Integer.parseInt(parm[2]);
							int z = Integer.parseInt(parm[3]);
							md.setStartState(new State<Position>(new Position(x,y,z)));
						}
						solution = bfs.search(md);
						return solution;
					}
				}
				else if((isDefault && algorithemForSolution.equals("A* Manhattan Distance")) || (!isDefault && parm[1].equalsIgnoreCase("ManhattanDistance"))){
					if(maze != null){
						AStar<Position> astarManhattanDistance = new AStar<Position>(new MazeManhattanDistance(new State<Position>(maze.getGoalPosition())));
						MazeDomain md = new MazeDomain(maze);
						if(notStartPositon){
							int x = Integer.parseInt(parm[1]);
							int y = Integer.parseInt(parm[2]);
							int z = Integer.parseInt(parm[3]);
							md.setStartState(new State<Position>(new Position(x,y,z)));
						}
						solution = astarManhattanDistance.search(md);
						return solution;
					}
				}
				else if((isDefault && algorithemForSolution.equals("A* Air Distance")) || (!isDefault && parm[1].equalsIgnoreCase("AirDistance"))){
					if(maze != null){
						AStar<Position> astarAirDistance = new AStar<Position>(new MazeAirDistance(new State<Position>(maze.getGoalPosition())));
						MazeDomain md = new MazeDomain(maze);
						if(notStartPositon){
							int x = Integer.parseInt(parm[1]);
							int y = Integer.parseInt(parm[2]);
							int z = Integer.parseInt(parm[3]);
							md.setStartState(new State<Position>(new Position(x,y,z)));
						}
						solution = astarAirDistance.search(md);
						return solution;
					}
				}
				return solution;
			}
		};
			
		Future<Solution<Position>> solutionCreate = threadpool.submit(callable);
		try {
			hashSolution.put(maze, solutionCreate.get());
			return solutionCreate.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public void exit(){
		threadpool.shutdown();
		try {
			while(!(threadpool.awaitTermination(10, TimeUnit.SECONDS)));
		} catch (InterruptedException e) {
			controller.setMessage(e.getMessage());
		}
		save();
	}
	
	@Override
	public void setController(Controller controller){
		this.controller = controller;
	}
	
	@Override
	public void save() {
		FileOutputStream fileSolutions;
		GZIPOutputStream GZIPOutput;
		ObjectOutputStream out;
		try {
			fileSolutions = new FileOutputStream("ServerSolution.zip");
			GZIPOutput = new GZIPOutputStream(fileSolutions);
			out = new ObjectOutputStream(GZIPOutput);
			out.writeObject(hashMaze);
			out.writeObject(hashSolution);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			controller.setMessage(e.getMessage());
		}
		catch (IOException e) {
			controller.setMessage(e.getMessage());
		}		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void load() {
		FileInputStream fileSolutions;
		GZIPInputStream GZIPInput;
		ObjectInputStream in;
		try {
			fileSolutions = new FileInputStream("ServerSolution.zip");
			GZIPInput = new GZIPInputStream(fileSolutions);
			in = new ObjectInputStream(GZIPInput);
			hashMaze = (HashMap<String, Maze3d>) in.readObject();
			hashSolution = (HashMap<Maze3d, Solution<Position>>) in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			controller.setMessage(e.getMessage());
		}
		catch (IOException e) {
			controller.setMessage(e.getMessage());
		}	
		catch (ClassNotFoundException e) {
			controller.setMessage(e.getMessage());
		}
	}
	
	@Override
	public boolean pause(){
		threadpool.shutdown();
		try {
			while(!(threadpool.awaitTermination(10, TimeUnit.SECONDS)));
		} catch (InterruptedException e) {
			controller.setMessage(e.getMessage());
			return false;
		}
		return true;
	}
	
	@Override
	public boolean resume(){
		threadpool = Executors.newFixedThreadPool(numberOfThreads);
		return true;
	}
	
}
