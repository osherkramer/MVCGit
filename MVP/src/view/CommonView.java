package view;

import java.util.HashMap;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;

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
	public abstract void displaySolution(Solution<Position> sol);

	
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
	
	@Override
	public abstract void notifyMessage(Properties properties);
}
