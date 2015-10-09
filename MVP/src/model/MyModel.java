package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import algorithms.IO.MyCompressorOutputStream;
import algorithms.IO.MyDecompressorInputStream;
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
import presenter.Properties;

/**
 * MyModel class extends CommonModel
 * manage the size of the model
 */


public class MyModel extends CommonModel {

	ExecutorService threadpool;
	HashMap<Maze3d,String> mazeFile;
	int xSize;
	int ySize;
	int zSize;
	int numberOfThreads;
	String algorithemForSolution;
	String algorithemForCreate;
	String name;
	Properties properties;
	
	/**
	 * Default Constructor of MyModel
	 */
	public MyModel(Properties properties) {
		super();
		this.properties = properties;
		setProperties(properties);
		threadpool = Executors.newFixedThreadPool(numberOfThreads);
		mazeFile = new HashMap<Maze3d,String>();
		load();
	}

	@Override
	public void generate(String name, int x, int y, int z) {
		this.name = name;
		Callable<Maze3d> callable = new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				Maze3d maze;
				if(algorithemForCreate.equals("My Maze generator"))
					maze = new MyMaze3dGenerator().generate(x, y, z);
				else
					maze = new SimpleMaze3dGenerator().generate(x, y, z);
				hashMaze.put(name,maze);
				setChanged();
				notifyObservers("Done: maze " +  name + " is ready");
				return maze;
			}
		};
		
		threadpool.submit(callable);
		
	}
	
	public void generate() {
		Callable<Maze3d> callable = new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				Maze3d maze;
				if(algorithemForCreate.equals("My Maze generator"))
					maze = new MyMaze3dGenerator().generate(xSize, ySize, zSize);
				else
					maze = new SimpleMaze3dGenerator().generate(xSize, ySize, zSize);
				hashMaze.put(name,maze);
				setChanged();
				notifyObservers("Done: maze " +  name + " is ready");
				return maze;
			}
		};
		
		threadpool.submit(callable);
		
	}
	
	@Override
	public void getMazeByName(String name){
		Maze3d maze = null;
		if(name.length() == 0)
			maze = hashMaze.get(this.name);
		else
			maze = hashMaze.get(name);
		if(maze == null){
			setChanged();
			notifyObservers("Not exist maze by name: " + name);
		}
		else{
			setChanged();
			notifyObservers(maze); 
		}
	}

	@Override
	public void createSolution(String str) {
		String[] parm=str.split(" ");
		boolean isDefault;
		boolean notStartPositon;
		if(parm.length < 1 || parm.length > 3){
			setChanged();
			notifyObservers("Done: Invalid Command");
			return;
		}
		
		String name = null;
		if(parm.length == 2)
			name = parm[0];
		else if(parm.length == 1 && !parm[0].equals(""))
			name = parm[0];
		else
			name = this.name;

		
		Solution<Position> solution = hashSolution.get(name);
		
		if(solution != null){
			if(parm.length==3){
				int x = Integer.parseInt(parm[0]);
				int y = Integer.parseInt(parm[1]);
				int z = Integer.parseInt(parm[2]);
				State<Position> newStart = new State<Position>(new Position(x,y,z));
				if(solution.indexOf(newStart) == solution.toString().split(" ").length-1){
					setChanged();
					notifyObservers("Done: solution for " + name + " is ready");
					return;
				}
			}
			else{
				setChanged();
				notifyObservers("Done: solution for " + name + " is ready");
				return;
			}	
		}
		
		if(parm.length == 2)
			isDefault = false;
		else
			isDefault = true;

		if(parm.length == 3){
			notStartPositon = true;
		}
		else
			notStartPositon = false;
		
		Maze3d maze = hashMaze.get(name);
		String string = name;
		
		Callable<Solution<Position>> callable = new Callable<Solution<Position>>() {

			@Override
			public Solution<Position> call() throws Exception {
				Solution<Position> solution = null;
				if((isDefault && algorithemForSolution.equals("BFS")) || (!isDefault && parm[1].equalsIgnoreCase("bfs"))){
					if(maze != null){
						BFS<Position> bfs = new BFS<Position>();
						MazeDomain md = new MazeDomain(maze);
						if(notStartPositon){
							int x = Integer.parseInt(parm[0]);
							int y = Integer.parseInt(parm[1]);
							int z = Integer.parseInt(parm[2]);
							md.setStartState(new State<Position>(new Position(x,y,z)));
						}
						solution = bfs.search(md);
						hashSolution.put(hashMaze.get(string), solution);
						setChanged();
						notifyObservers("Done: solution for " + string + " is ready");
					}
					else{
						setChanged();
						notifyObservers("Done: Invalid name");
					}
				}
				else if((isDefault && algorithemForSolution.equals("A* Manhattan Distance")) || (!isDefault && parm[1].equalsIgnoreCase("ManhattanDistance"))){
					if(maze != null){
						AStar<Position> astarManhattanDistance = new AStar<Position>(new MazeManhattanDistance(new State<Position>(maze.getGoalPosition())));
						MazeDomain md = new MazeDomain(maze);
						if(notStartPositon){
							int x = Integer.parseInt(parm[0]);
							int y = Integer.parseInt(parm[1]);
							int z = Integer.parseInt(parm[2]);
							md.setStartState(new State<Position>(new Position(x,y,z)));
						}
						solution = astarManhattanDistance.search(md);
						hashSolution.put(hashMaze.get(string), solution);
						setChanged();
						notifyObservers("Done: solution for " + string + " is ready");
					}
					else{
						setChanged();
						notifyObservers("Done: Invalid name");
					}
				}
				else if((isDefault && algorithemForSolution.equals("A* Air Distance")) || (!isDefault && parm[1].equalsIgnoreCase("AirDistance"))){
					if(maze != null){
						AStar<Position> astarAirDistance = new AStar<Position>(new MazeAirDistance(new State<Position>(maze.getGoalPosition())));
						MazeDomain md = new MazeDomain(maze);
						if(notStartPositon){
							int x = Integer.parseInt(parm[0]);
							int y = Integer.parseInt(parm[1]);
							int z = Integer.parseInt(parm[2]);
							md.setStartState(new State<Position>(new Position(x,y,z)));
						}
						solution = astarAirDistance.search(md);
						hashSolution.put(hashMaze.get(string), solution);
						setChanged();
						notifyObservers("Done: solution for " + string + " is ready");
					}
					else{
						setChanged();
						notifyObservers("Done: Invalid name");
					}
				}
				else{
					setChanged();
					notifyObservers("Done: Invalid algorithm");
				}
				return solution;
			}
		};
		
		threadpool.submit(callable);
	}

	@Override
	public void crossBy(String by, int index, String name) {
		Maze3d maze = null;
		if(name.length() == 0)
			maze = hashMaze.get(this.name);
		else
			maze = hashMaze.get(name);
		
		String strMaze ="";
		int[][] maze2d = null;
		if(maze == null){
			setChanged();
			notifyObservers("Maze not exist");
			return;
		}
		
		
		
		try{
			switch(by){
			case "X":
				maze2d = maze.getCrossSectionByX(index);
				break;
			case "x":
				maze2d = maze.getCrossSectionByX(index);
				break;
			case "Y":
				maze2d = maze.getCrossSectionByY(index);
				break;
			case "y":
				maze2d = maze.getCrossSectionByY(index);
				break;
			case "Z":
				maze2d = maze.getCrossSectionByZ(index);
				break;
			case "z":
				maze2d = maze.getCrossSectionByZ(index);
				break;
			default:
				setChanged();
				notifyObservers("Invalid cross section");
				return;
			}
		}
		catch(IndexOutOfBoundsException e){
			setChanged();
			notifyObservers("Invalid index");
			return;
		}
		
		
		
		for(int i = 0; i < maze2d.length; i++){
			for(int j = 0; j < maze2d[i].length; j++)
				strMaze += String.valueOf(maze2d[i][j]) + " ";
			strMaze += '\n';
		}
		
		setChanged();
		notifyObservers(strMaze);		
	}

	@Override
	public void saveMaze(String arg) {
		String[] parm = arg.split(" ");
		if(parm.length != 3 && parm.length!=2){
			setChanged();
			notifyObservers("Invalid Command");
			return;
		}
		
		Maze3d maze = hashMaze.get(parm[1]);
		if(maze == null){
			setChanged();
			notifyObservers("Maze " + parm[1] + " not exist");
			return;
		}
		
		OutputStream out = null;
		try {
			out = new MyCompressorOutputStream(new FileOutputStream(parm[2] + ".maz"));
			out.write(maze.toByteArray());	
			mazeFile.put(maze, parm[2] + ".maz");
		} catch (FileNotFoundException e) {
			setChanged();
			notifyObservers(e.getMessage());
			return;
		} catch (IOException e) {
			setChanged();
			notifyObservers(e.getMessage());
			return;
		}
		finally{
			try {
				out.flush();
			} catch (IOException e) {
				setChanged();
				notifyObservers(e.getMessage());
			}
			try {
				out.close();
			} catch (IOException e) {
				setChanged();
				notifyObservers(e.getMessage());
			}
		}
		
		
		setChanged();
		notifyObservers("File " + parm[2] + " save");
		
	}

	@Override
	public void loadMaze(String arg) {
		String[] parm = arg.split(" ");
		Maze3d loaded = null;
		boolean isOpen=false;
		
		if(parm.length != 3){
			setChanged();
			notifyObservers("Invalid Command");
			return;
		}
		
		try{
			@SuppressWarnings("unused")
			File file = new File(parm[1] + ".maz");
		}
		catch(NullPointerException e){
			setChanged();
			notifyObservers("File not exist");
			return;
		}
			
		InputStream in=null;
		try {
			in = new MyDecompressorInputStream(new FileInputStream(parm[1] + ".maz"));
			isOpen=true;
			byte b[] = new byte[4096];
			in.read(b);
			loaded = new Maze3d(b);
		} catch (FileNotFoundException e) {
			setChanged();
			notifyObservers(e.getMessage());
			return;
		} catch (IOException e) {
			setChanged();
			notifyObservers(e.getMessage());
			return;
		}
		catch(NullPointerException e)
		{
			setChanged();
			notifyObservers(e.getMessage());
			return;
		}
		finally
		{
			try {
				if(isOpen)
					in.close();
			} catch (IOException e) 
			{
				setChanged();
				notifyObservers("Maze "+ parm[2]+" was unsuccessfully");
			}
		}
			
		hashMaze.put(parm[2], loaded);
		mazeFile.put(loaded, parm[1] + ".maz");
		setChanged();
		notifyObservers("Maze " + parm[2] + " loaded successfully");
	}
	
	@Override
	public void mazeSizeMemory(String name) {
		Maze3d maze = hashMaze.get(name);
		if(maze == null){
			setChanged();
			notifyObservers("Maze " + name + " not exist");
			return;
		}
		
		int size = 4*(maze.getX()*maze.getY()*maze.getZ() + 3 + 3 + 3);
		
		setChanged();
		notifyObservers("Maze " + name + " size in memory: " + size);
		
	}


	@Override
	public void mazeSizeFile(String str) {
		
		try{
			String fielPath = mazeFile.get(hashMaze.get(str));
			if(fielPath == null){
				setChanged();
				notifyObservers("Maze " + str + " not exist in any file");
				return;
			}
			File maze = new File(fielPath);
			setChanged();
			notifyObservers("Maze file " + str + " size is: " + maze.length());
		}
		catch (NullPointerException e){
			setChanged();
			notifyObservers("Not exist " + str + " file");
		}
		
		
	}

	@Override
	public void exit(){
		threadpool.shutdown();
		try {
			while(!(threadpool.awaitTermination(10, TimeUnit.SECONDS)));
		} catch (InterruptedException e) {
			setChanged();
			notifyObservers(e.getMessage());
		}
		save();
	}

	@Override
	public void save() {
		FileOutputStream fileSolutions;
		GZIPOutputStream GZIPOutput;
		ObjectOutputStream out;
		try {
			fileSolutions = new FileOutputStream("solution.zip");
			GZIPOutput = new GZIPOutputStream(fileSolutions);
			out = new ObjectOutputStream(GZIPOutput);
			out.writeObject(hashMaze);
			out.writeObject(hashSolution);
			out.writeObject(mazeFile);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			setChanged();
			notifyObservers(e.getMessage());
		}
		catch (IOException e) {
			setChanged();
			notifyObservers(e.getMessage());
		}		
	}
	/**
	 * loat data from zip
	 */
	@SuppressWarnings("unchecked")
	public void load() {
		FileInputStream fileSolutions;
		GZIPInputStream GZIPInput;
		ObjectInputStream in;
		try {
			fileSolutions = new FileInputStream("solution.zip");
			GZIPInput = new GZIPInputStream(fileSolutions);
			in = new ObjectInputStream(GZIPInput);
			hashMaze = (HashMap<String, Maze3d>) in.readObject();
			hashSolution = (HashMap<Maze3d, Solution<Position>>) in.readObject();
			mazeFile = (HashMap<Maze3d, String>) in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			setChanged();
			notifyObservers(e.getMessage());
		}
		catch (IOException e) {
			setChanged();
			notifyObservers(e.getMessage());
		}	
		catch (ClassNotFoundException e) {
		setChanged();
		notifyObservers(e.toString());
		}
	}

	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	public void setySize(int ySize) {
		this.ySize = ySize;
	}

	public void setzSize(int zSize) {
		this.zSize = zSize;
	}

	public void setAlgorithemForSolution(String algorithemForSolution) {
		this.algorithemForSolution = algorithemForSolution;
	}

	public void setAlgorithemForCreate(String algorithemForCreate) {
		this.algorithemForCreate = algorithemForCreate;
	}
	/**
	 * return the number of threads
	 * @return -int
	 */	
	public int getNumberOfThreads() {
		return numberOfThreads;
	}
	/**
	 * set the number of threads
	 * @param -int
	 */	
	public void setNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}
	/**
	 * get the name of the maze
	 * @return String name
	 */

	public String getName() {
		return name;
	}
	/**
	 * set the name of the maze
	 * @param String name
	 */

	public void setName(String name) {
		this.name = name;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
		
		setName(properties.getName());		
		setxSize(properties.getXSize());
		setySize(properties.getYSize());
		setzSize(properties.getZSize());
		setAlgorithemForCreate(properties.getAlgorithemForCreate());
		setAlgorithemForSolution(properties.getAlgorithemForSolution());
		setNumberOfThreads(properties.getNumberOfThreads());
		threadpool = Executors.newFixedThreadPool(numberOfThreads);
		
	}
}
