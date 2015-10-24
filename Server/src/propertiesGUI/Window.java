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
import controller.Properties;
/**
 * Window class - extends BasicWindow
 * Configure the Window
 */
public class Window extends BasicWindow {
	Properties properties;
	int port;
	int numberOfThreads;
	String solve;
	String generate;
	
	/**
	 * constructor of Window
	 */
	public Window(String title, int width, int height) {
		super(title, width, height);
		properties = new Properties();
		numberOfThreads=properties.getNumberOfThreads();
		solve = properties.getAlgorithemForSolution();
		generate = properties.getAlgorithemForCreate();
	}

	@Override
	public void initWidgets() {		
		shell.setLayout(new GridLayout(2, false));
		
		Label numOfThreadsLabel = new Label(shell,SWT.NULL);
		numOfThreadsLabel.setText("Number of Threads:");
		numOfThreadsLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
				
		Text numOfThreadsText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		numOfThreadsText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label portNumber = new Label(shell,SWT.NULL);
		portNumber.setText("Port number:");
		portNumber.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
				
		Text portNumberText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		portNumberText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label generateAlgo = new Label(shell,SWT.NULL);
		generateAlgo.setText("Maze generator:");
		generateAlgo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		
		Combo generateAlgoButton = new Combo(shell, SWT.NONE);
		generateAlgoButton.setItems(new String[]{"My Maze generator","Simple Maze generator"});
		generateAlgoButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		generateAlgoButton.setText("My Maze generator");
		
		Label solveAlgo = new Label(shell,SWT.NULL);
		solveAlgo.setText("Solve algorithem:");
		solveAlgo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		
		Combo solveAlgoButton = new Combo(shell, SWT.NONE);
		solveAlgoButton.setItems(new String[]{"BFS","A* Manhattan Distance","A* Air Distance"});
		solveAlgoButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		solveAlgoButton.setText("A* Air Distance");
		
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
					boolean generateIsOk = true;
					boolean solveIsOk = true;
					boolean isThreadOk = true;
					boolean isOkPort = true;
					
					if(!numOfThreadsText.getText().equals(""))
						if(Integer.parseInt(numOfThreadsText.getText()) > 0)
							properties.setNumberOfThreads(Integer.parseInt(numOfThreadsText.getText()));
						else
							isThreadOk = false;
					else
						isOk = false;	
					
					if(!portNumberText.getText().equals(""))
						if(Integer.parseInt(portNumberText.getText()) > 0)
							properties.setPort(Integer.parseInt(portNumberText.getText()));
						else
							isOkPort = false;
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
						
					if(isOk && solveIsOk && generateIsOk && isThreadOk && isOkPort){
						XMLEncoder encoder;
						try {
							encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("PropertiesServer.xml")));
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
					else if(!isOkPort){
						DialogMessage dm = new DialogMessage(shell, SWT.CURSOR_SIZEE ,"Port < 1");
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
