package presenter;

import java.io.Serializable;

public class Properties implements Serializable {
	
	private static final long serialVersionUID = 42L;
	int xSize;
	int ySize;
	int zSize;
	int numberOfThreads;
	String algorithemForSolution;
	String algorithemForCreate;
	String ui;
	

	public Properties() {
		this.xSize = 7;
		this.ySize = 7;
		this.zSize = 7;
		this.numberOfThreads = 10;
		this.algorithemForSolution = "A* Air Distance";
		this.algorithemForCreate = "My Maze generator";
		this.ui = "GUI";
	}
	
	public String getAlgorithemForCreate() {
		return algorithemForCreate;
	}



	public void setAlgorithemForCreate(String algorithemForCreate) {
		this.algorithemForCreate = algorithemForCreate;
	}



	public String getUi() {
		return ui;
	}



	public void setUi(String ui) {
		this.ui = ui;
	}



	public int getXSize() {
		return xSize;
	}


	public void setXSize(int xSize) {
		this.xSize = xSize;
	}


	public int getYSize() {
		return ySize;
	}


	public void setYSize(int ySize) {
		this.ySize = ySize;
	}


	public int getZSize() {
		return zSize;
	}


	public void setZSize(int zSize) {
		this.zSize = zSize;
	}


	public int getNumberOfThreads() {
		return numberOfThreads;
	}


	public void setNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}


	public String getAlgorithemForSolution() {
		return algorithemForSolution;
	}


	public void setAlgorithemForSolution(String algorithemForSolution) {
		this.algorithemForSolution = algorithemForSolution;
	}
	
	
	

}
