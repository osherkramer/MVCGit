package propertiesGUI;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class BasicWindow implements Runnable {
	
	protected Display display;
	protected Shell shell;
	
	public BasicWindow(String title, int width, int height){
		display = new Display();
		shell = new Shell(display);
		shell.setSize(width,height);
		shell.setText(title);
		
	}
	
	public abstract void initWidgets();

	@Override
	public void run() {
		initWidgets();
		shell.open();
		
		while(!shell.isDisposed())
			if(!display.readAndDispatch())
				display.sleep();
		
		display.dispose();

	}

}
