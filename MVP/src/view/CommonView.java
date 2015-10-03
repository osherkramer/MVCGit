package view;

import java.util.HashMap;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import presenter.Command;

/**
 * CommonView - implements View
 * abstract class of the view size
 */

public abstract class CommonView extends Observable implements View {
	UserInterface ui;
	HashMap<String,Command> hashCommand;	
	
	public CommonView(UserInterface ui) {
		this.ui = ui;
	}
	
	@Override
	public abstract void exit();
	
	@Override
	public abstract void start();

	@Override
	public abstract void displayMessage(String message);
	
	@Override
	public abstract void setCommands(HashMap<String,Command> hashCommand);
	
	@Override
	public abstract void notifyMessage(String[] str);
	
	@Override
	public abstract void notifyMessage(String str);
	
	@Override
	public abstract void displayMaze(Maze3d maze);
}
