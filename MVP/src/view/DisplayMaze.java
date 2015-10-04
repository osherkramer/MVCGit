package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public abstract class DisplayMaze extends Canvas {
	
	int[][] mazeData;

	public DisplayMaze(Composite parent, int style) {
		super(parent, style);
	}
	
	public void setMazeData(int[][] mazeData){
		this.mazeData=mazeData;
	}

	public abstract void moveUp();

	public abstract  void moveDown();

	public abstract  void moveLeft();

	public  abstract void moveRight();
	
	public abstract  void moveForward();

	public  abstract void moveBack();

}
