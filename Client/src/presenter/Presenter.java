package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import model.Model;
import view.View;
/**
 *Presenter  class - implements the Observer
 * manage the communication between model and view
 */

public class Presenter  implements Observer{
	
	View view;
	Model model;
	Properties properties;
	HashMap<String,Command> hash;
	
	/**
	 * MyController constructor - get Model and View
	 * initialize the model and view in the CommonController
	 * create the HashMap from String to Command
	 * @param model - get object from type Model
	 * @param view - get object from type View
	 */
	
	public Presenter(Model model, View view) {
		this.model = model;
		this.view = view;
		
		this.hash = new HashMap<String,Command>();
		hash.put("dir", new Dir(this));
		hash.put("display", new Display(this));
		hash.put("generate", new Generate3dMaze(this));
		hash.put("solve", new Solve(this));
		hash.put("save", new SaveMaze(this));
		hash.put("load", new LoadMaze(this));
		hash.put("maze", new MazeSize(this));
		hash.put("file", new FileSize(this));
		hash.put("exit", new Exit(this));
		
		view.setCommands(hash);
	}
	/**
	 * set message and sent it to the view
	 * @param -String message
	 */	
	public void setMessage(String message) {
		this.view.displayMessage(message);

	}
	/**
	 * set message and sent it to the view
	 * @param -Solution sol
	 */	
	public void setMessage(Solution<Position> sol) {
		this.view.displaySolution(sol);

	}
	/**
	 * get the model
	 * @return -Model model
	 */	
	public Model getModel(){ return model; }
	/**
	 * get the view
	 * @return -View view
	 */	
	public View getView(){ return view; }
	/**
	 * update that change as occur
	 */	
	@Override
	public void update(Observable o, Object arg) {
		if(o == view){
			if(((arg.getClass()).getName()).equals("[Ljava.lang.String;")){
				String[] command = (String[]) arg;
				Command com = hash.get(command[0]);	
				if(com != null)
					if(command.length == 1)
						com.doCommand("");
					else
						com.doCommand(command[1]);
				else
					view.displayMessage("Error! Command not exist"); 			}
			else if (((arg.getClass()).getName()).equals("java.lang.String")){
				String command = (String) arg;
				if(command.equals("exit")){
					Command com = hash.get(command);
					com.doCommand("");
				}	
				else if (command.equals("newConnection"))
					model.newConnection();
				else
					view.displayMessage("Error! Command not exist");	
			}
			else if (((arg.getClass()).getName()).equals("presenter.Properties")){
				Properties properties = (Properties) arg;
				model.setProperties(properties);
			}
				
			else
				view.displayMessage("Error! Object not recognized");
		}
		else if(o == model){	
			if(((arg.getClass()).getName()).equals("java.lang.String")){
				String s = (String) arg;
				String[] parm = s.split(" ");
				Command com = null;
				
				if(parm[0].equals("Done:")){
					switch(parm[1]){
					case "maze":
						com = hash.get("display");
						com.doCommand(parm[2]);
						break;
					case "solution":
						com = hash.get("display");
						com.doCommand("solution " + parm[3]);
						break;
					case "Invalid":
						switch(parm[2]){
						case "name":
							view.displayMessage("Invalid name");
							break;
						case "algorithm":
							view.displayMessage("Invalid algorithm");
							break;
						case "Command":
							view.displayMessage("Invalid Command");
							break;
						default:
							break;	
						}
						break;
					default:
						break;	
					}
				}
				else{
					view.displayMessage(s);
				}
			}
			else if(((arg.getClass()).getName()).equals("algorithms.mazeGenerators.Maze3d")){
				Maze3d maze = (Maze3d)arg;
				view.displayMaze(maze);
			}
			else if(((arg.getClass()).getName()).equals("algorithms.search.Solution<Position>")){
				@SuppressWarnings("unchecked")
				Solution<Position> maze = (Solution<Position>) arg;
				view.displaySolution(maze);
			}
				
		}
		
	}

}
