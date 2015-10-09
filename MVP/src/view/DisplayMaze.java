package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
/**
 * DisplayMaze - extends Canvas
 */
public abstract class DisplayMaze extends Canvas {
	
	int[][] mazeData;
	 /**
     * DisplayMaze constructor
     */
	public DisplayMaze(Composite parent, int style) {
		super(parent, style);
	}
	/**
	 * set the maze
	 * @param int[][] mazeData
	 */
	public void setMazeData(int[][] mazeData){
		this.mazeData=mazeData;
	}
	/**
	 * move up
	 */
	public abstract void moveUp();
	/**
	 * move down
	 */
	public abstract  void moveDown();
	/**
	 * move left
	 */
	public abstract  void moveLeft();
	/**
	 * move right
	 */
	public  abstract void moveRight();
	/**
	 * move forward
	 */
	public abstract  void moveForward();
	/**
	 * move back
	 */
	public  abstract void moveBack();

}
