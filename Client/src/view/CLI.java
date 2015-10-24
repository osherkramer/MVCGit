package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
/**
 * CLI Class - manage the Command Line Interface for the client
 */

public class CLI implements UserInterface{
	
	BufferedReader in;
	PrintWriter out;
	View view;
	boolean running;
	HashMap<String, Command> hashCommand;
	
	/**
	 * CLI Constructor - initialize the CLI object
	 * @param in - get BufferedReader
	 * @param out - get PrintWriter
	 */
	public CLI(BufferedReader in, PrintWriter out){
		this.in = in;
		this.out = out;
	}
	
	/**
	 * Start the thread that manage the cli and running it
	 */
	public void start(){
		
		new Thread(new Runnable(){
			public void run(){
				String line;
				try{	
					while(!(line = in.readLine().intern()).equals("exit")){
						String[] command = line.split(" ", 2);
						Command com = hashCommand.get(command[0]);	
						if(com != null)
							view.notifyMessage(command);
						else{
							out.println("Invalid command");
							out.flush();
						}
					}
					view.notifyMessage(line);
				}
				catch (IOException e){	
					out.println(e.getMessage());
					out.flush();
				}
			}
		}).start();
	}
	
	public void displayMessage(String message){
		out.println(message);
		out.flush();
	}
	
	public void displayMaze(Maze3d maze){
		out.println(maze.toString());
		out.flush();
	}
	
	public void setCommands(HashMap<String, Command> hashCommand){
		this.hashCommand = hashCommand;
	}
	
	@Override
	public void setView(View view){ this.view = view; }

	@Override
	public void setRunning(boolean running){ this.running = running; }
	@Override
	public void displaySolution(Solution<Position> sol)
	{
		out.println(sol.toString());
		out.flush();
	}
}
