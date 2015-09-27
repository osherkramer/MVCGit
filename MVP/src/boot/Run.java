package boot;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import view.CLI;
import view.MyView;
import view.UserInterface;

public class Run {

	public static void main(String[] args) {
		XMLDecoder d;
		Properties properties = new Properties();
		try {
			d = new XMLDecoder(new BufferedInputStream(new FileInputStream("Properties.xml")));
			properties = (Properties) d.readObject();
			d.close();
		} catch (FileNotFoundException e) {}
		UserInterface ui = new CLI(new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out));
		MyModel model = new MyModel(properties.getNumberOfThreads());
		MyView view = new MyView(ui);
		Presenter presenter = new Presenter(model,view,properties);
		
		model.addObserver(presenter);
		view.addObserver(presenter);
		
		view.start();
	}

}
