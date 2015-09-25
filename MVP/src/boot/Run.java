package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.Presenter;
import view.CLI;
import view.MyView;
import view.UserInterface;

public class Run {

	public static void main(String[] args) {
		UserInterface ui = new CLI(new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out));
		MyModel model = new MyModel();
		MyView view = new MyView(ui);
		Presenter presenter = new Presenter(model,view);
		
		model.addObserver(presenter);
		view.addObserver(presenter);
		
		view.start();
	}

}
