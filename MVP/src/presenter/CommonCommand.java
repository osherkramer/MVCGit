package presenter;
/**
 * CommonCommand class - implements the Command interface
 * abstract class
 */

public abstract class CommonCommand implements Command {
	
	Presenter presenter;
	
	/**
	 * CommonCommand constructor
	 * @param controller - get the controller that it will be work with him
	 */
	public CommonCommand(Presenter presenter){
		this.presenter = presenter;
	}

	@Override
	public abstract void doCommand(String str);
}
