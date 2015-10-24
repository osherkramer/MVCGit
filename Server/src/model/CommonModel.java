package model;

import java.util.HashMap;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Controller;
/**
 * CommonModel implements Model interface
 * abstract class of the model
 */

public abstract class CommonModel implements Model {
	
	HashMap<String, Maze3d> hashMaze;
	HashMap<Maze3d, Solution<Position>> hashSolution;
	/**
	 * constructor for common model
	 */
	public CommonModel() {
		hashMaze = new HashMap<String,Maze3d>();
		hashSolution = new HashMap<Maze3d, Solution<Position>>();
	}
	
	@Override
	public abstract Maze3d generate(String str);
	
	@Override
	public abstract Solution<Position> createSolution(String name);

	@Override
	public abstract void exit();
	
	@Override
	public abstract void setController(Controller controller);

	@Override
	public abstract void save();
	
	@Override
	public abstract void load();

	@Override
	public abstract boolean pause();

	@Override
	public abstract boolean resume();
	
}
