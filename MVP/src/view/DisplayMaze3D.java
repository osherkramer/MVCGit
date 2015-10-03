package view;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;


public class DisplayMaze3D extends DisplayMaze {
	
	Maze3d maze;
	int characterX;
	int characterY;
	int characterZ;
	int exitX;
	int exitY;
	int exitZ;

	public DisplayMaze3D(Composite parent, int style) {
		super(parent, style);
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
		setBackground(new Color(null, 192, 192, 192));
		
    	addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				   e.gc.setForeground(new Color(null,0,0,0));
				   e.gc.setBackground(new Color(null,0,0,0));
				   
				   Pattern pattern = new Pattern(getDisplay(), image);

				    e.gc.setBackgroundPattern(pattern);

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
						          if(!maze.haveSpace(characterX, i, j))
						              e.gc.fillRectangle(x,y,w,h);
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
	public void setCharacterPosition(int row, int col) {
		// TODO Auto-generated method stub
		
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
	
	
}
