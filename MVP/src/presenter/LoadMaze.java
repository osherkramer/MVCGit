package presenter;

/**
 * LoadMaze class - extends the CommonComand
 * manage the load of maze3d from file, ask from the model to load the maze
 */

public class LoadMaze extends CommonCommand{
	/**
	 * LoadMaze constructor
	 * @param controller - get the controller to work with him.
	 */
	public LoadMaze(Presenter presenter){
		super(presenter);
	}

	@Override
	public void doCommand(String str) {
		presenter.getModel().loadMaze(str);
	}
}
