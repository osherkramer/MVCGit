package view;

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import algorithms.mazeGenerators.Maze3d;
import presenter.Command;
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
		MenuItem fileMenuHeader, gameMenuHeader, saveItem, loadItem, generateItem,
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
		
		saveItem = new MenuItem(gameMenu, SWT.PUSH);
		saveItem.setText("&Save maze");
		
		loadItem = new MenuItem(gameMenu, SWT.PUSH);
		loadItem.setText("&Load maze");
		
		shell.setMenuBar(menuBar);
				
		mazeDisplay = new DisplayMaze3D(shell, SWT.BORDER);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 7));
		view.notifyMessage(("generate 3d maze maze"+numberMaze).split(" ",2));
		mazeDisplay.draw();
		
		
		Label label = new Label(shell, SWT.ABORT);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
				
		Button forwardButton = new Button(shell,SWT.PUSH);
		forwardButton.setImage(new Image(display,"resources/Forward.jpg"));
		forwardButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,false,false,2,1));
	
		Button leftButton = new Button(shell,SWT.PUSH);
		leftButton.setImage(new Image(display,"resources/Left.jpg"));
		leftButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,false,false,1,1));
		
		Button rightButton = new Button(shell,SWT.PUSH);	
		rightButton.setImage(new Image(display,"resources/Right.jpg"));
		rightButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,false,false,1,1));
		
		Button backwardButton = new Button(shell,SWT.PUSH);
		backwardButton.setImage(new Image(display,"resources/Back.jpg"));
		backwardButton.setLayoutData(new GridData(SWT.CENTER, SWT.UP,false,false,2,1));
		
		Button upButton = new Button(shell,SWT.PUSH);
		upButton.setText("UP");
		upButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,false,false,1,1));
		
		Button downButton = new Button(shell,SWT.PUSH);
		downButton.setText("DOWN");
		downButton.setLayoutData(new GridData(SWT.DOWN, SWT.DOWN,false,false,1,1));
		
		Listener generateListenert = new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				view.notifyMessage("generate 3d maze osh".split(" ",2));
				mazeDisplay.redraw();
			}
		};
		
		Listener moveListener = new Listener() {
			
			@Override
			public void handleEvent(Event e)
			{
				if(e.widget==forwardButton || e.keyCode==38)
					mazeDisplay.moveForward();
				if(e.widget==leftButton|| e.keyCode==37)
					mazeDisplay.moveLeft();
				if(e.widget==rightButton|| e.keyCode==39)
					mazeDisplay.moveRight();
				if(e.widget==upButton|| e.keyCode==33)
					mazeDisplay.moveUp();
				if(e.widget==downButton|| e.keyCode==34)
					mazeDisplay.moveDown();
				if(e.widget==backwardButton|| e.keyCode==40)
					mazeDisplay.moveBack();
			}
		};
		
		Listener exitListener = new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				view.notifyMessage("exit");
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
		        
		        view.notifyMessage(("setProperties " + selected).split(" ", 2));
			}
		};
		
		shell.addListener(SWT.Close, exitListener);
		exitItem.addListener(SWT.Selection, exitListener);
		generateItem.addListener(SWT.Selection, generateListenert);
		backwardButton.addListener(SWT.Selection, moveListener);
		leftButton.addListener(SWT.Selection, moveListener);
		rightButton.addListener(SWT.Selection, moveListener);
		forwardButton.addListener(SWT.Selection, moveListener);
		upButton.addListener(SWT.Selection, moveListener);
		downButton.addListener(SWT.Selection, moveListener);
		openPropertiesItem.addListener(SWT.Selection, propertiesListener);
		
				
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

}
