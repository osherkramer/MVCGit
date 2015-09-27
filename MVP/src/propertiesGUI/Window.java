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
		mazeXSize.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		
		Text mazeXText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		mazeXText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		    
		Label mazeYSize = new Label(shell,SWT.NULL);
		mazeYSize.setText("Maze  Y:");
		mazeYSize.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
			
		Text mazeYText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		mazeYText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    
			 
		Label mazeZSize = new Label(shell,SWT.NULL);
		mazeZSize.setText("Maze  Z:");
		mazeZSize.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
				
		Text mazeZText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		mazeZText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Label numOfThreadsLabel = new Label(shell,SWT.NULL);
		numOfThreadsLabel.setText("Number of Threads:");
		numOfThreadsLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
				
		Text numOfThreadsText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		numOfThreadsText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Label generateAlgo = new Label(shell,SWT.NULL);
		generateAlgo.setText("Maze generator:");
		generateAlgo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		
		Combo generateAlgoButton = new Combo(shell, SWT.NONE);
		generateAlgoButton.setItems(new String[]{"My Maze generator","Simple Maze generator"});
		generateAlgoButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Label solveAlgo = new Label(shell,SWT.NULL);
		solveAlgo.setText("Solve algorithem:");
		solveAlgo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		
		Combo solveAlgoButton = new Combo(shell, SWT.NONE);
		solveAlgoButton.setItems(new String[]{"BFS","A* Manhattan Distance","A* Air Distance"});
		solveAlgoButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Label uiLabel = new Label(shell,SWT.NULL);
		uiLabel.setText("User Interface:");
		uiLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		
		Combo uiButton = new Combo(shell, SWT.NONE);
		uiButton.setItems(new String[]{"CLI","GUI"});
		uiButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Button saveButton=new Button(shell, SWT.PUSH);
		saveButton.setText("Save");
		saveButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		
		Button cancelButton=new Button(shell, SWT.PUSH);
		cancelButton.setText("Exit");
		cancelButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		
		Listener listener = new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				if(e.widget == saveButton){
					boolean isOk = true;
					if(!mazeXText.getText().equals(""))
						properties.setXSize(Integer.parseInt(mazeXText.getText()));
					else
						isOk = false;
						
					if(!mazeYText.getText().equals(""))
						properties.setYSize(Integer.parseInt(mazeYText.getText()));
					else
						isOk = false;
					
					if(!mazeZText.getText().equals(""))
						properties.setZSize(Integer.parseInt(mazeZText.getText()));
					else
						isOk = false;
					
					if(!numOfThreadsText.getText().equals(""))
					  properties.setNumberOfThreads(Integer.parseInt(numOfThreadsText.getText()));
					else
						isOk = false;
					
					if(!uiButton.getText().equals(""))
				     	properties.setUi(uiButton.getText());
					else
						isOk = false;
					
					if(!generateAlgoButton.getText().equals(""))
				     	properties.setAlgorithemForCreate(generateAlgoButton.getText());
					else
						isOk = false;
					
					if(!solveAlgoButton.getText().equals(""))
					     properties.setAlgorithemForSolution(solveAlgoButton.getText());
					else
						isOk = false;
					
					if(isOk){
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
						isOk = false;
					}
					else{				
						DialogMessage dm = new DialogMessage(shell, "Enter all values");
						dm.open();

						isOk = true;
					}
						
				}
				else if(e.widget == cancelButton){
					display.dispose();
				}
			}
		};
		
		saveButton.addListener(SWT.Selection, listener);
		cancelButton.addListener(SWT.Selection, listener);
			
		shell.pack();
	}

}
