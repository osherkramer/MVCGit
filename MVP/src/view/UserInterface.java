package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import presenter.Command;

public interface UserInterface {

	void setView(View view);
	void setRunning(boolean running);
	void start();
	void displayMessage(String message);
	void setCommands(HashMap<String, Command> hashCommand);
	void displayMaze(Maze3d maze);
}
