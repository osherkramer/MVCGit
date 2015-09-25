package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

import presenter.Command;

public abstract class CommonUserInterface implements UserInterface {
	
	BufferedReader in;
	PrintWriter out;
	View view;
	boolean running;
	HashMap<String, Command> hashCommand;
	
	
	public CommonUserInterface(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
		this.running = true;
	}
	
	@Override
	public void setView(View view){ this.view = view; }

	@Override
	public void setRunning(boolean running){ this.running = running; }

	@Override
	public abstract void start();
	
	@Override
	public abstract void setCommands(HashMap<String, Command> hashCommand);

}
