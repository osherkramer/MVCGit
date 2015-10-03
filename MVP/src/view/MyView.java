package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import presenter.Command;

/**
 * MyView - extands CommonView and manage the size of the client - the view
 */

public class MyView extends CommonView {
	
	Thread mainThread;
	
	public MyView(UserInterface ui) {
		super(ui);
		ui.setView(this);
	}

	@Override
	public void exit() {
		ui.setRunning(false);
	}
	
	@Override
	public void start() {
		ui.start();	
	}
	@Override
	public void displayMessage(String message) {
		ui.displayMessage(message);	
	}

	@Override
	public void notifyMessage(String[] str) {
		setChanged();
		notifyObservers(str);
	}

	@Override
	public void setCommands(HashMap<String, Command> hashCommand) {
		this.hashCommand = hashCommand;
		ui.setCommands(hashCommand);
	}

	@Override
	public void notifyMessage(String str) {
		setChanged();
		notifyObservers(str);
	}
	
	@Override
	public void displayMaze(Maze3d maze){
		ui.displayMaze(maze);
	}

}
