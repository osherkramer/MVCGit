package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Controller;

/**
 * Maze3dClientHandler class - handler to the server in the clients of maze3d game
 */
public class Maze3dClientHandler implements ClientHandler {
	Controller controller;
	boolean running = true;

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		try{
			BufferedReader in=new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out=new PrintWriter(outToClient);
			String line;
			while(!(line=in.readLine()).equals("exit") && running){
				String[] com = line.split(" ",2);
				switch(com[0]){
				
				case "generate":
					Maze3d maze = controller.getMaze(com[1]);
					byte[] bb = maze.toByteArray();
					
					out.println("doneMaze!");
					out.flush();
					while(!(line=in.readLine()).equals("GetMaze"));
					for(byte b : bb){
						out.write((int)b);
						out.flush();
					}
					break;
					
				case "solve":
					Solution<Position> solution = controller.getSolution(com[1]);
					if(solution == null){
						out.println("Error to create solution");
						break;
					}
					out.println("doneSolve!");
					out.flush();
					while(!(line=in.readLine()).equals("GetSolution"));
					out.println(solution.toString());
					out.flush();
					break;
					
				default:
					break;
				}
			}
			in.close();
			out.close();
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}
}
