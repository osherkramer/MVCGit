package view;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


public class DisplayMaze3D extends DisplayMaze {
	
	Maze3d maze;
	int characterX;
	int characterY;
	int characterZ;
	int exitX;
	int exitY;
	int exitZ;
	boolean running;
	Thread mainThread;

	public DisplayMaze3D(Composite parent, int style) {
		super(parent, style);
		running = true;
	}	
	
	public Maze3d getMaze() {
		return maze;
	}

	public void setMaze(Maze3d maze) {
		this.maze = maze;
		characterX = maze.getEnter().getX();
		characterY = maze.getEnter().getY();
		characterZ = maze.getEnter().getZ();
		
		exitX = maze.getExit().getX();
		exitY = maze.getExit().getY();
		exitZ = maze.getExit().getZ();
	}


	public void draw(){
		Image image = new Image(getDisplay(), "resources/wall.jpg");
		Image charachter = new Image(getDisplay(), "resources/Stewie_Griffin.jpg");
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
			        	  e.gc.drawImage(endGame, width/4, height/4);

				   else
					   for(int i=0;i<maze.getY();i++)
						      for(int j=0;j<maze.getZ();j++){
						          int x=j*w;
						          int y=i*h;
						          if(maze.haveSpace(characterX, i, j))
						              e.gc.fillRectangle(x,y,w,h);
						          if(maze.haveSpace(characterX, i, j) && maze.haveSpace(characterX + 1, i,j) && maze.haveSpace(characterX - 1, i,j))
						        	  e.gc.drawImage(upAndDown, x, y);
						          else if(maze.haveSpace(characterX, i, j) && maze.haveSpace(characterX + 1, i,j))
						        	  e.gc.drawImage(up, x, y);
						          else if(maze.haveSpace(characterX, i, j) && maze.haveSpace(characterX - 1, i,j))
						        	  e.gc.drawImage(down, x, y);
						          if(j == characterZ && i == characterY)
						        	  e.gc.drawImage(charachter, x, y);
						      }		
			}
				  
		});
	}
	
	private void moveCharacter(int x,int y, int z){
		if(characterX == exitX && characterZ == exitZ && characterY == exitY)
      	  return;
		
		if(x>=0 && x < maze.getX() && y>=0 && y<maze.getY() && z>=0 && z<maze.getZ() && maze.haveSpace(x, y, z)){
			characterX=x;
			characterY=y;
			characterZ=z;
			
				
			getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					redraw();
				}
			});
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
	
	public void displaySolution(Solution<Position> sol)
	{
		Thread mainThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
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
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		mainThread.start();
	}
	
	public void setRunning(boolean running){ 
		this.running = running;	
	}
}

