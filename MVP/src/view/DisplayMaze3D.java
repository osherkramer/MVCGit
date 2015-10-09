package view;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
/**
 * DisplayMaze3D calss - extends DisplayMaze
 *
 */

public class DisplayMaze3D extends DisplayMaze {
	
	Maze3d maze;
	int characterX;
	int characterY;
	int characterZ;
	int exitX;
	int exitY;
	int exitZ;
	boolean running;
	ExecutorService threadSolve;
	Thread solve;
	Thread run;
	 /**
     * DisplayMaze3D constructor
     */
	public DisplayMaze3D(Composite parent, int style) {
		super(parent, style);
		running = false;
		threadSolve = Executors.newFixedThreadPool(1);
	}	
	/**
     * get the maze
     * @return maze-the maze
     */
	public Maze3d getMaze() {
		return maze;
	}
	/**
     * set the maze
     * @param maze-the maze
     */
	public void setMaze(Maze3d maze) {
		this.maze = maze;
		characterX = maze.getEnter().getX();
		characterY = maze.getEnter().getY();
		characterZ = maze.getEnter().getZ();
		
		exitX = maze.getExit().getX();
		exitY = maze.getExit().getY();
		exitZ = maze.getExit().getZ();
	}

     /**
      * draw the maze 3d and the character
      */
	public void draw(){
		Image image = new Image(getDisplay(), "resources/wall.jpg");
		Image charachter = new Image(getDisplay(), "resources/Stewie_Griffin.png");
		Image endGame = new Image(getDisplay(), "resources/EndGame.jpg");
		Image up = new Image(getDisplay(), "resources/Up.jpg");
		Image down = new Image(getDisplay(), "resources/Down.jpg");
		Image upAndDown = new Image(getDisplay(), "resources/UpDown.jpg");
		setBackground(new Color(null, 192, 192, 192));
		
		setBackgroundImage(image);
		
    	addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				   e.gc.setForeground(new Color(null,255,255,255));
				   e.gc.setBackground(new Color(null,255,255,255));

				   int width=getSize().x;
				   int height=getSize().y;

				   int w=width/maze.getZ();
				   int h=height/maze.getY();
				   
				   if(characterX == exitX && characterZ == exitZ && characterY == exitY)
					   e.gc.drawImage(endGame, 0, 0, 280, 296, 0, 0, getSize().x, getSize().y);

				   else
					   for(int i=0;i<maze.getY();i++)
						      for(int j=0;j<maze.getZ();j++){
						          int x=j*w;
						          int y=i*h;
						          if(maze.haveSpace(characterX, i, j))
						              e.gc.fillRectangle(x,y,w,h);
						          if(maze.haveSpace(characterX, i, j) && maze.haveSpace(characterX + 1, i,j) && maze.haveSpace(characterX - 1, i,j))
						        	  e.gc.drawImage(upAndDown, 0, 0, 39,38 , x, y, w, h);
						          else if(maze.haveSpace(characterX, i, j) && maze.haveSpace(characterX + 1, i,j))
						        	  e.gc.drawImage(up, 0, 0, 38,37 , x, y, w, h);
						          else if(maze.haveSpace(characterX, i, j) && maze.haveSpace(characterX - 1, i,j))
						        	  e.gc.drawImage(down, 0, 0, 37,38 , x, y, w, h);
						          if(j == characterZ && i == characterY){
						        	  e.gc.drawImage(charachter, 0, 0, 242,273 , x, y, w, h);
						          }
						      }		
			}
				  
		});
	}
	/**
	 * move the character
	 * @param x
	 * @param y
	 * @param z
	 */
	private void moveCharacter(int x,int y, int z){
		if(characterX == exitX && characterZ == exitZ && characterY == exitY)
      	  return;
		
		if(x>=0 && x < maze.getX() && y>=0 && y<maze.getY() && z>=0 && z<maze.getZ() && maze.haveSpace(x, y, z)){
			characterX=x;
			characterY=y;
			characterZ=z;

			run=new Thread(new Runnable() {
				
				@Override
				public void run() {
					redraw();
				}
			});
			getDisplay().syncExec(run);
		
		}
	}

	@Override
	public void moveUp() {
		int x = characterX + 1;
		int y = characterY;
		int z = characterZ;
		
		moveCharacter(x, y, z);
	}

	@Override
	public void moveDown() {
		int x = characterX - 1;
		int y = characterY;
		int z = characterZ;
		
		moveCharacter(x, y, z);
	}

	@Override
	public void moveLeft() {
		int x = characterX;
		int y = characterY;
		int z = characterZ-1;
		
		moveCharacter(x, y, z);
	}

	@Override
	public void moveRight() {
		int x = characterX;
		int y = characterY;
		int z = characterZ + 1;
		
		moveCharacter(x, y, z);
	}

	@Override
	public void moveForward() {
		int x = characterX;
		int y = characterY - 1;
		int z = characterZ;
		
		moveCharacter(x, y, z);
	}

	@Override
	public void moveBack() {
		int x = characterX;
		int y = characterY+1;
		int z = characterZ;
		
		moveCharacter(x, y, z);
	}
	/**
	 * display the solution
	 * @param sol-the Solution for display
	 */
	public void displaySolution(Solution<Position> sol)
	{
		this.running = true;
		solve=new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(running)
				{
				String[] position=sol.toString().split(" ");
				int x,y,z;
				for(int i=position.length-1; running && i>=0; i--)
				{
					String[] numbers = position[i].split(",");
					x=Integer.parseInt(numbers[0].substring(1));
					y=Integer.parseInt(numbers[1]);
					z=Integer.parseInt(numbers[2].substring(0, numbers[2].length()-1));
					  moveCharacter(x,y,z);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {}
				}
				}
			}
		});
		threadSolve.execute(solve);
	}
	
	/**
	 * set the running
	 * @param running-the running
	 */
	public void setRunning(boolean running){ 
		
		if(!running)
		{
			this.running=running;
			if(run != null)
				while(run.isAlive());
			threadSolve.shutdown();
		}
	}
	/**
	 * set that new solve is occur
	 * @param running
	 */
	public void newSolve(boolean running){ 
		
		if(!running)
			this.running=running;
	}
	/**
	 * get the x of character
	 * @return characterX-the x
	 */
	public int getCharacterX() {
		return characterX;
	}
	/**
	 * get the y of character
	 * @return characterY-the y
	 */
	public int getCharacterY() {
		return characterY;
	}
	/**
	 * get the z of character
	 * @return characterZ-the z
	 */
	public int getCharacterZ() {
		return characterZ;
	}
	/**
	 * return if thread for solve is in progress
	 * @return boolean running
	 */
	public boolean isSolving() { return running; }
	
}

