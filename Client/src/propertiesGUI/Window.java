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
/**
 * Window class - extends BasicWindow
 * Configure the Window
 */
public class Window extends BasicWindow {
	Properties properties;
	int x;
	int y;
	int z;
	int port;
	String name;
	String ui;
	
	/**
	 * constructor of Window
	 */
	public Window(String title, int width, int height) {
		super(title, width, height);
		properties = new Properties();
		x = properties.getXSize();
		y = properties.getYSize();
		z = properties.getZSize();
		name = properties.getName();
		port = properties.getPort();
		ui = properties.getUi();
	}

	@Override
	public void initWidgets() {		
		shell.setLayout(new GridLayout(2, false));
		
		Label ip = new Label(shell,SWT.NULL);
		ip.setText("IP Address:");
		ip.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		
		Text ipText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		ipText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label port = new Label(shell,SWT.NULL);
		port.setText("Port:");
		port.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		
		Text portText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		portText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label name = new Label(shell,SWT.NULL);
		name.setText("Maze name:");
		name.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		
		Text nameText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
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
		
		Label uiLabel = new Label(shell,SWT.NULL);
		uiLabel.setText("User Interface:");
		uiLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		
		Combo uiButton = new Combo(shell, SWT.NONE);
		uiButton.setItems(new String[]{"GUI","CLI"});
		uiButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		uiButton.setText("GUI");
		
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
					boolean uiIsOk = true;
					boolean isNumber = true;
					try{
						Integer.parseInt(mazeXText.getText());
						Integer.parseInt(mazeYText.getText());
						Integer.parseInt(mazeZText.getText());
					}
					catch (NumberFormatException e1){
						isNumber = false;
						DialogMessage dm = new DialogMessage(shell, "X/Y/Z only Num");
						dm.open();	
						return;
					}
					
					try{
						Integer.parseInt(portText.getText());
					}
					catch (NumberFormatException e1){
						isNumber = false;
						DialogMessage dm = new DialogMessage(shell, "Invalid port");
						dm.open();	
						return;
					}
					
					if(isNumber){
						if(!nameText.getText().equals(""))
							properties.setName(nameText.getText());
						else
							isOk = false;
						
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
						
						if(!nameText.getText().equals(""))
							properties.setName(nameText.getText());
						else
							isOk = false;
						
						if(!ipText.getText().equals(""))
							properties.setIp(ipText.getText());
						else
							isOk = false;
						
						if(!portText.getText().equals(""))
							properties.setPort(Integer.parseInt(portText.getText()));
						else
							isOk = false;
						
						if(!nameText.getText().equals(""))
							properties.setName(nameText.getText());
						else
							isOk = false;
						
						if(!uiButton.getText().equals(""))
							if(uiButton.getText().equals("CLI") || uiButton.getText().equals("GUI"))
								properties.setUi(uiButton.getText());
							else
								uiIsOk = false;
						else
							isOk = false;
						
						if(isOk && sizeIsOk && uiIsOk){
							XMLEncoder encoder;
							try {
								encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("PropertiesClient.xml")));
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
