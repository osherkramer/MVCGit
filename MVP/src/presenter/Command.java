package presenter;
/**
 * Command interface - set the functional of the commands
 */
public interface Command {
	/**
	 * manage the run of the command, ask from the model to do the specific command
	 * @param str - get parameters of the command
	 */
	void doCommand(String str);
}
