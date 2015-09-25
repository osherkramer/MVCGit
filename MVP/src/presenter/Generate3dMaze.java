package presenter;

/**
 * Generate3dMaze class - extends the CommonComand
 * manage the create of maze3d, ask from the model to create the maze
 */

public class Generate3dMaze extends CommonCommand {

	/**
	 * Generate3dMaze constructor 
	 * @param controller - get object of type Controller
	 */
	public Generate3dMaze(Presenter presenter){
		super(presenter);
	}

	@Override
	public void doCommand(String str) {
		String[] parm = str.split(" ");
		
		if(parm.length != 6)
			presenter.setMessage("Invalid Command");
		else{
			int x = 0,y = 0,z = 0;
			try{
				x = Integer.parseInt(parm[3]);
				y = Integer.parseInt(parm[4]);
				z = Integer.parseInt(parm[5]);
			}
			catch (NumberFormatException e){
				presenter.setMessage("Invalid Command");
			}
			
			presenter.getModel().generate(parm[2], x, y, z);
			
		}
	}

}
