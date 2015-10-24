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
import view.GUI;
import view.MyView;
import view.UserInterface;

public class Run {

	public static void main(String[] args) {
		XMLDecoder d;
		Properties properties = new Properties();
		UserInterface ui = null;
		
		try {
			d = new XMLDecoder(new BufferedInputStream(new FileInputStream("PropertiesClient.xml")));
			properties = (Properties) d.readObject();
			d.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		if(properties.getUi().equals("CLI"))
			ui = new CLI(new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out));
		else if(properties.getUi().equals("GUI"))
			ui = new GUI("Maze 3D GAME", 1200, 700);
		
		MyModel model = new MyModel("127.0.0.1", 5400, properties);
		MyView view = new MyView(ui);
		Presenter presenter = new Presenter(model,view);
		
		model.addObserver(presenter);
		view.addObserver(presenter);

		
		view.start();
	}

}
