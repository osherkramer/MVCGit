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
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import algorithms.IO.MyCompressorOutputStream;
import algorithms.IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import presenter.Properties;

/**
 * MyModel class extends CommonModel
 * manage the size of the model
 */


public class MyModel extends CommonModel {

	HashMap<Maze3d,String> mazeFile;
	int xSize;
	int ySize;
	int zSize;
	String name;
	Properties properties;
	static int number = 0;
	
	/**
	 * Default Constructor of MyModel
	 */
	public MyModel(String ip, int port, Properties properties) {
		super(ip, port);
		this.properties = properties;
		setProperties(properties);
		mazeFile = new HashMap<Maze3d,String>();
		load();
	}	

	@Override
	public void generate(String name, int x, int y, int z) {
		this.name = name;
		byte[] bb = new byte[36 + x*y*z];
		byte b;
		String pid = null;
		String hostName = null;
		try{
			pid = ((ManagementFactory.getRuntimeMXBean().getName()).split("@")[0]);
			hostName = InetAddress.getLocalHost().getHostName();
		}
		catch(Exception e){
			setChanged();
			notifyObservers(e.getMessage());
		}		
		
		outToServer.println("generate 3d maze " + name + ++number + "@" + pid + "@" + hostName +
							" " + x + " " + y + " " + z);
		outToServer.flush();
		try {
			while(!inFromServer.readLine().equals("doneMaze!"));
		} catch (IOException e) {
			setChanged();
			notifyObservers("Connection Lost");
			return;
		}

		outToServer.println("GetMaze");
		outToServer.flush();

		try {
			for(int i = 0; i < 36 + x*y*z; i++){
				b = (byte)inFromServer.read();
				bb[i] = b;
			}
		} catch (IOException e) {
			setChanged();
			notifyObservers("Connection Lost");
			return;
		}
		Maze3d maze = new Maze3d(bb);
		
		hashMaze.put(name, maze);
		
		setChanged();
		notifyObservers("Done: maze " +  name + " is ready");
	}
	
	public void generate() {
		byte[] bb = new byte[36 + xSize*ySize*zSize];
		byte b;
		String pid = null;
		String hostName = null;
		try{
			pid = ((ManagementFactory.getRuntimeMXBean().getName()).split("@")[0]);
			hostName = InetAddress.getLocalHost().getHostName();
		}
		catch(Exception e){
			setChanged();
			notifyObservers(e.getMessage());
		}
		
		outToServer.println("generate 3d maze " + this.name + ++number +"@" + pid + "@" + hostName
				+ " " + xSize + " " + ySize + " " + zSize);
		outToServer.flush();
	
		try {
			while(!inFromServer.readLine().equals("doneMaze!"));
		} catch (IOException e) {
			setChanged();
			notifyObservers("Connection Lost");
			return;
		}
		
		outToServer.println("GetMaze");
		outToServer.flush();
		try {
			for(int i = 0; i < 36 + xSize*ySize*zSize; i++){
				b = (byte)inFromServer.read();
				bb[i] = b;
			}
		} catch (IOException e) {
			setChanged();
			notifyObservers("Connection Lost");
			return;
		}

		Maze3d maze = new Maze3d(bb);
		
		hashMaze.put(name, maze);
		
		setChanged();
		notifyObservers("Done: maze " +  name + " is ready");
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
		if(!outToServer.checkError())
		{
		String[] parm=str.split(" ");
		if(parm.length < 1 || parm.length > 3){
			setChanged();
			notifyObservers("Done: Invalid Command");
			return;
		}
		String pid = null;
		String hostName = null;
		
		try{
			pid = ((ManagementFactory.getRuntimeMXBean().getName()).split("@")[0]);
			hostName = InetAddress.getLocalHost().getHostName();
		}
		catch(Exception e){
			setChanged();
			notifyObservers(e.getMessage());
		}
		
		String name = this.name;
		if(parm.length == 1 && parm[0].equals("")){
			name = this.name;
			outToServer.println("solve " + name + number + "@" + pid + "@" + hostName);
			outToServer.flush();
		}
		else if(parm.length == 1 && !parm[0].equals("")){
			name = parm[0];
			outToServer.println("solve " + parm[0] + number + "@" + pid + "@" + hostName);
			outToServer.flush();
		}
		else if(parm.length == 2 && (parm[1].equalsIgnoreCase("bfs") || parm[1].equalsIgnoreCase("ManhattanDistance")
				|| parm[1].equalsIgnoreCase("AirDistance"))){
			name = parm[0];
			outToServer.println("solve " + parm[0] + number + " " + parm[1]);
			outToServer.flush();
		}
		else if(parm.length == 3){
			name = this.name;
			outToServer.println("solve " + name + number + "@" + pid + "@" + hostName
								+ " " + parm[0] + " " + parm[1] + " " + parm[2]);
			outToServer.flush();
		}

		String line = null;
		try {
			while(!(line = inFromServer.readLine()).equals("doneSolve!") &&
					!line.equals("Error to create solution"));
		} catch (IOException e) {
			setChanged();
			notifyObservers("Connection Error");
			return;
		}
		if(line.equals("Error to create solution")){
			setChanged();
			notifyObservers("Error to solve");
			return;
		}
		outToServer.println("GetSolution");
		outToServer.flush();
		String stringSolution = null;

		try {
			stringSolution = inFromServer.readLine();
		} catch (IOException e) {
			setChanged();
			notifyObservers("Connection Error");
			return;
		}
		
		String[] positionsSolution = stringSolution.split(" ");
		Solution<Position> solution = new Solution<Position>();
		
		for(int i = 0; i < positionsSolution.length; i++){
			String[] positions = positionsSolution[i].split(",");
			int x=Integer.parseInt(positions[0].substring(1));
			int y=Integer.parseInt(positions[1]);
			int z=Integer.parseInt(positions[2].substring(0, positions[2].length()-1));
			solution.add(new State<Position>(new Position(x, y, z)));
		}

		hashSolution.put(hashMaze.get(name), solution);		
		setChanged();
		notifyObservers("Done: solution for " + name + " is ready");
	}
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
		outToServer.println("exit");
		outToServer.flush();
		try {
			theServer.close();
		} catch (IOException e) {
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
			fileSolutions = new FileOutputStream("ClientSolution.zip");
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void load() {
		FileInputStream fileSolutions;
		GZIPInputStream GZIPInput;
		ObjectInputStream in;
		try {
			fileSolutions = new FileInputStream("ClientSolution.zip");
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
	}

	@Override
	public void setxSize(int xSize) {
		this.xSize = xSize;
		
	}

	@Override
	public void setySize(int ySize) {
		this.ySize = ySize;
		
	}

	@Override
	public void setzSize(int zSize) {
		this.zSize = zSize;
		
	}
}
