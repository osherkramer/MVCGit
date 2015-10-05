package propertiesGUI;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import presenter.Properties;

public class Window extends BasicWindow {
	Properties properties;
	int x;
	int y;
	int z;
	int numberOfThreads;
	String ui;
	String solve;
	String generate;
	

	public Window(String title, int width, int height) {
		super(title, width, height);
		properties = new Properties();
		x = properties.getXSize();
		y = properties.getYSize();
		z = properties.getZSize();
		numberOfThreads=properties.getNumberOfThreads();
		ui = properties.getUi();
		solve = properties.getAlgorithemForSolution();
		generate = properties.getAlgorithemForCreate();
		numberOfThreads = properties.getNumberOfThreads();
	}

	@Override
	public void initWidgets() {		
		shell.setLayout(new GridLayout(2, false));
		
		Label mazeXSize = new Label(shell,SWT.NULL);
		mazeXSize.setText("Maze  X:");
		mazeXSize.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		
		Text mazeXText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		mazeXText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		    
		Label mazeYSize = new Label(shell,SWT.NULL);
		mazeYSize.setText("Maze  Y:");
		mazeYSize.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
			
		Text mazeYText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		mazeYText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    
			 
		Label mazeZSize = new Label(shell,SWT.NULL);
		mazeZSize.setText("Maze  Z:");
		mazeZSize.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
				
		Text mazeZText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		mazeZText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label numOfThreadsLabel = new Label(shell,SWT.NULL);
		numOfThreadsLabel.setText("Number of Threads:");
		numOfThreadsLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
				
		Text numOfThreadsText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		numOfThreadsText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label generateAlgo = new Label(shell,SWT.NULL);
		generateAlgo.setText("Maze generator:");
		generateAlgo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		
		Combo generateAlgoButton = new Combo(shell, SWT.NONE);
		generateAlgoButton.setItems(new String[]{"My Maze generator","Simple Maze generator"});
		generateAlgoButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		
		Label solveAlgo = new Label(shell,SWT.NULL);
		solveAlgo.setText("Solve algorithem:");
		solveAlgo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		
		Combo solveAlgoButton = new Combo(shell, SWT.NONE);
		solveAlgoButton.setItems(new String[]{"BFS","A* Manhattan Distance","A* Air Distance"});
		solveAlgoButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		
		Label uiLabel = new Label(shell,SWT.NULL);
		uiLabel.setText("User Interface:");
		uiLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		
		Combo uiButton = new Combo(shell, SWT.NONE);
		uiButton.setItems(new String[]{"GUI","CLI"});
		uiButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		
		Button saveButton=new Button(shell, SWT.PUSH);
		saveButton.setText("Save");
		saveButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Button cancelButton=new Button(shell, SWT.PUSH);
		cancelButton.setText("Exit");
		cancelButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Listener listener = new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				if(e.widget == saveButton){
					boolean isOk = true;
					boolean sizeIsOk = true;
					boolean generateIsOk = true;
					boolean uiIsOk = true;
					boolean solveIsOk = true;
					boolean isThreadOk = true;
					if(!mazeXText.getText().equals("")){
						if(Integer.parseInt(mazeXText.getText()) > 2)
							properties.setXSize(Integer.parseInt(mazeXText.getText()));
						else
							sizeIsOk = false;
					}	
					else
						isOk = false;
						
					if(!mazeYText.getText().equals(""))
						if (Integer.parseInt(mazeYText.getText()) > 2)
							properties.setYSize(Integer.parseInt(mazeYText.getText()));
						else
							sizeIsOk = false;
					else
						isOk = false;
					
					if(!mazeZText.getText().equals(""))
						if(Integer.parseInt(mazeZText.getText()) > 2)
							properties.setZSize(Integer.parseInt(mazeZText.getText()));
						else
							sizeIsOk = false;
					else
						isOk = false;
					
					if(!numOfThreadsText.getText().equals(""))
						if(Integer.parseInt(numOfThreadsText.getText()) > 0)
							properties.setNumberOfThreads(Integer.parseInt(numOfThreadsText.getText()));
						else
							isThreadOk = false;
					else
						isOk = false;
					
					if(!uiButton.getText().equals(""))
						if(uiButton.getText().equals("CLI") || uiButton.getText().equals("GUI"))
							properties.setUi(uiButton.getText());
						else
							uiIsOk = false;
					else
						isOk = false;
					
					if(!generateAlgoButton.getText().equals(""))
						if(generateAlgoButton.getText().equals("My Maze generator") 
								|| generateAlgoButton.getText().equals("Simple Maze generator"))
								properties.setAlgorithemForCreate(generateAlgoButton.getText());
						else
							generateIsOk = false;
					else
						isOk = false;
					
					if(!solveAlgoButton.getText().equals(""))
						if(solveAlgoButton.getText().equals("BFS") 
								|| solveAlgoButton.getText().equals("A* Manhattan Distance") 
								|| solveAlgoButton.getText().equals("A* Air Distance"))
							properties.setAlgorithemForSolution(solveAlgoButton.getText());
						else
							solveIsOk = false;
					else
						isOk = false;
					
					if(isOk && sizeIsOk && solveIsOk && generateIsOk && uiIsOk && isThreadOk){
						XMLEncoder encoder;
						try {
							encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Properties.xml")));
							encoder.writeObject(properties);
							encoder.close();
							
					
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						
						DialogMessage dm = new DialogMessage(shell, "Properties saved!");
						dm.open();
						shell.dispose();
						
						isOk = false;
					}
					else if(!sizeIsOk){
						DialogMessage dm = new DialogMessage(shell, SWT.CURSOR_SIZEE ,"X/Y/Z < 3");
						dm.open();
					}
					else if(!isThreadOk){
						DialogMessage dm = new DialogMessage(shell, SWT.CURSOR_SIZEE ,"Threads < 1");
						dm.open();
					}
					else if(!generateIsOk){
						DialogMessage dm = new DialogMessage(shell, SWT.CURSOR_SIZEE ,"Invalid Generate");
						dm.open();
					}
					else if(!solveIsOk){
						DialogMessage dm = new DialogMessage(shell, SWT.CURSOR_SIZEE ,"Invalid Solve");
						dm.open();
					}
					else if(!uiIsOk){
						DialogMessage dm = new DialogMessage(shell, SWT.CURSOR_SIZEE ,"Invalid UI");
						dm.open();
					}
					else{				
						DialogMessage dm = new DialogMessage(shell, "Enter all values");
						dm.open();

						isOk = true;
					}
						
				}
				else if(e.widget == cancelButton){
					shell.dispose();
				}
			}
		};
		
		saveButton.addListener(SWT.Selection, listener);
		cancelButton.addListener(SWT.Selection, listener);
			
		shell.pack();
	}

}
