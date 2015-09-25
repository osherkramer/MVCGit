package view;

import java.util.HashMap;

import presenter.Command;

public interface UserInterface {

	void setView(View view);
	void setRunning(boolean running);
	void start();
	void displayMessage(String message);
	void setCommands(HashMap<String, Command> hashCommand);
}
