package propertiesGUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

public class MessegeWindow extends Widget {
	Shell dialog;
	Label label;
	Button okButton;
	

	public MessegeWindow(Widget parent, int style) {
		super(parent, style);
		
		dialog = new Shell();
		dialog.setText("Messege");
		dialog.setSize(150, 100);
		    
		label = new Label(dialog, SWT.NONE);
		label.setText("Enter all values");
		label.setBounds(35, 5, 100, 20);

		okButton = new Button(dialog, SWT.PUSH);
		okButton.setBounds(20, 35, 40, 25);
		okButton.setText("OK");
		okButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		
		okButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				dialog.close();
				
			}
		});
	}

}
