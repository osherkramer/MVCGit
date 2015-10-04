package view;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;
import propertiesGUI.BasicWindow;

public class GUI extends BasicWindow implements UserInterface {
	View view;
	HashMap<String, Command> hashCommand;
	DisplayMaze3D mazeDisplay;
	int numberMaze;

	public GUI(String title, int width, int height) {
		super(title, width, height);
		numberMaze=0;
	}

	@Override
	public void initWidgets() {
		shell.setLayout(new GridLayout(3, false));

		Menu menuBar, fileMenu, gameMenu;
		MenuItem fileMenuHeader, gameMenuHeader, generateItem,
			solveItem, openPropertiesItem, exitItem;
		
		menuBar = new Menu(shell,SWT.BAR);
		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");
		
		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);
		
		openPropertiesItem = new MenuItem(fileMenu, SWT.PUSH);
		openPropertiesItem.setText("&Open Properties");
		
		exitItem = new MenuItem(fileMenu, SWT.PUSH);
		exitItem.setText("&Exit");
		
		gameMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		gameMenuHeader.setText("&Game");
		
		gameMenu = new Menu(shell, SWT.DROP_DOWN);
		gameMenuHeader.setMenu(gameMenu);
		
		
		generateItem = new MenuItem(gameMenu, SWT.PUSH);
		generateItem.setText("&Generate maze");
			
		solveItem = new MenuItem(gameMenu, SWT.PUSH);
		solveItem.setText("&Solve maze");
		
		shell.setMenuBar(menuBar);
				
		mazeDisplay = new DisplayMaze3D(shell, SWT.BORDER);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 7));
		view.notifyMessage(("generate 3d maze f13").split(" ",2));
		mazeDisplay.draw();

		Listener generateListenert = new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				view.notifyMessage("generate 3d maze f13".split(" ",2));
				mazeDisplay.redraw();
			}
		};
		
		Listener exitListener = new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				view.notifyMessage("exit");
				mazeDisplay.setRunning(false);
				shell.dispose();
			}
		};
		
		Listener propertiesListener = new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
		        fd.setText("Open Properties");
		        try {
					fd.setFilterPath(new java.io.File( "." ).getCanonicalPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        String[] filterExt = { "*.xml" };
		        fd.setFilterExtensions(filterExt);
		        String selected = fd.open();
		        if(selected != null){
		        	XMLDecoder d;
		    		Properties properties = new Properties();
		    		try {
		    			d = new XMLDecoder(new BufferedInputStream(new FileInputStream(selected)));
		    			properties = (Properties) d.readObject();
		    			d.close();
		    		} catch (FileNotFoundException e) {
		    			e.printStackTrace();
		    		}
		    		view.notifyMessage(properties);
		        }
		        	
			}
		};

		Listener solveListtener =new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				view.notifyMessage(("solve f13").split(" ", 2));			
			}
		};
		
		shell.addListener(SWT.Close, exitListener);
		exitItem.addListener(SWT.Selection, exitListener);
		generateItem.addListener(SWT.Selection, generateListenert);
		openPropertiesItem.addListener(SWT.Selection, propertiesListener);
		solveItem.addListener(SWT.Selection, solveListtener);
		shell.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent key) {
				switch(key.keyCode)
				{
				case SWT.ARROW_DOWN:
					mazeDisplay.moveBack();
					break;
				case SWT.ARROW_UP:
					mazeDisplay.moveForward();
					break;
				case SWT.ARROW_LEFT:
					mazeDisplay.moveLeft();
					break;
				case SWT.ARROW_RIGHT:
					mazeDisplay.moveRight();
					break;
				case SWT.PAGE_UP:
					mazeDisplay.moveUp();
					break;
				case SWT.PAGE_DOWN:
					mazeDisplay.moveDown();
					break;
				}	
			}
		});
		
				
		shell.pack();

	}

	@Override
	public void setView(View view) {
		this.view = view;
	}

	@Override
	public void setRunning(boolean running) {}

	@Override
	public void start() {
		run();
	}

	@Override
	public void displayMessage(String messege) {}
	
	public void displayMaze(Maze3d maze) {
		mazeDisplay.setMaze(maze);

	}

	@Override
	public void setCommands(HashMap<String, Command> hashCommand) {
		this.hashCommand = hashCommand;
		
	}
	public void displaySolution(Solution<Position> sol)
	{
		mazeDisplay.displaySolution(sol);		
	}

}
