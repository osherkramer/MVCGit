package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import presenter.Command;
/**
 * CLI Class - manage the Command Line Interface for the client
 */

public class CLI extends CommonUserInterface{
	
	
	/**
	 * CLI Constructor - initialize the CLI object
	 * @param in - get BufferedReader
	 * @param out - get PrintWriter
	 */
	public CLI(BufferedReader in, PrintWriter out){
		super(in, out);
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
	
	public void setCommands(HashMap<String, Command> hashCommand){
		this.hashCommand = hashCommand;
	}
}
