package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
/**
 * BasicWindow class - implements Runnable
 * create the basic window
 */
public abstract class BasicWindow implements Runnable {
	
	protected Display display;
	protected Shell shell;
	/**
	 * constructor for Basic Window
	 * @param title
	 * @param width
	 * @param height
	 */
	public BasicWindow(String title, int width, int height){
		display = new Display();
		shell = new Shell(display);
		shell.setSize(width, height);
		shell.setText(title);
	}
	/**
	 * Configure the window widgets
	 */
	public abstract void initWidgets();
	/**
	 * start the thread of the window
	 */
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
