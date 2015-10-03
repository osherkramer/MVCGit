package presenter;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

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
	
	public Presenter(Model model, View view, Properties properties) {
		this.model = model;
		this.view = view;
		this.properties = properties;
		
		model.setxSize(properties.getXSize());
		model.setySize(properties.getYSize());
		model.setzSize(properties.getZSize());
		model.setAlgorithemForCreate(properties.getAlgorithemForCreate());
		model.setAlgorithemForSolution(properties.getAlgorithemForSolution());
		
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

	public void setMessage(String message) {
		this.view.displayMessage(message);

	}
	
	public Model getModel(){ return model; }
	
	public View getView(){ return view; }

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
				else if(command[0].equals("setProperties"))
					setProperties(command[1]);
				else
					view.displayMessage("Error! Command not exist"); 			}
			else if (((arg.getClass()).getName()).equals("java.lang.String")){
				String command = (String) arg;
				if(command.equals("exit")){
					Command com = hash.get(command);
					com.doCommand("");
				}				
				else
					view.displayMessage("Error! Command not exist");	
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
				
		}
		
	}
	
	private void setProperties(String path){
		XMLDecoder d;
		properties = new Properties();
		
		try {
			d = new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));
			properties = (Properties) d.readObject();
			d.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		model.setxSize(properties.getXSize());
		model.setySize(properties.getYSize());
		model.setzSize(properties.getZSize());
		model.setAlgorithemForCreate(properties.getAlgorithemForCreate());
		model.setAlgorithemForSolution(properties.getAlgorithemForSolution());
	}

}
